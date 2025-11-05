package miniROL;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FinestraMenuPrincipal menu = new FinestraMenuPrincipal();
            menu.mostrar();
        });
    }
}