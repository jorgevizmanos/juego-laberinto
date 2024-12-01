import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/*
    Clase que genera un zombie, un jugador. Este es una animacion sprite que se activa conforme se mueve
    mediante flechas. El zombie tiene tambien una direccion hacia la que 'mira', que varia segun la direccion
    que toma. Ademas, segun su sexo, tendra una imagen u otra. La clase detecta tambien si ha colisionado con
    algun elemento del tablero (calabaza y/o pocion).
 */
public class Zombie {

    // ATRIBUTOS
    //==================================================================================================================
    private int x, y;
    private final int TAMANYO_ZOMBIE = 30; // ancho y alto son lo mismo
    private int velocidad = 10;
    protected Rectangle limites;
    private ArrayList<Image> animacion; // lista de imagenes en donde se cargara cada sprite
    private int spriteActual = 0; // indice del sprite actual (fotograma)
    private Sexo sexo;
    private boolean mirandoDerecha; // controla el reflejo de las imagenes (sprites) segun se muevan izq/der


    // CONSTRUCTORES
    //==================================================================================================================
    public Zombie(Sexo sexo, Component observer) { // observer necesitado para inicializar sprites
        this.sexo = sexo;
        this.mirandoDerecha = (sexo == Sexo.HOMBRE); // hombre se inicia mirando derecha, mujer izquierda
        this.animacion = new ArrayList<>();
        this.limites = new Rectangle(x, y, TAMANYO_ZOMBIE / 2, TAMANYO_ZOMBIE / 2);
        inicializarSprites(observer);
    }

    // METODOS
    //==================================================================================================================

    // metodo que inicializa los sprites para la animacion
    private void inicializarSprites(Component observer) {
        String carpeta;
        if (sexo == Sexo.HOMBRE) {
            carpeta = "sprites_zombieM";
        } else {
            carpeta = "sprites_zombieF";
        }

        // recorremos los 5 sprites existentes para cada zombie
        for (int i = 1; i <= 5; i++) {
            try {
                String ruta = "imagenes/" + carpeta + "/Walk (" + i + ").png"; // ruta del recurso
                Image spriteOriginal = new ImageIcon(getClass().getResource(ruta)).getImage(); // imagen actual

                // forzamos la carga inmediata del recurso (evitamos laggeo)
                MediaTracker tracker = new MediaTracker(observer);
                tracker.addImage(spriteOriginal, 0);
                tracker.waitForAll();

                // modificamos proporcion de la imagen para ajustarla al tamanyo del zombie
                double proporcion = (double) spriteOriginal.getWidth(observer) / spriteOriginal.getHeight(observer);
                int nuevoAncho = (int) (TAMANYO_ZOMBIE * proporcion);
                Image spriteEscalado = spriteOriginal.getScaledInstance(nuevoAncho, TAMANYO_ZOMBIE, Image.SCALE_SMOOTH);

                // una vez escalada, esperamos a que todos los recursos se cargen y la anyadimos al array 'animacion'
                tracker.addImage(spriteEscalado, 1);
                tracker.waitForAll();

                animacion.add(spriteEscalado);

            } catch (Exception e) {
                System.out.println("Error cargando sprite " + i + ": " + e.getMessage());
            }
        }
    }

    // Metodo que pinta los zombies segun su sexo
    public void pintar(Graphics g, Component observer, boolean esZombieMujer) {

        // en caso de tener animacion
        if (!animacion.isEmpty()) {
            Graphics2D g2d = (Graphics2D) g;
            Image spriteActual = animacion.get(this.spriteActual); // obtenemos la imagen actual del sprite

            // para la mujer, siempre volteamos primero y luego aplicamos su direccion
            boolean debeVoltear;

            if (esZombieMujer) {
                debeVoltear = !mirandoDerecha; // invertimos la logica para la mujer

            } else {
                debeVoltear = !mirandoDerecha;
            }

            // si debemos voltear la imagen
            if (debeVoltear) {
                // reflejamos horizontalmente la imagen
                g2d.drawImage(spriteActual, x + getTamanyo(), y, -getTamanyo(), getTamanyo(), observer);

            } else {
                // dibujo sin reflejo
                g2d.drawImage(spriteActual, x, y, getTamanyo(), getTamanyo(), observer);

            }
        }
    }

    // Metodo que actualiza la animacion de los sprites
    public void actualizarAnimacion() {
        spriteActual = (spriteActual + 1) % animacion.size(); // metodo copiado de Andres... xd
    }

    // Metodo que mueve al zombie hombre en el laberinto y devuelve true o false en caso de moverse
    public boolean moverZombieHombre(KeyEvent evento, Laberinto laberinto) {

        // cargamos en variables nuevas la velocidad, las coordenadas, si se movio o no y la tecla del evento
        int movimiento = velocidad;
        int nuevoX = x;
        int nuevoY = y;
        boolean seMovio = false;
        int tecla = evento.getKeyCode();

        // solo actualiza la direcciona a la que mira el zombie en base a la tecla presionada
        switch (tecla) {
            case KeyEvent.VK_LEFT:
                mirandoDerecha = false;
                break;
            case KeyEvent.VK_RIGHT:
                mirandoDerecha = true;
                break;
        }

        // movimiento real del zombie sobre el tablero segun la flecha presionada
        switch (tecla) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:

                // mientras que haya movimiento
                while (movimiento > 0) {
                    int paso = Math.min(1, movimiento);

                    if (tecla == KeyEvent.VK_LEFT) nuevoX -= paso;
                    else if (tecla == KeyEvent.VK_RIGHT) nuevoX += paso;
                    else if (tecla == KeyEvent.VK_UP) nuevoY -= paso;
                    else if (tecla == KeyEvent.VK_DOWN) nuevoY += paso;

                    if (!hayColision(nuevoX, nuevoY, laberinto)) {
                        x = nuevoX;
                        y = nuevoY;
                        this.limites.x = x + 9; // mas 9 para encajar con su cabeza jaj
                        this.limites.y = y;
                        seMovio = true; // actualizamos
                    }
                    movimiento -= paso;
                }
                break;
        }
        return seMovio; // devolvemos si se movio
    }

    // Metodo que mueve a la mujer zombie en el tablero
    public boolean moverZombieMujer(KeyEvent evento, Laberinto laberinto) {

        // cargamos en variables nuevas la velocidad, las coordenadas, si se movio o no y la tecla del evento
        int movimiento = velocidad;
        int nuevoX = x;
        int nuevoY = y;
        boolean seMovio = false;
        int tecla = evento.getKeyCode();

        // solo procesamos las teclas W A S D y actualiza su direccion
        switch (tecla) {
            case KeyEvent.VK_A:
                mirandoDerecha = false;
                break;
            case KeyEvent.VK_D:
                mirandoDerecha = true;
                break;
        }

        // movimiento real del zombie sobre el tablero segun la flecha presionada
        switch (tecla) {
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
                while (movimiento > 0) {
                    int paso = Math.min(1, movimiento);

                    if (tecla == KeyEvent.VK_A) nuevoX -= paso;
                    else if (tecla == KeyEvent.VK_D) nuevoX += paso;
                    else if (tecla == KeyEvent.VK_W) nuevoY -= paso;
                    else if (tecla == KeyEvent.VK_S) nuevoY += paso;

                    if (!hayColision(nuevoX, nuevoY, laberinto)) {
                        x = nuevoX;
                        y = nuevoY;
                        this.limites.x = x + 8;
                        this.limites.y = y + 4;
                        seMovio = true; // actualizamos
                    }
                    movimiento -= paso;
                }
                break;
        }
        return seMovio; // devolvemos si se movio
    }

    // Metodo que se encarga de verificar si hay colision contra las paredes del laberinto del juego
    private boolean hayColision(int nuevoX, int nuevoY, Laberinto laberinto) {

        // cargamos en variables el array del laberinto y las dimensiones de sus bloques
        int[][] lab = laberinto.crearLaberinto();
        int anchoBloque = laberinto.getAnchoBloque();
        int altoBloque = laberinto.getAltoBloque();

        // ajuste para centrar el area de colision del zombie
        int margen = (TAMANYO_ZOMBIE - 25) / 2;

        // puntos a comprobar con el margen
        int[] puntosX = {nuevoX + margen, nuevoX + TAMANYO_ZOMBIE - margen};
        int[] puntosY = {nuevoY + margen, nuevoY + TAMANYO_ZOMBIE - margen};

        // permite salir por la izquierda en la fila 15
        if (nuevoY / altoBloque == 15 && nuevoX < 0) {
            return false;
        }

        for (int px : puntosX) {
            for (int py : puntosY) {
                int filaLab = py / altoBloque;
                int colLab = px / anchoBloque;

                // verificamos los limites del laberinto
                if (filaLab < 0 || filaLab >= lab.length ||
                        colLab < 0 || colLab >= lab[0].length) {
                    return true;
                }

                // solo colisiona con muros (1), no con la salida (2)
                if (lab[filaLab][colLab] == 1) {
                    return true;
                }
            }
        }
        return false;
    }


    // METODOS SETTERS & GETTERS
    //==================================================================================================================

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getTamanyo() {
        return TAMANYO_ZOMBIE;
    }
}