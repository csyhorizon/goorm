package study13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class 학점_예측하기 {

    static int MOD = 1_000_000;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {

        // 학점은 A, B, C 중 하나
        // B 학점을 받은 과목은 최대 하나 / B는 0또는 1이어야함
        // 세 과목 이상 연속으로 C를 받지 않음
        // 수강 과목의 수는 N개
        // 값이 너무 커질 수 있으니 1_000_000으로 나눈 나머지 출력

        int N = Integer.parseInt(br.readLine());

        // 3차원 배열로 해결 [1. 과목 수, 2. 학점{a, b, c}, 3. 연속된 수{0, 1, 2}, 4. 사용 여부]
        long[][][][] dp = new long[2][3][3][2];

        // 처음의 디폴트는 다 1로 설정
        dp[1][0][0][0] = 1;
        dp[1][1][0][1] = 1;
        dp[1][2][1][0] = 1;

        int prev, curr;
        for (int i = 2; i <= N; i++) {
            curr = i % 2;
            prev = (i - 1) % 2;

            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 2; l++) {
                        dp[curr][j][k][l] = 0;
                    }
                }
            }

            // A로 끝나는 경우 (B를 사용하지 않은 상태)
            dp[curr][0][0][0] = (dp[prev][0][0][0] + dp[prev][0][1][0] + dp[prev][0][2][0] +
                    dp[prev][2][0][0] + dp[prev][2][1][0] + dp[prev][2][2][0]) % MOD;

            // A로 끝나는 경우 (B를 사용한 상태)
            dp[curr][0][0][1] = (dp[prev][0][0][1] + dp[prev][0][1][1] + dp[prev][0][2][1] +
                    dp[prev][1][0][1] + dp[prev][1][1][1] + dp[prev][1][2][1] +
                    dp[prev][2][0][1] + dp[prev][2][1][1] + dp[prev][2][2][1]) % MOD;

            // B로 끝나는 경우 (B를 사용하지 않은 상태에서만)
            dp[curr][1][0][1] = (dp[prev][0][0][0] + dp[prev][0][1][0] + dp[prev][0][2][0] +
                    dp[prev][2][0][0] + dp[prev][2][1][0] + dp[prev][2][2][0]) % MOD;

            // C로 끝나는 경우 (B를 사용하지 않은 상태)
            dp[curr][2][1][0] = (dp[prev][0][0][0] + dp[prev][2][0][0]) % MOD;
            dp[curr][2][2][0] = dp[prev][2][1][0] % MOD;

            // C로 끝나는 경우 (B를 사용한 상태)
            dp[curr][2][1][1] = (dp[prev][0][0][1] + dp[prev][1][0][1] + dp[prev][2][0][1]) % MOD;
            dp[curr][2][2][1] = dp[prev][2][1][1] % MOD;
        }

        // 문제없음 (시간 초과 날만하지 않음)
        long result = 0;
        curr = N % 2;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 2; k++) {
                    result = (result + dp[curr][i][j][k]) % MOD;
                }
            }
        }

        System.out.println(result);
    }
}
