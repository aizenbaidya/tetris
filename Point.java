public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void incrementX() {
        x += 1;
    }

    public void incrementY() {
        y += 1;
    }

    public void decrementX() {
        x -= 1;
    }
}
