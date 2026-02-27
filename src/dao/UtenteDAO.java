package dao;

import model.Utente;
import java.sql.*;

public class UtenteDAO {

    public boolean verificaLogin(Utente utente) {
        boolean loginValido = false;

        // query SQL con parametri
        String query = "SELECT * FROM utenti WHERE username = ? AND password = ?";

        try {
            // connessione al database
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, utente.getUsername());
                pstmt.setString(2, utente.getPassword());

                ResultSet rs = pstmt.executeQuery();

                // Se c'è almeno un risultato, le credenziali sono corrette
                if (rs.next()) {
                    loginValido = true;
                }

                // Libero la memoria
                rs.close();
                pstmt.close();
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la verifica del login.");
            e.printStackTrace();
        }

        return loginValido;
    }

    public boolean registraUtente(Utente u) {
        boolean registrazioneRiuscita = false;

        // query SQL
        String query = "INSERT INTO utenti (username, password) VALUES (?, ?)";

        try {
            // connessione al database
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, u.getUsername());
                pstmt.setString(2, u.getPassword());

                // salvo il numero di righe modificate
                int righeInserite = pstmt.executeUpdate();

                // Se almeno una riga è stata inserita, la registrazione ha avuto successo
                if (righeInserite > 0) {
                    registrazioneRiuscita = true;
                }

                // libero la memoria
                pstmt.close();
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la registrazione dell'utente.");
            e.printStackTrace();
        }

        return registrazioneRiuscita;
    }
}