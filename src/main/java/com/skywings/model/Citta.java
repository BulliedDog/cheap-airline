package com.skywings.model;

public class Citta {
    private Long id;
    private String nome;
    private String nazione;
    private String codiceIata;

    public Citta() {}

    public Citta(Long id, String nome, String nazione, String codiceIata) {
        this.id = id;
        this.nome = nome;
        this.nazione = nazione;
        this.codiceIata = codiceIata;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getNazione() { return nazione; }
    public void setNazione(String nazione) { this.nazione = nazione; }

    public String getCodiceIata() { return codiceIata; }
    public void setCodiceIata(String codiceIata) { this.codiceIata = codiceIata; }
}