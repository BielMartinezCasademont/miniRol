package miniROL;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class Personatge extends Entitat implements Serializable {

    private static final long serialVersionUID = 1L;

    // NOU: Variable per al seguiment de la progressió de l'arma a la botiga (0=Espasa, 1=Catana, etc.)
    private int nivellArmaComprada = 0;

    private int nivell, or, xp, xpNecessaria, danyTotalRebut, danyTotalCausat;

    private int agilitat;

    private Arma arma;
    private Raca raca;

    HashMap<String, Integer> mostresVencuts = new HashMap<>();

    private HashMap<String, Integer> danyCausatPerMonstre = new HashMap<>();
    private HashMap<String, Integer> danyRebutPerMonstre = new HashMap<>();

    public Personatge(String nom, int atac, int defensa, double vidaMax, Raca raca) {
        super(nom, atac, defensa, vidaMax);
        nivell = 1;
        or = 0;
        xp = 0;
        xpNecessaria = 10;

        this.raca = raca;
        this.arma = raca.getArmaInicial();

        this.agilitat = raca.getAgilitatBase();
    }

    // NOU: Getters i Setters per al nivell de l'arma comprada
    public int getNivellArmaComprada() {
        return nivellArmaComprada;
    }

    public void setNivellArmaComprada(int nivellArmaComprada) {
        this.nivellArmaComprada = nivellArmaComprada;
    }

    public boolean equiparArma(Arma novaArma) {
        if (novaArma.potEquipar(this.raca)) {
            this.arma = novaArma;
            System.out.println(getNom() + " ha equipat " + novaArma.getNom());
            return true;
        } else {
            System.out.println(getNom() + " no pot equipar " + novaArma.getNom()
                    + " (restricció de raça: " + this.raca.getNom() + ")");
            return false;
        }
    }

    public Arma getArma() {
        return arma;
    }

    public Raca getRaca() {
        return raca;
    }

    // ... (la resta de mètodes existents: getNivell, getDanyTotalRebut, etc.)

    public int getNivell() {
        return nivell;
    }

    public int getDanyTotalRebut() {
        return danyTotalRebut;
    }

    public int getDanyTotalCausat() {
        return danyTotalCausat;
    }

    @Override
    public int getAgilitat() {
        return agilitat;
    }

    @Override
    public void setAgilitat(int agilitat) {
        this.agilitat = agilitat;
    }

    public void sumarDanyCausat(String nomMonstre, int dany) {
        int actual = danyCausatPerMonstre.getOrDefault(nomMonstre, 0);
        danyCausatPerMonstre.put(nomMonstre, actual + dany);
    }

    public void sumarDanyRebut(String nomMonstre, int dany) {
        int actual = danyRebutPerMonstre.getOrDefault(nomMonstre, 0);
        danyRebutPerMonstre.put(nomMonstre, actual + dany);
    }

    public void pujarNivell() {
        nivell++;
        setAtac(getAtac() + 2);
        setDefensa(getDefensa() + 1);
        setVidaMax(getVidaMax() * 1.1);
        getBarraVida().setMaximum((int) getVidaMax());
        setVidaActual((int) getVidaMax());
        establirVida((int) getVidaMax());
        xpNecessaria += (xpNecessaria + 5);
    }


    public int getXp() {
        return xp;
    }

    public void pujarXp(int quantitat) {
        xp+=quantitat;
        if (xp>=xpNecessaria) pujarNivell();
    }

    public int getXpNecessaria() {
        return xpNecessaria;
    }

    public int getOr() {
        return or;
    }

    public void setOr(int or) {
        this.or = or;
    }

    public void registrarMonstresVencuts(String nomMonstre) {
        if (!mostresVencuts.containsKey(nomMonstre)) {
            mostresVencuts.put(nomMonstre, 1);
        } else {
            mostresVencuts.put(nomMonstre, mostresVencuts.get(nomMonstre) + 1);
        }
    }

    public HashMap<String, Integer> getMostresVencuts() {
        return mostresVencuts;
    }

    public String obtenirEstadistiquesVencuts() {

        String resultat = "";

        resultat += "\n===  ESTADÍSTIQUES DETALLADES DE COMBAT  ===\n";

        resultat += "\n---  DANY FET ---\n";
        for (String nom : danyCausatPerMonstre.keySet()) {
            int dany = danyCausatPerMonstre.get(nom);
            resultat += String.format("- A %s: %d dany\n", nom, dany);
        }

        resultat += "\n---  DANY REBUT ---\n";
        for (String nom : danyRebutPerMonstre.keySet()) {
            int dany = danyRebutPerMonstre.get(nom);
            resultat += String.format("- De %s: %d dany\n", nom, dany);
        }

        if (mostresVencuts.isEmpty()) {
            System.out.println();
            return " \n No has vençut cap monstre en aquesta partida.";
        }

        resultat += "\n ---MONSTRES VENÇUTS--- \n";

        for (String nom : mostresVencuts.keySet()) {
            int vegades = mostresVencuts.get(nom);

            resultat += "- " + nom + ": " + vegades + " vegades \n";
        }
        resultat += "=============================";

        return resultat;
    }
}