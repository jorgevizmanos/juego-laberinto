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
        iniciarPosicionRandomPocion();

        laberinto.pintarLaberinto(g2d);
        pocion.pintar(g2d);
        calabaza.pintar(g2d);
    }

    public void iniciarPosicionRandomCalabaza() {
        int[][] mazeArray = laberinto.crearLaberinto();
        boolean posicionValida = false;

        while (!posicionValida) {
            // Obtener posición aleatoria en el grid
            int gridX = random.nextInt(28); // numeroColumna
            int gridY = random.nextInt(28); // numeroFila

            // Verificar si es pasillo (0)
            if (mazeArray[gridY][gridX] == 0) {
                // Convertir posición de grid a píxeles
                calabaza.setX(gridX * 25); // anchoBloque
                calabaza.setY(gridY * 25); // altoBloque
                posicionValida = true;
            }
        }
    }

    public void iniciarPosicionRandomPocion() {
        int[][] mazeArray = laberinto.crearLaberinto();
        boolean posicionValida = false;

        while (!posicionValida) {
            // Obtener posición aleatoria en el grid
            int gridX = random.nextInt(28); // numeroColumna
            int gridY = random.nextInt(28); // numeroFila

            // Verificar si es pasillo (0)
            if (mazeArray[gridY][gridX] == 0) {
                // Convertir posición de grid a píxeles
                pocion.setX(gridX * 25); // anchoBloque
                pocion.setY(gridY * 25); // altoBloque
                posicionValida = true;
            }
        }
    }
}
