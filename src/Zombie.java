import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Zombie {
    // ATRIBUTOS
    //==================================================================================================================
    private int x, y;
    private final int TAMANYO_ZOMBIE = 30; // ancho y alto son lo mismo
    private int velocidad = 10;
    protected Rectangle limites;
    private ArrayList<Image> animacion; // lista de imagenes en donde se cargara cada sprite
    private int spriteActual = 0; // indice del sprite actual (fotograma)
    private Sexo sexo;


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
    private void inicializarSprites(Component observer) {
        String carpeta;
        if (sexo == Sexo.HOMBRE) {
            carpeta = "sprites_zombieM";
        } else {
            carpeta = "sprites_zombieF";
        }

        for (int i = 1; i <= 5; i++) {
            try {
                String ruta = "imagenes/" + carpeta + "/Walk (" + i + ").png";
                Image spriteOriginal = new ImageIcon(getClass().getResource(ruta)).getImage();

                // Forzar carga inmediata
                MediaTracker tracker = new MediaTracker(observer);
                tracker.addImage(spriteOriginal, 0);
                tracker.waitForAll();

                double proporcion = (double) spriteOriginal.getWidth(observer) / spriteOriginal.getHeight(observer);
                int nuevoAncho = (int) (TAMANYO_ZOMBIE * proporcion);
                Image spriteEscalado = spriteOriginal.getScaledInstance(nuevoAncho, TAMANYO_ZOMBIE, Image.SCALE_SMOOTH);

                tracker.addImage(spriteEscalado, 1);
                tracker.waitForAll();

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

    public boolean moverZombieHombre(KeyEvent evento, Laberinto laberinto) {
        int movimiento = velocidad;
        int nuevoX = x;
        int nuevoY = y;
        boolean seMovio = false;
        int tecla = evento.getKeyCode();

        // Solo procesa teclas de flechas
        switch(tecla) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                while (movimiento > 0) {
                    int paso = Math.min(1, movimiento);

                    if (tecla == KeyEvent.VK_LEFT) nuevoX -= paso;
                    else if (tecla == KeyEvent.VK_RIGHT) nuevoX += paso;
                    else if (tecla == KeyEvent.VK_UP) nuevoY -= paso;
                    else if (tecla == KeyEvent.VK_DOWN) nuevoY += paso;

                    if (!hayColision(nuevoX, nuevoY, laberinto)) {
                        x = nuevoX;
                        y = nuevoY;
                        this.limites.x = x;
                        this.limites.y = y;
                        seMovio = true;
                    }
                    movimiento -= paso;
                }
                break;
        }
        return seMovio;
    }

    public boolean moverZombieMujer(KeyEvent evento, Laberinto laberinto) {
        int movimiento = velocidad;
        int nuevoX = x;
        int nuevoY = y;
        boolean seMovio = false;
        int tecla = evento.getKeyCode();

        // Solo procesa teclas WASD
        switch(tecla) {
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
                while (movimiento > 0) {
                    int paso = Math.min(1, movimiento);

                    if (tecla == KeyEvent.VK_A) nuevoX -= paso;
                    else if (tecla == KeyEvent.VK_D) nuevoX += paso;
                    else if (tecla == KeyEvent.VK_W) nuevoY -= paso;
                    else if (tecla == KeyEvent.VK_S) nuevoY += paso;

                    if (!hayColision(nuevoX, nuevoY, laberinto)) {
                        x = nuevoX;
                        y = nuevoY;
                        this.limites.x = x;
                        this.limites.y = y;
                        seMovio = true;
                    }
                    movimiento -= paso;
                }
                break;
        }
        return seMovio;
    }

    private boolean hayColision(int nuevoX, int nuevoY, Laberinto laberinto) {
        int[][] lab = laberinto.crearLaberinto();
        int anchoBloque = laberinto.getAnchoBloque();
        int altoBloque = laberinto.getAltoBloque();

        // ajuste para centrar el area de colision del zombie
        int margen = (TAMANYO_ZOMBIE - 25) / 2;

        // puntos a comprobar con el margen
        int[] puntosX = {nuevoX + margen, nuevoX + TAMANYO_ZOMBIE - margen};
        int[] puntosY = {nuevoY + margen, nuevoY + TAMANYO_ZOMBIE - margen};

        // Permitir salir por la izquierda en la fila 15
        if (nuevoY / altoBloque == 15 && nuevoX < 0) {
            return false;
        }

        for (int px : puntosX) {
            for (int py : puntosY) {
                int filaLab = py / altoBloque;
                int colLab = px / anchoBloque;

                // Verificar límites del laberinto
                if (filaLab < 0 || filaLab >= lab.length ||
                        colLab < 0 || colLab >= lab[0].length) {
                    return true;
                }

                // Solo colisiona con muros (1), no con la salida (2)
                if (lab[filaLab][colLab] == 1) {
                    return true;
                }
            }
        }
        return false;
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