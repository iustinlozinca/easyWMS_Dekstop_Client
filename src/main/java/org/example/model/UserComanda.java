package org.example.model;

public class UserComanda {
    private Integer userId;
    private Integer comandaId;

    public UserComanda() {}

    public UserComanda(Integer userId, Integer comandaId) {
        this.userId = userId;
        this.comandaId = comandaId;
    }

    public Integer getUserId() {return userId;}
    public void setUserId(Integer userId) {this.userId = userId;}

    public Integer getComandaId() {return comandaId;}
    public void setComandaId(Integer comandaId) {this.comandaId = comandaId;}
}
