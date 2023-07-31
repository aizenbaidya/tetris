import java.awt.Font;
import java.awt.event.KeyEvent;

public class Main {
    public static void main(String[] args) {
        final int WIDTH = Tetris.COLS * Tetris.CELL_SIZE;
        final int HEIGHT = Tetris.ROWS * Tetris.CELL_SIZE;
        StdDraw.setTitle("Tetris");
        StdDraw.setCanvasSize(WIDTH, HEIGHT);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(HEIGHT + Tetris.ROW_BUFFER * Tetris.CELL_SIZE,
                Tetris.ROW_BUFFER * Tetris.CELL_SIZE);
        StdDraw.enableDoubleBuffering();
        StdDraw.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
        Tetris tetris = new Tetris();
        while (true) {
            if (tetris.isGameOver()) {
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.text(WIDTH / 2, HEIGHT / 2, "GAME OVER!");
                if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
                    tetris = new Tetris();
                }
            } else {
                StdDraw.clear(StdDraw.BLACK);
                tetris.update();
                tetris.draw();
            }
            StdDraw.show();
            StdDraw.pause(67);
        }
    }
}
