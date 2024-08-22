package tetris.core;

import java.awt.Font;
import java.awt.event.KeyEvent;
import tetris.util.StdDraw;

public class Main {
    public static final int WIDTH = Tetris.COLS * Tetris.CELL_SIZE;
    public static final int HEIGHT = Tetris.ROWS * Tetris.CELL_SIZE;

    public static void main(String[] args) {
        StdDraw.setTitle("Tetris");
        StdDraw.setCanvasSize(WIDTH, HEIGHT);
        StdDraw.setXscale(0, WIDTH);
        final int HEIGHT_BUFFER = Tetris.ROW_BUFFER * Tetris.CELL_SIZE;
        StdDraw.setYscale(HEIGHT + HEIGHT_BUFFER, HEIGHT_BUFFER);
        StdDraw.enableDoubleBuffering();
        StdDraw.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
        runGameLoop();
    }

    private static void runGameLoop() {
        TitleScreen.display();

        Tetris tetris = Tetris.getInstance();
        while (true) {
            if (StdDraw.isKeyPressed(KeyEvent.VK_ESCAPE)) {
                System.exit(0);
            }

            if (tetris.isGameOver()) {
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.text(WIDTH / 2, HEIGHT / 2, "GAME OVER!");

                if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
                    tetris.reset();
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
