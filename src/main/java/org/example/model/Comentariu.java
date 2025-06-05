package org.example.model;

import java.time.LocalDateTime;

public class Comentariu {
    private Integer id;
    private Integer userId;
    private Integer comandaId; // poate fi null
    private LocalDateTime data;
    private String comentariu;

    public Comentariu() {
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public Integer getUserId() {return userId;}
    public void setUserId(Integer userId) {this.userId = userId;}

    public Integer getComandaId() {return comandaId;}
    public void setComandaId(Integer comandaId) {this.comandaId = comandaId;}

    public LocalDateTime getData() {return data;}
    public void setData(LocalDateTime data) {this.data = data;}

    public String getComentariu() {return comentariu;}
    public void setComentariu(String comentariu) {this.comentariu = comentariu;}
}
