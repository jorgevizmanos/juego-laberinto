import java.awt.*;

public class Calabaza extends ObjetosMagicos {

    // ATRIBUTOS
    //==================================================================================================================
    private static final int TAMANIO_CALABAZA = 25;  // declaramos el tamanyo como constante
    private int x, y; // posicion coordenadas
    private int velocidad = 2; // desplazamiento

    Laberinto laberinto = new Laberinto();

    // CONSTRUCTORES
    //==================================================================================================================
    public Calabaza() {
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
        g.setColor(Color.orange);
        g.fillOval(x, y, TAMANIO_CALABAZA, TAMANIO_CALABAZA);
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
