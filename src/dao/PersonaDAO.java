package dao;

import model.Persona;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class PersonaDAO {

    public boolean insertPersona(Persona p) {

        // Costruzione della query SQL
        String query = "INSERT INTO persone (nome, cognome, indirizzo, telefono, eta) VALUES ('"
                + p.getNome() + "', '"
                + p.getCognome() + "', '"
                + p.getIndirizzo() + "', '"
                + p.getTelefono() + "', "
                + p.getEta() + ")";
        try {
            // ottiene la connessione al database
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                // inserimento e salvo il numero di righe modificate
                Statement stmt = conn.createStatement();
                int rowsAffected = stmt.executeUpdate(query);
                stmt.close();
                // Se almeno una riga è stata modificata, l'inserimento ha avuto successo
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
        // Query per selezionare tutte le colonne di tutti i record
        String query = "SELECT * FROM persone";
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                Statement stmt = conn.createStatement();
                // restituisce i risultati
                ResultSet rs = stmt.executeQuery(query);

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
                rs.close();
                stmt.close();
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero delle persone.");
            e.printStackTrace();
        }
        return listaPersone;
    }

    public boolean updatePersona(Persona p) {
        // costruzione query
        String query = "UPDATE persone SET "
                + "nome='" + p.getNome() + "', "
                + "cognome='" + p.getCognome() + "', "
                + "indirizzo='" + p.getIndirizzo() + "', "
                + "telefono='" + p.getTelefono() + "', "
                + "eta=" + p.getEta()
                + " WHERE id=" + p.getId();
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                Statement stmt = conn.createStatement();
                int rowsAffected = stmt.executeUpdate(query);
                stmt.close();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento della persona.");
            e.printStackTrace();
        }
        return false;
    }

    public boolean deletePersona(int id) {
        // costruzione query
        String query = "DELETE FROM persone WHERE id=" + id;
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                Statement stmt = conn.createStatement();
                int rowsAffected = stmt.executeUpdate(query);
                stmt.close();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'eliminazione della persona.");
            e.printStackTrace();
        }
        return false;
    }
}