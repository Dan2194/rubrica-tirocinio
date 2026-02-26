package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    // una singola istanza della connessione
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Leggo il file delle properties
                Properties props = new Properties();
                FileInputStream fis = new FileInputStream("credenziali_database.properties");
                props.load(fis);
                fis.close();

                // Recupero i parametri richiesti dalle specifiche
                String ip = props.getProperty("ip-server-mysql");
                String porta = props.getProperty("porta");
                String user = props.getProperty("username");
                String pass = props.getProperty("password");

                // Costruisco l'URL di connessione (rubrica_db è il nome nel file SQL)
                String url = "jdbc:mysql://" + ip + ":" + porta + "/rubrica_db";

                // Stabilisco la connessione
                connection = DriverManager.getConnection(url, user, pass);
                System.out.println("Connessione al database stabilita con successo!");

            } catch (IOException e) {
                System.err.println("Errore: Impossibile leggere il file credenziali_database.properties");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("Errore: Connessione al database fallita. Verifica le credenziali o se MySQL è avviato.");
                e.printStackTrace();
            }
        }
        return connection;
    }
}