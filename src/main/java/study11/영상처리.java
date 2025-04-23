package study11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class 영상처리 {

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public static void main(String[] args) throws IOException {
        // BFS, 방문, 크기 문제
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());

        boolean[][] arr = new boolean[y][x];

        for(int i = 0; i < arr.length; i++) {
            String s = br.readLine();
            for(int j = 0; j < arr[i].length; j++) {
                if (s.charAt(j) == '#') {
                    arr[i][j] = true;
                }
            }
        }

        int count = 0, maxSize = 0;

        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr[i].length; j++) {
                if (arr[i][j]) {
                    count ++;
                    arr[i][j] = false;

                    ArrayDeque<int[]> q = new ArrayDeque<>();
                    q.addLast(new int[]{i, j});
                    int size = 1;

                    while(!q.isEmpty()) {
                        int[] now = q.removeFirst();
                        int nowX = now[0];
                        int nowY = now[1];

                        for(int m = 0; m < 4; m++) {
                            int movePosX = nowX + dx[m];
                            int movePosY = nowY + dy[m];

                            if (movePosX >= 0 && movePosY >= 0 && movePosX < y && movePosY < x) {
                                if (arr[movePosX][movePosY]) {
                                    arr[movePosX][movePosY] = false;
                                    q.addLast(new int[]{movePosX, movePosY});
                                    size ++;
                                }
                            }
                        }
                    }
                    maxSize = Math.max(maxSize, size);
                }
            }
        }

        System.out.println(count + "\n" + maxSize);
    }
}
