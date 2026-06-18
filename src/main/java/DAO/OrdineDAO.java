package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import control.DataSourceFactory;
import model.Ordine;

public class OrdineDAO {
    private final DataSource dataSource;

    public OrdineDAO() {
        this.dataSource = DataSourceFactory.getDataSource();
    }

    public List<Ordine> findAll() throws SQLException {
        String sql = "SELECT * FROM ORDINE ORDER BY data_ordine DESC";
        List<Ordine> results = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                results.add(mapRow(resultSet));
            }
        }
        return results;
    }

    public Ordine findById(int id) throws SQLException {
        String sql = "SELECT * FROM ORDINE WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRow(resultSet);
                }
            }
        }
        return null;
    }

    public List<Ordine> findByUtente(int utenteId) throws SQLException {
        String sql = "SELECT * FROM ORDINE WHERE utente_id = ? ORDER BY data_ordine DESC";
        List<Ordine> results = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, utenteId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(mapRow(resultSet));
                }
            }
        }
        return results;
    }

    public void insert(Ordine ordine) throws SQLException {
        String sql = "INSERT INTO ORDINE(seqId, utente_id, dvd_id, prezzo, quantita) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
        	statement.setString(1, ordine.getSeq());
            statement.setInt(2, ordine.getUtenteId());
            statement.setInt(3, ordine.getDvdId());
            statement.setFloat(4, ordine.getPrezzo());
            statement.setInt(5, ordine.getQuantita());
            statement.executeUpdate();
        }
    }

    public void update(Ordine ordine) throws SQLException {
        String sql = "UPDATE ORDINE SET utente_id = ?, dvd_id = ?, prezzo = ?, quantita = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ordine.getUtenteId());
            statement.setInt(2, ordine.getDvdId());
            statement.setFloat(3, ordine.getPrezzo());
            statement.setInt(4, ordine.getQuantita());
            statement.setInt(5, ordine.getId());
            statement.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM ORDINE WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private Ordine mapRow(ResultSet resultSet) throws SQLException {
        return new Ordine(
                resultSet.getInt("id"),
                resultSet.getString("seqId"),
                resultSet.getInt("utente_id"),
                resultSet.getInt("dvd_id"),
                resultSet.getFloat("prezzo"),
                resultSet.getTimestamp("data_ordine"),
                resultSet.getInt("quantita")
        );
    }
}