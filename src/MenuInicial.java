import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

/*
    Clase que genera un menu inicial del juego. Posee dos botones principales integrados en su imagen de fondo:
    - jugar (en la imagen llamado "Run!"): inicia el juego, dando paso a otra ventana de las mismas caracteristicas
    - acerca de (en la imagen mostrado como "?"): muestra un mensaje con informacion didactica del juego
 */
public class MenuInicial extends JFrame implements ActionListener {

    // ATRIBUTOS
    //==================================================================================================================
    private final Image backgroundImage;
    private final JButton jugarBoton;
    private final JButton acercaDeBoton;

    // CONSTRUCTORES
    //==================================================================================================================
    public MenuInicial() {
        // inicializamos variables
        backgroundImage = new ImageIcon("src/imagenes/menuPrincipal.png").getImage();
        jugarBoton = new JButton("");
        acercaDeBoton = new JButton("");

        // propiedades de la ventana
        this.setTitle("Eazy ezcape!");
        this.setSize(700, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setUndecorated(true);  // elimina decoraciones propias de una ventana (y permite ver su contenido global)

        // creamos panel (con imagen de fondo)
        JPanel panelFondo = crearImagenFondo();

        // agregamos los componentes al panel
        agregarComponentesAlPanel(panelFondo);

        // anyadimos el panel a la ventana y la setteamos a visible
        add(panelFondo);
        setVisible(true);
    }

    // METODOS
    //==================================================================================================================

    // Metodo que crea un panel, pinta la imagen background del fondo y lo devuelve
    private JPanel crearImagenFondo() {
        // creamos panel y sobreescribimos su paintComponent para dibujar imagen
        JPanel panelImagenFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    // cargamos imagen en el panel (con las mismas dimensiones)
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // setteamos el layout a null puesto que manejamos nosotros el disenyo
        panelImagenFondo.setLayout(null);
        return panelImagenFondo;
    }

    // Metodo que anyade los componentes visuales e interactivos (texto y botones) al panel de fondo
    private void agregarComponentesAlPanel(JPanel panel) {

        // configuracion del boton 'Jugar' (en la imagen fondo 'RUN!!')
        jugarBoton.setBounds(295, 510, 110, 45);
        jugarBoton.addActionListener(this);
        jugarBoton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jugarBoton.setContentAreaFilled(false);
        jugarBoton.setBorderPainted(false);
        acercaDeBoton.setOpaque(false);
        jugarBoton.setFocusPainted(false);
        panel.add(jugarBoton);

        // configurar el boton 'Acerca de' (en la imagen fondo '?')
        acercaDeBoton.setBounds(185, 300, 55, 65);
        acercaDeBoton.addActionListener(this);
        acercaDeBoton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        acercaDeBoton.setContentAreaFilled(false);
        acercaDeBoton.setBorderPainted(false);
        acercaDeBoton.setOpaque(false);
        acercaDeBoton.setFocusPainted(false);
        panel.add(acercaDeBoton);
    }

    // ACTION PERFORMED
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugarBoton) {
            new Juego().setVisible(true);
            dispose();
        } else if (e.getSource() == acercaDeBoton) {
            String textoInformativo = "Tu zalir del zementerio... tu comer zerebrozzz"
                    + "\n...calabazas malas, pozion rica!\n...oponente dudoso";
            JOptionPane.showMessageDialog(this, textoInformativo);
        }
    }

    // MAIN
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuInicial::new);
    }
}