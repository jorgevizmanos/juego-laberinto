import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Tablero extends JPanel implements ActionListener {

    // ATRIBUTOS
    //==================================================================================================================
    private static final int ALTO = 700;
    private static final int ANCHO = 700;

    private Laberinto lab = new Laberinto();
    private int laberinto[][];
    private ArrayList<Calabaza> calabazas; // lista de 5 objetos Calabaza
    private Pocion pocion = new Pocion();

    private Random random = new Random();
    private Timer timer; // un unico timer sobre todos los elementos del tablero

    // CONSTRUCTORES
    //==================================================================================================================
    public Tablero() {
        // propiedades del tablero
        this.setSize(ANCHO, ALTO);
        this.setBackground(Color.DARK_GRAY);

        // creacion de los elementos (personajes, calabazas y pocion)
        crearLaberinto();
        crearCalabazas();

        // inicializacion de los elementos del tablero y el timer
        iniciarPosicionRandomCalabaza();
        iniciarPosicionRandomPocion();
        iniciarTimer();
    }

    private int[][] crearLaberinto() {
        return laberinto = lab.crearLaberinto();
    }

    // METODOS
    //==================================================================================================================
    public void iniciarTimer() {
        timer = new Timer(20, this); // 'this' es la implementacion ActionListener en esta clase
                                                    // y su metodo actionPerfomed()
        timer.start();
    }

    // ACTION PERFORMED
    @Override
    public void actionPerformed(ActionEvent e) {
        // actualizar movimientos de las calabazas
        for (Calabaza calabaza : calabazas) {
            calabaza.moverCalabaza(laberinto);
        }

        // actualizar posiciones de los personajes

        // detecar colisiones

        // actualizar el tiempo (para imprimirlo por pantalla)

        // comprobar estado del juego (si ha ganado algun jugador o si ha terminado el tiempo)

        repaint(); // CRUCIAL!!!!!
    }

    // PINTAR EL TABLERO
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        lab.pintarLaberinto(g2d);
        pocion.pintar(g2d);

        // recorremos el ArrayList de calabazas para pintar cada calabaza (5)
        for (Calabaza calabaza : calabazas) {
            calabaza.pintar(g2d);
        }
    }

    // CALABAZAS
    private void crearCalabazas() {
        calabazas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            calabazas.add(new Calabaza());
        }
    }

    public void iniciarPosicionRandomCalabaza() {
        // Inicializar posición para cada calabaza del ArrayList
        for (Calabaza calabaza : calabazas) {
            boolean posicionValida = false;

            while (!posicionValida) {
                int gridX = random.nextInt(28);
                int gridY = random.nextInt(28);

                if (laberinto[gridY][gridX] == 0) {
                    calabaza.setX(gridX * 25);
                    calabaza.setY(gridY * 25);
                    posicionValida = true;
                }
            }
        }
    }

    // POCION
    public void iniciarPosicionRandomPocion() {
        boolean posicionValida = false;

        while (!posicionValida) {
            // Obtener posición aleatoria en el grid
            int gridX = random.nextInt(28); // numeroColumna
            int gridY = random.nextInt(28); // numeroFila

            // Verificar si es pasillo (0)
            if (laberinto[gridY][gridX] == 0) {
                // Convertir posición de grid a píxeles
                pocion.setX(gridX * 25); // anchoBloque
                pocion.setY(gridY * 25); // altoBloque
                posicionValida = true;
            }
        }
    }
}
