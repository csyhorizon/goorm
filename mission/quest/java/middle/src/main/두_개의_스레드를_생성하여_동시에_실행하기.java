package main;

class HelloThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println("hello " + i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.fillInStackTrace();
            }
        }
    }
}

class ByeThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println("bye " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.fillInStackTrace();
            }
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
