import javax.swing.*;
import java.awt.*;

public class Calabaza extends ObjetosMagicos {

    // ATRIBUTOS
    //==================================================================================================================
    private static final int TAMANIO_CALABAZA = 25;  // declaramos el tamanyo como constante
    private int x, y; // posicion coordenadas
    private int velocidad = 3; // desplazamiento

    private Laberinto laberinto = new Laberinto();
    private Image imagen;

    // CONSTRUCTORES
    //==================================================================================================================
    public Calabaza() {
        // cargamos la imagen de la calabaza una vez solo, en el constructor (para evitar errores con el repaint() )
        try {
            imagen = new ImageIcon(getClass().getResource("/imagenes/calabaza.png")).getImage();
        } catch (Exception e) {
            System.out.println("Error al cargar la imagen de la calabaza: " + e.getMessage());
            // Si falla la carga, dibujamos un círculo naranja como respaldo
            imagen = null;
        }
    }

    // METODOS
    //==================================================================================================================

    @Override
    public void desaparecer() {

    }

    @Override
    public void cambiarVelocidad() {

    }

    @Override
    public void pintar(Graphics g) {
        if (imagen != null) {
            g.setColor(Color.RED); // BORRAR AL FINAL
            g.drawRect(x, y, TAMANIO_CALABAZA, TAMANIO_CALABAZA); // BORRAR AL FINAL

            g.drawImage(imagen, x, y, TAMANIO_CALABAZA, TAMANIO_CALABAZA, null);
        } else {
            // Si no se pudo cargar la imagen, dibujamos un círculo naranja
            g.setColor(Color.ORANGE);
            g.fillOval(x, y, TAMANIO_CALABAZA, TAMANIO_CALABAZA);
        }
    }

    public void moverCalabaza(int[][] lab) {
        // calculamos la proxima posicion en X de la calabaza
        int proximaPosX = x + velocidad;

        // si se desplaza hacia la derecha
        if (velocidad > 0) {
            // detectamos si hay colision en el extremo derecho de la calabaza
            if (lab[y / laberinto.getAltoBloque()][(proximaPosX + TAMANIO_CALABAZA) / laberinto.getAnchoBloque()] == 1) {
                velocidad = -velocidad; // invertimos direccion

            } else {
                // si no hay colision, actualizamos la posicion a la nueva para avanzar
                x = proximaPosX;
            }

            // en caso de desplazarse hacia la izquierda
        } else {
            // detectamos si hay colision en el extremo izquierdo de la calabaza
            if (lab[y / laberinto.getAltoBloque()][proximaPosX / laberinto.getAnchoBloque()] == 1) {
                // si hay colision, invertimos la direccion
                velocidad = -velocidad;
            } else {
                // si no hay colision, actualizamos la posicion a la nueva para avanzar
                x = proximaPosX;
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
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public static int getTAMANIO_CALABAZA() {
        return TAMANIO_CALABAZA;
    }
}
