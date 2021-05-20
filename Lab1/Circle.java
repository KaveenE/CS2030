public class Circle {

    private final Point centre;
    private final double radius;

    public Circle(Point centre, double radius) {
        this.centre = new Point(centre.getX(), centre.getY());
        this.radius = radius;
    }

    public static Circle createCircle(Point centre, double radius) {
        return new Circle(centre, radius);
    }

    public double getRadius() {
        return this.radius;
    }

    @Override
    public String toString() {
        return String.format("circle of radius %.1f centered at %s",this.radius, this.centre);
    }

    boolean contains(Point p) {
        
        return Double.compare(radius, centre.distanceTo(p)) >= 0;
    }
}


