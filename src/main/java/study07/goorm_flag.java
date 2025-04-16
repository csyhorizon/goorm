package study07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class goorm_flag {

    static int[][] arr;
    static int N, K;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        arr = new int[N][N];


        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
                if (arr[i][j] == 1) {
                    arr[i][j] = -1;
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (arr[i][j] == -1) {
                    plus(i, j);
                }
            }
        }

        System.out.println(Arrays.stream(arr).flatMapToInt(Arrays::stream).filter(n -> n == K).count());
    }

    public static void plus(int i, int j) {
        for (int a = i - 1; a <= i + 1; a++) {
            for (int b = j - 1; b <= j + 1; b++) {
                if (a < 0 || a >= N) continue;
                if (b < 0 || b >= N) continue;
                if (arr[a][b] == - 1) continue;
                arr[a][b] ++;
            }
        }
    }
}
