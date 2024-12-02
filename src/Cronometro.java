import javax.swing.*;
import java.awt.*;

public class Cronometro extends JPanel {
    private int segundos;
    private JLabel tiempoLabel;
    private Image imagen;

    public Cronometro() {
        this.setLayout(null); // Importante: control absoluto del layout
        this.setBackground(Color.BLACK);
        this.setSize(700, 100);

        this.imagen = new ImageIcon(getClass().getResource("/imagenes/cronometro.png")).getImage();

        tiempoLabel = new JLabel("Tiempo: 45");
        tiempoLabel.setForeground(Color.WHITE);
        tiempoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        tiempoLabel.setBounds(300, 35, 200, 30); // Posicionamiento exacto del label
        this.add(tiempoLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Aseguramos que la imagen se escale a las dimensiones exactas del panel
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }

    public void actualizarTiempo(int segundos) {
        this.segundos = segundos;
        tiempoLabel.setText("Tiempo: " + segundos);
        repaint();
    }
}
