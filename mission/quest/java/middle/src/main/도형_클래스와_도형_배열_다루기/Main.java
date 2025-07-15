package main.도형_클래스와_도형_배열_다루기;

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = new Shape[3];

        shapes[0] = new Circle(5);
        shapes[1] = new Rectangle(4, 5);
        shapes[2] = new Triangle(4, 4);

        for (Shape shape : shapes) {
            System.out.println(shape.getType() + "의 면적 : " + shape.getArea());
        }
    }
}
