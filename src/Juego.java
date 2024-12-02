import javax.swing.*;
import java.awt.*;

/*
    Clase ventana que une 2 paneles primordiales: el tablero (y sus elementos) y el cronometro.
    Ambos componentes forman el juego, de ahi que se llame 'Juego'.
 */
public class Juego extends JFrame {

    // ATRIBUTOS
    //==================================================================================================================
    private Cronometro cronometro = new Cronometro(); // panel Cronometro
    private Tablero tablero = new Tablero(cronometro); // panel Tablero

    private int ANCHO = tablero.getWidth(); // 700
    private int ALTO = tablero.getHeight() + cronometro.getHeight(); // altura Tablero + Cronometro = 800

    // CONSTRUCTORES
    //==================================================================================================================
    public Juego() {
        this.setUndecorated(true);  // elimina decoraciones propias de una ventana (y permite ver su contenido global)
        this.setTitle("Eazy Ezcape");
        this.setSize(ANCHO, ALTO);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        // el tablero comienza en (0,0) y ocupa 700 x 700
        tablero.setBounds(0, 0, 700, 700);

        // el cronometro comienza en (0,700) y ocupa 700 x 100
        cronometro.setBounds(0, 700, 700, 100);  // 100 de alto, mismo de ancho que el resto

        // anyadimos el tablero y el cronometro a la ventana Juego y la hacemos visible
        this.add(tablero);
        this.add(cronometro);
        this.setVisible(true);
    }
}
