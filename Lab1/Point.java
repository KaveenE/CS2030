public class Point {

    private final double x;
    private final double y;

    public Point(double x,double y) {
        this.y = y;
        this.x = x;
    }


    public static Point createPoint(double x, double y) {
        return new Point(x,y);
    }



    //Getters
    public double getX() {
        return x;
    } 

    public double getY() {
        return y;
    }



    //Removed javadocs
    // @param {@code other} Looks at some coordinate
    // @return midpoint of  {@code this Point} and {@code other}
    Point midPoint(Point other) {

        double newX = (this.x + other.x) / 2;
        double newY = (this.y + other.y) / 2;
        return Point.createPoint(newX, newY);  
    }

    public double distanceTo(Point other) {

        return Math.sqrt(square(other.x - this.x) + square(other.y - this.y));
    }

    Point moveTo(double theta, double distanceBetweenPoints) {

        double newX = Math.cos(theta) * distanceBetweenPoints + this.x;
        double newY = Math.sin(theta) * distanceBetweenPoints + this.y;

        return Point.createPoint(newX, newY);

    }

    //Removed javadocs
    //@param {@code other} Takes some coordinate
    // @return angle wrt to the imaginery line cutting {@code Point} in anticlockwise
    // direction till we reachh the {@code other Point}
    double angleTo(Point other) {
        
        return Math.atan2(other.y - this.y, other.x - this.x);
    }

    private double square(double val) {

        return val * val;
    }

    @Override
    public String toString() {
        return String.format("point (%.3f, %.3f)", this.x, this.y); 
    }

}
