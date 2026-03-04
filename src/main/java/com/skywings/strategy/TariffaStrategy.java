package com.skywings.strategy;

import com.skywings.model.Volo;

import java.math.BigDecimal;

public interface TariffaStrategy {
    /**
     * Calcola il prezzo finale del volo applicando la strategia specifica.
     * @param volo Il volo su cui calcolare il prezzo
     * @param prezzoBase Il prezzo di partenza del biglietto
     * @return Il prezzo calcolato
     */
    BigDecimal calcolaPrezzo(Volo volo, BigDecimal prezzoBase);
}