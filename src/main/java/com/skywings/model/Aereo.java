package com.skywings.model;

public class Aereo {
    private Long id;
    private String modello;
    private String produttore;
    private int capacitaEconomy;
    private int capacitaBusiness;

    public Aereo() {}

    public Aereo(Long id, String modello, String produttore, int capacitaEconomy, int capacitaBusiness) {
        this.id = id;
        this.modello = modello;
        this.produttore = produttore;
        this.capacitaEconomy = capacitaEconomy;
        this.capacitaBusiness = capacitaBusiness;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getModello() { return modello; }
    public void setModello(String modello) { this.modello = modello; }

    public String getProduttore() { return produttore; }
    public void setProduttore(String produttore) { this.produttore = produttore; }

    public int getCapacitaEconomy() { return capacitaEconomy; }
    public void setCapacitaEconomy(int capacitaEconomy) { this.capacitaEconomy = capacitaEconomy; }

    public int getCapacitaBusiness() { return capacitaBusiness; }
    public void setCapacitaBusiness(int capacitaBusiness) { this.capacitaBusiness = capacitaBusiness; }
}
