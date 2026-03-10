package com.skywings.repository.repositories;

import com.skywings.mapper.VoloMapper;
import com.skywings.model.Volo;
import com.skywings.repository.interfaces.VoloDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class VoloRepository implements VoloDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private VoloMapper voloMapper;

    @Override
    public List<Volo> findAll() {
        String sql = "SELECT * FROM voli";
        return jdbcTemplate.query(sql, voloMapper);
    }

    @Override
    public Optional<Volo> findById(Long id) {
        String sql = "SELECT * FROM voli WHERE id = ?";
        return jdbcTemplate.query(sql, voloMapper, id).stream().findFirst();
    }

    @Override
    public void save(Volo volo) {
        if (volo.getId() != null && volo.getId() > 0) {
            // UPDATE
            String sql = "UPDATE voli SET codice_volo = ?, id_citta_partenza = ?, id_citta_arrivo = ?, id_aereo = ?, orario_partenza = ?, orario_arrivo = ?, prezzo_base = ?, stato = ? WHERE id = ?";

            jdbcTemplate.update(sql,
                    volo.getCodiceVolo(),
                    volo.getIdCittaPartenza(),
                    volo.getIdCittaArrivo(),
                    volo.getIdAereo(),
                    volo.getOrarioPartenza(),
                    volo.getOrarioArrivo(),
                    volo.getPrezzoBase(),
                    volo.getStato() != null ? volo.getStato().name() : "PROGRAMMATO",
                    volo.getId()
            );
        } else {
            // INSERT
            String sql = "INSERT INTO voli (codice_volo, id_citta_partenza, id_citta_arrivo, id_aereo, orario_partenza, orario_arrivo, prezzo_base, stato) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            jdbcTemplate.update(sql,
                    volo.getCodiceVolo(),
                    volo.getIdCittaPartenza(),
                    volo.getIdCittaArrivo(),
                    volo.getIdAereo(),
                    volo.getOrarioPartenza(),
                    volo.getOrarioArrivo(),
                    volo.getPrezzoBase(),
                    volo.getStato() != null ? volo.getStato().name() : "PROGRAMMATO"
            );
        }
    }

    @Override
    public void update(Volo volo) {
        String sql = "UPDATE voli SET codice_volo = ?, id_citta_partenza = ?, id_citta_arrivo = ?, id_aereo = ?, orario_partenza = ?, orario_arrivo = ?, prezzo_base = ?, stato = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                volo.getCodiceVolo(),
                volo.getIdCittaPartenza(),
                volo.getIdCittaArrivo(),
                volo.getIdAereo(),
                volo.getOrarioPartenza(),
                volo.getOrarioArrivo(),
                volo.getPrezzoBase(),
                volo.getStato().name(),
                volo.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM voli WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Volo> findVoliFiltered(Long originId, Long destId, LocalDate date) {
        // Partiamo con una condizione sempre vera, così possiamo aggiungere "AND" a cascata
        StringBuilder sql = new StringBuilder("SELECT * FROM voli WHERE 1=1");
        List<Object> params = new ArrayList<>();

        // Se c'è l'origine, aggiungiamo il filtro
        if (originId != null) {
            sql.append(" AND id_citta_partenza = ?");
            params.add(originId);
        }

        // Se c'è la destinazione, aggiungiamo il filtro
        if (destId != null) {
            sql.append(" AND id_citta_arrivo = ?");
            params.add(destId);
        }

        // Se c'è la data, aggiungiamo il filtro
        if (date != null) {
            sql.append(" AND CAST(orario_partenza AS DATE) = ?");
            params.add(java.sql.Date.valueOf(date));
        }

        // Aggiungiamo l'ordinamento alla fine
        sql.append(" ORDER BY orario_partenza ASC");

        // Eseguiamo la query con i parametri dinamici
        return jdbcTemplate.query(sql.toString(), voloMapper, params.toArray());
    }

}