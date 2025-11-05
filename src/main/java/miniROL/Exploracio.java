package miniROL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Exploracio {

    private JDialog marc;
    private JPanel panellPrincipal, panellSuperior, panellInferior, panellMonstre, panellMonstreSec;
    private JButton botAtacar, botFugir;
    private JTextArea infoExploracio;
    private JScrollPane barraDes;

    private Personatge pj;
    private Monstre enemic;
    private FinestraPrincipal fp;

    private boolean esBoss;
    private static int numExploracio = 0;

    // Bandera de control per a la lògica del botó Fugir/Tornar
    private boolean combatFinalitzat = false;

    public Exploracio(FinestraPrincipal fp) {

        this.fp = fp;
        pj = fp.getPj();
        marc = new JDialog();

        // 1. Inicialització de l'enemic per obtenir el seu nom (CORRECCIÓ D'ORDRE)
        decidirDificultat();

        marc.setTitle("Combat contra " + enemic.getNom());

        // 2. Inicialització de Components Swing
        panellPrincipal = new JPanel(new BorderLayout());
        panellSuperior = fp.getPanellSuperior();
        panellInferior = new JPanel();
        panellMonstre = new JPanel();
        panellMonstreSec = new JPanel();

        infoExploracio = new JTextArea();
        infoExploracio.setEditable(false);
        infoExploracio.setLineWrap(true);
        infoExploracio.setWrapStyleWord(true);

        barraDes = new JScrollPane(infoExploracio);
        barraDes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        botAtacar = new JButton("Atacar");
        botFugir = new JButton("Fugir");
    }

    public void startExploracio() {
        montarInteficie();
    }

    private void decidirDificultat() {
        int numAlea = (int) (Math.random() * 100) + numExploracio;
        numExploracio++;
        enemic = Monstre.generaMontre(numAlea);
        if (enemic.getNom().equals("Boss")) esBoss = true;
    }

    private void montarInteficie() {

        panellPrincipal.add(barraDes, BorderLayout.CENTER);

        panellMonstreSec.add(enemic.getEtNom());
        panellMonstreSec.add(enemic.getBarraVida());

        panellMonstre.setLayout(new BoxLayout(panellMonstre, BoxLayout.Y_AXIS));
        panellMonstre.add(enemic.getImatge());
        panellMonstre.add(panellMonstreSec);

        // Assignació de les accions als botons
        botAtacar.addActionListener(e -> atacar());
        botFugir.addActionListener(e -> gestionarSortida()); // Crida al mètode de control

        panellInferior.add(botAtacar);
        panellInferior.add(new JLabel("          "));
        panellInferior.add(botFugir);

        panellPrincipal.add(panellSuperior, BorderLayout.NORTH);
        panellPrincipal.add(panellInferior, BorderLayout.SOUTH);
        panellPrincipal.add(panellMonstre, BorderLayout.EAST);

        marc.add(panellPrincipal);
        marc.setSize(750, 500);
        marc.setLocationRelativeTo(null);
        marc.setModal(true);

        infoExploracio.setText("Ha aparegut un " + enemic.getNom() + "!\n");

        marc.setVisible(true);
    }

    // ----------------------------------------------------
    // CONTROL DEL BOTÓ FUGIR / TORNAR
    // ----------------------------------------------------
    private void gestionarSortida() {
        if (combatFinalitzat) {
            // Si el combat ha acabat (guanyar o fugida exitosa), tanca la finestra
            marc.dispose();
        } else {
            // Si el combat segueix actiu, intenta fugir
            intentarFugir();
        }
    }

    // ----------------------------------------------------
    // LÒGICA D'INTENT DE FUGIDA
    // ----------------------------------------------------
    private void intentarFugir() {

        if (!pj.isEstaViu()){
            derrota();
            return;
        }

        final int REQUISIT_FUGIDA = 15;
        int d20 = (int) (Math.random() * 20) + 1;

        int tiradaAgilitat = pj.getAgilitat() + d20;

        infoExploracio.append("\n*** INTENT DE FUGIDA ***\n");
        infoExploracio.append(String.format(" Tirada d'Agilitat: D20 (%d) + Agilitat Base (%d) = **%d**\n", d20, pj.getAgilitat(), tiradaAgilitat));

        if (tiradaAgilitat >= REQUISIT_FUGIDA) {

            infoExploracio.append(" **ÈXIT!** Has aconseguit escapar del combat.\n***************************\n");

            botAtacar.setEnabled(false);

            // 🚨 HABILITACIÓ DE LA SORTIDA DESPRÉS DE FUGIR
            botFugir.setText("Tornar");
            botFugir.setEnabled(true);
            combatFinalitzat = true; // Permet sortir al següent clic

        } else {

            infoExploracio.append(String.format(" **FRACÀS!** El teu intent de fugida ha estat bloquejat (Requerit: %d).\n\n", REQUISIT_FUGIDA));

            // Bloqueig de la fugida fallida
            botFugir.setEnabled(false);

            // Penalització: Torn de l'enemic
            int vidaPersonatgeAbans = pj.getVidaActual();

            enemic.atacar(pj);
            infoExploracio.append(enemic.getNom() + " aprofita la teva distracció i ataca amb una força de " + enemic.getAtac() + ".\n");

            int danyReialRebut = vidaPersonatgeAbans - pj.getVidaActual();

            pj.sumarDanyRebut(enemic.getNom(), danyReialRebut);

            if (danyReialRebut > 0) {
                infoExploracio.append(pj.getNom() + " ha rebut " + danyReialRebut + " de mal.\n\n" );
            } else {
                infoExploracio.append(pj.getNom() + " ha bloquejat l'atac (Defensa superior).\n\n" );
            }

            pj.establirVida(pj.getVidaActual());

            infoExploracio.append("***************************\n");

            if (!pj.isEstaViu()) {
                derrota();
            }
        }
        infoExploracio.setCaretPosition(infoExploracio.getDocument().getLength());
    }

    // ----------------------------------------------------
    // LÒGICA D'ATAC
    // ----------------------------------------------------
    private void atacar() {

        if (!pj.isEstaViu()) {
            derrota();
            return;
        }

        // Torn del Personatge
        String nomEnemic = enemic.getNom();
        int vidaEnemicAbans = enemic.getVidaActual();
        int danyBaseSenseDefensa = pj.getArma().tirarAtac(pj.getAtac(), enemic.getDefensa());

        if (danyBaseSenseDefensa > 0) {
            enemic.rebreFerida(danyBaseSenseDefensa);
        }

        int danyReialFet = vidaEnemicAbans - enemic.getVidaActual();
        pj.sumarDanyCausat(nomEnemic, danyReialFet);

        infoExploracio.append(pj.getNom() + " intenta atacar amb un dany base de " + danyBaseSenseDefensa + ".\n");

        if (danyReialFet > 0) {
            infoExploracio.append(enemic.getNom() + " ha rebut " + danyReialFet + " de mal gràcies a la seva defensa.\n\n");
        } else {
            infoExploracio.append(enemic.getNom() + " ha bloquejat o evitat l'atac.\n\n");
        }

        enemic.establirVida(enemic.getVidaActual());

        if (!enemic.isEstaViu()) {
            enemicDerrotat();
            return;
        }

        // Torn de l'Enemic
        int vidaPersonatgeAbans = pj.getVidaActual();

        enemic.atacar(pj);
        infoExploracio.append(enemic.getNom() + " ataca amb una força de " + enemic.getAtac() + ".\n");

        int danyReialRebut = vidaPersonatgeAbans - pj.getVidaActual();

        pj.sumarDanyRebut(nomEnemic, danyReialRebut);

        if (danyReialRebut > 0) {
            infoExploracio.append(pj.getNom() + " ha rebut " + danyReialRebut + " de mal gràcies a la seva defensa.\n\n");
        } else {
            infoExploracio.append(pj.getNom() + " ha bloquejat o evitat l'atac de l'enemic (Defensa superior).\n\n");
        }

        pj.establirVida(pj.getVidaActual());

        if (!pj.isEstaViu()) {
            derrota();
        }

        infoExploracio.setCaretPosition(infoExploracio.getDocument().getLength());
    }

    // ----------------------------------------------------
    // MONSTRE DERROTAT
    // ----------------------------------------------------
    private void enemicDerrotat() {

        botAtacar.setEnabled(false);

        // HABILITEM LA SORTIDA DESPRÉS DE DERROTAR EL MONSTRE
        botFugir.setText("Tornar");
        botFugir.setEnabled(true);
        combatFinalitzat = true; // Permet sortir al següent clic


        String nomMonstre = enemic.getNom();

        if (!pj.getMostresVencuts().containsKey(nomMonstre)) {
            pj.getMostresVencuts().put(nomMonstre, 1);
        } else {
            int compteActual = pj.getMostresVencuts().get(nomMonstre);
            pj.getMostresVencuts().put(nomMonstre, compteActual + 1);
        }

        if (esBoss) {
            FinestraFinal f = new FinestraFinal(FinestraFinal.VICTORIA, pj);
            marc.dispose();
            f.obrir();
            return;
        }

        infoExploracio.append(enemic.getNom() + " ha sigut derrotat \n");
        infoExploracio.append(String.format("Has obtingut %d XP i %d Or.\n", enemic.getPremiXp(), enemic.getPremiOr()));

        pj.pujarXp(enemic.getPremiOr());
        pj.setOr(pj.getOr() + enemic.getPremiOr());

        // Actualització dels Labels de la Finestra Principal
        fp.getEtXp().setText(" Xp:" + pj.getXp() + "/" + pj.getXpNecessaria());
        fp.getEtNivell().setText(" Lvl:" + pj.getNivell());
        fp.getEtAtributs().setText(" Atc:" + pj.getAtac() + " | Def: " + pj.getDefensa() + " | Ag: " + pj.getAgilitat());
        fp.getEtOr().setText(" Or: " + pj.getOr());

        infoExploracio.setCaretPosition(infoExploracio.getDocument().getLength());
    }

    private void derrota() {
        FinestraFinal f = new FinestraFinal(FinestraFinal.DERROTA, pj);
        marc.dispose();
        f.obrir();
    }

    public static int getNumExploracio() {
        return numExploracio;
    }

    public static void setNumExploracio(int numExploracio) {
        Exploracio.numExploracio = numExploracio;
    }
}