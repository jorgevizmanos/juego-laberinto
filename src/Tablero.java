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
    private int arrayLaberinto[][];

    private Laberinto laberinto = new Laberinto();
    private ArrayList<Zombie> zombies = new ArrayList<>();
    private ArrayList<Calabaza> calabazas = new ArrayList<>(); // lista de 5 objetos Calabaza
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
        crearZombies();
        crearCalabazas();

        // inicializacion de los elementos del tablero y el timer
        iniciarPosicionZombies();
        iniciarPosicionRandomCalabazas();
        iniciarPosicionRandomPocion();
        iniciarTimer();
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
            calabaza.moverCalabaza(arrayLaberinto);
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

        laberinto.pintarLaberinto(g2d); // pintamos laberinto

        for (Zombie zombie : zombies) { // pintamos zombies: recorremos su ArrayList de 2 zombies para ello
            zombie.pintar(g);
        }

        for (Calabaza calabaza : calabazas) { // pintamos calabazas: recorremos su ArrayList de 5 calbazas para ello
            calabaza.pintar(g2d);
        }

        pocion.pintar(g2d); // pintamos pocion

    }

    // LABERINTO
    private int[][] crearLaberinto() {
        return arrayLaberinto = laberinto.crearLaberinto();
    }

    // ZOMBIES
    private void crearZombies() {
        for (int i = 0; i < 2; i++) {
            zombies.add(new Zombie());
        }
    }

    private void iniciarPosicionZombies() {
        int anchoBloque = laberinto.getAnchoBloque();
        int mitadBloque = anchoBloque / 2;

        // zombie 1 (esquina superior izquierda)
        Zombie zombie1 = zombies.get(0);
        int mitadZombieX = zombie1.getTamanyo()/2;
        int mitadZombieY = zombie1.getTamanyo()/2;
        int zombieX1 = mitadBloque - mitadZombieX;
        int zombieY1 = mitadBloque - mitadZombieY;
        zombie1.setX(zombieX1 + anchoBloque);
        zombie1.setY(zombieY1 + anchoBloque);

        // zombie 2 (esquina superior derecha)
        Zombie zombie2 = zombies.get(1);
        int zombieX2 = mitadBloque - mitadZombieX;
        int zombieY2 = mitadBloque - mitadZombieY;
        zombie2.setX(zombieX2 + (26 * anchoBloque)); // 26 bloques a la derecha
        zombie2.setY(zombieY2 + anchoBloque);
    }

    // CALABAZAS
    private void crearCalabazas() {
        for (int i = 0; i < 5; i++) {
            calabazas.add(new Calabaza());
        }
    }

    public void iniciarPosicionRandomCalabazas() {
        // inicializar posición para cada calabaza del ArrayList
        for (Calabaza calabaza : calabazas) {
            boolean posicionValida = false;

            while (!posicionValida) {
                int gridX = random.nextInt(28);
                int gridY = random.nextInt(28);

                if (arrayLaberinto[gridY][gridX] == 0) {
                    calabaza.setX(gridX * 25);
                    calabaza.setY(gridY * 25 );
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
            if (arrayLaberinto[gridY][gridX] == 0) {
                // Convertir posición de grid a píxeles
                pocion.setX(gridX * 25); // anchoBloque
                pocion.setY(gridY * 25); // altoBloque
                posicionValida = true;
            }
        }
    }
}
