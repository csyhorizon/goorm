package study13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class 거리두기 {
    static int MOD = 100_000_007;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    // 가능한 조합
    static int[] state = {0b000, 0b001, 0b010, 0b100, 0b101};

    static boolean valid(int s1, int s2) {
        return (s1 & s2) == 0;
    }

    public static void main(String[] args) throws IOException {
        // 0과 1이 정말 싫은데 이거 비트마스킹........
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        // 배열 초기화 (조합 가능성에 대한 아랫값임)
        int[][] dp = new int[N][state.length];

        for (int i = 0; i < state.length; i++) {
            dp[0][i] = 1;
        }

        for (int line = 1; line < N; line++) {
            for (int i = 0; i < state.length; i++) {
                for (int j = 0; j < state.length; j++) {
                    // 각 범위 체크 (i는 과거위치, j는 현재 위치)
                    // 즉 현재 위치의 비트마스킹에 해당되면 그에 대한 좌표의 값을 가져옴
                    if (valid(state[i], state[j])) {
                        dp[line][j] = (dp[line][j] + dp[line - 1][i]) % MOD;
                    }
                }
            }
        }

        int result = 0;
        for (int i = 0; i < dp[0].length; i++) {
            result = (result + dp[N - 1][i]) % MOD;
        }

        System.out.println(result);
    }
}
