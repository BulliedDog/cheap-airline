package com.skywings.dto;

/**
 * DTO per visualizzare i dettagli dell'equipaggio associato ai voli.
 * Unisce i dati di VoloEquipaggio, Volo e Utente.
 */
public class VoloEquipaggioDTO {

    private Long idVolo;
    private Long idUtente;
    private String codiceVolo;
    private String nomeMembro;
    private String cognomeMembro;
    private String ruoloMembro;

    // Costruttore vuoto (necessario per il RowMapper)
    public VoloEquipaggioDTO() {}

    // Costruttore completo
    public VoloEquipaggioDTO(Long idVolo, Long idUtente, String codiceVolo,
                                   String nomeMembro, String cognomeMembro, String ruoloMembro) {
        this.idVolo = idVolo;
        this.idUtente = idUtente;
        this.codiceVolo = codiceVolo;
        this.nomeMembro = nomeMembro;
        this.cognomeMembro = cognomeMembro;
        this.ruoloMembro = ruoloMembro;
    }

    // --- GETTER E SETTER ---

    public Long getIdVolo() {
        return idVolo;
    }

    public void setIdVolo(Long idVolo) {
        this.idVolo = idVolo;
    }

    public Long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }

    public String getCodiceVolo() {
        return codiceVolo;
    }

    public void setCodiceVolo(String codiceVolo) {
        this.codiceVolo = codiceVolo;
    }

    public String getNomeMembro() {
        return nomeMembro;
    }

    public void setNomeMembro(String nomeMembro) {
        this.nomeMembro = nomeMembro;
    }

    public String getCognomeMembro() {
        return cognomeMembro;
    }

    public void setCognomeMembro(String cognomeMembro) {
        this.cognomeMembro = cognomeMembro;
    }

    public String getRuoloMembro() {
        return ruoloMembro;
    }

    public void setRuoloMembro(String ruoloMembro) {
        this.ruoloMembro = ruoloMembro;
    }

    public String getNomeCompleto() {
        return nomeMembro + " " + cognomeMembro;
    }

    public String getCompositeId() {
        return idVolo + "-" + idUtente;
    }
}