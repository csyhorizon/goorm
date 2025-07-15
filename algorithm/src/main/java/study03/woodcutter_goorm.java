package study03;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class woodcutter_goorm {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // N = 그루 수, M = 벌목 높이, X = 구름이 위치
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int X = Integer.parseInt(st.nextToken()) - 1;
        // H = 초기 나무 높이
        int[] arr = new int[N];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        // Q = 벌목 반복 횟수
        int Q = Integer.parseInt(br.readLine());
        // 조정값 (나무 높이 비례 계산식 적용)
        int abjustment = 0;
        // 벌목 개수
        long result = 0;
        // D = 이동 명령 (L, R, S)
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < Q; i++) {
            char cmd = st.nextToken().charAt(0);

            if (arr[X] + abjustment >= M) {
                result += arr[X] + abjustment;
                arr[X] = 0 - abjustment;
            }

            if (cmd == 'R') X = (X + 1) == N ? 0 : (X + 1);
            if (cmd == 'L') X = (X - 1) < 0 ? (N - 1) : X - 1;

            abjustment ++;
        }
        System.out.println(result);
    }
}
