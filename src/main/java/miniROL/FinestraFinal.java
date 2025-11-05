package miniROL;

import javax.swing.*;
import java.awt.*;

public class FinestraFinal {

    private JTextArea areaText;
    private JLabel imatge;
    private JButton botoSortir;
    private ImageIcon rutaImatge;

    private JDialog marc;
    private JPanel panellPrincipal;

    private Personatge pj;

    public static final int VICTORIA = 0;
    public static final int DERROTA = 1;

    private int condicio;

    public FinestraFinal(int condicio, Personatge pj) {

        marc= new JDialog();
        panellPrincipal = new JPanel(new BorderLayout());

        areaText = new JTextArea();
        botoSortir = new JButton("Finalitzar");;

        this.pj = pj;
        this.condicio = condicio;

        if (condicio == VICTORIA)  rutaImatge = new ImageIcon("./imatges/victoria.png");
        else rutaImatge = new ImageIcon("./imatges/derrota.png");

        imatge = new JLabel(rutaImatge);
    }

    public void obrir() {

        prepararMissatge();
        muntarEscena();
        marc.setVisible(true);
    }

    private void prepararMissatge() {

        String missatgeFinal;
        String missatgeEstadistiques;

        String estadistiquesMonstres = pj.obtenirEstadistiquesVencuts();

        if (condicio==VICTORIA) {

            missatgeFinal = "\n Has aconseguit derrotar al senyor del castell. Tornes a casa" +
                    "teva amb una victòria.\n"
                    + pj.getNom() + " Nivell: " +pj.getNivell() + " has portat amb tu "
                    + pj.getOr() + " monedes d'or";

            System.out.println();

            missatgeFinal += estadistiquesMonstres;

        } else {

            missatgeFinal = "Has sigut aniquilat en el castell. Els teus sers estimats " +
                    " han enterrat el que quedava de tu.\n"
                    + "Torna a intentartar-ho si t'atreveixes! \n" + estadistiquesMonstres;

        }

        areaText.setText(missatgeFinal);
    }

    private void muntarEscena() {

        panellPrincipal.add(imatge, BorderLayout.NORTH);

        panellPrincipal.add(areaText, BorderLayout.CENTER);

        botoSortir.addActionListener(e-> System.exit(0));


        panellPrincipal.add(botoSortir, BorderLayout.SOUTH);

        marc.add(panellPrincipal);
        marc.setSize(600,500);
        marc.setLocationRelativeTo(null);
        marc.setModal(true);


    }


}

