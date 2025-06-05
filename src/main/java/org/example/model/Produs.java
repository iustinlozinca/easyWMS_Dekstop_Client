package org.example.model;

public class Produs implements Comparable<Produs> {
    /// variabile
    private Integer id;
    private String ean;
    private String nume;
    private String codIntern;
    private Integer unitate;

    private Integer stoc;
    /// //////final variabile




    /// constructor
    public Produs() {
    }

    public Produs(String ean, String nume, String codIntern, Integer unitate, Integer stoc) {
        this.ean = ean;
        this.nume = nume;
        this.codIntern = codIntern;
        this.unitate = unitate;
        this.stoc = stoc;
    }
    /// ////////////////////// final constructor




    ///geteri si seteri
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public String getEan(){return ean;}
    public void setEan(String ean){this.ean = ean;}

    public String getNume(){return nume;}
    public void setNume(String nume){this.nume = nume;}

    public String getCodIntern(){return codIntern;}
    public void setCodIntern(String codIntern){this.codIntern = codIntern;}

    public Integer getUnitate(){return unitate;}
    public void setUnitate(Integer unitate){this.unitate=unitate;}

    public Integer getStoc(){return stoc;}
    public void setStoc(Integer stoc){this.stoc = stoc;}
    /// ////////////////final geteri si seteri

    /// comparare pentru sortare
    @Override
    public int compareTo(Produs other){
        return this.ean.compareTo(other.ean);
    }


}