package com.skywings.repository.repositories;

import com.skywings.repository.interfaces.NotificaDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class NotificaRepository implements NotificaDAO {
    private final JdbcTemplate jdbcTemplate;

    public NotificaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Map<String, Object>> getNotificheNonLette(Long utenteId) {
        return jdbcTemplate.queryForList(
                "SELECT * FROM notifiche WHERE utente_id = ? AND letta = FALSE ORDER BY data_creazione DESC",
                utenteId
        );
    }

    @Override
    public void segnaTutteComeLette(Long utenteId) {
        jdbcTemplate.update("UPDATE notifiche SET letta = TRUE WHERE utente_id = ?", utenteId);
    }
}