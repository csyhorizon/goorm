package study01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class fence {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        // 가로
        int x = N * 2;
        st = new StringTokenizer(br.readLine());
        int y = Integer.parseInt(st.nextToken());
        // 세로
        int last = y;
        for (int i = 1; i < N; i++) {
            int ck = Integer.parseInt(st.nextToken());
            y += (last == ck ? 0 : Math.abs(last - ck));
            last = ck;
        }
        System.out.println(x + y + last);
    }
}
