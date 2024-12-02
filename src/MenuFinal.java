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

    // ATRIBUTOS
    // =================================================================================================================
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

    // CONSTRUCTORES
    // =================================================================================================================
    private void inicializarComponentes() {

        // volcamos imagenes en variables de la clase
        try {
            gameOver = new ImageIcon(getClass().getResource("/imagenes/gameOver.png")).getImage();
            youWin = new ImageIcon(getClass().getResource("/imagenes/youwin.png")).getImage();
        } catch (Exception e) {
            System.out.println("Error cargando imágenes: " + e.getMessage());
        }

        // creamos boton de SALIR (y lo hacemos funcional pero transparente, pues la imagen posee el boton dinbujado)
        btnSalir = new JButton();
        btnSalir.setBounds(45, 670, 105, 70);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR)); // para mostrar manita sobre el boton
        btnSalir.addActionListener(e -> System.exit(0)); // CERRAMOS VENTANA SALIENDO DEL SISTEMA
        btnSalir.setContentAreaFilled(false);  // sin relleno
        btnSalir.setBorderPainted(false); // sin borde
        btnSalir.setFocusPainted(false); // sin efecto focus
        btnSalir.setOpaque(false);// transparente


        // creamos boton de RESTART (y lo hacemos funcional pero transparente, pues la imagen posee el boton dinbujado)
        btnJugarDeNuevo = new JButton();
        btnJugarDeNuevo.setBounds(492, 670, 170, 70);
        btnJugarDeNuevo.setCursor(new Cursor(Cursor.HAND_CURSOR)); // para mostrar manita sobre el boton
        btnJugarDeNuevo.addActionListener(e -> reiniciarJuego()); // REINICIA EL JUEGO
        btnJugarDeNuevo.setContentAreaFilled(false); // sin relleno
        btnJugarDeNuevo.setBorderPainted(false); // sin borde
        btnJugarDeNuevo.setFocusPainted(false); // sin efecto focus
        btnJugarDeNuevo.setOpaque(false); // transparente

        add(btnJugarDeNuevo);
        add(btnSalir);
    }

    // METODOS
    // =================================================================================================================

    // Metodo que reinicia el juego de nuevo, trayendo las clases sobre este mismo panel
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

    // PAINTCOMPONENT
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // en base a si gana un zombie o ninguno, dibujamos un fondo de ganadores u otro de Game Over
        Image imagenResultado = ganador == '\0' ? gameOver : youWin;
        g.drawImage(imagenResultado, 0, 0, getWidth(), getHeight(), this);

        // si hay un ganador, dibujamos al zombie (sprite 1) sobre el fondo de ganadores
        if (ganador == 'h') {
            Image zombieHombre = new ImageIcon(getClass().getResource("imagenes/sprites_zombieM/Walk (1).png")).getImage();
            g.drawImage(zombieHombre, getWidth()/2 - 100, 300, 200, 200, this);
        } else if (ganador == 'm') {
            Image zombieMujer = new ImageIcon(getClass().getResource("imagenes/sprites_zombieF/Walk (1).png")).getImage();
            g.drawImage(zombieMujer, getWidth()/2 - 100, 300, 200, 200, this);
        }
    }
}