import javax.swing.*;
import java.awt.*;

public class Ventana extends JFrame {
    // ATRIBUTOS
    //==================================================================================================================
    private Cronometro cronometro = new Cronometro(); // Panel
    private Tablero tablero = new Tablero(cronometro); // Panel

    private int ANCHO_VENTANA = tablero.getWidth(); // 700
    private int ALTO_VENTANA = tablero.getHeight() + 66; // no se por que funciona con 66 y no sumando altura de Cronometro...

    // CONSTRUCTORES
    //==================================================================================================================
    public Ventana() {
        this.setTitle("Laberinto del Terror");
        this.setSize(ANCHO_VENTANA, ALTO_VENTANA);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.add(tablero);
        this.add(cronometro, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    // METODOS
    //==================================================================================================================
    public static void main(String[] args) {
        new Ventana();
    }
}
