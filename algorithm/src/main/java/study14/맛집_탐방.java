package study14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class 맛집_탐방 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N;
    static List<Integer>[] edge;
    static boolean[] visited;
    static int maxDistance = 0;
    static int farthestNode = 1;

    public static void main(String[] args) throws IOException {
        /*
        * 문제 분석
        * 입력은 N이 주어지며, 간선은 N - 1
        * 이미 방문한 위치는 방문하지 않으며, 최대한 많은 위치를 방문
        * 입력은 10,000
        * 접근 방식 : DFS, 시작 좌표는 1
        * 가장 긴 지름 문제? 방식은 유지하되 한번 더 dfs 탐색해서 최장길이 판단
        * 추가 : dfs 재귀로 해결하려니 런타임 에러가 난다? (가설)
        */

        N = Integer.parseInt(br.readLine());
        edge = new ArrayList[N + 1];

        for (int i = 0; i <= N; i++) {
            edge[i] = new ArrayList<>();
        }

        for(int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            edge[a].add(b);
            edge[b].add(a);
        }

        visited = new boolean[N + 1];
        dfs(1);


        visited = new boolean[N + 1];
        maxDistance = 0;
        dfs(farthestNode);

        System.out.println(maxDistance + 1);
    }

    static void dfs(int now) {
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.add(new int[] {now, 0});

        while (!q.isEmpty()) {
            int[] qq = q.removeLast();
            int qnow = qq[0];
            int qdepth = qq[1];

            visited[qnow] = true;

            if (qdepth > maxDistance) {
                maxDistance = qdepth;
                farthestNode = qnow;
            }

            for (int i : edge[qnow]) {
                if (!visited[i]) {
                    q.add(new int[] {i, qdepth + 1});
                }
            }
        }
    }
}
