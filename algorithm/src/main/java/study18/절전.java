package study18;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class 절전 {

    static final int MAX = 200_000;
    static List<int[]>[] G = new ArrayList[MAX];
    static boolean[] visited = new boolean[MAX];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            G[i] = new ArrayList<>();
        }

        long sum = 0;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int to = Integer.parseInt(st.nextToken()) - 1;
            int from = Integer.parseInt(st.nextToken()) - 1;
            int height = Integer.parseInt(st.nextToken());
            G[to].add(new int[] {from, height});
            G[from].add(new int[] {to, height});
            sum += height;
        }

        PriorityQueue<long[]> q = new PriorityQueue<>(Comparator.comparingLong(o -> o[0]));
        q.add(new long[] {0, 0});

        // 트리 크기, 가중치
        int ct = 0;
        long res = 0;

        while (ct < K) {
            long[] current = q.poll();

            // 가중치, 정점
            long d = current[0];
            int a = (int) current[1];

            if (visited[a]) continue;
            visited[a] = true;
            ++ct;
            res += d;

            for (int[] edge : G[a]) {
                int from = edge[0];
                int height = edge[1];
                if (!visited[from]) {
                    q.add(new long[] {height, from});
                }
            }
        }

        System.out.println(sum - res);
    }
}
