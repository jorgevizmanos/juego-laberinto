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
    private final int ANCHO = 700;
    private final int ALTO = 700;
    private int arrayLaberinto[][];

    private Laberinto laberinto = new Laberinto();
    private ArrayList<Zombie> zombies = new ArrayList<>();
    private ArrayList<Calabaza> calabazas = new ArrayList<>();
    private Pocion pocion = new Pocion();
    private Cronometro cronometro;

    private Random random = new Random();
    private Timer animationTimer;
    private Timer gameTimer;

    private int segundosCronometro = 30;
    private boolean juegoActivo;

    // CONSTRUCTORES
    //==================================================================================================================
    public Tablero(Cronometro cronometro) {
        this.cronometro = cronometro;

        this.setSize(ANCHO, ALTO);
        this.setBackground(Color.pink);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.juegoActivo = true;

        crearLaberinto();
        crearZombies();
        crearCalabazas();

        iniciarPosicionZombies();
        iniciarPosicionRandomCalabazas();
        iniciarPosicionRandomPocion();

        this.animationTimer = new Timer(50, this);
        this.animationTimer.start();

        this.gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (juegoActivo) {
                    segundosCronometro--;
                    if (segundosCronometro <= 0) {
                        finalizarJuego();
                    }
                    cronometro.actualizarTiempo(segundosCronometro);
                    repaint();
                }
            }
        });
        this.gameTimer.start();
    }

    // METODOS
    //==================================================================================================================
    private void finalizarJuego() {
        juegoActivo = false;
        gameTimer.stop();
        animationTimer.stop();
        System.out.println("¡GAME OVER!");
    }

    // ACTION PERFORMED
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!juegoActivo) return;

        // movemos calabazas de lado a lado
        for (Calabaza calabaza : calabazas) {
            calabaza.moverCalabaza(laberinto);
        }

        // detectar colisiones
        Zombie zombieHombre = zombies.get(0);
        Zombie zombieMujer = zombies.get(1);

        // con pocion
        if (pocion.isActiva()) {  // Solo comprobar si la poción está activa
            if (zombieHombre.limites.intersects(pocion.limites)) {
                pocion.desaparecer();
                zombieHombre.setVelocidad(zombieHombre.getVelocidad() + 5);
            }
            if (zombieMujer.limites.intersects(pocion.limites)) {
                pocion.desaparecer();
                zombieMujer.setVelocidad(zombieMujer.getVelocidad() + 5);
            }
        }
        // con calabazas
        Calabaza calabazaAeliminar = null;
        for (Calabaza calabazaActual : calabazas) {
            if (zombieHombre.limites.intersects(calabazaActual.limites)) {
                calabazaAeliminar = calabazaActual;
                zombieHombre.setVelocidad(zombieHombre.getVelocidad() - 2);
                break;
            }
        }

        // solo si no hubo colision con zombieHombre
        if (calabazaAeliminar == null) {
            for (Calabaza calabazaActual : calabazas) {
                if (zombieMujer.limites.intersects(calabazaActual.limites)) {
                    calabazaAeliminar = calabazaActual;
                    zombieMujer.setVelocidad(zombieMujer.getVelocidad() - 2); // velocidad original = 10 (no podemos restar toda)
                    break;
                }
            }
        }

        if (calabazaAeliminar != null) {
            calabazaAeliminar.desaparecer();
            calabazas.remove(calabazaAeliminar);
        }

        repaint();
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

        pocion.pintar(g2d); // pintamos pocion

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
        zombie2.setX(zombieX2 + (18 * anchoBloque));
        zombie2.setY(zombieY2 + anchoBloque);
    }

    // CALABAZAS
    private void crearCalabazas() {
        for (int i = 0; i < 5; i++) {
            calabazas.add(new Calabaza());
        }
    }

    public void iniciarPosicionRandomCalabazas() {
        for (Calabaza calabaza : calabazas) {
            boolean posicionValida = false;

            while (!posicionValida) {
                int gridX = random.nextInt(20);
                int gridY = random.nextInt(20);

                if (arrayLaberinto[gridY][gridX] == 0) {
                    calabaza.setX(gridX * laberinto.getAnchoBloque());
                    calabaza.setY(gridY * laberinto.getAltoBloque());
                    posicionValida = true;
                }
            }
        }
    }

    public void iniciarPosicionRandomPocion() {
        boolean posicionValida = false;

        while (!posicionValida) {
            int gridX = random.nextInt(20);
            int gridY = random.nextInt(20);

            if (arrayLaberinto[gridY][gridX] == 0) {
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
        if (!juegoActivo) return;

        // mover zombies cuando se presiona tecla y actualizar solo si se movieron
        if (zombies.get(0).moverZombie(e, laberinto)) {
            zombies.get(0).actualizarAnimacion();
        }
        if (zombies.get(1).moverZombie(e, laberinto)) {
            zombies.get(1).actualizarAnimacion();
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}