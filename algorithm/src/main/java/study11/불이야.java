package study11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class 불이야 {

    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // BFS 문제
        // 그냥 퍼지다가 만나면 끝내는 문제
        // Queue로 해결 - 단, 종료까지 안만나면 그대로 종료
        // 바로 옆에 불이 존재하는 경우도 있음 (그런 경우 -1이 아닌 0으로 처리)
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());

        char[][] arr = new char[x][y];
        ArrayDeque<int[]> dq = new ArrayDeque<>();

        for(int i = 0; i < x; i++) {
            String s = br.readLine();
            for (int j = 0; j < y; j++) {
                arr[i][j] = s.charAt(j);
                if (arr[i][j] == '@') {
                    dq.addLast(new int[]{0, i, j});
                }
            }
        }

        int fire = 0;
        boolean now_break = false;

        while (!dq.isEmpty()) {
            int[] now = dq.removeFirst();

            for(int i = 0; i < 4; i++) {
                int movex = now[1] + dx[i];
                int movey = now[2] + dy[i];

                if (movex >= 0 && movey >= 0 && movex < x && movey < y) {
                    if (arr[movex][movey] == '.') {
                        dq.addLast(new int[]{now[0] + 1, movex, movey});
                        arr[movex][movey] = '@';
                    }
                    if (arr[movex][movey] == '&') {
                        now_break = true;
                        fire = now[0];
                        break;
                    }
                }
            }
            if (now_break) break;
        }
        System.out.println(fire == 0 ? now_break ? 0 : -1 : fire);
    }
}
