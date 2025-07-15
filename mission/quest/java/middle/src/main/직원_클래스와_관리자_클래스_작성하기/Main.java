package main.직원_클래스와_관리자_클래스_작성하기;

public class Main {
    public static void main(String[] args) {
        Staff staff = new Staff("김철수", 2200000);
        Staff admin = new Admin("관찰레", 999999999, "보안");

        staff.displayInfo();
        admin.displayInfo();
    }
}
