package tetris.blocks;
import java.awt.Color;
import tetris.util.*;

public class JBlock extends Tetromino {
    public JBlock() {
        super(new Point[] {new Point(3, 0), new Point(3, 1), new Point(4, 1), new Point(5, 1)},
                new Point(4, 1), Color.decode("#0058f8"));
    }
}
