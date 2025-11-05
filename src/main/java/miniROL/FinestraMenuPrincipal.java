package miniROL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FinestraMenuPrincipal {

    private JDialog marc;
    private JPanel panellPrincipal;
    private JButton botNou, botCarregar, botSortir;
    private JButton botConfirmarCreacio;
    private JTextField campNom;
    private JComboBox<Raca> selectorRaca;

    public FinestraMenuPrincipal() {
        marc = new JDialog();
        marc.setTitle("MiniROL - Menú Principal");
        marc.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        marc.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        panellPrincipal = new JPanel(new GridLayout(3, 1, 10, 10));

        botNou = new JButton("Crear Nou Personatge");
        botCarregar = new JButton("Carregar Personatge Guardat");
        botSortir = new JButton("Sortir del Joc");

        botNou.addActionListener(e -> mostrarPanellCreacio());
        botCarregar.addActionListener(e -> carregarPartida());
        botSortir.addActionListener(e -> System.exit(0));

        botCarregar.setEnabled(GestorFitxers.existeixPartidaGuardada());

        panellPrincipal.add(botNou);
        panellPrincipal.add(botCarregar);
        panellPrincipal.add(botSortir);

        marc.add(panellPrincipal);
        marc.setSize(350, 200);
        marc.setLocationRelativeTo(null);
        marc.setModal(true);
    }

    public void mostrar() {
        marc.setVisible(true);
    }

    private void mostrarPanellCreacio() {
        marc.getContentPane().removeAll();

        JPanel panellCreacio = new JPanel(new GridLayout(4, 1, 10, 10));
        panellCreacio.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel pNom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        campNom = new JTextField("Heroi", 15);
        pNom.add(new JLabel("Nom del Personatge:"));
        pNom.add(campNom);
        panellCreacio.add(pNom);

        JPanel pRaca = new JPanel(new FlowLayout(FlowLayout.CENTER));

        Raca[] racesDisponibles = Raca.obtenirRaces();

        selectorRaca = new JComboBox<>(racesDisponibles);
        pRaca.add(new JLabel("Selecciona la Raça:"));
        pRaca.add(selectorRaca);
        panellCreacio.add(pRaca);

        botConfirmarCreacio = new JButton("Començar Aventura");
        botConfirmarCreacio.addActionListener(e -> crearNouPersonatge());
        panellCreacio.add(botConfirmarCreacio);

        JButton botTornar = new JButton("Tornar al Menú Principal");
        botTornar.addActionListener(e -> tornarAlMenu());
        panellCreacio.add(botTornar);

        marc.add(panellCreacio);
        marc.revalidate();
        marc.repaint();
        marc.setSize(400, 250);
        marc.setLocationRelativeTo(null);
    }

    private void tornarAlMenu() {
        marc.getContentPane().removeAll();
        marc.add(panellPrincipal);
        marc.revalidate();
        marc.repaint();
        marc.setSize(350, 200);
        marc.setLocationRelativeTo(null);
    }

    private void crearNouPersonatge() {
        String nom = campNom.getText().trim();
        Raca racaSeleccionada = (Raca) selectorRaca.getSelectedItem();

        if (nom.isEmpty()) {
            JOptionPane.showMessageDialog(marc, "El nom no pot estar buit.", "Error de Creació", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Personatge heroi = new Personatge(nom, 6, 2, 25, racaSeleccionada);

        heroi.equiparArma(racaSeleccionada.getArmaInicial());

        JOptionPane.showMessageDialog(marc, "Aventura començada amb " + nom + " (" + racaSeleccionada.getNom() + ")!", "A punt!", JOptionPane.INFORMATION_MESSAGE);

        iniciarJoc(heroi);
    }

    private void carregarPartida() {
        Personatge heroiCarregat = GestorFitxers.carregarPersonatge();
        if (heroiCarregat != null) {
            JOptionPane.showMessageDialog(marc, "Personatge '" + heroiCarregat.getNom() + "' carregat correctament!", "Càrrega OK", JOptionPane.INFORMATION_MESSAGE);
            iniciarJoc(heroiCarregat);
        } else {
            tornarAlMenu();
        }
    }

    private void iniciarJoc(Personatge heroi) {
        marc.dispose();
        FinestraPrincipal joc = new FinestraPrincipal(heroi);
        joc.StartJoc();
    }
}