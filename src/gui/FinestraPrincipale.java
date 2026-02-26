package gui;

import dao.PersonaDAO;
import model.Persona;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Vector;

public class FinestraPrincipale extends JFrame {

    private JTable tabellaPersone;
    private DefaultTableModel modelloTabella;
    private PersonaDAO personaDAO;
    private Vector<Persona> listaAttualePersone;

    public FinestraPrincipale() {
        setTitle("Rubrica Contatti");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        personaDAO = new PersonaDAO();

        // Toolbar in alto
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        // Creo i bottoni con le icone a 20x20 pixel
        JButton btnNuovo = new JButton("Nuovo", scalaIcona("/icons/add.png", 20, 20));
        JButton btnModifica = new JButton("Modifica", scalaIcona("/icons/edit.png", 20, 20));
        JButton btnElimina = new JButton("Elimina", scalaIcona("/icons/delete.png", 20, 20));

        // levo il bordino di java
        btnNuovo.setFocusPainted(false);
        btnModifica.setFocusPainted(false);
        btnElimina.setFocusPainted(false);

        // aggiungo un po di padding
        btnNuovo.setMargin(new Insets(5, 15, 5, 15));
        btnModifica.setMargin(new Insets(5, 15, 5, 15));
        btnElimina.setMargin(new Insets(5, 15, 5, 15));

        toolBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        toolBar.add(btnNuovo);
        toolBar.add(Box.createHorizontalStrut(15)); //  15 pixel di spazio vuoto
        toolBar.add(btnModifica);
        toolBar.add(Box.createHorizontalStrut(15)); // 15 pixel di spazio vuoto
        toolBar.add(btnElimina);

        add(toolBar, BorderLayout.NORTH);

        // Tabella
        // specifico le colonne richieste dalla traccia
        String[] colonne = {"Nome", "Cognome", "Telefono"};
        modelloTabella = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // vieto che l'utente modifichi i dati scrivendo direttamente nelle celle
            }
        };

        tabellaPersone = new JTable(modelloTabella);
        tabellaPersone.setRowHeight(30);
        tabellaPersone.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabellaPersone.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        // Aggiungo la tabella a un JScrollPane in modo che possa scorrere
        JScrollPane scrollPane = new JScrollPane(tabellaPersone);
        add(scrollPane, BorderLayout.CENTER);

        // aggiorno la tabella cosi carico i dati dal db
        aggiornaTabella();

        // Bottone NUOVO
        btnNuovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditorPersona editor = new EditorPersona(FinestraPrincipale.this, null);
                editor.setVisible(true);
            }
        });

        // Bottone MODIFICA
        btnModifica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rigaSelezionata = tabellaPersone.getSelectedRow();
                if (rigaSelezionata == -1) {
                    JOptionPane.showMessageDialog(FinestraPrincipale.this,
                            "Per modificare è necessario prima selezionare una persona.",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    // recupero la persona che mi serve usando l'indice della riga
                    Persona p = listaAttualePersone.get(rigaSelezionata);
                    // apro l'editor e gli dico di modificare quella persona
                    EditorPersona editor = new EditorPersona(FinestraPrincipale.this, p);
                    editor.setVisible(true);
                }
            }
        });

        // Bottone ELIMINA
        btnElimina.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rigaSelezionata = tabellaPersone.getSelectedRow();
                if (rigaSelezionata == -1) {
                    JOptionPane.showMessageDialog(FinestraPrincipale.this,
                            "Per eliminare è necessario prima selezionare una persona.",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    Persona p = listaAttualePersone.get(rigaSelezionata);
                    int conferma = JOptionPane.showConfirmDialog(FinestraPrincipale.this,
                            "Eliminare la persona " + p.getNome() + " " + p.getCognome() + "?",
                            "Conferma Eliminazione", JOptionPane.YES_NO_OPTION);

                    if (conferma == JOptionPane.YES_OPTION) {
                        if (personaDAO.deletePersona(p.getId())) {
                            aggiornaTabella(); // aggiorno i dati
                        } else {
                            JOptionPane.showMessageDialog(FinestraPrincipale.this, "Errore durante l'eliminazione.");
                        }
                    }
                }
            }
        });
    }

    public void aggiornaTabella() {
        // Svuota la tabella attuale
        modelloTabella.setRowCount(0);
        // Recupera i dati aggiornati dal database
        listaAttualePersone = personaDAO.getAllPersone();

        // Popola la tabella riga per riga
        for (Persona p : listaAttualePersone) {
            Object[] riga = {p.getNome(), p.getCognome(), p.getTelefono()};
            modelloTabella.addRow(riga);
        }
    }

    private ImageIcon scalaIcona(String percorso, int larghezza, int altezza) {
        // carico l'immagine originale in modo sicuro
        ImageIcon iconaOriginale = new ImageIcon(java.util.Objects.requireNonNull(getClass().getResource(percorso)));

        // ridimensiono l'immagine
        java.awt.Image immagineScalata = iconaOriginale.getImage().getScaledInstance(larghezza, altezza, java.awt.Image.SCALE_SMOOTH);

        // restituisco la nuova icona
        return new ImageIcon(immagineScalata);
    }
}