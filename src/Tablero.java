import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Tablero extends JPanel implements ActionListener, KeyListener {

    // ATRIBUTOS
    //==================================================================================================================
    private final int ALTO = 700;
    private final int ANCHO = 700;
    private int arrayLaberinto[][];

    private Laberinto laberinto = new Laberinto();
    private ArrayList<Zombie> zombies = new ArrayList<>();
    private ArrayList<Calabaza> calabazas = new ArrayList<>();
    private Pocion pocion = new Pocion();

    private Random random = new Random();
    protected Timer timer; // un único timer sobre todos los elementos del tablero

    // creamos el temporizador
    private int segundos = 20;
    protected Timer cronometro;

    // CONSTRUCTORES
    //==================================================================================================================
    public Tablero() {
        // propiedades del tablero
        this.setSize(ANCHO, ALTO);
        this.setBackground(Color.DARK_GRAY);

        this.addKeyListener(this);
        setFocusable(true);

        // creación de los elementos (personajes, calabazas y poción)
        crearLaberinto();
        crearZombies(); // 2 zombies
        crearCalabazas(); // 5 calabazas

        // inicialización de los elementos del tablero y el timer
        iniciarPosicionZombies();
        iniciarPosicionRandomCalabazas();
        iniciarPosicionRandomPocion();
        iniciarTimer();
    }

    // MÉTODOS
    //==================================================================================================================
    public void iniciarTimer() {
        timer = new Timer(40, this); // 'this' es la implementación ActionListener en esta clase
        // y su método actionPerformed()
        timer.start();
        cronometro = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                segundos--;
                if (segundos <= 0) {
                    System.out.println("aquí va el panel de game over");
                    cronometro.stop();
                }
            }
        });
        cronometro.start();
    }

    // ACTION PERFORMED
    @Override
    public void actionPerformed(ActionEvent e) {

        // movemos calabazas de lado a lado
        for (Calabaza calabaza : calabazas) {
            calabaza.moverCalabaza(laberinto);
        }

        // detectar colisiones
        Zombie zombieHombre = zombies.get(0);
        Zombie zombieMujer = zombies.get(1);

        // con poción
        if (zombieHombre.limites.intersects(pocion.limites)) {
            pocion.desaparecer();
            zombieHombre.setVelocidad(25);
        }
        if (zombieMujer.limites.intersects(pocion.limites)) {
            pocion.desaparecer();
            zombieMujer.setVelocidad(25);
        }

        // con calabazas
        Calabaza calabazaAeliminar = null;
        for (Calabaza calabazaActual : calabazas) {
            if (zombieHombre.limites.intersects(calabazaActual.limites)) {
                calabazaAeliminar = calabazaActual;
                break;
            }
        }
        if (calabazaAeliminar != null) {
            zombieHombre.setVelocidad(5);
            calabazaAeliminar.desaparecer();
            calabazas.remove(calabazaAeliminar);
        }

        // actualizar el tiempo (para imprimirlo por pantalla)

        // comprobar estado del juego (si ha ganado algún jugador o si ha terminado el tiempo)

        repaint(); // CRUCIAL!!!!!
    }

    // PINTAR EL TABLERO
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        laberinto.pintarLaberinto(g2d); // pintamos laberinto

        zombies.get(0).pintar(g, this, false);
        zombies.get(1).pintar(g, this, true);

        for (Calabaza calabaza : calabazas) { // pintamos calabazas: recorremos su ArrayList de 5 calabazas para ello
            calabaza.pintar(g2d);
        }

        pocion.pintar(g2d); // pintamos poción

        // Pintar el cronómetro debajo del laberinto
        g2d.setColor(Color.WHITE);
        g2d.drawString("Tiempo restante: " + segundos, 10, ALTO - 10);
    }

    // LABERINTO
    private int[][] crearLaberinto() {
        return arrayLaberinto = laberinto.crearLaberinto();
    }

    // ZOMBIES
    private void crearZombies() {
        Zombie zombieHombre = new Zombie(Sexo.HOMBRE, this);
        Zombie zombieMujer = new Zombie(Sexo.MUJER, this);
        zombies.add(zombieHombre); // [0]
        zombies.add(zombieMujer); // [1]
    }

    private void iniciarPosicionZombies() {
        int anchoBloque = laberinto.getAnchoBloque();
        int mitadBloque = anchoBloque / 2;

        // zombie 1 (esquina superior izquierda)
        Zombie zombie1 = zombies.get(0);
        int mitadZombieX = zombie1.getTamanyo() / 2;
        int mitadZombieY = zombie1.getTamanyo() / 2;
        int zombieX1 = mitadBloque - mitadZombieX;
        int zombieY1 = mitadBloque - mitadZombieY;
        zombie1.setX(zombieX1 + anchoBloque);
        zombie1.setY(zombieY1 + anchoBloque);

        // zombie 2 (esquina superior derecha)
        Zombie zombie2 = zombies.get(1);
        int zombieX2 = mitadBloque - mitadZombieX;
        int zombieY2 = mitadBloque - mitadZombieY;
        zombie2.setX(zombieX2 + (18 * anchoBloque)); // 26 bloques a la derecha
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
                int gridX = random.nextInt(20); // Cambiado de 28 a 20
                int gridY = random.nextInt(20); // Cambiado de 28 a 20

                if (arrayLaberinto[gridY][gridX] == 0) {
                    calabaza.setX(gridX * laberinto.getAnchoBloque()); // Usar 35 en lugar de 25
                    calabaza.setY(gridY * laberinto.getAltoBloque());  // Usar 35 en lugar de 25
                    posicionValida = true;
                }
            }
        }
    }

    public void iniciarPosicionRandomPocion() {
        boolean posicionValida = false;

        while (!posicionValida) {
            // Obtener posición aleatoria en el grid
            int gridX = random.nextInt(20);
            int gridY = random.nextInt(20);

            // Verificar si es pasillo (0)
            if (arrayLaberinto[gridY][gridX] == 0) {
                // Convertir posición de grid a píxeles
                pocion.setX(gridX * laberinto.getAnchoBloque());
                pocion.setY(gridY * laberinto.getAltoBloque());
                posicionValida = true;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // mover zombies cuando se presiona tecla
        zombies.get(0).moverZombie(e, laberinto); // mover hombre
        zombies.get(1).moverZombie(e, laberinto); // mover mujer

        // actualizar sprites conforme se presiona tecla
        for (Zombie zombie : zombies) {
            zombie.actualizarAnimacion();
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}