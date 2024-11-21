import javax.swing.*;
import java.awt.*;

public class Calabaza extends ObjetosMagicos {

    // ATRIBUTOS
    //==================================================================================================================
    private final int TAMANIO_CALABAZA = 25;  // tamanyo CONSTANTE
    private int x, y; // posicion coordenadas
    private int velocidad = 3; // desplazamiento
    private Image imagen;
    protected Rectangle limites;

    // CONSTRUCTORES
    //==================================================================================================================
    public Calabaza() {
        this.limites = new Rectangle(0,0, TAMANIO_CALABAZA, TAMANIO_CALABAZA);

        // cargamos la imagen de la calabaza una vez solo, en el constructor (para evitar errores con el repaint() )
        try {
            this.imagen = new ImageIcon(getClass().getResource("/imagenes/calabaza.png")).getImage();

        } catch (Exception e) {
            System.out.println("Error al cargar la imagen de la calabaza: " + e.getMessage());

            // si falla la carga, la dejamos a null
            this.imagen = null;
        }
    }

    // METODOS
    //==================================================================================================================

    @Override
    public void desaparecer() {
        this.imagen = null;
        this.limites = null;
    }

    @Override
    public void cambiarVelocidad() {

    }

    @Override
    public void pintar(Graphics g) {
        if (imagen != null) {
            g.setColor(Color.RED); // BBX BORRAR AL FINAL
            g.drawRect(x, y, TAMANIO_CALABAZA, TAMANIO_CALABAZA); // BBX BORRAR AL FINAL

            g.drawImage(imagen, x, y, TAMANIO_CALABAZA, TAMANIO_CALABAZA, null);
        }
    }

    public void moverCalabaza(Laberinto laberinto) {


        // calculamos la proxima posicion en X de la calabaza
        int[][] lab = laberinto.crearLaberinto();
        int proximaPosX = x + velocidad;

        // si se desplaza hacia la derecha
        if (velocidad > 0) {
            // detectamos si hay colision en el extremo derecho de la calabaza
            if (lab[y / laberinto.getAltoBloque()][(proximaPosX + TAMANIO_CALABAZA) / laberinto.getAnchoBloque()] == 1) {
                velocidad = -velocidad; // invertimos direccion

            } else {
                // si no hay colision, actualizamos la posicion a la nueva para avanzar y sus limites
                x = proximaPosX;
                this.limites.x = x;
            }

            // en caso de desplazarse hacia la izquierda
        } else {
            // detectamos si hay colision en el extremo izquierdo de la calabaza
            if (lab[y / laberinto.getAltoBloque()][proximaPosX / laberinto.getAnchoBloque()] == 1) {
                // si hay colision, invertimos la direccion
                velocidad = -velocidad;

            } else {
                // si no hay colision, actualizamos la posicion a la nueva para avanzar y sus limites
                x = proximaPosX;
                this.limites.x = x;
            }
        }
    }

    // METODOS GETTERS & SETTERS
    //==================================================================================================================
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        this.limites.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        this.limites.y = y;
    }
    public int getTAMANIO_CALABAZA() {
        return TAMANIO_CALABAZA;
    }
}
