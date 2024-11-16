import java.awt.*;

public class Zombie {
    // ATRIBUTOS
    //==================================================================================================================
    private static final int TAMANYO_ZOMBIE = 20;
    private int x, y;
    private int velocidad;

    // CONSTRUCTORES
    //==================================================================================================================


    // METODOS
    //==================================================================================================================
    public void pintar(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(x,y, TAMANYO_ZOMBIE, TAMANYO_ZOMBIE);
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
