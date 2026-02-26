package dao;

import model.Utente;

import java.sql.*;

public class UtenteDAO {

    public boolean verificaLogin(Utente utente) {
        boolean loginValido = false;

        // Costruzione della query SQL
        String query = "SELECT * FROM utenti WHERE username = '" + utente.getUsername() + "' AND password = '" + utente.getPassword() + "'";

        try {
            // ottiene la connessione al database
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                Statement stmt = conn.createStatement();
                // eseguo la query di lettura (SELECT)
                ResultSet rs = stmt.executeQuery(query);

                // Se c'è almeno un risultato (rs.next() è true), le credenziali sono corrette
                if (rs.next()) {
                    loginValido = true;
                }
                // libero la memoria
                rs.close();
                stmt.close();
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la verifica del login.");
            e.printStackTrace();
        }

        return loginValido;
    }

    public boolean registraUtente(Utente u) {
        boolean registrazioneRiuscita = false;

        // Costruzione della query SQL
        String query = "INSERT INTO utenti (username, password) VALUES ('"
                + u.getUsername() + "', '" + u.getPassword() + "')";

        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                Statement stmt = conn.createStatement();

                // il numero di righe modificate o inserite
                int righeInserite = stmt.executeUpdate(query);

                // se almeno una riga è stata inserita, la registrazione ha avuto successo
                if (righeInserite > 0) {
                    registrazioneRiuscita = true;
                }

                stmt.close();
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la registrazione dell'utente.");
            e.printStackTrace();
        }

        return registrazioneRiuscita;
    }
}