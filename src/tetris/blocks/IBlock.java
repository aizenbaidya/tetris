package tetris.blocks;
import java.awt.Color;
import tetris.util.*;

public class IBlock extends Tetromino {
    public IBlock() {
        super(new Point[] {new Point(3, 0), new Point(4, 0), new Point(5, 0), new Point(6, 0)},
                new Point(4.5, 0.5), Color.decode("#3cbcfc"));
    }
}
