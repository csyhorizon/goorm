package main.도형_클래스와_도형_배열_다루기;

public class Rectangle extends Shape {
    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
        this.type = "사각형";
    }

    public double getArea() {
        return width * height;
    }
}
