package study06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class bigger_than_you {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[] arr;
    static long target;
    static int N;
    static long min = Long.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        arr = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        target = Long.parseLong(br.readLine());
        dfs(0L);
        System.out.println(min);
    }

    public static void dfs(long value) {
        if (target < value) {
            min = Math.min(min, value);
            return;
        }
        for(int i = 0; i < N; i++) {
            if (value == 0 && arr[i] == 0) continue;
            dfs((value * 10) + arr[i]);
        }
    }
}