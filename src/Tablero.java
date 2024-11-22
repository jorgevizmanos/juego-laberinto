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

    // atributos propios del tablero
    private final int ANCHO = 700;
    private final int ALTO = 700;

    // atributos de elementos visuales del juego
    private int arrayLaberinto[][];
    private Laberinto laberinto = new Laberinto();
    private ArrayList<Zombie> zombies = new ArrayList<>();
    private ArrayList<Calabaza> calabazas = new ArrayList<>();
    private Pocion pocion = new Pocion();
    private Cronometro cronometro;
    private int segundosCronometro = 60;

    // atributos de elementos NO visuales del juego
    private boolean juegoActivo;
    private Random random = new Random();
    private Timer animationTimer; // animaciones del juego (calabazas)
    private Timer gameTimer; // tiempo de juego (cronometro)

    // CONSTRUCTORES
    //==================================================================================================================
    public Tablero(Cronometro cronometro) { // recibe el PANEL cronometro por parametro
        // propiedades del panel Tablero
        this.setSize(ANCHO, ALTO);
        this.setBackground(Color.pink);
        this.addKeyListener(this);
        this.setFocusable(true);

        // creamos elementos visuales
        crearLaberinto();
        crearZombies();
        crearCalabazas();

        // inciamos posiciones de elementos visuales
        iniciarPosicionZombies();
        iniciarPosicionRandomCalabazas();
        iniciarPosicionRandomPocion();

        // inicializamos elementos no visuales que controlan tiempo y estado del juego
        this.cronometro = cronometro; // cronometro/panel de parametro
        this.juegoActivo = true;
        this.animationTimer = new Timer(50, this);
        this.animationTimer.start();
        this.gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (juegoActivo) {
                    segundosCronometro--;
                    if (segundosCronometro <= 0 || zombieSaleLaberinto()) {
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

    // ACTION PERFORMED
    @Override
    public void actionPerformed(ActionEvent e) {

        // si el juego se acaba, regresamos para anular metodo
        if (!juegoActivo) return;

        // movemos calabazas de lado a lado
        for (Calabaza calabaza : calabazas) {
            calabaza.moverCalabaza(laberinto);
        }

        // detectamos las colisiones zombies con pocion
        detectarColisionZombiesConPocion();

        // detectamos las colisiones zombies con calabazas
        detectarColisionZombiesConCalabazas();

        // CRUCIAL, REPINTAMOS
        repaint();
    }



    // EVENTOS TECLADO
    @Override
    public void keyPressed(KeyEvent e) {

        // si el juego se acaba se anula metodo
        if (!juegoActivo) return;

        // movemos los zombies cuando se presiona tecla y actualizar animacion solo si se movieron
        if (zombies.get(0).moverZombie(e, laberinto)) {
            zombies.get(0).actualizarAnimacion();
        }
        if (zombies.get(1).moverZombie(e, laberinto)) {
            zombies.get(1).actualizarAnimacion();
        }

        repaint();
    }

    // PINTAR ELEMENTOS VIUSALES
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // pintamos laberinto
        laberinto.pintar(g);

        // pintamos zombie
        zombies.get(0).pintar(g, this, false); // hombre
        zombies.get(1).pintar(g, this, true); // mujer

        // pintamos calabazas
        for (Calabaza calabaza : calabazas) {
            calabaza.pintar(g);
        }

        // pintamos pocion
        pocion.pintar(g);
    }

    // FIN DEL JUEGO
    private void finalizarJuego() {
        this.juegoActivo = false;
        this.gameTimer.stop();
        this.animationTimer.stop();
        System.out.println("¡GAME OVER!");
    }

    // LABERINTO
    private int[][] crearLaberinto() {
        return this.arrayLaberinto = this.laberinto.crearLaberinto();
    }

    // ZOMBIES
    private void crearZombies() {
        Zombie zombieHombre = new Zombie(Sexo.HOMBRE, this);
        Zombie zombieMujer = new Zombie(Sexo.MUJER, this);
        zombies.add(zombieHombre); // [0]
        zombies.add(zombieMujer); // [1]
    }

    private void iniciarPosicionZombies() {

        // almacenamos medidas del bloque del laberinto
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

    private boolean zombieSaleLaberinto() {
        Zombie zombieHombre = zombies.get(0);
        Zombie zombieMujer = zombies.get(1);

        int filaHombre = zombieHombre.getY() / laberinto.getAltoBloque();
        int columnaHombre = zombieHombre.getX() / laberinto.getAnchoBloque();
        int filaMujer = zombieMujer.getY() / laberinto.getAltoBloque();
        int columnaMujer = zombieMujer.getX() / laberinto.getAnchoBloque();

        // BORRAR LUEGO
        System.out.println("Zombie Hombre - Fila: " + filaHombre + ", Columna: " + columnaHombre);
        System.out.println("Zombie Mujer - Fila: " + filaMujer + ", Columna: " + columnaMujer);

        // IMPORTANTE [15][0]
        if (filaHombre == 14 && columnaHombre <= 0) {
            System.out.println("¡Zombie Hombre ha llegado a la salida!");
            return true;
        }
        if (filaMujer == 14 && columnaMujer == 0) {
            System.out.println("¡Zombie Mujer ha llegado a la salida!");
            return true;
        }
        return false;
    }

    // CALABAZAS
    private void crearCalabazas() {
        for (int i = 0; i < 5; i++) { // 5 calabazas
            calabazas.add(new Calabaza());
        }
    }

    public void iniciarPosicionRandomCalabazas() {

        // recorremos el array de las calabazas para situar cada una en un lugar
        for (Calabaza calabaza : calabazas) {
            boolean posicionValida = false;

            // mientras que la posicion sea invalida
            while (!posicionValida) {

                // creamos coordenadas aleatorias(X,Y) de la calabaza mediante Random y almacenamos
                int x = random.nextInt(20);
                int y = random.nextInt(20);

                // si las coordenadas de la calabaza coincide con el pasillo del laberinto (con los 0s)
                if (arrayLaberinto[y][x] == 0) {
                    calabaza.setX(x * laberinto.getAnchoBloque()); // seteamos x
                    calabaza.setY(y * laberinto.getAltoBloque()); // seteamos y
                    posicionValida = true; // salimos del bucle
                }
            }
        }
    }

    // POCION
    public void iniciarPosicionRandomPocion() {
        boolean posicionValida = false;

        // mientras que la posicion sea invalida
        while (!posicionValida) {

            // creamos coordenadas aleatorias(X,Y) de la pocion mediante Random y almacenamos
            int x = random.nextInt(20);
            int y = random.nextInt(20);

            // si las coordenadas de la pocion coincide con el pasillo del laberinto (con los 0s)
            if (arrayLaberinto[y][x] == 0) {
                pocion.setX(x * laberinto.getAnchoBloque());
                pocion.setY(y * laberinto.getAltoBloque());
                posicionValida = true;
            }
        }
    }

    // COLISIONES ENTRE ELEMENTOS
    private void detectarColisionZombiesConPocion() {
        // volcamos cada zombie del array en variables
        Zombie zombieHombre = zombies.get(0);
        Zombie zombieMujer = zombies.get(1);

        // si la pocion existe
        if (pocion.isActiva()) {

            // si el zombie hombre colisiona con la pocion
            if (zombieHombre.limites.intersects(pocion.limites)) {
                pocion.desaparecer(); // la pocion desaparece
                zombieHombre.setVelocidad(zombieHombre.getVelocidad() + 7); // velocidad se dispara
            }

            // si el zombie mujer colisiona con la pocion
            if (zombieMujer.limites.intersects(pocion.limites)) {
                pocion.desaparecer(); // la pocion desaparece
                zombieMujer.setVelocidad(zombieMujer.getVelocidad() + 7); // velocidad se dispara
            }
        }
    }

    private void detectarColisionZombiesConCalabazas() {
        // volcamos cada zombie del array en variables y creamos una calabaza nula a eliminar
        Zombie zombieHombre = zombies.get(0);
        Zombie zombieMujer = zombies.get(1);
        Calabaza calabazaAeliminar = null;

        // recorremos el array de las calabazas
        for (Calabaza calabazaActual : calabazas) {

            // si el zombie hombre colisiona con la calabaza actual
            if (zombieHombre.limites.intersects(calabazaActual.limites)) {
                calabazaAeliminar = calabazaActual; // volcamos la calabaza actual a la variable a eliminar (no se puede eliminar durante el loop)
                zombieHombre.setVelocidad(zombieHombre.getVelocidad() - 2); // reducimos la velocidad del zombie hombre
                break; // salimos del bucle
            }

            // si el zombie mujer colisiona con la calabaza actual
            if (zombieMujer.limites.intersects(calabazaActual.limites)) {
                calabazaAeliminar = calabazaActual; // volcamos la calabaza actual a la variable a eliminar (no se puede eliminar durante el loop)
                zombieMujer.setVelocidad(zombieMujer.getVelocidad() - 2); ///reducimos la velocidad del zombie mujer
                break; // salimos del bucle
            }
        }

        // si hay una calabaza a eliminar porque fue colisionada
        if (calabazaAeliminar != null) {
            calabazaAeliminar.desaparecer(); // la hacemos desaparecer
            calabazas.remove(calabazaAeliminar); // la eliminamos del array de calabazas
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}