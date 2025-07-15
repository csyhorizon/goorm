package study01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class leisure_golden_ratio {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        int succ = 0;

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            double a = Double.parseDouble(st.nextToken());
            double b = Double.parseDouble(st.nextToken());
            double ck = Math.min(a, b);
            double ckTarget = Math.max(a, b);
            double ckA = ck * 1.6;
            double ckB = ck * 1.63;

            if (ckA <= ckTarget && ckB >= ckTarget) succ ++;
        }
        System.out.println(succ);
    }
}
