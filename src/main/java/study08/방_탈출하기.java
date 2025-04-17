package study08;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class 방_탈출하기 {

    static int[] arr;
    static int N;

    public static void main(String[] args) throws IOException {
//        이거 도저히 이분 탐색 문제가 아니긴한데.. 메모리 제한이라도 빡빡하게 해뒀으려나요?
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        arr = new int[N];

        for(int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);
        int M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {
            bw.write(binarySearch(Integer.parseInt(br.readLine())) + "\n");
        }
        bw.close();
    }

    public static int binarySearch(int target) {
        int start = 0;
        int end = N - 1;
        while(start <= end) {
            int mid = (start + end) / 2;
            if(arr[mid] < target) start = mid + 1;
            else if (arr[mid] > target) end = mid - 1;
            else return 1;
        }
        return 0;
    }
}
