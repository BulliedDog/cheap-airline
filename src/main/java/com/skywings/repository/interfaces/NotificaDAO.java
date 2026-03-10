package com.skywings.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface NotificaDAO {
    public List<Map<String, Object>> getNotificheNonLette(Long utenteId);
    public void segnaTutteComeLette(Long utenteId);
}
