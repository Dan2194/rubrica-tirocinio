package gui;

import dao.PersonaDAO;
import model.Persona;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// uso JDialog (invece di JFrame) per creare una finestra "modale",
// praticamente una finestra che blocca l'interazione con quella principale finché non viene chiusa.
public class EditorPersona extends JDialog {

    private JTextField txtNome;
    private JTextField txtCognome;
    private JTextField txtIndirizzo;
    private JTextField txtTelefono;
    private JTextField txtEta;

    private Persona personaDaModificare;
    private FinestraPrincipale finestraPadre;

    // passo al costruttore la finestra padre, cosi dopo posso aggiornare le tabelle della finestra principale.
    // passo l'oggetto Persona (che sarà null se il contatto è nuovo)
    public EditorPersona(FinestraPrincipale padre, Persona persona) {
        super(padre, "Editor Persona", true);
        this.finestraPadre = padre;
        this.personaDaModificare = persona;

        setSize(350, 300);
        setLocationRelativeTo(padre); // La centra rispetto alla finestra principale
        setLayout(new BorderLayout());

        // Form Centrale (campi divisi per riga come da specifiche)
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelForm.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        panelForm.add(txtNome);

        panelForm.add(new JLabel("Cognome:"));
        txtCognome = new JTextField();
        panelForm.add(txtCognome);

        panelForm.add(new JLabel("Indirizzo:"));
        txtIndirizzo = new JTextField();
        panelForm.add(txtIndirizzo);

        panelForm.add(new JLabel("Telefono:"));
        txtTelefono = new JTextField();
        panelForm.add(txtTelefono);

        panelForm.add(new JLabel("Età:"));
        txtEta = new JTextField();
        panelForm.add(txtEta);

        add(panelForm, BorderLayout.CENTER);

        // Toolbar con i bottoni Salva e Annulla
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton btnSalva = new JButton("Salva");
        JButton btnAnnulla = new JButton("Annulla");

        toolBar.add(btnSalva);
        toolBar.add(Box.createHorizontalStrut(15)); // 15 pixel di spazio vuoto per sistemare graficamente
        toolBar.add(btnAnnulla);

        toolBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // levo i bordi fastidiosi di java
        btnSalva.setFocusPainted(false);
        btnAnnulla.setFocusPainted(false);

        btnSalva.setMargin(new Insets(5, 15, 5, 15));
        btnAnnulla.setMargin(new Insets(5, 15, 5, 15));

        add(toolBar, BorderLayout.NORTH);

        // se la persona già esiste, riempio i campi
        if (personaDaModificare != null) {
            txtNome.setText(personaDaModificare.getNome());
            txtCognome.setText(personaDaModificare.getCognome());
            txtIndirizzo.setText(personaDaModificare.getIndirizzo());
            txtTelefono.setText(personaDaModificare.getTelefono());
            txtEta.setText(String.valueOf(personaDaModificare.getEta()));
        }

        // Bottone SALVA
        btnSalva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvaDati();
            }
        });

        // Bottone ANNULLA
        btnAnnulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void salvaDati() {
        // i dati scritti dall'utente
        String nome = txtNome.getText().trim();
        String cognome = txtCognome.getText().trim();
        String indirizzo = txtIndirizzo.getText().trim();
        String telefono = txtTelefono.getText().trim();
        int eta = 0;

        // Controllo i dati
        try {
            eta = Integer.parseInt(txtEta.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Inserire un numero valido per l'età.", "Errore", JOptionPane.ERROR_MESSAGE);
            return; // Ferma il salvataggio
        }
        if (eta < 0) {
            JOptionPane.showMessageDialog(this, "L'età non può essere minore di 0.", "Errore", JOptionPane.ERROR_MESSAGE);
            return; // Ferma il salvataggio
        }
        // Controllo lunghezze massime consentite dal database
        if (nome.length() > 50 || cognome.length() > 50) {
            JOptionPane.showMessageDialog(this, "Nome e Cognome non possono superare i 50 caratteri.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (indirizzo.length() > 100) {
            JOptionPane.showMessageDialog(this, "L'indirizzo inserito è troppo lungo (max 100 caratteri).", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (telefono.length() > 20) {
            JOptionPane.showMessageDialog(this, "Il numero di telefono è troppo lungo (max 20 caratteri).", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // controllo numero telefono
        if (!telefono.isEmpty() && !telefono.matches("^[0-9+ ]+$")) {
            JOptionPane.showMessageDialog(this, "Il numero di telefono contiene caratteri non validi.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Controllo campi obbligatori
        if (nome.isEmpty() || cognome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e Cognome sono obbligatori!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PersonaDAO dao = new PersonaDAO();

        if (personaDaModificare == null) {
            // CREAZIONE NUOVA PERSONA
            Persona nuovaPersona = new Persona(nome, cognome, indirizzo, telefono, eta);
            if (!dao.insertPersona(nuovaPersona)) {
                JOptionPane.showMessageDialog(this, "Errore durante il salvataggio.");
            }
        } else {
            // MODIFICA PERSONA ESISTENTE
            personaDaModificare.setNome(nome);
            personaDaModificare.setCognome(cognome);
            personaDaModificare.setIndirizzo(indirizzo);
            personaDaModificare.setTelefono(telefono);
            personaDaModificare.setEta(eta);

            if (!dao.updatePersona(personaDaModificare)) {
                JOptionPane.showMessageDialog(this, "Errore durante la modifica.");
            }
        }

        // Dopo aver salvato, la finestra padre dovrà aggiornarsi
        finestraPadre.aggiornaTabella();

        // chiudo la finestra modale.
        this.dispose();
    }
}