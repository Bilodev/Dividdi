package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import control.DataSourceFactory;
import model.OrdineRiepilogo;

public class OrdineRiepilogoDAO {
    private final DataSource dataSource;

    public OrdineRiepilogoDAO() {
        this.dataSource = DataSourceFactory.getDataSource();
    }

    // Ordini di un utente specifico
    public List<OrdineRiepilogo> findByUtente(int utenteId) throws SQLException {
        String sql =
            "SELECT o.seqId, MIN(o.data_ordine) AS data_ordine, " +
            "SUM(o.prezzo * o.quantita) AS totale, " +
            "u.username, u.email, " +
            "i.nome, i.cognome, i.indirizzo, i.citta, i.cap, i.paese, " +
            "GROUP_CONCAT(d.nome ORDER BY d.nome SEPARATOR ', ') AS dvd_acquistati " +
            "FROM ORDINE o " +
            "JOIN UTENTE u ON u.id = o.utente_id " +
            "JOIN INDIRIZZO i ON i.seqId = o.seqId " +
            "JOIN DVD d ON d.id = o.dvd_id " +
            "WHERE o.utente_id = ? " +
            "GROUP BY o.seqId, u.username, u.email, " +
            "i.nome, i.cognome, i.indirizzo, i.citta, i.cap, i.paese " +
            "ORDER BY data_ordine DESC";

        List<OrdineRiepilogo> results = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, utenteId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRow(rs));
                }
            }
        }
        return results;
    }

    // Tutti gli ordini in un intervallo di date
    public List<OrdineRiepilogo> findByDateRange(String dataInizio, String dataFine) throws SQLException {
        String sql =
            "SELECT o.seqId, MIN(o.data_ordine) AS data_ordine, " +
            "SUM(o.prezzo * o.quantita) AS totale, " +
            "u.username, u.email, " +
            "i.nome, i.cognome, i.indirizzo, i.citta, i.cap, i.paese, " +
            "GROUP_CONCAT(d.nome ORDER BY d.nome SEPARATOR ', ') AS dvd_acquistati " +
            "FROM ORDINE o " +
            "JOIN UTENTE u ON u.id = o.utente_id " +
            "JOIN INDIRIZZO i ON i.seqId = o.seqId " +
            "JOIN DVD d ON d.id = o.dvd_id " +
            "WHERE o.data_ordine BETWEEN ? AND ? " +
            "GROUP BY o.seqId, u.username, u.email, " +
            "i.nome, i.cognome, i.indirizzo, i.citta, i.cap, i.paese " +
            "ORDER BY data_ordine DESC";

        List<OrdineRiepilogo> results = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, dataInizio);
            statement.setString(2, dataFine);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRow(rs));
                }
            }
        }
        return results;
    }

    // Ordini di un utente specifico in un intervallo di date
    public List<OrdineRiepilogo> findByUtenteAndDateRange(String utenteId, String dataInizio, String dataFine) throws SQLException {
        String sql =
            "SELECT o.seqId, MIN(o.data_ordine) AS data_ordine, " +
            "SUM(o.prezzo * o.quantita) AS totale, " +
            "u.username, u.email, " +
            "i.nome, i.cognome, i.indirizzo, i.citta, i.cap, i.paese, " +
            "GROUP_CONCAT(d.nome ORDER BY d.nome SEPARATOR ', ') AS dvd_acquistati " +
            "FROM ORDINE o " +
            "JOIN UTENTE u ON u.id = o.utente_id " +
            "JOIN INDIRIZZO i ON i.seqId = o.seqId " +
            "JOIN DVD d ON d.id = o.dvd_id " +
            "WHERE u.email = ? " +
            "AND o.data_ordine BETWEEN ? AND ? " +
            "GROUP BY o.seqId, u.username, u.email, " +
            "i.nome, i.cognome, i.indirizzo, i.citta, i.cap, i.paese " +
            "ORDER BY data_ordine DESC";

        List<OrdineRiepilogo> results = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, utenteId);
            statement.setString(2, dataInizio);
            statement.setString(3, dataFine);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRow(rs));
                }
            }
        }
        return results;
    }

    // Riepilogo totali per cliente (dashboard admin)
    public List<OrdineRiepilogo> findTotaliPerCliente(String dataInizio, String dataFine) throws SQLException {
        String sql =
            "SELECT u.username, u.email, " +
            "COUNT(DISTINCT o.seqId) AS num_ordini, " +
            "SUM(o.prezzo * o.quantita) AS totale_speso " +
            "FROM ORDINE o " +
            "JOIN UTENTE u ON u.id = o.utente_id " +
            "WHERE o.data_ordine BETWEEN ? AND ? " +
            "GROUP BY u.id, u.username, u.email " +
            "ORDER BY totale_speso DESC";

        List<OrdineRiepilogo> results = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, dataInizio);
            statement.setString(2, dataFine);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    OrdineRiepilogo o = new OrdineRiepilogo();
                    o.setUsername(rs.getString("username"));
                    o.setEmail(rs.getString("email"));
                    o.setNumOrdini(rs.getInt("num_ordini"));
                    o.setTotaleSpeso(rs.getFloat("totale_speso"));
                    results.add(o);
                }
            }
        }
        return results;
    }

    private OrdineRiepilogo mapRow(ResultSet rs) throws SQLException {
        OrdineRiepilogo o = new OrdineRiepilogo();
        o.setSeqId(rs.getString("seqId"));
        o.setDataOrdine(rs.getTimestamp("data_ordine"));
        o.setTotale(rs.getFloat("totale"));
        o.setUsername(rs.getString("username"));
        o.setEmail(rs.getString("email"));
        o.setNome(rs.getString("nome"));
        o.setCognome(rs.getString("cognome"));
        o.setIndirizzo(rs.getString("indirizzo"));
        o.setCitta(rs.getString("citta"));
        o.setCap(rs.getString("cap"));
        o.setPaese(rs.getString("paese"));
        o.setDvdAcquistati(rs.getString("dvd_acquistati"));
        return o;
    }
}