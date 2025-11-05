package miniROL;

import javax.swing.*;
import java.awt.*;
import marcPanellPersonalitzat.FramePer;

public class FinestraPrincipal {


    private FramePer marc;
    private JPanel panellPrincipal, panellSuperior, panellInferior;

    private JLabel etNom, etNivell, etXp, etOr, etAtributs;
    private JLabel etImatge;

    private JButton botExplorar, botBotiga, botGuardar;

    private Personatge pj;

    public FinestraPrincipal(Personatge pj) {

        this.pj = pj;

        marc = new FramePer(600,500,"Mini ROL", true);
        panellPrincipal = new JPanel(new BorderLayout());
        panellSuperior = new JPanel();
        panellInferior = new JPanel();

        etNom = new JLabel(pj.getNom()+ "       ");
        etNivell = new JLabel(" Lvl:" + pj.getNivell());
        etXp = new JLabel(" Xp:"+ pj.getXp() + "/" + pj.getXpNecessaria());
        etOr = new JLabel(" Or: "+ pj.getOr());

        etAtributs = new JLabel(" Atc:" + pj.getAtac()+ " | Def: "+ pj.getDefensa() + " | Ag: " + pj.getAgilitat());
        etImatge = new JLabel();
        botExplorar = new JButton("Explorar");
        botBotiga = new JButton("Botiga");
        botGuardar = new JButton("Guardar Partida");

    }

    public void StartJoc(){
        muntarEscena();
        marc.setVisible(true);

    }

    private void muntarEscena() {

        modificarFonts();

        panellSuperior.add(etNom);
        panellSuperior.add(etNivell);
        panellSuperior.add(etXp);
        panellSuperior.add(etOr);
        panellSuperior.add(etAtributs);

        panellSuperior.add(pj.getBarraVida());


        etImatge.setIcon(new ImageIcon("./imatges/castell.jpg"));
        panellPrincipal.add(etImatge, BorderLayout.CENTER);

        botExplorar.addActionListener(e->novaExploracio());
        botBotiga.addActionListener(e ->obrirBotiga());
        botGuardar.addActionListener(e -> guardarPartida());

        panellInferior.add(botExplorar);
        panellInferior.add(botBotiga);
        panellInferior.add(botGuardar);

        panellPrincipal.add(panellSuperior, BorderLayout.NORTH);
        panellPrincipal.add(panellInferior, BorderLayout.SOUTH);

        marc.add(panellPrincipal);


    }

    private void guardarPartida() {
        GestorFitxers.guardarPersonatge(pj);
        JOptionPane.showMessageDialog(marc,
                "Partida de " + pj.getNom() + " guardada amb èxit!",
                "Guardat",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void triaClasse() {
        CatalegRaces cr = new CatalegRaces(this);
        cr.obrirCataleg();
        panellPrincipal.add(panellSuperior, BorderLayout.NORTH);
        marc.repaint();
    }

    private void obrirBotiga() {
        Botiga b  = new Botiga(this,pj);
        b.obrirBotiga();
        panellPrincipal.add(panellSuperior, BorderLayout.NORTH);
        marc.repaint();
    }

    private void modificarFonts() {

        Font mevafont= new Font("Roboto", Font.BOLD, 20);

        etNom.setFont(mevafont);
    }

    private void novaExploracio() {

        Exploracio explora = new Exploracio(this);
        explora.startExploracio();
        panellPrincipal.add(panellSuperior, BorderLayout.NORTH);
        marc.repaint();

    }

    public Personatge getPj() {
        return pj;
    }

    public JPanel getPanellSuperior() {
        return panellSuperior;
    }

    public FramePer getMarc() {
        return marc;
    }

    public JLabel getEtAtributs() {
        return etAtributs;
    }

    public JLabel getEtXp() {
        return etXp;
    }

    public JLabel getEtNivell() {
        return etNivell;
    }

    public JLabel getEtOr() {
        return etOr;
    }
}