package study10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class 연합 {
    public static void main(String[] args) throws IOException {
        // 양방향 연결지점 MST 문제
        // 연합의 개수 출력

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        boolean[][] world = new boolean[N][N];

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            world[Integer.parseInt(st.nextToken()) - 1][Integer.parseInt(st.nextToken()) - 1] = true;
        }

        // 연합 수 체크
        int result = 0;
        boolean[] visied = new boolean[N];
        for(int i = 0; i < N; i++) {
            if(!visied[i]) {
                result ++;
                ArrayDeque<Integer> q = new ArrayDeque<>();
                q.addLast(i);
                while (!q.isEmpty()) {
                    int now = q.removeFirst();
                    for(int j = 0; j < N; j++) {
                        if(!visied[j] && world[now][j] && world[j][now]) {
                            visied[j] = true;
                            q.addLast(j);
                        }
                    }
                }
            }
        }

        System.out.println(result);
    }
}
