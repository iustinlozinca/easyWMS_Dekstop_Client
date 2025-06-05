package org.example.input;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class XlsxReader {

    /***
     * citeste primul sheet dintr-un fisier .xlsx si scoate o lista de randuri,
     * fiecare rand este o lista de siruri
     *
     * @param path calea catre un fisier XLSX
     * @return o lista cu valorile din celule
     */

    public static List<List<String>> read(Path path){
        DataFormatter fmt = new DataFormatter();
        List<List<String>> rezultat = new ArrayList<>();

        try(InputStream is = new FileInputStream(path.toFile());
            Workbook wb = new XSSFWorkbook(is)){

            Sheet sheet = wb.getSheetAt(0);
            for (Row row : sheet) {
                List<String > rowData = new ArrayList<>();
                for (Cell cell : row) {
                    switch (cell.getCellType()) {

                        case STRING:
                            rowData.add(cell.getStringCellValue());
                            break;

                        case NUMERIC:
                            rowData.add(fmt.formatCellValue(cell));
                            break;

                        case BOOLEAN:
                            rowData.add(String.valueOf(cell.getBooleanCellValue()));
                            break;

                        case BLANK:
                            rowData.add("@BLANK");
                            break;

                        case ERROR:
                            rowData.add("#ERROR");
                            break;

                        case FORMULA:
                            switch (cell.getCachedFormulaResultType()) {
                                case STRING:
                                    rowData.add(cell.getStringCellValue());
                                    break;

                                case NUMERIC:
                                    rowData.add(fmt.formatCellValue(cell));
                                    break;

                                case BOOLEAN:
                                    rowData.add(String.valueOf(cell.getBooleanCellValue()));
                                    break;

                                case BLANK:
                                    rowData.add("@BLANK");
                                    break;

                                case ERROR:
                                    rowData.add("#ERROR");
                                    break;

                                default:
                                    System.err.println("XlsxReader: rezultat FORMULA necunoscut: "
                                            + cell.getCachedFormulaResultType());
                                    rowData.add("");
                                    break;
                            }
                            break;

                        default:
                            System.err.println("XlsxReader: tip celula necunoscut: " + cell.getCellType());
                            rowData.add("");
                            break;
                    }
                }
                rezultat.add(rowData);
            }

        } catch (Exception e) {
            System.err.println("XlsxReader: eroare la citirea .xlsx");
            e.printStackTrace();
        }
        return rezultat;
    }
}