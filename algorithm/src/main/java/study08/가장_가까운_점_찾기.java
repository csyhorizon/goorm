package study08;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class 가장_가까운_점_찾기 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        long[] arr = new long[N];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            arr[i] = Long.parseLong(st.nextToken());
        }

        Arrays.sort(arr);

        for(int i = 0; i < Q; i++) {
            long input = Long.parseLong(br.readLine());
            int result = Arrays.binarySearch(arr, input);

            if (result >= 0) bw.write(arr[result] + "\n");
            else {
                int insertPoint = -result - 1;
                if (insertPoint >= N) bw.write(arr[insertPoint - 1] + "\n");
                else if (insertPoint == 0) bw.write(arr[insertPoint] + "\n");
                else {
                    long left = Math.abs(input - arr[insertPoint - 1]);
                    long right = Math.abs(input - arr[insertPoint]);
                    bw.write((left <= right ? arr[insertPoint - 1] : arr[insertPoint]) + "\n");
                }
            }
        }
        System.out.println();
        bw.close();
    }
}
