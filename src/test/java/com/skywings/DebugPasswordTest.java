package com.skywings; // Assicurati che il package sia corretto

import com.skywings.service.AutenticazioneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// AGGIUNGI QUESTA RIGA indicando la tua classe main
@SpringBootTest(classes = SkyWingsApplication.class)
public class DebugPasswordTest {

    @Autowired
    private AutenticazioneService autenticazioneService;

    @Test
    public void generaHash() {
        if (autenticazioneService != null) {
            String hash = autenticazioneService.criptaPassword("alberto");
            System.out.println("==========================================");
            System.out.println("COPIA QUESTO HASH: " + hash);
            System.out.println("==========================================");
        } else {
            System.out.println("Errore: AutenticazioneService è null!");
        }
    }
}