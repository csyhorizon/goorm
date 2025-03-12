package main.직사각형_클래스_작성하기;

public class Main {
    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle(5, 5);
        System.out.println("넒이 : " + rectangle.getArea());
        System.out.println("둘레 : " + rectangle.getPerimeter());
    }
}
