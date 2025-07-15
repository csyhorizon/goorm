package study13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 주사위_여행 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int slice = 1_000_000_007;
        long[][] dp = new long[N + 1][M + 1];
        boolean[][] blocked = new boolean[N + 1][M + 1];

        // 휴양지 표시
        for(int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            blocked[x][y] = true;
        }

        // 시작점 초기화
        dp[1][1] = 1;

        // 모든 위치에 대해
        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= M; j++) {
                if(blocked[i][j]) continue; // 휴양지는 건너뛰기

                // 현재 위치에서 주사위를 굴려서 갈 수 있는 모든 경우 고려
                for(int dice = 1; dice <= 6; dice++) {
                    // 오른쪽으로 이동
                    if(j + dice <= M && !blocked[i][j + dice]) {
                        dp[i][j + dice] = (dp[i][j + dice] + dp[i][j]) % slice;
                    }

                    // 아래로 이동
                    if(i + dice <= N && !blocked[i + dice][j]) {
                        dp[i + dice][j] = (dp[i + dice][j] + dp[i][j]) % slice;
                    }
                }
            }
        }

        System.out.println(dp[N][M]);
    }
}