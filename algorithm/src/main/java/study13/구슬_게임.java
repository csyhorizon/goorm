package study13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;

public class 구슬_게임 {
    public static void main(String[] args) throws IOException {
        // 입력
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // n, m = 플레이어 k = 반복 횟수
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // BigInteger 배열 생성 및 초기화(100, 100, 1000이라는 최악의 경우는 값이 엄청 길게 나올 것
        BigInteger[][] dp = new BigInteger[k + 1][n + m + 1];
        for (int i = 0; i < k + 1; i++) {
            Arrays.fill(dp[i], BigInteger.ZERO);
        }

        // 시작점 셋팅 (어차피 총 배열의 크기는 두 구슬의 합이고, 시작점이 n이면 대략 중심점)
        dp[0][n] = BigInteger.ONE;

        // K는 반복횟수
        // n + m은 총 구슬의 개수

        for (int i = 0; i < k; i++) {
            for (int j = 1; j < n + m; j++) {
                // 아래 배열의 좌, 중앙, 우로 값 뿌리기
                // 시간 복잡도는 괜찮지만 값은 정말 커지니 알아보지 않음
                dp[i + 1][j - 1] = dp[i + 1][j - 1].add(dp[i][j]);
                dp[i + 1][j] = dp[i + 1][j].add(dp[i][j]);
                dp[i + 1][j + 1] = dp[i + 1][j + 1].add(dp[i][j]);
            }
        }

        // 결과 : 왼쪽 가장자리, 오른쪽 가장자리의 값들을 합 연산
        BigInteger answer = BigInteger.ZERO;
        for (int i = 1; i <= k; i++) {
            answer = answer.add(dp[i][0]).add(dp[i][n + m]);
        }

        System.out.println(answer);
    }
}