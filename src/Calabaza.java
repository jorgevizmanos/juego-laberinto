import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Calabaza extends ObjetosMagicos{
    // atributos
    private int x, y;
    private int velocidad = 1;
    private Tablero tablero;
    Laberinto laberinto = new Laberinto();

    public Calabaza (Tablero tablero) {
        this.tablero = tablero;
        moverCalabaza();

    }

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
        g.fillOval(x, y, 20,20);
    }

    public void moverCalabaza() {

        int [][] lab = laberinto.crearLaberinto();

        Timer timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                x += velocidad;
                if (lab[y/25][(x/25 +1)] == 1 || lab[y/25][(x/25 -1)] == 1) {
                        velocidad = -velocidad;
                }
                tablero.repaint();
            }
        });
        timer.start();
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
