package miniROL;

import java.util.Collections;
import java.util.Set;
import java.io.Serializable;

public class Raca implements Serializable {
    private String nom;
    private Arma armaInicial;
    private String bonus;
    private int agilitatBase;

    public static final Raca HUMA = new Raca("Humà",
            new Arma("Espasa d'Acer", 3, "Una espasa ben equilibrada.", Collections.emptySet()),
            "Cap bonus especial, són versàtils.", 2);

    public static final Raca ELF = new Raca("Elf",
            new Arma("Arc Llarg", 1, "Un arc que compensa el poc dany amb precisió.", Collections.emptySet()),
            "Petita bonificació a l'Agilitat." , 4);

    public static final Raca NAN = new Raca("Nan",
            new Arma("Destral de Batalla", 4, "Una destral pesada que causa gran dany.", Collections.emptySet()),
            "Petita bonificació a la Vida Màxima.", 1);

    public static final Arma ESPASA_GRAN = new Arma("Espasa a Dues Mans", 8,
            "Una espasa massa gran per ser usada per algunes races.",
            Set.of(NAN.getNom()));

    // <-- AFEGEIX AQUEST ARRAY ESTÀTIC AMB LES RACES DISPONIBLES
    private static final Raca[] RACES_DISPONIBLES = {HUMA, ELF, NAN};

    // <-- AFEGEIX AQUEST MÈTODE ESTÀTIC PER OBTENIR L'ARRAY
    public static Raca[] obtenirRaces() {
        return RACES_DISPONIBLES;
    }

    private Raca(String nom, Arma armaInicial, String bonus, int agilitatBase) {
        this.nom = nom;
        this.armaInicial = armaInicial;
        this.bonus = bonus;
        this.agilitatBase = agilitatBase;
    }

    public String getNom() {
        return nom;
    }

    public Arma getArmaInicial() {
        return armaInicial;
    }

    public String getBonus() {
        return bonus;
    }

    public int getAgilitatBase() {
        return agilitatBase;
    }

    @Override
    public String toString() {
        return nom;
    }
}