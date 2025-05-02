package study15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class 단체_이동 {

    // PriorityQueue
    static class Node implements Comparable<Node> {
        int next;
        long size;

        public Node(int next, long size) {
            this.next = next;
            this.size = size;
        }

        @Override
        public int compareTo(Node o) {
            return Long.compare(this.size, o.size);
        }
    }

    // 인접 리스트
    static class Edge {
        int to, size;

        public Edge(int to, int size) {
            this.to = to;
            this.size = size;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static Map<Integer, Map<Integer, Edge>> arr = new HashMap<>();
    static long[] checked;

    public static void main(String[] args) throws IOException {
        // 양방향 이동
        // N = 방 개수, M = 통로
        // 방에는 C명의 사람이 있고, E번으로 이동하려고 함
        // 방을 이동하려면 지정된 크기만 이동할 수 있고 크면 나눠서 이동
        // 이동할 수 없다면 -1 반환

        // S = 시작 방 번호, E = 이동하려는 방 번호, C = 인원 수
        // a - b 이동 통로, k = 이동가능한 최대 인원 수

        // 방 - 20만개..
        // 통로 - 30만개..
        // 지나는 사람의 수 = 1000..
        // 단순 작업은 반복문 불가 - 인접 리스트, 우선순위 큐, 수식?
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        checked = new long[N + 1];
        Arrays.fill(checked, Long.MAX_VALUE);

        st = new StringTokenizer(br.readLine());
        // S = 시작 방 번호, E = 목표 방 번호, C = 이동하는 사람 수
        int S = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            int resize = (C / k) + (C % k != 0 ? 1 : 0);

            arr.computeIfAbsent(a, x -> new HashMap<>());
            arr.computeIfAbsent(b, x -> new HashMap<>());

            arr.get(a).compute(b, (key, edge) ->
                edge == null ? new Edge(b, resize) : new Edge(b, Math.min(edge.size, resize))
            );
            arr.get(b).compute(a, (key, edge) ->
                    edge == null ? new Edge(a, resize) : new Edge(a, Math.min(edge.size, resize))
            );
        }

        PriorityQueue<Node> q = new PriorityQueue<>();
        q.add(new Node(S, 0));
        checked[S] = 0;

        while (!q.isEmpty()) {
            Node node = q.poll();
            // Node = now = 현재 좌표, size = 현재 크기 (편리하게 계산 목적)
            int now = node.next;
            long size = node.size;

            if (checked[now] < size) continue;

            Map<Integer, Edge> edges = arr.get(now);
            if (edges == null) continue;

            for (Map.Entry<Integer, Edge> entry : arr.get(now).entrySet()) {
                int next = entry.getKey();
                Edge edge = entry.getValue();

                // 다음 좌표 크기
                long cost = size + edge.size;

                if(checked[next] == Long.MIN_VALUE) {
                    checked[next] = cost;
                    q.add(new Node(next, cost));
                }
                else {
                    if (checked[next] > cost) {
                        checked[next] = cost;
                        q.add(new Node(next, cost));
                    }
                }
            }
        }

        if (checked[E] == Long.MAX_VALUE) {
            System.out.println("-1");
        }
        else {
            System.out.println(checked[E]);
        }
    }
}
