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
    //==================================================================================================================
    private char ganador;
    private JButton btnJugarDeNuevo;
    private JButton btnSalir;
    private Image zombieHombre;
    private Image zombieMujer;
    private Image gameOver; // LOS DOS ZOMBIES MUEREN
    private Image youWin;

    // CONSTRUCTOR
    //==================================================================================================================

    // constructor que recibe un ganador de Tablero por parametro para inicializar un componente u otro en base al resultado
    public MenuFinal(char ganador) {
        this.ganador = ganador; // puede ser 'h' (zombie hombre gano), 'm' (zombie mujer gano) o nulo (ninguno gano)
        this.setBackground(Color.LIGHT_GRAY);
        inicializarComponentes();
    }

    // METODOS
    //==================================================================================================================

    // Metodo que se encarga de cargar las imagenes, inicializar y crear los botones y disenyo
    private void inicializarComponentes() {

        // cargamos imagenes
        try {
            zombieHombre = new ImageIcon(getClass().getResource("imagenes/sprites_zombieM/Walk (1).png")).getImage();
            zombieMujer = new ImageIcon(getClass().getResource("imagenes/sprites_zombieF/Walk (1).png")).getImage();
            gameOver = new ImageIcon(getClass().getResource("/imagenes/gameover.png")).getImage();
            youWin = new ImageIcon(getClass().getResource("/imagenes/youwin.png")).getImage();
        } catch (Exception e) {
            System.out.println("Error cargando imágenes: " + e.getMessage());
        }

        // setteamos el layout a null
        this.setLayout(null);

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
                System.exit(0); // cerramos ventana
            }
        });

        // anyadimos botones al panel
        add(btnJugarDeNuevo);
        add(btnSalir);
    }

    // metodo que se encarga de reiniciar el juego (regresar a Tablero)
    private void reiniciarJuego() {

        // creamos una ventana
        JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);
        ventana.getContentPane().removeAll(); // obtenemos y eliminamos todos los elementos actuales del panel

        // creamos nuevos componentes del juego: Tablero + Cronometro
        Cronometro cronometro = new Cronometro();
        Tablero tablero = new Tablero(cronometro);

        // los anyadimos al ventana creada
        ventana.setLayout(new BorderLayout());
        ventana.add(tablero, BorderLayout.CENTER);
        ventana.add(cronometro, BorderLayout.SOUTH);

        // actualizamos y repintamos la ventana
        ventana.revalidate();
        ventana.repaint();

        // CRUCIAL: solicitamos el foco sobre el tablero para poder jugar con los KeyEvents
        tablero.requestFocusInWindow();
    }

    // PAINTCOMPONENT
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // dibujamos la imagen final en base al resultado
        Image imagenResultado;

        // si ningun jugador gano
        if (ganador == '\0') {
            imagenResultado = gameOver; // cargamos la imagen de perdedores
            g.drawString("GAME OVER", 300, 200); // la dibujamos

        } else {
            imagenResultado = youWin; // cargamos la imagen del ganador al resultado y la dibujamos
            g.drawString("YOU WON", 300, 200);
        }
        g.drawImage(imagenResultado, getWidth()/2 - 150, 50, 300, 100, this);

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