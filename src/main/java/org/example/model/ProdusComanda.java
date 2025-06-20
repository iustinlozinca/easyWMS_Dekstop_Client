package org.example.model;


public class ProdusComanda extends Produs{
    /// variabile
    private Integer id;
    private Integer produsId;
    private Integer comandaId;
    private Integer cantitateComandata;
    private Integer cantitateScanata;
    /// //////final variabile



    /// constructor
    public ProdusComanda() {
        super();
    }

    public ProdusComanda(String ean, String nume, String codIntern, Integer unitate,Integer stoc, Integer cantitateComandata){
        super(ean,nume,codIntern,unitate,stoc);
        this.cantitateComandata = cantitateComandata;
        this.cantitateScanata = 0;
    }
    /// /////////final constructor



    /// /// geter si seter
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public Integer getProdusId() {return produsId;}
    public void setProdusId(Integer produsId) {this.produsId = produsId;}

    public Integer getComandaId() {return comandaId;}
    public void setComandaId(Integer comandaId) {this.comandaId = comandaId;}

    public int getCantitateComandata() { return cantitateComandata; }
    public void setCantitateComandata(int cantitateComandata) {
        this.cantitateComandata = cantitateComandata;
    }

    public int getCantitateScanata() { return cantitateScanata; }
    public void setCantitateScanata(int cantitateScanata) {
        this.cantitateScanata = cantitateScanata;
    }
    /// //////final geter si seter


    /// suprascriem toString
    @Override
    public String toString(){
        return ""+getNume()+"  "+getEan()+"   "+getCodIntern()+"  cantitate: "+ cantitateComandata;
    }

}
