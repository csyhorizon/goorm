package study12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

public class 초코_쿠키 {

    static class Cookie {
        int pos;
        int taste;

        public Cookie(int pos, int taste) {
            this.pos = pos;
            this.taste = taste;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        // 맛있는 쿠키 곱 연산 최대화하기
        // 0이 되버리면 어찌되었든 곱이 0이므로 사전순으로 출력한다.
        // 10만개의 입력이므로 StringBuilder를 사용

        ArrayList<Cookie> arr = new ArrayList<>();

        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr.add(new Cookie(i + 1, Integer.parseInt(st.nextToken())));
        }

        arr.sort((Comparator.comparingInt(o -> o.taste)));

        for (int i = 0; i < N; i++) {
            if(arr.get(i).taste > i) {
                sb.append((arr.get(i).pos)).append(" ");
            }
            else {
                sb = new StringBuilder();
                for (int j = 1; j <= N; j++) {
                    sb.append(j).append(" ");
                }
                break;
            }
        }

        System.out.println(sb);
    }
}
