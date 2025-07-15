package study10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 폭탄_해제 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        // 각 정점의 차수를 저장하는 배열
        int[] degree = new int[N + 1];
        int[][] wires = new int[M][2];

        // 전선 정보 입력받기
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // 각 정점의 차수 증가
            degree[a]++;
            degree[b]++;

            wires[i][0] = a;
            wires[i][1] = b;
        }

        boolean found = false;
        // 각 전선에 대해 검사
        for (int i = 0; i < M; i++) {
            int a = wires[i][0];
            int b = wires[i][1];

            // 양쪽 정점의 차수가 1보다 큰 경우만 출력
            if (degree[a] > 1 && degree[b] > 1) {
                System.out.print((i + 1) + " ");
                found = true;
            }
        }

        if (!found) {
            System.out.println("-1");
        }
    }
}
