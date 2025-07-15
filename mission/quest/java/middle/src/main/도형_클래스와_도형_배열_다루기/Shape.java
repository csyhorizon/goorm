package main.도형_클래스와_도형_배열_다루기;

abstract class Shape {
    protected String type;
    public abstract double getArea();

    public String getType() {
        return type;
    }
}