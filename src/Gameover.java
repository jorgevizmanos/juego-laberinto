import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Gameover extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    // Botones Jugar de nuevo y Salir
    JButton jugarDeNuevoBoton = new JButton("Jugar de nuevo");
    JButton salirBoton = new JButton("Salir");

    public Gameover() {
        // Configuracion de la ventana
        this.setSize(700, 700); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        // Fondo gris
        this.getContentPane().setBackground(new Color(200, 200, 200));

        //  GAME OVER!!
        JLabel titulo = new JLabel("GAME OVER!!");
        titulo.setBounds(200, 20, 300, 50);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(new Color(0, 0, 150));
        titulo.setHorizontalAlignment(JLabel.CENTER);
        this.add(titulo);

        // Boton Jugar de nuevo
        jugarDeNuevoBoton.setBounds(250, 300, 200, 50);
        jugarDeNuevoBoton.setFont(new Font("Arial", Font.BOLD, 16));
        jugarDeNuevoBoton.setBackground(new Color(0, 204, 102)); // Verde
        jugarDeNuevoBoton.setForeground(Color.WHITE);
        jugarDeNuevoBoton.addActionListener(this);
        this.add(jugarDeNuevoBoton);

        // Boton Salir
        salirBoton.setBounds(250, 360, 200, 50);
        salirBoton.setFont(new Font("Arial", Font.BOLD, 16));
        salirBoton.setBackground(new Color(51, 102, 255)); // Azul
        salirBoton.setForeground(Color.WHITE);
        salirBoton.addActionListener(this);
        this.add(salirBoton);

        // Hacer la ventana visible
        this.setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(Gameover::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == jugarDeNuevoBoton) {
            new Ventana().setVisible(true);
            dispose();
        }
        //boton de salir clip
        else if (e.getSource() == salirBoton) {
            // Cerrar la app
            System.exit(0);
        }
    }
}