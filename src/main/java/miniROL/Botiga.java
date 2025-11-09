package miniROL;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import javax.swing.JOptionPane;

public class Botiga {

    // NOU: Estructura de dades per a la seqüència d'armes
    private static class ArmaVenda {
        final String nom;
        final int atacBonus;
        final int preu;
        final String rutaImatge;

        ArmaVenda(String nom, int atacBonus, int preu, String rutaImatge) {
            this.nom = nom;
            this.atacBonus = atacBonus;
            this.preu = preu;
            this.rutaImatge = rutaImatge;
        }
    }

    // NOU: Array amb les armes i els seus atributs
    private static final ArmaVenda[] SEQ_ARMES = {
            new ArmaVenda("Espasa", 2, 50, "./imatges/espasa.png"), // +2 Atc, 100 Or
            new ArmaVenda("Catana", 3, 100, "./imatges/catana.png"), // +3 Atc, 250 Or
            new ArmaVenda("Martell Medieval", 4, 150, "./imatges/martell.png"), // +4 Atc, 450 Or
            new ArmaVenda("Flagell Medieval", 5, 200, "./imatges/flagell.png") // +5 Atc, 700 Or (Màxim)
    };

    private JDialog marc;

    private JPanel panellPrincipal,  panellSuperior, panellInferior, panellBotiga;
    private JPanel panellEspasa, panellEscut, panellPocio, panellMapa;

    private JLabel imatgeEspasa, imatgeEscut, imatgePocio, imatgeMapa;
    private JLabel desEspasa, desEscut, desPocio, desMapa;

    // CANVI: Esborra 'static' dels botons de la botiga
    private JButton botEspasa, botEscut, botPocio, botMapa;

    // CANVI: Esborra 'agoEspasa' ja que es gestiona per l'array
    private static boolean agoEscut=false, agoPocio=false, agoMapa=false;

    private JButton botSortir;

    private FinestraPrincipal fp;
    private Personatge pj;

    public Botiga(FinestraPrincipal fp, Personatge pj) {
        this.pj = pj;
        this.fp = fp;

        marc = new JDialog();

        // ... (la resta d'inicialitzacions)

        panellPrincipal = new JPanel(new BorderLayout());
        panellSuperior = fp.getPanellSuperior();
        panellInferior = new JPanel();
        panellBotiga = new JPanel(new GridLayout(2,2));

        panellEspasa = new JPanel();
        panellEscut = new JPanel();
        panellPocio = new JPanel();
        panellMapa = new JPanel();

        // Les imatges inicials es mantenen, seran actualitzades a montarInterficie
        imatgeEspasa = new JLabel(new ImageIcon("./imatges/espasa.png"));
        imatgeEscut = new JLabel(new ImageIcon("./imatges/escut.png"));
        imatgePocio = new JLabel(new ImageIcon("./imatges/pocio.png"));
        imatgeMapa = new JLabel(new ImageIcon("./imatges/mapa.png"));

        desEspasa = new JLabel("Espasa - 100 or.");
        desEscut = new JLabel("Escut - 100 or.");
        desPocio = new JLabel("Pocio - 50 or.");
        desMapa = new JLabel("Mapa - 10 or.");

        botEspasa = new JButton("Comprar");
        botEscut = new JButton("Comprar");
        botPocio = new JButton("Comprar");
        botMapa = new JButton("Comprar");

        botSortir = new JButton("Sortir");
    }

    public void obrirBotiga(){
        montarInterficie();
        marc.setVisible(true);
    }

    private void montarInterficie() {

        panellPrincipal.add(panellSuperior, BorderLayout.NORTH);

        // NOU: Crida al mètode d'actualització abans de muntar el panell
        actualitzarVistaArma();

        // CANVI: Modificació de la crida, ja no utilitza agoEspasa
        addObjecte(panellEspasa, imatgeEspasa, desEspasa, botEspasa, "espasa", false);

        // La resta es manté
        addObjecte(panellEscut, imatgeEscut, desEscut, botEscut,"escut", agoEscut);
        addObjecte(panellPocio, imatgePocio, desPocio, botPocio, "pocio", agoPocio);
        addObjecte( panellMapa, imatgeMapa, desMapa, botMapa,"mapa", agoMapa);

        panellPrincipal.add(panellBotiga, BorderLayout.CENTER);

        botSortir.addActionListener(e->marc.dispose());
        panellInferior.add(botSortir);
        panellPrincipal.add(panellInferior, BorderLayout.SOUTH);

        marc.setSize(600,600);
        marc.setLocationRelativeTo(null);
        marc.setModal(true);
        marc.add(panellPrincipal);
    }

    // NOU MÈTODE: Actualitza la visualització de l'arma
    private void actualitzarVistaArma() {
        int nivellActual = pj.getNivellArmaComprada();

        if (nivellActual >= SEQ_ARMES.length) {
            // ** ARMA AL MÀXIM **
            botEspasa.setEnabled(false);
            botEspasa.setText("Arma al Màxim");
            desEspasa.setText("Arma al màxim: " + SEQ_ARMES[SEQ_ARMES.length - 1].nom + " (+5 Atc)");
            imatgeEspasa.setIcon(new ImageIcon(SEQ_ARMES[SEQ_ARMES.length - 1].rutaImatge));

        } else {
            // ** HI HA UNA NOVA ARMA DISPONIBLE **
            ArmaVenda armaSeguent = SEQ_ARMES[nivellActual];

            botEspasa.setEnabled(true);
            botEspasa.setText("Comprar (" + armaSeguent.preu + " or)");

            desEspasa.setText(armaSeguent.nom + " - Atac +" + armaSeguent.atacBonus + " - " + armaSeguent.preu + " or.");

            imatgeEspasa.setIcon(new ImageIcon(armaSeguent.rutaImatge));
        }
    }


    private void addObjecte(JPanel panObjecte, JLabel imatge, JLabel descripcio, JButton boto, String nom, boolean esgotat) {

        imatge.setAlignmentX(Component.CENTER_ALIGNMENT);
        descripcio.setAlignmentX(Component.CENTER_ALIGNMENT);
        boto.setAlignmentX(Component.CENTER_ALIGNMENT);

        // CANVI: Només desactiva si no és l'espasa i està esgotat (l'espasa es gestiona a actualitzarVistaArma)
        if(!nom.equals("espasa") && esgotat) boto.setEnabled(false);

        boto.addActionListener(e->comprarObjecte(boto, nom));

        panObjecte.setLayout(new BoxLayout(panObjecte, BoxLayout.Y_AXIS));
        panObjecte.add(imatge);
        panObjecte.add(descripcio);
        panObjecte.add((boto));

        panellBotiga.add(panObjecte);
    }

    private void comprarObjecte(JButton boto, String nom) {

        switch(nom) {

            case "espasa":
                int nivellActual = pj.getNivellArmaComprada();

                if (nivellActual < SEQ_ARMES.length) {
                    ArmaVenda armaAComprar = SEQ_ARMES[nivellActual];

                    if (pj.getOr() >= armaAComprar.preu) {

                        // 1. APLICAR BONUS D'ATAC
                        pj.setAtac(pj.getAtac() + armaAComprar.atacBonus);

                        // 2. ACTUALITZAR OR I NIVELL D'ARMA
                        pj.setOr(pj.getOr() - armaAComprar.preu);
                        pj.setNivellArmaComprada(nivellActual + 1);

                        // 3. ACTUALITZAR VISTES
                        fp.getEtAtributs().setText(" Atc:" + pj.getAtac()+ " | Def: "+ pj.getDefensa() + " | Ag: " + pj.getAgilitat());
                        fp.getEtOr().setText(" Or: "+ pj.getOr());

                        actualitzarVistaArma();

                    } else {
                        JOptionPane.showMessageDialog(marc, "No tens prou or per comprar el/la " + armaAComprar.nom + " (" + armaAComprar.preu + " or).", "Or Insuficient", JOptionPane.WARNING_MESSAGE);
                    }
                }
                break;

            case "escut":
                if (pj.getOr()>=100 && !agoEscut){
                    pj.setDefensa(pj.getDefensa()+ 1);
                    fp.getEtAtributs().setText(" Atc:" + pj.getAtac()+ "| Def: "+ pj.getDefensa() + " | Ag: " + pj.getAgilitat());
                    pj.setOr(pj.getOr() - 100);
                    fp.getEtOr().setText(" Or: "+ pj.getOr());
                    boto.setEnabled(false);
                    agoEscut = true;
                } else if (!agoEscut) {
                    JOptionPane.showMessageDialog(marc, "No tens prou or per comprar l'escut.", "Or Insuficient", JOptionPane.WARNING_MESSAGE);
                }
                break;

            case "pocio":
                if (pj.getOr()>=50){
                    pj.setVidaActual((int)pj.getVidaMax());
                    pj.establirVida(pj.getVidaActual());
                    pj.setOr(pj.getOr() - 50);
                    fp.getEtOr().setText(" Or: "+ pj.getOr());
                    // NO desactivem el botó de la poció, ja que potser es vol comprar més d'una vegada
                }
                break;

            case "mapa":
                if (pj.getOr()>=10 && !agoMapa){
                    Exploracio.setNumExploracio(250);
                    pj.setOr(pj.getOr() - 10);
                    fp.getEtOr().setText(" Or: "+ pj.getOr());
                    boto.setEnabled(false);
                    agoMapa = true;
                } else if (!agoMapa) {
                    JOptionPane.showMessageDialog(marc, "No tens prou or per comprar el mapa.", "Or Insuficient", JOptionPane.WARNING_MESSAGE);
                }
                break;
        }
    }


}