package tetris.blocks;

import tetris.core.Tetris;
import tetris.util.Point;
import tetris.util.StdDraw;
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

    private static final double COS_PI_OVER_2 = Math.cos(Math.PI / 2);
    private static final double SIN_PI_OVER_2 = Math.sin(Math.PI / 2);

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

        boolean moved = false;

        if (MOVE_TIME_DIFF >= UPDATE_INTERVAL_MS && canMoveDown(board)) {
            move(0, 1);
            previousMoveTime = CURRENT_TIME;
            moved = true;
        }
        if (!moved) {
            if (StdDraw.isKeyPressed(KeyEvent.VK_S) && canMoveDown(board)) {
                move(0, 1);
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_A) && canMoveLeft(board)) {
                move(-1, 0);
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_D) && canMoveRight(board)) {
                move(1, 0);
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_R)
                    && ROTATE_TIME_DIFF >= ROTATE_INTERVAL_MS) {
                Point[] rotatedPoints = getRotatedPoints();
                if (canRotate(board, rotatedPoints)) {
                    rotate(rotatedPoints);
                    previousRotateTime = CURRENT_TIME;
                }
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
        return canMove(board, 0, 1);
    }

    private boolean canMoveLeft(Color[][] board) {
        return canMove(board, -1, 0);
    }

    private boolean canMoveRight(Color[][] board) {
        return canMove(board, 1, 0);
    }

    private boolean canMove(Color[][] board, int dx, int dy) {
        for (Point point : points) {
            int x = (int) point.getX();
            int y = (int) point.getY();
            if (x + dx < 0 || x + dx >= Tetris.COLS || y + dy >= Tetris.ROWS + Tetris.ROW_BUFFER
                    || board[y + dy][x + dx] != null) {
                if (dy > 0) {
                    isPlaced = true;
                }
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

    private void move(int dx, int dy) {
        for (Point point : points) {
            point.incrementX(dx);
            point.incrementY(dy);
        }
        origin.incrementX(dx);
        origin.incrementY(dy);
    }

    private Point[] getRotatedPoints() {
        Point[] rotatedPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            double x0 = points[i].getX() - origin.getX();
            double y0 = points[i].getY() - origin.getY();
            double x1 = x0 * COS_PI_OVER_2 - y0 * SIN_PI_OVER_2;
            double y1 = x0 * SIN_PI_OVER_2 + y0 * COS_PI_OVER_2;
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
