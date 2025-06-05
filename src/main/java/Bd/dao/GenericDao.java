package Bd.dao;

import Bd.util.AuditService;
import Bd.util.DbUtil;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GenericDao<T> {

    protected Connection getConnection() {
        return DbUtil.getConnection();
    }

    private List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            for (Field f : clazz.getDeclaredFields()) {
                fields.add(f);
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    private List<Field> getPersistedFields(Class<?> clazz, boolean includeId) {
        List<Field> result = new ArrayList<>();
        for (Field f : getAllFields(clazz)) {
            if (java.lang.reflect.Modifier.isStatic(f.getModifiers())) continue;
            if (java.util.List.class.isAssignableFrom(f.getType())) continue;
            if (!includeId && f.getName().equalsIgnoreCase("id")) continue;
            f.setAccessible(true);
            result.add(f);
        }
        return result;
    }

    public T create(T entity) {
        Class<?> clazz = entity.getClass();
        String table = clazz.getSimpleName();
        List<Field> fields = getPersistedFields(clazz, false);

        StringBuilder sb = new StringBuilder("INSERT INTO ").append(table).append(" (");
        for (int i = 0; i < fields.size(); i++) {
            sb.append(fields.get(i).getName());
            if (i < fields.size() - 1) sb.append(",");
        }
        sb.append(") VALUES (");
        sb.append("?,".repeat(fields.size()));
        if (fields.size() > 0) sb.setLength(sb.length() - 1); // remove last comma
        sb.append(")");

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < fields.size(); i++) {
                setParam(ps, i + 1, fields.get(i), entity);
            }
            ps.executeUpdate();
            AuditService.getInstance().log("create_" + table);
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    Field idField = clazz.getDeclaredField("id");
                    idField.setAccessible(true);
                    idField.set(entity, rs.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    public T update(T entity) {
        Class<?> clazz = entity.getClass();
        String table = clazz.getSimpleName();
        List<Field> fields = getPersistedFields(clazz, false);

        StringBuilder sb = new StringBuilder("UPDATE ").append(table).append(" SET ");
        for (Field f : fields) {
            sb.append(f.getName()).append("=?,");
        }
        sb.setLength(sb.length() - 1);
        sb.append(" WHERE id=?");

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            int idx = 1;
            for (Field f : fields) {
                setParam(ps, idx++, f, entity);
            }
            Field idField = clazz.getDeclaredField("id");
            idField.setAccessible(true);
            Object idVal = idField.get(entity);
            ps.setObject(idx, idVal);
            ps.executeUpdate();
            AuditService.getInstance().log("update_" + table);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    public void deleteById(Class<T> clazz, Integer id) {
        String table = clazz.getSimpleName();
        String sql = "DELETE FROM " + table + " WHERE id=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            AuditService.getInstance().log("delete_" + table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public T findById(Class<T> clazz, Integer id) {
        String table = clazz.getSimpleName();
        String sql = "SELECT * FROM " + table + " WHERE id=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    AuditService.getInstance().log("find_" + table);
                    return mapResultSet(clazz, rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<T> findAll(Class<T> clazz) {
        String table = clazz.getSimpleName();
        String sql = "SELECT * FROM " + table;
        List<T> list = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSet(clazz, rs));
            }
            AuditService.getInstance().log("find_" + table);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private T mapResultSet(Class<T> clazz, ResultSet rs) throws Exception {
        T obj = clazz.getDeclaredConstructor().newInstance();
        for (Field f : getPersistedFields(clazz, true)) {
            Object value = getColumnValue(rs, f);
            if (value != null || !f.getType().isPrimitive()) {
                f.setAccessible(true);
                f.set(obj, value);
            }
        }
        return obj;
    }

    private Object getColumnValue(ResultSet rs, Field f) throws SQLException {
        String name = f.getName();
        Class<?> type = f.getType();
        if (type.equals(LocalDateTime.class)) {
            Timestamp ts = rs.getTimestamp(name);
            return ts != null ? ts.toLocalDateTime() : null;
        } else if (type.equals(Integer.class)) {
            int val = rs.getInt(name);
            return rs.wasNull() ? null : val;
        } else if (type.equals(String.class)) {
            return rs.getString(name);
        } else if (type.equals(java.math.BigDecimal.class)) {
            return rs.getBigDecimal(name);
        } else {
            return rs.getObject(name);
        }
    }

    private void setParam(PreparedStatement ps, int index, Field f, Object entity) throws IllegalAccessException, SQLException {
        Object value = f.get(entity);
        if (value instanceof LocalDateTime) {
            ps.setTimestamp(index, Timestamp.valueOf((LocalDateTime) value));
        } else if (value instanceof java.math.BigDecimal) {
            ps.setBigDecimal(index, (java.math.BigDecimal) value);
        } else {
            ps.setObject(index, value);
        }
    }
}
