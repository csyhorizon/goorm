package main.직원_클래스와_관리자_클래스_작성하기;

public class Admin extends Staff{
    private final String position;

    public Admin(String name, int salary, String position) {
        super(name, salary);
        this.position = position;
    }

    @Override
    public void displayInfo() {
        System.out.println(getName() + "는 " + getSalary() + "를 받고 일하는 " + position + " 관리자입니다.");
    }
}
