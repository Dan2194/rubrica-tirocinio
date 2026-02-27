package dao;

import model.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PersonaDAO {

    public boolean insertPersona(Persona p) {

        // query SQL con parametri
        String query = "INSERT INTO persone (nome, cognome, indirizzo, telefono, eta) VALUES (?, ?, ?, ?, ?)";
        try {
            // connessione al database
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, p.getNome());
                pstmt.setString(2, p.getCognome());
                pstmt.setString(3, p.getIndirizzo());
                pstmt.setString(4, p.getTelefono());
                pstmt.setInt(5, p.getEta());

                // salvo il numero di righe modificate
                int rowsAffected = pstmt.executeUpdate();
                pstmt.close();

                // se almeno una riga è stata modificata, l'inserimento ha avuto successo
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento della persona.");
            e.printStackTrace();
        }
        return false;
    }

    public Vector<Persona> getAllPersone() {
        Vector<Persona> listaPersone = new Vector<>();

        // query per selezionare tutte le colonne di tutti i record
        String query = "SELECT * FROM persone";
        try {
            // connessione al database
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(query);

                // cosi ottengo i risultati
                ResultSet rs = pstmt.executeQuery();

                // scorro i risultati riga per riga
                while (rs.next()) {
                    // creo un nuovo oggetto Persona utilizzando i dati dalle colonne del database
                    Persona p = new Persona(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("cognome"),
                            rs.getString("indirizzo"),
                            rs.getString("telefono"),
                            rs.getInt("eta")
                    );
                    // aggiungo la persona alla lista da restituire
                    listaPersone.add(p);
                }

                // libero la memoria
                rs.close();
                pstmt.close();
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero delle persone.");
            e.printStackTrace();
        }
        return listaPersone;
    }

    public boolean updatePersona(Persona p) {

        // query di aggiornamento
        String query = "UPDATE persone SET nome=?, cognome=?, indirizzo=?, telefono=?, eta=? WHERE id=?";
        try {
            // connessione al database
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, p.getNome());
                pstmt.setString(2, p.getCognome());
                pstmt.setString(3, p.getIndirizzo());
                pstmt.setString(4, p.getTelefono());
                pstmt.setInt(5, p.getEta());
                pstmt.setInt(6, p.getId());

                // salvo il numero di righe modificate
                int rowsAffected = pstmt.executeUpdate();
                pstmt.close();

                // Se almeno una riga è stata modificata, l'aggiornamento ha avuto successo
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento della persona.");
            e.printStackTrace();
        }
        return false;
    }

    public boolean deletePersona(int id) {

        // query di eliminazione
        String query = "DELETE FROM persone WHERE id=?";
        try {
            // connessione al database
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, id);

                // salvo il numero di righe modificate
                int rowsAffected = pstmt.executeUpdate();
                pstmt.close();

                // se almeno una riga è stata modificata, l'eliminazione ha avuto successo
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'eliminazione della persona.");
            e.printStackTrace();
        }
        return false;
    }
}