package study11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class 세계_여행 {
    public static void main(String[] args) throws IOException {
        // 규칙 : 구름이는 1번 나라의 언어를 알고 있음
        // 언어를 1개 배워서 다른 나라에 방문할 수 있음
        // 방식 : BFS로 확장 + 서비스 키를 최대 2개 보유 가능 + 그 이상 확장 시도하면 return
        // 2000개의 간선을 10번..? 20000번 이니 걍 싹 다 돌려보는게 편하겠다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] language = new int[N + 1];
        int[] checked = new int[11];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            language[i] = Integer.parseInt(st.nextToken());
        }

        HashMap<Integer, LinkedList<Integer>> map = new HashMap<>();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            if (!map.containsKey(x)) {
                map.put(x, new LinkedList<>());
            }
            if (!map.containsKey(y)) {
                map.put(y, new LinkedList<>());
            }
            map.get(x).add(y);
            map.get(y).add(x);
        }

        for(int i = 1; i <= 10; i++) {
            int default_language = language[1];
            int custom_language = i;
            int size = 1;

            boolean[] visied = new boolean[N + 1];
            visied[1] = true;

            ArrayDeque<Integer> q = new ArrayDeque<>();
            q.addLast(1);

            while (!q.isEmpty()) {
                int now = q.removeFirst();

                if (map.isEmpty()) break;
                if (!map.containsKey(now)) continue;

                for(int link : map.get(now)) {
                    if (!visied[link] && (language[link] == default_language || language[link] == custom_language)){
                        visied[link] = true;
                        q.addLast(link);
                        size ++;
                    }
                }
            }

            checked[i] = size;
        }

        System.out.println(Arrays.stream(checked).max().getAsInt());
    }
}
