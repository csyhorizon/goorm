package main;

class HelloThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println("hello " + i);
        }
    }
}

class ByeThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println("bye " + i);
        }
    }
}

public class 두_개의_스레드를_생성하여_동시에_실행하기 {
    public static void main(String[] args) {
        HelloThread helloThread = new HelloThread();
        ByeThread byeThread = new ByeThread();

        helloThread.start();
        byeThread.start();
    }
}
