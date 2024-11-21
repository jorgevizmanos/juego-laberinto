import javax.swing.*;
import java.awt.*;

public class PanelCronometro extends JPanel {
    private int segundos;
    private JLabel tiempoLabel;

    public PanelCronometro() {
        this.setPreferredSize(new Dimension(700, 100));
        this.setBackground(Color.BLACK);

        tiempoLabel = new JLabel("Tiempo: 30");
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
