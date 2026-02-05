package com.skywings.model;

public class Utente {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String nome;
    private String cognome;
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

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }
}