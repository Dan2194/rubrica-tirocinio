import gui.LoginFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        // Avvio swing all'interno dell'Event Dispatch Thread per evitare blocchi
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // creo e rendo visibile la finestra di login
                LoginFrame frameDiLogin = new LoginFrame();
                frameDiLogin.setVisible(true);
            }
        });
    }
}