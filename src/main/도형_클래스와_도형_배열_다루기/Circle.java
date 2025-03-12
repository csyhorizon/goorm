package main.도형_클래스와_도형_배열_다루기;

public class Circle extends Shape {
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
        this.type ="원";
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }
}
