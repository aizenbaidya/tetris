package tetris.blocks;
import java.awt.Color;
import tetris.util.*;

public class SBlock extends Tetromino {
    public SBlock() {
        super(new Point[] {new Point(3, 1), new Point(4, 1), new Point(4, 0), new Point(5, 0)},
                new Point(4, 1), Color.decode("#58d854"));
    }
}
