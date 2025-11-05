package miniROL;

import java.io.*;
import javax.swing.JOptionPane;

public class GestorFitxers {

    private static final String DIRECTORI_SAVE = "./saves/";
    private static final String NOM_FITXER = "personatge.sav";

    public static void guardarPersonatge(Personatge pj) {

        File directori = new File(DIRECTORI_SAVE);
        if (!directori.exists()) {
            directori.mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DIRECTORI_SAVE + NOM_FITXER))) {
            oos.writeObject(pj);
            System.out.println("Personatge guardat a: " + DIRECTORI_SAVE + NOM_FITXER);
        } catch (IOException e) {
            System.err.println("Error al guardar el personatge: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al guardar la partida: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static Personatge carregarPersonatge() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DIRECTORI_SAVE + NOM_FITXER))) {
            Personatge pj = (Personatge) ois.readObject();
            pj.setAgilitat(pj.getRaca().getAgilitatBase());
            System.out.println("Personatge carregat: " + pj.getNom());
            return pj;
        } catch (FileNotFoundException e) {
            System.err.println("Fitxer de personatge no trobat: " + DIRECTORI_SAVE + NOM_FITXER);
            return null;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al carregar o deserialitzar el personatge: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error en el fitxer de guardat. Es crearà un nou personatge.", "Error de Càrrega", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public static boolean existeixPartidaGuardada() {
        File f = new File(DIRECTORI_SAVE + NOM_FITXER);
        return f.exists();
    }
}