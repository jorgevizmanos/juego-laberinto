import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Tablero extends JPanel {
    // ATRIBUTOS
    //==================================================================================================================
    private static final int ALTO = 700;
    private static final int ANCHO = 700;
    Laberinto laberinto = new Laberinto();
    Calabaza calabaza = new Calabaza();
    Pocion pocion = new Pocion();
    Random random = new Random();

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

        iniciarPosicionRandomCalabaza();

        laberinto.pintarLaberinto(g2d);
        pocion.pintar(g2d);
        calabaza.pintar(g2d);
    }

    public void iniciarPosicionRandomCalabaza() {
        calabaza.setX(random.nextInt(ANCHO));
        calabaza.setY(random.nextInt(ALTO));
        System.out.println(calabaza.getX() + " " + calabaza.getY());
    }


}
