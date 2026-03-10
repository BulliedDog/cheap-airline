package com.skywings.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PrenotazioneDTO {
    private Long id;
    private Long voloId;
    private Long utenteId;
    private String nomePasseggero;
    private String cognomePasseggero;
    private String numeroDocumento;
    private LocalDateTime dataPrenotazione;
    private BigDecimal prezzoAcquistato;
    private String posto;
    private String classe;

    public PrenotazioneDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getVoloId() { return voloId; }
    public void setVoloId(Long voloId) { this.voloId = voloId; }
    public Long getUtenteId() { return utenteId; }
    public void setUtenteId(Long utenteId) { this.utenteId = utenteId; }
    public String getNomePasseggero() { return nomePasseggero; }
    public void setNomePasseggero(String nomePasseggero) { this.nomePasseggero = nomePasseggero; }
    public String getCognomePasseggero() { return cognomePasseggero; }
    public void setCognomePasseggero(String cognomePasseggero) { this.cognomePasseggero = cognomePasseggero; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    public LocalDateTime getDataPrenotazione() { return dataPrenotazione; }
    public void setDataPrenotazione(LocalDateTime dataPrenotazione) { this.dataPrenotazione = dataPrenotazione; }
    public BigDecimal getPrezzoAcquistato() { return prezzoAcquistato; }
    public void setPrezzoAcquistato(BigDecimal prezzoAcquistato) { this.prezzoAcquistato = prezzoAcquistato; }
    public String getPosto() { return posto; }
    public void setPosto(String posto) { this.posto = posto; }
    public String getClasse() { return classe; }
    public void setClasse(String classe) { this.classe = classe; }
}