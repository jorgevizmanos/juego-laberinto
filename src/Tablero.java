import javax.swing.*;
import java.awt.*;

public class Tablero extends JPanel {
    // ATRIBUTOS
    //==================================================================================================================
    private static final int ALTO = 700;
    private static final int ANCHO = 700;
    Laberinto laberinto = new Laberinto();

    // CONSTRUCTORES
    //==================================================================================================================
    public Tablero() {
        this.setSize(ANCHO, ALTO);
        this.setBackground(Color.DARK_GRAY);
    }

    // METODOS
    //==================================================================================================================

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        laberinto.pintarLaberinto(g2d);
    }
}
