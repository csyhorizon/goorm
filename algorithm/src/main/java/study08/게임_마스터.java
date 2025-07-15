package study08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 게임_마스터 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 게임 수
        long N = Long.parseLong(st.nextToken());
        // 승리한 횟수
        long M = Long.parseLong(st.nextToken());

        // 현재 승률
        int value = (int) (M * 100 / N);
        // 목표 승률
        int rate = value + 1;

        long start = 1L;
        long end = 999_999_999_999L;
        long max = 1_000_000_000_000L;

        while(start <= end) {
            long mid = (start + end) / 2;
            long nowN = N + mid;
            long nowM = M + mid;

            int nowrate = (int) (nowM * 100 / nowN);
            if (rate <= nowrate) {
                max = mid;
                end = mid - 1;
            }
            else {
                start = mid + 1;
            }
        }

        System.out.println(max == 1_000_000_000_000L ? "X" : max);
    }
}
