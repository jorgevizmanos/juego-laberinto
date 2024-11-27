import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaginaFinal extends JPanel {

    // ATRIBUTOS
    //==================================================================================================================
    private char ganador;
    private JButton btnJugarDeNuevo;
    private JButton btnSalir;
    private Image zombieHombre;
    private Image zombieMujer;
    private Image gameOver;
    private Image youWin;

    // CONSTRUCTOR
    //==================================================================================================================
    public PaginaFinal(char ganador) {
        this.ganador = ganador;
        this.setBackground(Color.LIGHT_GRAY);
        inicializarComponentes();
    }

    // METODOS
    //==================================================================================================================
    private void inicializarComponentes() {
        // Cargamos imágenes
        try {
            zombieHombre = new ImageIcon(getClass().getResource("imagenes/sprites_zombieM/Walk (1).png")).getImage();
            zombieMujer = new ImageIcon(getClass().getResource("imagenes/sprites_zombieF/Walk (1).png")).getImage();
            gameOver = new ImageIcon(getClass().getResource("/imagenes/gameover.png")).getImage();
            youWin = new ImageIcon(getClass().getResource("/imagenes/youwin.png")).getImage();
        } catch (Exception e) {
            System.out.println("Error cargando imágenes: " + e.getMessage());
        }

        // Configuramos layout
        setLayout(null);

        // creamos botones
        btnJugarDeNuevo = new JButton("Jugar de nuevo");
        btnJugarDeNuevo.setBounds(300, 400, 150, 40);
        btnJugarDeNuevo.setBackground(new Color(59, 219, 97));
        btnJugarDeNuevo.setFont(new Font("Arial", Font.BOLD, 14));
        btnJugarDeNuevo.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnSalir = new JButton("Salir");
        btnSalir.setBounds(300, 460, 150, 40);
        btnSalir.setBackground(new Color(82, 144, 220));
        btnSalir.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // anyadimos listeners a los botones
        btnJugarDeNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciarJuego();
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // anyadimos botones al panel
        add(btnJugarDeNuevo);
        add(btnSalir);
    }

    private void reiniciarJuego() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().removeAll();

        // creamos nuevos componentes del juego
        Cronometro cronometro = new Cronometro();
        Tablero tablero = new Tablero(cronometro);

        // los anyadimos al frame/ventana
        frame.setLayout(new BorderLayout());
        frame.add(tablero, BorderLayout.CENTER);
        frame.add(cronometro, BorderLayout.SOUTH);

        frame.revalidate();
        frame.repaint();

        // CRUCIAL: solicitamos el foco sobre el tablero
        tablero.requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // dibujamos la imagen del titulo
        Image titulo;
        if (ganador == '\0') {
            titulo = gameOver;
            g.drawString("GAME OVER", 300, 200);

        } else {
            titulo = youWin;
            g.drawString("YOU WON", 300, 200);
        }
        g.drawImage(titulo, getWidth()/2 - 150, 50, 300, 100, this);

        // dibujamos zombies segun ganador
        if (ganador == 'h') {
            g.drawImage(zombieHombre, getWidth()/2 - 100, 200, 200, 200, this);
        } else if (ganador == 'm') {
            g.drawImage(zombieMujer, getWidth()/2 - 100, 200, 200, 200, this);
        } else {
            g.drawImage(zombieHombre, 100, 200, 200, 200, this);
            g.drawImage(zombieMujer, getWidth() - 300, 200, 200, 200, this);
        }
    }
}