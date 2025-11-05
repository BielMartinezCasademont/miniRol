package marcPanellPersonalitzat;

import javax.swing.*;
import java.awt.*;

public class PanellPer extends JPanel {

    public static final int FLOWLAYOUT = 0;
    public static final int BORDERLAYOUT = 1;
    public static final int GRIDLAYOUT = 2;

    public PanellPer(int LayoutPerDefecte) {
        if (LayoutPerDefecte==1) setLayout(new BorderLayout());
        if (LayoutPerDefecte==2) setLayout(new GridLayout());
// flowlayout és per defecte no cal fer if

    }
}

