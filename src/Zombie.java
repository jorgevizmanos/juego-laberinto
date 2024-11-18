import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Zombie {
    // ATRIBUTOS
    //==================================================================================================================
    private final int TAMANYO_ZOMBIE = 35; // MISMO QUE TAMNYO BLOQUE LAB
    private int x, y;
    private int velocidad = 15; // desplazamiento del zombie MISMO QUE TAMNYO BLOQUE LAB
    private ArrayList<Image> animacion; // lista de imagenes en donde se cargara cada sprite
    private int spriteActual = 0; // indice del sprite actual (fotograma)
    private Sexo sexo;
    protected Rectangle limites = new Rectangle(x, y, TAMANYO_ZOMBIE, TAMANYO_ZOMBIE);;


    // CONSTRUCTORES
    //==================================================================================================================
    public Zombie(Sexo sexo, Component observer) { // observer necesitado para inicializar sprites
        this.sexo = sexo;
        this.animacion = new ArrayList<>();
        this.limites = new Rectangle(0, 0, TAMANYO_ZOMBIE, TAMANYO_ZOMBIE); // limites se actualizan en metodos de mover (conforme se mueve zombie)
        inicializarSprites(observer); // cargamos recursos sprites PERO la animacion debe ser actualizada en actionPerformed() de Tablero
    }

    // METODOS
    //==================================================================================================================
    private void inicializarSprites(Component observer) { // pasamos el oberver, que es 'this' en Tablero
        // elegimos carpeta de sprites segun el sexo del zombie
        String carpeta;
        if (sexo == Sexo.HOMBRE) {
            carpeta = "sprites_zombieM"; // ASI SE LLAMA LA CARPETA DE RECURSOS
        } else {
            carpeta = "sprites_zombieF";
        }

        // recorremos cada imagen dentro de la carpeta de sprites para anyadirlas a la animacion (son 5)
        for (int i = 1; i <= 5; i++) {
            try {
                String ruta = "imagenes/" + carpeta + "/Walk (" + i + ").png"; // carpeta--> directorio M/H; i--> imagen actual del directorio
                Image spriteOriginal = new ImageIcon(getClass().getResource(ruta)).getImage(); // volcamos la imagen actual del directorio en variable

                // ajustamos tamanyo imagen: calculamos la nueva anchura manteniendo la proporcion de la imagen
                double proporcion = (double) spriteOriginal.getWidth(observer) / spriteOriginal.getHeight(observer);
                int nuevoAncho = (int) (TAMANYO_ZOMBIE * proporcion);

                // creamos imagen escalada manteniendo proporciones
                Image spriteEscalado = spriteOriginal.getScaledInstance(nuevoAncho, TAMANYO_ZOMBIE, Image.SCALE_SMOOTH);
                animacion.add(spriteEscalado);

            } catch (Exception e) {
                System.out.println("Error cargando sprite " + i + ": " + e.getMessage());
            }
        }
    }

    public void pintar(Graphics g, Component observer, boolean reflejado) {
        if (!animacion.isEmpty()) {
            Graphics2D g2d = (Graphics2D) g;
            Image spriteActual = animacion.get(this.spriteActual);

            if (reflejado) {
                // reflejamos horizontalmente la imagen
                g2d.drawImage(spriteActual, x + getTamanyo(), y, -getTamanyo(), getTamanyo(), observer); // width inverso

                g2d.setColor(Color.RED); // BORRAR MAS ADELANTE
                g2d.drawRect(x, y, getTamanyo(), getTamanyo()); // BORRAR MAS ADELANTE (caja envolvente al zombie)
            } else {
                // dibujo sin reflejo
                g2d.drawImage(spriteActual, x, y, getTamanyo(), getTamanyo(), observer);

                g2d.setColor(Color.RED); // BORRAR MAS ADELANTE
                g2d.drawRect(x, y, getTamanyo(), getTamanyo()); // BORRAR MAS ADELANTE (caja envolvente al zombie)
            }
        }
    }

    public void actualizarAnimacion() {
        spriteActual = (spriteActual + 1) % animacion.size(); // metodo copiado de Andres... xd
    }

    public void moverZombieHombre(KeyEvent evento, Laberinto laberinto) {
        int[][] lab = laberinto.crearLaberinto();
        int movimiento = velocidad; // La distancia a mover en este turno
        int nuevoX = x;
        int nuevoY = y;

        while (movimiento > 0) {
            int paso = Math.min(5, movimiento); // Mueve en pasos de 5 píxeles

            if (evento.getKeyCode() == KeyEvent.VK_LEFT) {
                nuevoX -= paso;
            } else if (evento.getKeyCode() == KeyEvent.VK_RIGHT) {
                nuevoX += paso;
            } else if (evento.getKeyCode() == KeyEvent.VK_UP) {
                nuevoY -= paso;
            } else if (evento.getKeyCode() == KeyEvent.VK_DOWN) {
                nuevoY += paso;
            }

            if (hayColision(nuevoX, nuevoY, laberinto)) {
                break; // Detener el movimiento si hay colisión
            }

            // Actualizar la posición
            x = nuevoX;
            y = nuevoY;
            this.limites.x = x;
            this.limites.y = y;

            movimiento -= paso; // Reducir la distancia restante a mover
        }
    }



    private boolean hayColision(int nuevoX, int nuevoY, Laberinto laberinto) {
        int[][] lab = laberinto.crearLaberinto();
        int anchoBloque = laberinto.getAnchoBloque();
        int altoBloque = laberinto.getAltoBloque();
        Rectangle zombieRect = new Rectangle(nuevoX, nuevoY, TAMANYO_ZOMBIE, TAMANYO_ZOMBIE);

        for (int fila = 0; fila < lab.length; fila++) {
            for (int col = 0; col < lab[0].length; col++) {
                if (lab[fila][col] == 1) {
                    Rectangle bloqueRect = new Rectangle(col * anchoBloque, fila * altoBloque, anchoBloque, altoBloque);
                    if (zombieRect.intersects(bloqueRect)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }




    public void moverZombieFemenino(KeyEvent evento, Laberinto laberinto) {
        int[][] lab = laberinto.crearLaberinto();

        if (evento.getKeyCode() == KeyEvent.VK_A) {
            if (lab[y / laberinto.getAltoBloque()][(x / laberinto.getAnchoBloque()) - 1] != 1) {
                x = x - velocidad;
                this.limites.x = x;
            }
        }

        if (evento.getKeyCode() == KeyEvent.VK_D) {
            if (lab[y / laberinto.getAltoBloque()][(x / laberinto.getAnchoBloque()) + 1] != 1) {
                x = x + velocidad;
                this.limites.x = x;
            }
        }
        if (evento.getKeyCode() == KeyEvent.VK_W) {
            if (lab[(y / laberinto.getAltoBloque()) - 1][x / laberinto.getAnchoBloque()] != 1) {
                y = y - velocidad;
                this.limites.y = y;
            }
        }
        if (evento.getKeyCode() == KeyEvent.VK_S) {
            if (lab[(y / laberinto.getAltoBloque()) + 1][x / laberinto.getAnchoBloque()] != 1) {
                y = y + velocidad;
                this.limites.y = y;
            }
        }
    }

    // METODOS SETTERS & GETTERS
    //==================================================================================================================

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getTamanyo() {
        return TAMANYO_ZOMBIE;
    }
}