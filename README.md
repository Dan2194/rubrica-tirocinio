# rubrica-tirocinio
Un progetto in Java che rappresenta una rubrica per la gestione dei contatti.
Il progetto è stato sviluppato come programma Java stand-alone/desktop. 

## Funzionalità Implementate 

* **Finestra di Login:**
  * All'avvio, l'applicazione mostra esclusivamente la schermata di accesso.
  * In caso di credenziali errate, l'accesso viene bloccato e viene mostrato un popup di "Login errato".
  * In caso di successo, la finestra si chiude per lasciare spazio alla schermata principale.
* **Finestra Principale (Rubrica):**
  * Contiene una `JTable` non modificabile direttamente dall'utente (per evitare alterazioni accidentali).
  * Come richiesto, la tabella mostra esclusivamente le colonne `Nome`, `Cognome` e `Telefono`. I dati completi rimangono comunque salvati nel database e sono visualizzabili dalla schermata di editor.
* **Finestra Editor (Inserimento/Modifica):**
  * Sviluppata come finestra modale (`JDialog`), blocca l'interazione con la finestra sottostante finché non si salva o si annulla.
  * Contiene i campi divisi per riga (`Nome`, `Cognome`, `Indirizzo`, `Telefono`, `Età`) e i bottoni per il salvataggio o l'annullamento.
* **Connessione al Database:**
  * Le credenziali di accesso al DB vengono lette dal file esterno `credenziali_database.properties`.

---

## Evoluzioni EXTRA implementate

* **Utilizzo delle JToolBar:** I pulsanti di azione (sia nella schermata principale che in quella di login ed editor) sono stati raggruppati all'interno di `JToolBar`.
* **Icone sui Pulsanti:** I bottoni principali hanno delle icone.
* **Utilizzo di sistema di versioning online** come si vede il progetto è stato caricato su GitHub per renderlo accessibile a tutti in modo pubblico.
* **Salvataggio dati su DB** I dati vengono salvati su database sfruttando MySQL.

---

## Aggiunte e Interpretazioni Personali

* **Pulsante "Registrati" nel Login:** Poiché la traccia richiede il login ma non specifica come popolare il database utenti, ho aggiunto una funzione di registrazione affiancata al tasto di login.
* **Interpretazione accesso contatti** Ho interpretato il login come un accesso globale. In sostanza ogni utente vede sempre gli stessi contatti.
  Se avessi voluto che ogni utente vedesse solo i propri contatti avrei dovuto:
  * aggiungere una colonna in più allo schema delle persone nel database per sapere a chi appartengono;
  * aggiungere un ID alle persone;
  * cambiare il login in modo che restituisse l'utente (per ricordare chi ha effettuato il login);
  * la query in cui prendo tutte le persone sarebbe cambiata per prendere solo quelle rispettive a quell'utente;
  * la finestra principale avrebbe dovuto prendere come input l'utente loggato.
    
