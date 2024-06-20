import java.awt.Color;
import java.util.Arrays;
import java.util.LinkedList;

public class Tetris {
    private static Tetris instance = null;

    public static final int ROWS = 20;
    public static final int ROW_BUFFER = 2;
    public static final int COLS = 10;
    public static final int CELL_SIZE = 32;

    private Color[][] board;
    private LinkedList<Block> bag;
    private Tetromino currentBlock;

    private Tetris() {
        initializeGame();
    }

    public static Tetris getInstance() {
        if (instance == null) {
            instance = new Tetris();
        }
        return instance;
    }

    private void initializeGame() {
        board = new Color[ROWS + ROW_BUFFER][COLS];
        bag = new LinkedList<>();
        fillBag();
        currentBlock = getNextBlock();
    }

    public void reset() {
        initializeGame();
    }

    public void update() {
        checkAndRemoveFullRows();
        if (currentBlock.isPlaced()) {
            Point[] points = currentBlock.getPoints();
            for (Point point : points) {
                int x = (int) point.getX();
                int y = (int) point.getY();
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
        bag.addAll(Arrays.asList(Block.values()));
    }

    private Tetromino getNextBlock() {
        if (bag.isEmpty()) {
            fillBag();
        }
        int rand = (int) (Math.random() * bag.size());
        Block block = bag.get(rand);
        bag.remove(rand);
        return switch (block) {
            case I_BLOCK -> new IBlock();
            case J_BLOCK -> new JBlock();
            case L_BLOCK -> new LBlock();
            case O_BLOCK -> new OBlock();
            case S_BLOCK -> new SBlock();
            case T_BLOCK -> new TBlock();
            case Z_BLOCK -> new ZBlock();
            default -> null;
        };
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
            System.arraycopy(board[row - 1], 0, board[row], 0, COLS);
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
            for (Point point : points) {
                double y = point.getY();
                if (y < ROW_BUFFER) {
                    return true;
                }
            }
        }
        return false;
    }
}
