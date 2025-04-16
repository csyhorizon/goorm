package study07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class panda_is_cute {
    static int[] arrR;
    static int[] arrC;
    static int N, M, K;
    static int result = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        arrR = new int[K];
        arrC = new int[K];

        for(int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            arrR[i] = Integer.parseInt(st.nextToken());
            arrC[i] = Integer.parseInt(st.nextToken());
        }

        for(int r = 1; r <= N; r++) {
            for(int c = 1; c <= M; c++) {
                boolean isPanda = true;
                for(int k = 0; k < K; k++) {
                    if (r == arrR[k] && c == arrC[k]) {
                        isPanda = false;
                        break;
                    }
                }

                if (!isPanda) continue;

                result = Math.min(result, check(r, c));
            }
        }

        System.out.println(result);
    }

    static int check(int r, int c) {
        int res = 0;
        for (int i = 0; i < K; i++) {
            res += (int) (Math.pow((r - arrR[i]), 2) + Math.pow((c - arrC[i]), 2));
        }
        return res;
    }
}
