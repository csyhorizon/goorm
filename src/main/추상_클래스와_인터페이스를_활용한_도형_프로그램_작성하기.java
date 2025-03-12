package main;

abstract class Shape {
    abstract double getArea();
}

interface Measurable {
    double getArea();
}

class Circle extends Shape implements Measurable {
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape implements Measurable {
    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }
}

public class 추상_클래스와_인터페이스를_활용한_도형_프로그램_작성하기 {
    public static void main(String[] args) {
        Shape circle = new Circle(5);
        Shape rectangle = new Rectangle(5, 5);

        System.out.println(circle.getArea());
        System.out.println(rectangle.getArea());
    }
}
