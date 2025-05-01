package study14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class 달집_태우기 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static List<int[]>[] G;
    static int[] dist;
    static int[] pa;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        dist = new int[N + 1];
        pa = new int[N + 1];

        // 인접 리스트 생성 및 초기화
        G = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            G[i] = new ArrayList<>();
        }

        // 간선 생성
        for (int i = 2; i<= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            G[i].add(new int[]{a, b});
            G[a].add(new int[]{i, b});
        }

        //두 정점 찾기
        Arrays.fill(dist, 0);
        dfs(1);

        int u = 1;
        for (int i = 2; i <= N; i++) {
            if (dist[u] < dist[i]) {
                u = i;
            }
        }

        // 첫 정점
        Arrays.fill(dist, 0);
        dfs(u);

        int v = 1;
        for (int i = 2; i <= N; i++) {
            if (dist[v] < dist[i]) {
                v = i;
            }
        }

        // 두 번째
        Arrays.fill(pa, 0);
        dfsLast(u);

        int res = Integer.MAX_VALUE;
        for (int i = v; i != 0; i = pa[i]) {
            res = Math.min(res, Math.max(dist[i], dist[v] - dist[i]));
        }

        System.out.println(res);
    }

    static void dfs(int start) {
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.addLast(new int[] {start, 0});

        while(!q.isEmpty()) {
            int[] current = q.removeLast();
            int i = current[0], p = current[1];

            for (int[] lol : G[i]) {
                int j = lol[0], k = lol[1];
                if (j == p) continue;
                dist[j] = dist[i] + k;
                q.addLast(new int[]{j, i});
            }
        }
    }


    static void dfsLast(int start) {
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.addLast(new int[] {start, 0});

        while(!q.isEmpty()) {
            int[] current = q.removeLast();
            int i = current[0], p = current[1];

            for (int[] lol : G[i]) {
                int j = lol[0], k = lol[1];
                if (j == p) continue;
                pa[j] = i;
                q.addLast(new int[]{j, i});
            }
        }
    }
}