package marcPanellPersonalitzat;

import javax.swing.*;

public class FramePer extends JFrame {

    public FramePer(int ample, int alt, String titol, boolean esPrincipal) {

        setSize(ample, alt);
        setTitle(titol);
        setLocationRelativeTo(null);
        if (esPrincipal) setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}