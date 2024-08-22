package tetris.core;

import java.awt.Font;
import tetris.util.StdDraw;

public class TitleScreen {
    public static void display() {
        StdDraw.clear(StdDraw.BLACK);

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
        StdDraw.text(Main.WIDTH / 2.0, Main.HEIGHT / 2.0 - 100, "TETRIS");

        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.filledRectangle(Main.WIDTH / 2, Main.HEIGHT / 2, 100, 40);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        StdDraw.text(Main.WIDTH / 2, Main.HEIGHT / 2, "Start Game");

        StdDraw.show();

        // Wait for a mouse click on the start button
        while (true) {
            if (StdDraw.isMousePressed()) {
                double mouseX = StdDraw.mouseX();
                double mouseY = StdDraw.mouseY();
                if (mouseX > Main.WIDTH / 2 - 100 && mouseX < Main.WIDTH / 2 + 100
                        && mouseY > Main.HEIGHT / 2 - 40 && mouseY < Main.HEIGHT / 2 + 40) {
                    // Start button clicked
                    break;
                }
            }
            StdDraw.pause(50); // Small pause to reduce CPU usage
        }
    }
}
