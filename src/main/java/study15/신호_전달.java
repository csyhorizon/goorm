package study15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class 신호_전달 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    // 8방향
    static int[][] dd = {{1, 0, -1, 0, 1, -1, 1, -1}, {0, 1, 0, -1, 1, -1, -1, 1}};

    static int N, M;
    static char[][] arr;
    static int[][] visited;

    static int[] start;
    static int[] end;

    static class Node implements Comparable<Node> {
        int x;
        int y;
        int size;
        int pos;

        public Node(int x, int y, int size, int pos) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.pos = pos;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.size, o.size);
        }
    }


    public static void main(String[] args) throws IOException {
        // 나라 행 = N, 열 = M
        /*
          - 1 ~ 9 : 빈 땅                 (각 숫자는 저항력)
          - . : 여덟 방향 중 한 방향으로 보냄   (저항력 : 1)
          - S : 본사 > 신호를 생성하여 한 방향으로 보냄 (저항력 : 1)
          - E : 지사 > 본사나 안테나가 보낸 신호를 받을 수 있음 (저항력 : 1)
          - # : 경쟁사 > 신호가 지나갈 수 없음
          - 목표 : 본사 -> 지사로 갈수 있는 신호 중 최솟값 찾기 (도달 불가시 -1)
          */

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        visited = new int[N][M];

        arr = new char[N][M];
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                // 일단 만들어두고 생각
                if (s.charAt(j) == 'S') {
                    start = new int[] {i, j};
                }
                else if (s.charAt(j) == 'E') {
                    end = new int[] {i, j};
                }

                arr[i][j] = s.charAt(j);
            }
        }

        // 최소 간선 찾기 (처음에는 모든 방향 뿌림)
        PriorityQueue<Node> q = new PriorityQueue<>();
        for(int i = 0; i < 8; i++) {
            int x = start[0] + dd[i][0];
            int y = start[1] + dd[i][1];

            if(x >= 0 && y >= 0 && x < N && y < M) {
                // 벽은 추가 X (처음엔 어찌되었든 모든 방향으로 가야함)
                if (arr[x][y] == '#') continue;
                q.add(new Node(x, y, 1, i));
                visited[x][y] = 1;
            }
        }

        while (!q.isEmpty()) {
            Node node = q.poll();
            int x = node.x, y = node.y, size = node.size, pos = node.pos;
            char ck = arr[x][y];

            if (ck == '#' || ck == 'S') {
                // 방문 제외
                continue;
            }
            if (ck == 'E') {
                if (visited[x][y] == 0) {
                    visited[x][y] = size;
                }
                else {
                    visited[x][y] = Math.min(visited[x][y], size);
                }
            }
            else if(ck == '.') {
                // 다시 8방향으로..

            }
            else {
                // 그 방향 그대로
                int dx = x + dd[pos][0];
                int dy = y + dd[pos][1];

                if (dx >= 0 && dy >= 0 && dx < N && dy < M) {
                    char cks = arr[dx][dy];
                    if (cks != '#') {

                    }
                }
            }
        }
    }
}
