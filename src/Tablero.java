import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

/*
    Clase que crea un tablero de juego, que es un laberinto, y sus elementos: piezas-jugadores (zombies) y
    obstaculos moviles automaticos (calabazas y pocion). Esta clase se encarga de englobar toda la practica del juego,
    desde su inicializacion, la interactividad entre sus componetnes y  hasta su fin,
    comprobando si ha terminado el cronometro o ha habido algun ganador.
 */
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
    private int segundosCronometro = 45;
    Image background;

    // atributos de elementos NO visuales del juego
    private char ganador;
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
        this.background = new ImageIcon(getClass().getResource("/imagenes/bg2.png")).getImage();
        iniciarPosicionZombies();
        iniciarPosicionRandomCalabazas();
        iniciarPosicionRandomPocion();

        // inicializamos elementos no visuales que controlan tiempo y estado del juego
        this.cronometro = cronometro; // cronometro/panel de parametro
        this.juegoActivo = true;
        this.animationTimer = new Timer(50, this);
        this.animationTimer.start();
        this.gameTimer = new Timer(1000, new ActionListener() {
            // ACTION PERFORMED del gameTimer que checkea si el juego sigue activo o no para finalizarlo
            @Override
            public void actionPerformed(ActionEvent e) {

                // si el juego esta activo
                if (juegoActivo) {
                    segundosCronometro--; // restamos segundos al cronometro

                    // si el cronometro ha llegado a cero o algun zombie ha salido del laberinto
                    if (segundosCronometro <= 0 || zombieSaleLaberinto()) {
                        finalizarJuego(); // finalizamos juego y damos paso al panel Menu Final

                    }

                    // actualizamos el tiempo del cronometro por cada segundo y repintamos
                    cronometro.actualizarTiempo(segundosCronometro);
                    repaint();
                }
            }
        });
        this.gameTimer.start(); // IMPORTANTE, iniciamos el timer del juego
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
        if (zombies.get(0).moverZombieHombre(e, laberinto)) {
            zombies.get(0).actualizarAnimacion();
        }
        if (zombies.get(1).moverZombieMujer(e, laberinto)) {
            zombies.get(1).actualizarAnimacion();
        }

        repaint();
    }

    // PINTAR ELEMENTOS VIUSALES CON PAINTCOMPONENT
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // pintamos fondo
        g.drawImage(background, 0, 0, ANCHO, ALTO, this);

        // pintamos laberinto
//        laberinto.pintar(g);

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
    // Metodo que detiene el juego, limpia el panel y lanza el Menu Final al segundo
    private void finalizarJuego() {

        // detenemos juego, su timer y el timer de la animacion
        this.juegoActivo = false;
        this.gameTimer.stop();
        this.animationTimer.stop();
        System.out.println("¡GAME OVER!");

        // creamos un timer de 1 segundo antes de inicial menu final
        Timer cambiarPanelTimer = new Timer(1000, e -> {
            try {
                // obtenemos el JFrame principal de manera segura
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window instanceof JFrame) {

                    // creamos nueva ventana
                    JFrame ventana = (JFrame) window;

                    // obtenemos y elinimanos el panel actual
                    ventana.getContentPane().removeAll();

                    // creamos una instancia de la clase MenuFinal y le pasamos el ganador
                    MenuFinal menuFinal = new MenuFinal(ganador);
                    menuFinal.setBounds(0, 0, 700, 800);  // ancho igual, altura Tablero + Cronometro
                    ventana.getContentPane().add(menuFinal); // anyadimos el panel a la ventana

                    // hacemos focusable el MenuFinal para eventos de teclado (botones jugar de nuevo / salir)
                    menuFinal.setFocusable(true);
                    menuFinal.requestFocus();

                    // actualizamos y repintamos ventana
                    ventana.revalidate();
                    ventana.repaint();

                    System.out.println("Llegaste al Menu Final");

                } else {
                    System.err.println("No se pudo obtener el JFrame");
                }
            } catch (Exception ex) {
                System.err.println("Error al cambiar al menú final: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // hacemos que el segundo de espera entre fin de juego y menu NO se repita de nuevo, y arrancamos ese timer
        cambiarPanelTimer.setRepeats(false);
        cambiarPanelTimer.start();
    }

    // LABERINTO
    // Metodo que crea el laberinto bidimensional de Laberinto y lo vuelca a la variable de esta clase
    private int[][] crearLaberinto() {
        return this.arrayLaberinto = this.laberinto.crearLaberinto();
    }

    // ZOMBIES
    // Metodo que crea a un hombre zombie y otro mujer y son anyadidos al array 'zombies'
    private void crearZombies() {
        Zombie zombieHombre = new Zombie(Sexo.HOMBRE, this);
        Zombie zombieMujer = new Zombie(Sexo.MUJER, this);
        zombies.add(zombieHombre); // indice [0]
        zombies.add(zombieMujer); // indice [1]
    }

    // Metodo que inicializa los zombies en una posicion equidistante al inicia del laberinto (parte superior)
    private void iniciarPosicionZombies() {

        // almacenamos medidas del bloque del laberinto para contrarestarlas a las posiciones de los zombies
        int anchoBloque = laberinto.getAnchoBloque();
        int mitadBloque = anchoBloque / 2;

        // zombie 1 (esquina superior izquierda)
        Zombie zombieHombre = zombies.get(0); // [0] --> hombre
        int mitadZombieX = zombieHombre.getTamanyo() / 2;
        int mitadZombieY = zombieHombre.getTamanyo() / 2;
        int zombieX1 = mitadBloque - mitadZombieX;
        int zombieY1 = mitadBloque - mitadZombieY;
        zombieHombre.setX(zombieX1 + anchoBloque); // set X
        zombieHombre.setY(zombieY1 + anchoBloque); // set Y

        // zombie 2 (esquina superior derecha)
        Zombie zombieMujer = zombies.get(1); // [1] --> mujer
        int zombieX2 = mitadBloque - mitadZombieX;
        int zombieY2 = mitadBloque - mitadZombieY;
        zombieMujer.setX(zombieX2 + (18 * anchoBloque)); // set X
        zombieMujer.setY(zombieY2 + anchoBloque); // set Y
    }

    // Metodo que verifica si un zombie ha salido del laberinto o no
    private boolean zombieSaleLaberinto() {

        // volcamos cada zombie en una variable
        Zombie zombieHombre = zombies.get(0);
        Zombie zombieMujer = zombies.get(1);

        // obtenemos la fila y columna donde se encuentran nuestros zombies
        int filaHombre = zombieHombre.getY() / laberinto.getAltoBloque();
        int columnaHombre = zombieHombre.getX() / laberinto.getAnchoBloque();
        int filaMujer = zombieMujer.getY() / laberinto.getAltoBloque();
        int columnaMujer = zombieMujer.getX() / laberinto.getAnchoBloque();

        // si la fila y columna del zombie hombre coincide con las coordenadas de la salida del laberinto
        if (filaHombre == 14 && columnaHombre <= 0) {
            System.out.println("¡Zachary zalio del zementerio! ZEREBROZZZZZ");
            this.ganador = 'h';  // asignamos ganador al hombre
            return true; // devolvemos true
        }
        if (filaMujer == 14 && columnaMujer == 0) {
            System.out.println("¡Zoe zalio del zementerio! ZEREBROZZZZZ!");
            this.ganador = 'm';  // asignamos ganador a la mujer
            return true; // devolvemos true
        }
        if (segundosCronometro <= 0) {
            this.ganador = '\0';  // ninguno gano :(
        }
        return false;
    }

    // CALABAZAS
    // Metodo que crea X calabazas y las anyade a la variable 'calabazas' de la clase
    private void crearCalabazas() {
        for (int i = 0; i < 8; i++) { // 8 calabazas para hacerlo mas picante
            calabazas.add(new Calabaza());
        }
    }

    // Metodo que inicializa la posicon random de las calabazas (nunca sobre la salida ni donde estan los zombies)
    public void iniciarPosicionRandomCalabazas() {
        // recorremos el array de las calabazas para situar cada una en un lugar
        for (Calabaza calabaza : calabazas) {
            boolean posicionValida = false;

            // bucle que se repite hasta posicionar correctamente las calabazas en un lugar optimo
            while (!posicionValida) {
                // creamos coordenadas aleatorias(X,Y)
                int x = random.nextInt(20);
                int y = random.nextInt(20);

               // condiciones de que deben...
                boolean esPasillo = arrayLaberinto[y][x] == 0; // estar en el pasillo (los 0s del laberinto)
                boolean noEsSalida = !(y == 15 && (x == 0 || x == 1)); // NO estar en salida y su casilla derecha
                boolean noEsPosicionZombies = !(y == 1 && (x == 1 || x == 18)); // NO estar en posicion iniciales zombies

                // si las calabazas cumplen las condiciones, las posicionamos y salimos del bucle
                if (esPasillo && noEsSalida && noEsPosicionZombies) {
                    calabaza.setX(x * laberinto.getAnchoBloque());
                    calabaza.setY(y * laberinto.getAltoBloque());
                    posicionValida = true;
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
    // metodo que detecta la colision de un zombie con la pocion aceleradora
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

    // Metodo que detecta la colision de un zombie con una de las calabazas del tablero
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