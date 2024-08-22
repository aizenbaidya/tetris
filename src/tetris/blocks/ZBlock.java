package tetris.blocks;

import java.awt.Color;
import tetris.util.Point;

public class ZBlock extends Tetromino {
    public ZBlock() {
        super(new Point[] {new Point(3, 0), new Point(4, 0), new Point(4, 1), new Point(5, 1)},
                new Point(4, 1), Color.decode("#a80020"));
    }
}
