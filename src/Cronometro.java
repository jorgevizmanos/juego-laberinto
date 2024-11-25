import javax.swing.*;
import java.awt.*;

public class Cronometro extends JPanel {
    private int segundos;
    private JLabel tiempoLabel;

    public Cronometro() {
        this.setBackground(Color.BLACK);
        this.setSize(700,150);
        tiempoLabel = new JLabel("Tiempo: 45");
        tiempoLabel.setForeground(Color.WHITE);
        tiempoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        this.add(tiempoLabel);
    }

    public void actualizarTiempo(int segundos) {
        this.segundos = segundos;
        tiempoLabel.setText("Tiempo: " + segundos);
        repaint();
    }
}
