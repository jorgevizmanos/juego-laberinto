import javax.swing.*;
import java.awt.*;

public class Pocion extends ObjetosMagicos{
    // ATRIBUTOS
    //==================================================================================================================
    private final int TAMANIO_POCION = 25;
    private int x, y;
    private Image imagen;

    // CONSTRUCTORES
    //==================================================================================================================
    public Pocion() {
        try {
            this.imagen = new ImageIcon(getClass().getResource("/imagenes/pocion.png")).getImage();
        } catch (Exception e) {
            System.out.println("Error al cargar la imagen de la calabaza: " + e.getMessage());
            // Si falla la carga, dibujamos un círculo naranja como respaldo
            this.imagen = null;
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
            g.setColor(Color.RED); // BBX BORRAR AL FINAL
            g.drawRect(x, y, TAMANIO_POCION, TAMANIO_POCION); // BBX BORRAR AL FINAL

            g.drawImage(imagen, x, y, TAMANIO_POCION, TAMANIO_POCION, null);

        } else {
            g.setColor(Color.red);
            g.fillOval(x, y, 20, 20);
        }
    }

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
}
