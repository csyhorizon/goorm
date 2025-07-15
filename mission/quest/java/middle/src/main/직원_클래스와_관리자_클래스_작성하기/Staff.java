package main.직원_클래스와_관리자_클래스_작성하기;

class Staff {
    private final String name;
    private final int salary;

    public Staff(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public void displayInfo() {
        System.out.println(name + "은 " + salary + "원을 받고 일하는 직원입니다.");
    }
}
