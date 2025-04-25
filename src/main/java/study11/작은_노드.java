package study11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;

public class 작은_노드 {

    public static void main(String[] args) throws IOException {
        // 노드의 개수 - N, 간선의 개수 - M, 시작 노드 - K
        // 시작 위치는 방문 처리 (count = 1)
        // 이동 규칙은 가능한 조건 중 가장 작은 노드
        // 더 이상 이동하지 못하면 종료
        // 잘 풀었는데.. 양방향 체크 안해서.......
        // 다행히 코드 자체는 수정할 게 없어서 바로 맞았습니다...........

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        Map<Integer, LinkedList<Integer>> map = new HashMap<>();

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int posX = Integer.parseInt(st.nextToken());
            int posY = Integer.parseInt(st.nextToken());

            if (!map.containsKey(posX)) {
                map.put(posX, new LinkedList<>());
            }
            if (!map.containsKey(posY)) {
                map.put(posY, new LinkedList<>());
            }
            map.get(posX).add(posY);
            map.get(posY).add(posX);
        }

        boolean[] visited = new boolean[N + 1];
        visited[K] = true;
        int last = K;
        int count = 1;

        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.addLast(K);

        while(!q.isEmpty()) {
            int now = q.removeFirst();
            int min = Integer.MAX_VALUE;

            if (!map.containsKey(now)) {
                last = now;
                break;
            }

            for(int i : map.get(now)) {
                if (!visited[i]) {
                    min = Math.min(min, i);
                }
            }
            if (min == Integer.MAX_VALUE) {
                last = now;
                break;
            }
            else {
                visited[min] = true;
                q.addLast(min);
                count++;
            }
        }

        System.out.println(count + " " + last);
    }
}
