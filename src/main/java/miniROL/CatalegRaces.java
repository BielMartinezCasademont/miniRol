package miniROL;

import javax.swing.*;
import java.awt.*;

public class CatalegRaces {

    private JDialog marc;
    private JPanel panellPrincipal, panellSuperior, panellInferior, panellRaces;
    private JPanel panellHuma, panellElf, panellNan, panellOrc;

    private JLabel imgHuma, imgElf, imgNan, imgOrc;
    private JLabel desHuma, desElf, desNan, desOrc;

    private static JButton botHuma, botElf, botNan, botOrc;
    private static boolean seleccionatHuma = false, seleccionatElf = false, seleccionatNan = false, seleccionatOrc = false;

    private JButton botSortir;
    private FinestraPrincipal fp;
    private Personatge pj;

    public CatalegRaces(FinestraPrincipal fp) {
        this.fp = fp;
        this.pj = fp.getPj();
        marc = new JDialog();
        ompleCataleg(fp);
        botSortir = new JButton("Sortir");
    }

    private void ompleCataleg(FinestraPrincipal fp) {
        panellPrincipal = new JPanel(new BorderLayout());
        panellSuperior = fp.getPanellSuperior();
        panellInferior = new JPanel();
        panellRaces = new JPanel(new GridLayout(2, 2, 10, 10));
        panellRaces.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panellPrincipal.add(panellSuperior, BorderLayout.NORTH);

        posaRaca("huma", panellHuma = new JPanel(),
                imgHuma = new JLabel(new ImageIcon("./imatges/huma.gif")),
                desHuma = new JLabel("Humà - Equilibrat"),
                botHuma = new JButton("Seleccionar"),
                "Una raça equilibrada amb atributs uniformes. Ideal per a principiants.",
                seleccionatHuma);

        posaRaca("elf", panellElf = new JPanel(),
                imgElf = new JLabel(new ImageIcon("./imatges/elf.gif")),
                desElf = new JLabel("Elf - Àgil i encanteris poderosos"),
                botElf = new JButton("Seleccionar"),
                "Els elfs són àgils, elegants i tenen afinitat amb la màgia i la natura.",
                seleccionatElf);

        posaRaca("nan", panellNan = new JPanel(),
                imgNan = new JLabel(new ImageIcon("./imatges/nan.gif")),
                desNan = new JLabel("Nan - Fort i resistent"),
                botNan = new JButton("Seleccionar"),
                "Els nans són coneguts per la seva gran força física i resistència. Excel·lents guerrers.",
                seleccionatNan);

        posaRaca("orc", panellOrc = new JPanel(),
                imgOrc = new JLabel(new ImageIcon("./imatges/orc.gif")),
                desOrc = new JLabel("Orc - Brutal i poderós"),
                botOrc = new JButton("Seleccionar"),
                "Els orcs són criatures ferotges amb força descomunal però poca agilitat.",
                seleccionatOrc);
    }

    private void posaRaca(String nomRaca, JPanel panell, JLabel imatge, JLabel descripcio, JButton boto, String tooltip, boolean seleccionat) {
        afegirRaca(panell, imatge, descripcio, boto, nomRaca, tooltip, seleccionat);
    }

    public void obrirCataleg() {
        muntarInterficie();
        marc.setUndecorated(true);
        marc.setVisible(true);
    }

    private void muntarInterficie() {
        panellPrincipal.add(panellRaces, BorderLayout.CENTER);

        botSortir.addActionListener(e -> marc.dispose());
        panellInferior.add(botSortir);
        panellPrincipal.add(panellInferior, BorderLayout.SOUTH);

        marc.setSize(850, 700);
        marc.setLocationRelativeTo(null);
        marc.setModal(true);
        marc.add(panellPrincipal);
    }

    private void afegirRaca(JPanel panRaca, JLabel imatge, JLabel descripcio, JButton boto, String nom, String tooltip, boolean seleccionat) {
        imatge.setAlignmentX(Component.CENTER_ALIGNMENT);
        descripcio.setAlignmentX(Component.CENTER_ALIGNMENT);
        boto.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tooltip amb descripció detallada
        imatge.setToolTipText("<html><b>" + descripcio.getText() + "</b><br>" + tooltip + "</html>");
        boto.setToolTipText(tooltip);

        if (seleccionat) boto.setEnabled(false);
        boto.addActionListener(e -> seleccionarRaca(boto, nom));

        panRaca.setLayout(new BoxLayout(panRaca, BoxLayout.Y_AXIS));
        panRaca.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        panRaca.setBackground(new Color(245, 245, 245));
        panRaca.add(Box.createVerticalGlue());
        panRaca.add(imatge);
        panRaca.add(Box.createVerticalStrut(5));
        panRaca.add(descripcio);
        panRaca.add(Box.createVerticalStrut(5));
        panRaca.add(boto);
        panRaca.add(Box.createVerticalGlue());

        panellRaces.add(panRaca);
    }

    private void seleccionarRaca(JButton boto, String nom) {
        // Reiniciem altres botons
        botHuma.setEnabled(true);
        botElf.setEnabled(true);
        botNan.setEnabled(true);
        botOrc.setEnabled(true);

        seleccionatHuma = seleccionatElf = seleccionatNan = seleccionatOrc = false;

        switch (nom) {
            case "huma":
                pj.setAtac(5);
                pj.setDefensa(5);
                pj.setAgilitat(5);
                seleccionatHuma = true;
                break;
            case "elf":
                pj.setAtac(4);
                pj.setDefensa(3);
                pj.setAgilitat(8);
                seleccionatElf = true;
                break;
            case "nan":
                pj.setAtac(6);
                pj.setDefensa(8);
                pj.setAgilitat(3);
                seleccionatNan = true;
                break;
            case "orc":
                pj.setAtac(9);
                pj.setDefensa(6);
                pj.setAgilitat(2);
                seleccionatOrc = true;
                break;
        }

        boto.setEnabled(false);
        fp.getEtAtributs().setText(" Atc:" + pj.getAtac() + " | Def:" + pj.getDefensa() + " | Ag:" + pj.getAgilitat());
    }
}