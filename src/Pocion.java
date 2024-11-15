import java.awt.*;

public class Pocion extends ObjetosMagicos{
    @Override
    public void desaparecer() {

    }

    @Override
    public void cambiarVelocidad() {

    }

    @Override
    public void pintar(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(400, 30, 40,40);
    }
}
