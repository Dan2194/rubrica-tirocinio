package gui;

import dao.UtenteDAO;
import model.Utente;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegistrati;

    public LoginFrame() {
        // Impostazioni base della finestra
        setTitle("Accesso Rubrica");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centra la finestra nello schermo
        setLayout(new BorderLayout());

        // Pannello centrale per i campi di inserimento con layout a griglia
        JPanel panelCentrale = new JPanel(new GridLayout(2, 2, 5, 5));

        panelCentrale.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelCentrale.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        panelCentrale.add(txtUsername);

        panelCentrale.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        panelCentrale.add(txtPassword);

        add(panelCentrale, BorderLayout.CENTER);

        // toolbar con bottoni di login e registrazione
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        btnLogin = new JButton("Login");
        btnRegistrati = new JButton("Registrati");

        toolBar.add(btnLogin);
        toolBar.add(Box.createHorizontalStrut(15)); // 15 pixel di spazio vuoto (per distanziare i bottoni)
        toolBar.add(btnRegistrati);

        toolBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // per non mettere il bordino fastidioso di Java
        btnLogin.setFocusPainted(false);
        btnRegistrati.setFocusPainted(false);

        btnLogin.setMargin(new Insets(5, 15, 5, 15));
        btnRegistrati.setMargin(new Insets(5, 15, 5, 15));

        add(toolBar, BorderLayout.SOUTH);

        // Azione Login
        btnLogin.addActionListener(e -> effettuaLogin());

        // Azione Registrati
        btnRegistrati.addActionListener(e -> effettuaRegistrazione());

    }

    private void effettuaLogin() {
        // recupero lo username
        String username = txtUsername.getText();
        // recupero la password (getPassword restituisce un char array, lo converto a stringa)
        String password = new String(txtPassword.getPassword());

        Utente utenteTemp = new Utente(username, password);
        UtenteDAO dao = new UtenteDAO();

        if (dao.verificaLogin(utenteTemp)) {
            // se i dati sono corretti, si chiude il login e si apre la finestra principale
            this.dispose();
            FinestraPrincipale fp = new FinestraPrincipale();
            fp.setVisible(true);

        } else {
            // se i dati non sono corretti, mostra un messaggio di errore
            JOptionPane.showMessageDialog(this, "Login errato", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void effettuaRegistrazione() {

        // prendo username e password dai campi di input
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Inserisci username e password per registrarti.");
            return;
        }

        Utente nuovoUtente = new Utente(username, password);
        UtenteDAO dao = new UtenteDAO();

        if (dao.registraUtente(nuovoUtente)) {
            JOptionPane.showMessageDialog(this, "Registrazione completata! Ora puoi fare il login.");
        } else {
            JOptionPane.showMessageDialog(this, "Errore: Username già esistente o problema al database.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}