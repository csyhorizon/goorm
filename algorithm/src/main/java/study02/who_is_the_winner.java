package study02;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class who_is_the_winner {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int scoreA = 0;
        int scoreB = 0;

        int N = Integer.parseInt(br.readLine());
        StringTokenizer stA = new StringTokenizer(br.readLine());
        StringTokenizer stB = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            int a = Integer.parseInt(stA.nextToken());
            int b = Integer.parseInt(stB.nextToken());

            scoreA += a > b ? (a - 7 == b ? -1 : 2) : (a == b ? 1 : (b - 7 == a ? 3 : 0));
            scoreB += b > a ? (b - 7 == a ? -1 : 2) : (b == a ? 1 : (a - 7 == b ? 3 : 0));
        }

        System.out.println(scoreA + " " + scoreB);
    }
}
