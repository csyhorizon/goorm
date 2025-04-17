package study08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class 섭외하기_대작전 {
    static long[] arr;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        arr = new long[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Long.parseLong(st.nextToken());
        }

        Arrays.sort(arr);
        long ans = 0;

        for (int i = 0; i < N - 2; i++) {
            for (int j = i + 1; j < N - 1; j++) {
                long sum = arr[i] + arr[j];
                int idx = Arrays.binarySearch(arr, sum);

                if (idx < 0) {
                    idx = -idx - 1;
                }
                else {
                    idx ++;
                }

                if (idx > j + 1) {
                    ans += (idx - (j + 1));
                }

            }
        }
        System.out.println(ans);
    }
}
