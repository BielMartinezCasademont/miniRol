package miniROL;

import java.io.Serializable;
import java.util.Set;
import java.util.Collections;

public class Arma implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nom;
    private int danyBase;
    private String descripcio;
    private Set<String> racesRestringides;

    public Arma(String nom, int danyBase, String descripcio, Set<String> racesRestringides) {
        this.nom = nom;
        this.danyBase = danyBase;
        this.descripcio = descripcio;
        this.racesRestringides = racesRestringides;
    }

    public Arma(String nom, int danyBase, String descripcio) {
        this(nom, danyBase, descripcio, Collections.emptySet());
    }

    public int tirarAtac(int atacPersonatge, int defensaEnemic) {
        int d20 = (int) (Math.random() * 20) + 1;

        int tiradaTotal = d20 + atacPersonatge;

        int danyCalculat = this.danyBase + atacPersonatge;
        String missatge;

        if (d20 == 20) {
            danyCalculat *= 2;
            missatge = "CRÍTIC! (D20: 20) El dany es doble!";
            System.out.println("Dau D20: " + missatge);
            return danyCalculat;

        } else if (d20 == 1) {
            missatge = "FALLA! (D20: 1) L'atac ha fallat completament.";
            System.out.println("Dau D20: " + missatge);
            return 0;

        } else if (tiradaTotal > defensaEnemic) {
            missatge = String.format("Impacte! (Dau pur: %d, Tirada Modificada: %d vs. Defensa: %d)", d20, tiradaTotal, defensaEnemic);
            System.out.println("Dau D20: " + missatge);
            return danyCalculat;

        } else {
            missatge = String.format("Bloquejat! (Tirada: %d vs. Defensa: %d)", tiradaTotal, defensaEnemic);
            System.out.println("Dau D20: " + missatge);
            return 0;
        }
    }

    public int calcularDanyAtac(int atacPersonatge) {
        return 0;
    }

    public boolean potEquipar(Raca raca) {
        return !racesRestringides.contains(raca.getNom());
    }

    public String getNom() {
        return nom;
    }

    public int getDanyBase() {
        return danyBase;
    }

    public String getDescripcio() {
        return descripcio;
    }
}