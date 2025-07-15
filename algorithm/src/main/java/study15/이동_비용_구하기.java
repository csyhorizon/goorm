package study15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class 이동_비용_구하기 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static class Node implements Comparable<Node> {
        int ver;
        long distance;

        public Node(int ver, long distance) {
            this.ver = ver;
            this.distance = distance;
        }

        @Override
        public int compareTo(Node o) {
            return Long.compare(this.distance, o.distance);
        }
    }

    static class Edge {
        int to, weight;

        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(br.readLine());

        ArrayList<Edge>[] arr = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            arr[i] = new ArrayList<>();
        }

        long[] dir = new long[N + 1];
        Arrays.fill(dir, Long.MAX_VALUE);
        dir[S] = 0;

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            // 단방향
            boolean exists = false;
            for (Edge edge : arr[s]) {
                if (edge.to == e) {
                    edge.weight = Math.min(edge.weight, w);
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                arr[s].add(new Edge(e, w));
            }
        }

        PriorityQueue<Node> q = new PriorityQueue<>();
        q.offer(new Node(S, 0));

        while(!q.isEmpty()) {
            Node current = q.poll();
            int now = current.ver;
            long distance = current.distance;

            if (distance > dir[now]) continue;

            for (Edge next : arr[now]) {
                long newDist = distance + next.weight;
                if (newDist < dir[next.to]) {
                    dir[next.to] = newDist;
                    q.offer(new Node(next.to, newDist));
                }
            }
        }

        long sum = 0;
        for (int i = 1; i <= N; i++) {
            if (i != S) {
                if (dir[i] == Long.MAX_VALUE) sum -= 1;
                else sum += dir[i];
            }
        }
        System.out.println(sum);
    }
}
