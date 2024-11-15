import javax.swing.*;
import java.awt.*;

public class Ventana extends JFrame {
    // ATRIBUTOS
    //==================================================================================================================
    private Tablero tablero = new Tablero();

    // CONSTRUCTORES
    //==================================================================================================================
    public Ventana() {
        this.setSize(tablero.getWidth(), tablero.getHeight());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(tablero);
        this.setVisible(true);
    }

    // METODOS
    //==================================================================================================================

}
