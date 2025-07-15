package study18;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class 친구_사이 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static class Edge implements Comparable<Edge> {
        int from;
        int to;
        int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public int compareTo(Edge o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    public static void main(String[] args) throws IOException {
        // 연결되어 있다면 친구
        int N = Integer.parseInt(br.readLine());
        int M = Integer.parseInt(br.readLine());
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken()) - 1;
            int to = Integer.parseInt(st.nextToken()) - 1;
            edges.add(new Edge(from, to, 0));
        }
        int K = Integer.parseInt(br.readLine());
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken()) - 1;
            int to = Integer.parseInt(st.nextToken()) - 1;
            int weight = Integer.parseInt(st.nextToken());
            edges.add(new Edge(from, to, weight));
        }
        Collections.sort(edges);
        UnionFind uf = new UnionFind(N);
        long mstWeight = 0;
        for (Edge e : edges) {
            if (uf.union(e.from, e.to)) {
                mstWeight += e.weight;
            }
        }
        for (int i = 0; i < N; i++) {
            if (uf.find(i) != uf.find(0)) {
                System.out.println(-1);
                return;
            }
        }
        System.out.println(mstWeight);
    }

    static class UnionFind {
        int[] parent;

        public UnionFind(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public boolean union(int a, int b) {
            int roota = find(a);
            int rootb = find(b);
            if (roota == rootb) {
                return false;
            }
            if (roota < rootb) {
                parent[rootb] = roota;
            } else {
                parent[roota] = rootb;
            }
            return true;
        }
    }
}