import java.awt.Color;
import java.util.LinkedList;

public class Tetris {
    public static final int ROWS = 20;
    public static final int ROW_BUFFER = 2;
    public static final int COLS = 10;
    public static final int CELL_SIZE = 32;

    private Color[][] board;
    private LinkedList<Block> bag;
    private Tetromino currentBlock;

    public Tetris() {
        board = new Color[ROWS + ROW_BUFFER][COLS];
        bag = new LinkedList<>();
        fillBag();
        currentBlock = getNextBlock();
    }

    public void update() {
        checkAndRemoveFullRows();
        if (currentBlock.isPlaced()) {
            Point[] points = currentBlock.getPoints();
            for (int i = 0; i < points.length; i++) {
                int x = (int) points[i].getX();
                int y = (int) points[i].getY();
                board[y][x] = currentBlock.getColor();
            }
            currentBlock = getNextBlock();
        } else {
            currentBlock.update(board);
        }
    }

    public void draw() {
        currentBlock.draw();
        drawBoard();
        drawGrid();
    }

    private void fillBag() {
        for (Block block : Block.values()) {
            bag.add(block);
        }
    }

    private Tetromino getNextBlock() {
        if (bag.isEmpty()) {
            fillBag();
        }
        int rand = (int) (Math.random() * bag.size());
        Block block = bag.get(rand);
        bag.remove(rand);
        switch (block) {
            case I_BLOCK:
                return new IBlock();
            case J_BLOCK:
                return new JBlock();
            case L_BLOCK:
                return new LBlock();
            case O_BLOCK:
                return new OBlock();
            case S_BLOCK:
                return new SBlock();
            case T_BLOCK:
                return new TBlock();
            case Z_BLOCK:
                return new ZBlock();
            default:
                return null;
        }
    }

    private void checkAndRemoveFullRows() {
        for (int row = ROWS + ROW_BUFFER - 1; row >= ROW_BUFFER; row--) {
            if (isRowFull(row)) {
                shiftAllRowsDown(row);
            }
        }
    }

    private boolean isRowFull(int row) {
        for (int col = 0; col < COLS; col++) {
            if (board[row][col] == null) {
                return false;
            }
        }
        return true;
    }

    private void shiftAllRowsDown(int r) {
        for (int row = r; row > ROW_BUFFER; row--) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = board[row - 1][col];
            }
        }
    }

    private void drawBoard() {
        for (int row = ROW_BUFFER; row < ROWS + ROW_BUFFER; row++) {
            int y = row * CELL_SIZE + CELL_SIZE / 2;
            for (int col = 0; col < COLS; col++) {
                int x = col * CELL_SIZE + CELL_SIZE / 2;
                Color color = board[row][col];
                if (color != null) {
                    StdDraw.setPenColor(color);
                    StdDraw.filledRectangle(x, y, CELL_SIZE / 2, CELL_SIZE / 2);
                }
            }
        }
    }

    private void drawGrid() {
        StdDraw.setPenColor(StdDraw.GRAY);
        for (int row = ROW_BUFFER + 1; row < ROWS + ROW_BUFFER; row++) {
            int y = row * CELL_SIZE;
            StdDraw.line(0, y, COLS * CELL_SIZE, y);
        }
        for (int col = 1; col < COLS; col++) {
            int x = col * CELL_SIZE;
            StdDraw.line(x, 0, x, (ROWS + ROW_BUFFER) * CELL_SIZE);
        }
    }

    public boolean isGameOver() {
        if (currentBlock.isPlaced()) {
            Point[] points = currentBlock.getPoints();
            for (int i = 0; i < points.length; i++) {
                double y = points[i].getY();
                if (y < ROW_BUFFER) {
                    return true;
                }
            }
        }
        return false;
    }
}
