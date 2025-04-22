package study10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 가뭄 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        // 문제가 요란해서 MST 생각했다가 '모든 지점 연결' 이라는 조건과 '항상 연결 됨' 조건을 확인했습니다.
        // N 말고는 필요없다 판단했으며 그래프 문제가 맞아요?

        int T = Integer.parseInt(br.readLine());
        for(int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            for (int j = 0; j < M; j++) {
                st = new StringTokenizer(br.readLine());
                st.nextToken(); st.nextToken();
            }

            System.out.println(N - 1);
        }
    }
}
