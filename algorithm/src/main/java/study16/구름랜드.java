package study16;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class 구름랜드 {

    static long[] prefixSum;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        prefixSum = new long[N + 1];

        // 누적합임에 따라 0의 0임
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            prefixSum[i] += prefixSum[i - 1] + Integer.parseInt(st.nextToken());
        }

        int Q = Integer.parseInt(br.readLine());
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            int remove = Integer.parseInt(st.nextToken());
            int target = Integer.parseInt(st.nextToken());
            long result = prefixSum[target] - prefixSum[remove - 1];
            bw.write(result + "\n");
        }

        bw.close();
    }
}
