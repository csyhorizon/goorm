package main;

interface Diagram {
    double calculateArea();
}

// 원
class QuestCircle implements Diagram {
    private final double radius;

    public QuestCircle(int radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return radius * radius * Math.PI;
    }
}

// 사각형
class QuestRectangle implements Diagram {
    private final double width;
    private final double height;

    public QuestRectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }
}

// 삼각형
class QuestTriangle implements Diagram {
    private final double base;
    private final double height;

    public QuestTriangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }
}

public class 도형_인터페이스_작성하기 {
    public static void main(String[] args) {
        Diagram circle = new QuestCircle(5);
        System.out.println(circle.calculateArea());

        Diagram rectangle = new QuestRectangle(5, 5);
        System.out.println(rectangle.calculateArea());

        Diagram triangle = new QuestTriangle(5, 5);
        System.out.println(triangle.calculateArea());
    }
}