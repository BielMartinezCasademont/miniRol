package miniROL;

import javax.swing.*;
import java.awt.*;

public class Monstre extends Entitat{

    private int premiOr;
    private int premiXp;

    private JLabel etNom, imatge;

    private static String[] nomsFacil = {"Llop","Esquelet","Zombi"};
    private static String[] nomsMitja = {"Troll","Golem","Gargola"};
    private static String[] nomsDificil = {"Bruixot","Momia","Dimoni"};

    public Monstre(String nom, int atac, int defensa, double vidaMax, String dificultat) {
        super(nom, atac, defensa, vidaMax);

        etNom = new JLabel(nom);
        etNom.setFont(new Font("Roboto",Font.BOLD, 20));
        String rutaImatge = "./imatges/" + nom.toLowerCase() + ".png";
        imatge = new JLabel();
        imatge.setIcon(new ImageIcon(rutaImatge));

        switch (dificultat){

            case "facil":
                premiXp = (int) (Math.random()*2+1);
                premiOr = (int) (Math.random()*5+1);
                etNom.setForeground(Color.GREEN);
                break;
            case "mitja":
                premiXp = (int) (Math.random()*6+1);
                premiOr = (int) (Math.random()*20+1);
                etNom.setForeground(Color.ORANGE);
                break;
            case "dificil":
                premiXp = (int) (Math.random()*16+1);
                premiOr = (int) (Math.random()*50+1);
                etNom.setForeground(Color.RED);
                break;
            default:
                premiXp = 500;
                premiOr = 1000;
                break;
        }
    }

    @Override
    public void atacar(IAtacable enemic) {
        enemic.rebreFerida(getAtac());
    }

    public static Monstre generaMontre(int i){

        Monstre m;

        int nMonstre=(int) (Math.random()*3);
        int nVida = (int) (Math.random()*30);
        int nAtac = (int) (Math.random()*5);
        int nDefensa = (int) (Math.random()*2);

        if (i<80) {

            m = new Monstre(nomsFacil[nMonstre], nAtac+1, nDefensa, nVida+15, "facil");

        } else if (i<140){
            m = new Monstre(nomsMitja[nMonstre], nAtac+4, nDefensa+2, nVida+30, "mitja");

        } else if (i<200){
            m = new Monstre(nomsDificil[nMonstre], nAtac+8, nDefensa+5, nVida+80, "dificil");
        } else {
            m = new Monstre("Boss", nAtac+15, nDefensa+10, nVida+150, "jefe");
        }

        return m;
    }


    public int getPremiOr() {
        return premiOr;
    }
    public void setPremiOr(int premiOr) {
        this.premiOr = premiOr;
    }
    public JLabel getEtNom() {
        return etNom;
    }
    public void setEtNom(JLabel etNom) {
        this.etNom = etNom;
    }
    public int getPremiXp() {
        return premiXp;
    }
    public void setPremiXp(int premiXp) {
        this.premiXp = premiXp;
    }
    public JLabel getImatge() {
        return imatge;
    }

    public void setImatge(JLabel imatge) {
        this.imatge = imatge;
    }
}