import javax.swing.*;
import java.awt.*;

public class Pocion extends ObjetosMagicos{
    // ATRIBUTOS
    //==================================================================================================================
    private final int TAMANIO_POCION = 25;
    private int x, y;
    private Image imagen;
    protected Rectangle limites;
    private boolean activa = true;

    // CONSTRUCTORES
    //==================================================================================================================
    public Pocion() {
        this.limites = new Rectangle(0, 0, TAMANIO_POCION, TAMANIO_POCION); // X e Y se actualizan en Setters
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

    // Metodo que simula la desaparicion de la pocion (tras ser colisionada)
    @Override
    public void desaparecer() {
        this.imagen = null;
        this.activa = false;
        this.limites = new Rectangle(0, 0, 0, 0);  // limites nulos
    }

    // Metodo que pinta la pocion con la imagen proporcionada
    @Override
    public void pintar(Graphics g) {
        if (imagen != null) {
            g.drawImage(imagen, x, y, TAMANIO_POCION, TAMANIO_POCION, null);
        }
    }

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

    public boolean isActiva() {
        return activa;
    }
}