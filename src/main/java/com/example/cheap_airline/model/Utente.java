package com.example.cheap_airline.model;

public class Utente {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String nomeCompleto;
    private String ruolo;

    public Utente() {}

    // --- Getter e Setter Aggiornati ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; } // NUOVO
    public void setUsername(String username) { this.username = username; } // NUOVO

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }
}