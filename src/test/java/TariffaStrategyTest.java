import com.skywings.model.Volo;
import com.skywings.strategy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TariffaStrategyTest {

    private Volo volo;
    private BigDecimal prezzoBase;

    @BeforeEach
    void setUp() {
        prezzoBase = new BigDecimal("100.00");
        volo = new Volo();
        volo.setPrezzoBase(prezzoBase);
    }

    @Test
    void testTariffaStandard() {
        TariffaStrategy strategy = new TariffaStandard();
        BigDecimal risultato = strategy.calcolaPrezzo(volo, prezzoBase);

        // Verifichiamo che 100 rimanga 100
        assertEquals(0, prezzoBase.compareTo(risultato), "La tariffa standard non deve variare il prezzo");
    }

    @Test
    void testTariffaWeekend() {
        TariffaStrategy strategy = new TariffaWeekend();

        // Impostiamo un sabato (07 Marzo 2026 è sabato)
        volo.setOrarioPartenza(LocalDateTime.of(2026, 3, 7, 10, 0));

        BigDecimal risultato = strategy.calcolaPrezzo(volo, prezzoBase);
        BigDecimal atteso = new BigDecimal("120.00");

        // Confrontiamo i BigDecimal (usando compareTo per ignorare differenze di scala come 120.0 vs 120.00)
        assertEquals(0, atteso.compareTo(risultato), "La tariffa weekend deve aumentare il prezzo del 20%");
    }

    @Test
    void testTariffaLastMinute() {
        TariffaStrategy strategy = new TariffaLastMinute();

        // Impostiamo la partenza a domani (così scatta il Last Minute)
        volo.setOrarioPartenza(LocalDateTime.now().plusDays(1));

        BigDecimal risultato = strategy.calcolaPrezzo(volo, prezzoBase);
        BigDecimal atteso = new BigDecimal("70.00");

        assertEquals(0, atteso.compareTo(risultato), "La tariffa last minute deve scontare il prezzo del 30%");
    }
}