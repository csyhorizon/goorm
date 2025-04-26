package study12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class 보조_배터리 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        // 보조배터리 타입 (X = A_TYPE, Y = B_TYPE, XY = C_TYPE
        st = new StringTokenizer(br.readLine());
        int[] TYPE = new int[3];
        TYPE[0] = Integer.parseInt(st.nextToken());
        TYPE[1] = Integer.parseInt(st.nextToken());
        TYPE[2] = Integer.parseInt(st.nextToken());

        /*
          조건부 : 동시에 충전할 수 있는 보조 배터리의 개수
          - 비용이 낮은 순으로 정렬
          - A, B 타입 우선 처리
          - 없다면 C 타입 처리
         */

        int N = Integer.parseInt(br.readLine());
        int[][] cost = new int[N][2];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            cost[i][0] = Integer.parseInt(st.nextToken());
            cost[i][1] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(cost, (Comparator.comparingInt(o -> o[0])));

        int count = 0;
        long Allpay = 0;

        for(int i = 0; i < N; i++) {
            int pay = cost[i][0];
            int target = cost[i][1];

            if (TYPE[target] > 0) {
                count ++;
                Allpay += pay;
                TYPE[target] --;
            }
            else if (TYPE[2] > 0) {
                count ++;
                Allpay += pay;
                TYPE[2] --;
            }
        }

        System.out.println(count + " " + Allpay);
    }
}
