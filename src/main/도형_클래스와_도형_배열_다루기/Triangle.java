package main.도형_클래스와_도형_배열_다루기;

public class Triangle extends Shape {
    private final double base;
    private final double height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
        this.type = "삼각형";
    }

    public double getArea() {
        return 0.5 * base * height;
    }
}
