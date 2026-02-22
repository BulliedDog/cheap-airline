import java.sql.ResultSet;
import java.sql.SQLException;
import com.skywings.model.Citta;
public class CittaMapper {
    // Trasforma una riga del database in un oggetto Citta
    public static Citta mapRow(ResultSet rs) throws SQLException {
        return new Citta(
                rs.getLong("id"),
                rs.getString("nome"),
                rs.getString("nazione"),
                rs.getString("codice_iata")
        );
    }
}