package com.skywings.config;

import com.skywings.model.Utente;
import com.skywings.util.GestoreSessione; // Importa il tuo util
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Autowired
    private GestoreSessione gestoreSessione; // Iniettiamo il tuo gestore

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        // Usiamo il tuo metodo ufficiale per prendere l'utente
        Utente utente = gestoreSessione.getUtenteCorrente(session);

        if (utente == null || !"ADMIN".equals(utente.getRuolo())) {
            // Redirect al login se non è admin
            response.sendRedirect("/login?error=restricted");
            return false;
        }

        return true;
    }
}