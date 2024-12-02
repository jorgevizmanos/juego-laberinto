import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
    Clase que genera un menu final del juego y muestra un resultado u otro en base a las casuisticas:
    - El zombie hombre ha ganado
    - El zombie mujer ha ganado
    - Se ha terminado el tiempo y ningun zombie ha ganado
 */
public class MenuFinal extends JPanel {
    private char ganador;
    private JButton btnJugarDeNuevo;
    private JButton btnSalir;
    private Image gameOver;
    private Image youWin;

    public MenuFinal(char ganador) {
        this.ganador = ganador;
        this.setLayout(null);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        try {
            gameOver = new ImageIcon(getClass().getResource("/imagenes/gameOver.png")).getImage();
            youWin = new ImageIcon(getClass().getResource("/imagenes/youwin.png")).getImage();
        } catch (Exception e) {
            System.out.println("Error cargando imágenes: " + e.getMessage());
        }

        btnSalir = new JButton("");
        btnSalir.setBounds(45, 670, 105, 70);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.addActionListener(e -> System.exit(0));
        btnSalir.setContentAreaFilled(false);  // Sin relleno
        btnSalir.setBorderPainted(false);      // Sin borde
        btnSalir.setFocusPainted(false);       // Sin efecto focus
        btnSalir.setOpaque(false);             // Transparente

        btnJugarDeNuevo = new JButton("");
        btnJugarDeNuevo.setBounds(492, 670, 170, 70);
        btnJugarDeNuevo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnJugarDeNuevo.addActionListener(e -> reiniciarJuego());
        btnJugarDeNuevo.setContentAreaFilled(false);
        btnJugarDeNuevo.setBorderPainted(false);
        btnJugarDeNuevo.setFocusPainted(false);
        btnJugarDeNuevo.setOpaque(false);

        add(btnJugarDeNuevo);
        add(btnSalir);
    }

    private void reiniciarJuego() {
        JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);
        ventana.getContentPane().removeAll();

        Cronometro cronometro = new Cronometro();
        Tablero tablero = new Tablero(cronometro);
        cronometro.setPreferredSize(new Dimension(700, 100));

        ventana.setLayout(new BorderLayout());
        ventana.add(tablero, BorderLayout.CENTER);
        ventana.add(cronometro, BorderLayout.SOUTH);

        ventana.revalidate();
        ventana.repaint();
        tablero.requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image imagenResultado = ganador == '\0' ? gameOver : youWin;
        g.drawImage(imagenResultado, 0, 0, getWidth(), getHeight(), this);
    }
}