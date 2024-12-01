import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuInicial extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    Image zombiChico = new ImageIcon("/imagenes/sprites_zombieM/Walk(1).png").getImage();
    Image zombiChica = new ImageIcon("/imagenes/sprites_zombieF/Walk(1).png").getImage();

    private JButton jugarBoton = new JButton("Jugar");
    private JButton acercaDeBoton = new JButton("Acerca de");

    public MenuInicial() {
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

        // Configurar y añadir el botón "Jugar"
        jugarBoton.setPreferredSize(new Dimension(200, 50));
        jugarBoton.setFont(new Font("Arial", Font.BOLD, 16));
        jugarBoton.setForeground(Color.BLACK);
        jugarBoton.addActionListener(this);
        panelBotones.add(jugarBoton);

        // Configurar y añadir el botón "Acerca de"
        acercaDeBoton.setPreferredSize(new Dimension(200, 50));
        acercaDeBoton.setFont(new Font("Arial", Font.BOLD, 16));
        acercaDeBoton.addActionListener(this);
        panelBotones.add(acercaDeBoton);

        // Añadir el panel de botones al centro del panel principal
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);

        // Añadir el panel principal al JFrame
        add(panelPrincipal);

        // Hacer visible el JFrame
        setVisible(true);
    }


    public static void main(String[] args) {
        // Crear y mostrar la página
        SwingUtilities.invokeLater(MenuInicial::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugarBoton) {
            new Juego().setVisible(true);
            dispose();
        } else if (e.getSource() == acercaDeBoton) {
            // Acción para el botón "Acerca de"
            JOptionPane.showMessageDialog(this, "Información sobre el Laberinto del Terror");
        }
    }
}