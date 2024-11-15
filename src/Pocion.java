import java.awt.*;

public class Pocion extends ObjetosMagicos{
    // atributos
    private int x, y;

    @Override
    public void desaparecer() {

    }

    @Override
    public void cambiarVelocidad() {

    }

    @Override
    public void pintar(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(x, y, 20,20);
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
