package study03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class replica_robot {

    public static class RobotPos {
        private int x = 0;
        private int y = 0;
        private final HashSet<String> set;

        public RobotPos(int x, int y) {
            this.x = x;
            this.y = y;
            set = new HashSet<>();
        }

        public void setmovelock(int x, int y) {
            set.add(x + " " + y);
        }

        public void moveCheck(char move) {
            switch (move) {
                case 'R':
                    if (!set.contains((x + 1) + " " + y)) x += 1;
                    break;
                case 'L':
                    if (!set.contains((x - 1) + " " + y)) x -= 1;
                    break;
                case 'U':
                    if (!set.contains(x + " " + (y + 1))) y += 1;
                    break;
                case 'D':
                    if (!set.contains(x + " " + (y - 1))) y -= 1;
                    break;
            }
        }

        @Override
        public String toString() {
            return x + " " + y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        RobotPos robotPos = new RobotPos(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));

        int n = Integer.parseInt(br.readLine());
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            robotPos.setmovelock(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        int input = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < input; i++) {
            robotPos.moveCheck(st.nextToken().charAt(0));
        }

        System.out.println(robotPos);
    }
}
