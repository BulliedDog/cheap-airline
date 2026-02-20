package com.skywings.controller;
import com.skywings.model.Utente;
import com.skywings.service.AutenticazioneService;
import com.skywings.util.GestoreSessione;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    public final GestoreSessione gestoreSessione;

    public AdminController(GestoreSessione gestoreSessione) {
        this.gestoreSessione = gestoreSessione;
    }
    @getMapping("/admin")
    public admin()

        if(gestoreSessione.getUtenteCorrente() == null)
            return("redirect:/login")

}
