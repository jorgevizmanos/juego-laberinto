import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaginaPrincipal extends JFrame {

    public PaginaPrincipal() {
        // Configuración del JFrame
        setTitle("Laberinto del Terror");
        setSize(700, 766);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el JPanel principal con BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Crear el JLabel para el texto superior
        JLabel textoSuperior = new JLabel("Bienvenido al Laberinto del Terror", JLabel.CENTER);
        textoSuperior.setFont(new Font("Serif", Font.BOLD, 24));
        panelPrincipal.add(textoSuperior, BorderLayout.NORTH);

        // Crear el JPanel para los botones con FlowLayout centrado
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Botón "Jugar"
        JButton btnJugar = new JButton("Jugar");
        btnJugar.setPreferredSize(new Dimension(200, 50));
        btnJugar.setFont(new Font("Arial", Font.BOLD, 16));
        btnJugar.setBackground(Color.ORANGE);
        btnJugar.setForeground(Color.BLACK);
        btnJugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Ventana().setVisible(true);
                dispose();
            }
        });

        // Añadir el botón "Jugar" al panel de botones
        panelBotones.add(btnJugar);

        // Añadir el panel de botones al centro del panel principal
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);

        // Añadir el panel principal al JFrame
        add(panelPrincipal);

        // Hacer visible el JFrame
        setVisible(true);
    }

    public static void main(String[] args) {
        // Crear y mostrar la página
        SwingUtilities.invokeLater(PaginaPrincipal::new);
    }
}