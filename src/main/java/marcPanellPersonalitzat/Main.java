package marcPanellPersonalitzat;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        FramePer marc = new FramePer(400, 250, "titol", true );

        PanellPer panell=new PanellPer(PanellPer.FLOWLAYOUT);

        JButton boto = new JButton("es un boto");

        panell.add(boto);

        marc.add(panell);

        marc.setVisible(true);
    }
}

