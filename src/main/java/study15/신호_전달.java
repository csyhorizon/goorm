package study15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class 신호_전달 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, M;
    static final int INF = Integer.MAX_VALUE;

    static int[][] map;
    static int[][][] dist;
    static int[] start = new int[2];
    static int[] end = new int[2];

    // 8방향 (4방향 문제가 좋은데..)
    static int[][] dir = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        dist = new int[N][M][8];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Arrays.fill(dist[i][j], INF);
            }
        }

        // 시작, 끝, 안테나 = 0 (어차피 8방향), 방해하는 회사 = -1, 이외 = c - 0'
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                char c = s.charAt(j);
                if (c == 'S') {
                    start[0] = i;
                    start[1] = j;
                    map[i][j] = 0;
                } else if (c == 'E') {
                    end[0] = i;
                    end[1] = j;
                    map[i][j] = 0;
                } else if (c == '.') {
                    map[i][j] = 0;
                } else if (c == '#') {
                    map[i][j] = -1;
                } else {
                    map[i][j] = c - '0';
                }
            }
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();

        for (int k = 0; k < 8; k++) {
            dist[start[0]][start[1]][k] = 0;
            pq.offer(new Node(start[0], start[1], k, 0));
        }

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            if (dist[cur.x][cur.y][cur.dir] < cur.cost) continue;

            if (map[cur.x][cur.y] > 0) {
                int nx = cur.x + dir[cur.dir][0];
                int ny = cur.y + dir[cur.dir][1];

                if (isValid(nx, ny) && map[nx][ny] != -1) {
                    int nCost = cur.cost + map[cur.x][cur.y];
                    if (dist[nx][ny][cur.dir] > nCost) {
                        dist[nx][ny][cur.dir] = nCost;
                        pq.offer(new Node(nx, ny, cur.dir, nCost));
                    }
                }
            }


            else if (map[cur.x][cur.y] == 0) {
                for (int k = 0; k < 8; k++) {
                    int nx = cur.x + dir[k][0];
                    int ny = cur.y + dir[k][1];

                    if (isValid(nx, ny) && map[nx][ny] != -1) {
                        int nCost = cur.cost + 1;
                        if (dist[nx][ny][k] > nCost) {
                            dist[nx][ny][k] = nCost;
                            pq.offer(new Node(nx, ny, k, nCost));
                        }
                    }
                }
            }
        }

        int result = INF;
        for (int k = 0; k < 8; k++) {
            result = Math.min(result, dist[end[0]][end[1]][k]);
        }

        System.out.println(result == INF ? -1 : result);
    }

    static boolean isValid(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < M;
    }

    static class Node implements Comparable<Node> {
        int x, y, dir, cost;

        Node(int x, int y, int dir, int cost) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.cost, o.cost);
        }
    }
}