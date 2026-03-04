package com.skywings.dto;

import com.skywings.model.Volo.StatoVolo;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VoloDTO {
    private Long id;
    private String codiceVolo;
    private Long idCittaPartenza;
    private Long idCittaArrivo;
    private Long idAereo;
    private LocalDateTime orarioPartenza;
    private LocalDateTime orarioArrivo;
    private BigDecimal prezzoBase;      // Coerente con il Model
    private BigDecimal prezzoCalcolato; // Coerente con il Model
    private StatoVolo stato;            // Usa l'Enum del Model

    // Getter e Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodiceVolo() { return codiceVolo; }
    public void setCodiceVolo(String codiceVolo) { this.codiceVolo = codiceVolo; }

    public Long getIdCittaPartenza() { return idCittaPartenza; }
    public void setIdCittaPartenza(Long idCittaPartenza) { this.idCittaPartenza = idCittaPartenza; }

    public Long getIdCittaArrivo() { return idCittaArrivo; }
    public void setIdCittaArrivo(Long idCittaArrivo) { this.idCittaArrivo = idCittaArrivo; }

    public Long getIdAereo() { return idAereo; }
    public void setIdAereo(Long idAereo) { this.idAereo = idAereo; }

    public LocalDateTime getOrarioPartenza() { return orarioPartenza; }
    public void setOrarioPartenza(LocalDateTime orarioPartenza) { this.orarioPartenza = orarioPartenza; }

    public LocalDateTime getOrarioArrivo() { return orarioArrivo; }
    public void setOrarioArrivo(LocalDateTime orarioArrivo) { this.orarioArrivo = orarioArrivo; }

    public BigDecimal getPrezzoBase() { return prezzoBase; }
    public void setPrezzoBase(BigDecimal prezzoBase) { this.prezzoBase = prezzoBase; }

    public BigDecimal getPrezzoCalcolato() { return prezzoCalcolato; }
    public void setPrezzoCalcolato(BigDecimal prezzoCalcolato) { this.prezzoCalcolato = prezzoCalcolato; }

    public StatoVolo getStato() { return stato; }
    public void setStato(StatoVolo stato) { this.stato = stato; }

    // Utility per la durata (comoda per Thymeleaf)
    public String getDurataFormattata() {
        if (orarioPartenza == null || orarioArrivo == null) return "N/A";
        java.time.Duration duration = java.time.Duration.between(orarioPartenza, orarioArrivo);
        return duration.toHours() + "h " + duration.toMinutesPart() + "m";
    }
}