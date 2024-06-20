import java.awt.Color;
import java.awt.event.KeyEvent;

public abstract class Tetromino {
    private final Color COLOR;
    private final int UPDATE_INTERVAL_MS = 500;
    private final int ROTATE_INTERVAL_MS = 250;

    private final Point[] points;
    private final Point origin;
    private boolean isPlaced;
    private long previousMoveTime;
    private long previousRotateTime;

    public Tetromino(Point[] points, Point origin, Color color) {
        this.points = points;
        this.origin = origin;
        COLOR = color;
        isPlaced = false;
        previousMoveTime = previousRotateTime = System.currentTimeMillis();
    }

    public void update(Color[][] board) {
        final long CURRENT_TIME = System.currentTimeMillis();
        final long MOVE_TIME_DIFF = CURRENT_TIME - previousMoveTime;
        final long ROTATE_TIME_DIFF = CURRENT_TIME - previousRotateTime;

        if (MOVE_TIME_DIFF >= UPDATE_INTERVAL_MS && canMoveDown(board)) {
            incrementYPoints();
            previousMoveTime = System.currentTimeMillis();
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_S) && canMoveDown(board)) {
            incrementYPoints();
        } else if (StdDraw.isKeyPressed(KeyEvent.VK_A) && canMoveLeft(board)) {
            decrementXPoints();
        } else if (StdDraw.isKeyPressed(KeyEvent.VK_D) && canMoveRight(board)) {
            incrementXPoints();
        } else if (StdDraw.isKeyPressed(KeyEvent.VK_R) && ROTATE_TIME_DIFF >= ROTATE_INTERVAL_MS) {
            Point[] rotatedPoints = getRotatedPoints();
            if (canRotate(board, rotatedPoints)) {
                rotate(rotatedPoints);
                previousRotateTime = CURRENT_TIME;
            }
        }
    }

    public void draw() {
        StdDraw.setPenColor(COLOR);
        for (Point point : points) {
            double x = point.getX() * Tetris.CELL_SIZE + Tetris.CELL_SIZE / 2;
            double y = point.getY() * Tetris.CELL_SIZE + Tetris.CELL_SIZE / 2;
            StdDraw.filledRectangle(x, y, Tetris.CELL_SIZE / 2, Tetris.CELL_SIZE / 2);
        }
    }

    private boolean canMoveDown(Color[][] board) {
        for (Point point : points) {
            int x = (int) point.getX();
            int y = (int) point.getY();
            if (y + 1 >= Tetris.ROWS + Tetris.ROW_BUFFER || board[y + 1][x] != null) {
                isPlaced = true;
                return false;
            }
        }
        return true;
    }

    private boolean canMoveLeft(Color[][] board) {
        for (Point point : points) {
            int x = (int) point.getX();
            int y = (int) point.getY();
            if (x <= 0 || board[y][x - 1] != null) {
                return false;
            }
        }
        return true;
    }

    private boolean canMoveRight(Color[][] board) {
        for (Point point : points) {
            int x = (int) point.getX();
            int y = (int) point.getY();
            if (x + 1 >= Tetris.COLS || board[y][x + 1] != null) {
                return false;
            }
        }
        return true;
    }

    private boolean canRotate(Color[][] board, Point[] rotatedPoints) {
        for (Point point : rotatedPoints) {
            if (outOfBounds(point) || intersects(board, point)) {
                return false;
            }
        }
        return true;
    }

    private boolean outOfBounds(Point point) {
        double x = point.getX();
        double y = point.getY();
        return x < 0 || x >= Tetris.COLS || y < 0 || y >= Tetris.ROWS;
    }

    private boolean intersects(Color[][] board, Point point) {
        int x = (int) point.getX();
        int y = (int) point.getY();
        return board[y][x] != null;
    }

    private void rotate(Point[] rotatedPoints) {
        for (int i = 0; i < points.length; i++) {
            points[i].setX(rotatedPoints[i].getX());
            points[i].setY(rotatedPoints[i].getY());
        }
    }

    private void incrementXPoints() {
        for (Point point : points) {
            point.incrementX();
        }
        origin.incrementX();
    }

    private void incrementYPoints() {
        for (Point point : points) {
            point.incrementY();
        }
        origin.incrementY();
    }

    private void decrementXPoints() {
        for (Point point : points) {
            point.decrementX();
        }
        origin.decrementX();
    }

    private Point[] getRotatedPoints() {
        Point[] rotatedPoints = new Point[4];
        for (int i = 0; i < points.length; i++) {
            double x0 = points[i].getX() - origin.getX();
            double y0 = points[i].getY() - origin.getY();
            double x1 = x0 * Math.cos(Math.PI / 2) - y0 * Math.sin(Math.PI / 2);
            double y1 = x0 * Math.sin(Math.PI / 2) + y0 * Math.cos(Math.PI / 2);
            x1 += origin.getX();
            y1 += origin.getY();
            rotatedPoints[i] = new Point(x1, y1);
        }
        return rotatedPoints;
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public Point[] getPoints() {
        return points;
    }

    public Color getColor() {
        return COLOR;
    }
}
