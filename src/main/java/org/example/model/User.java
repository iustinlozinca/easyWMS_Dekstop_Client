package org.example.model;


import java.util.List;

public class User {
    private Integer id;
    private List<Comanda> comenzi;
    private String nume;
    private String tipulDeUtilizator;

    public User() {
    }

    public User(String nume, String tipulDeUtilizator) {
        this.nume = nume;
        this.tipulDeUtilizator = tipulDeUtilizator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Comanda> getComenzi() {
        return comenzi;
    }

    public void setComenzi(List<Comanda> comenzi) {
        this.comenzi = comenzi;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getTipulDeUtilizator() {
        return tipulDeUtilizator;
    }

    public void setTipulDeUtilizator(String tipulDeUtilizator) {
        this.tipulDeUtilizator = tipulDeUtilizator;
    }
}
