import java.awt.*;
import java.util.Random;

public class Calabaza extends ObjetosMagicos{
    // atributos
    private int x, y;

    // metodos
    @Override
    public void desaparecer() {

    }

    @Override
    public void cambiarVelocidad() {

    }

    @Override
    public void pintar(Graphics g) {
        g.setColor(Color.orange);
        g.fillOval(x, y, 40,40);
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
