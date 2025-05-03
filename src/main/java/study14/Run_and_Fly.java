package study14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Run_and_Fly {
    static int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N, M, T;
    static int[][] arr;

    public static void main(String[] args) throws IOException {
        // 아 잠만 이거 너무 어렵다..
        // 해설 보고 하는 겁니다.. BFS에 이진탐색은 생각도 안해봐서...

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        arr = new int[N][M];

        // 배열 생성
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 여기까진 쉬운데.. 이 다음이 참...
        int st = 0, en = N * M - 1, ans = -1;
        while(st <= en) {
            int mid = (st + en) / 2;
            if (bfs(mid)) {
                ans = mid;
                en = mid - 1;
            } else {
                st = mid + 1;
            }
        }

        System.out.println(ans);
    }

    static int[][][] dist = new int[50][50][50 * 50];
    static ArrayDeque<int[]> q = new ArrayDeque<>();
    static int INF = 1_000_000_000;

    public static boolean bfs(int K) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Arrays.fill(dist[i][j], INF);
            }
        }

        q.clear();
        q.add(new int[]{0, 0, K});
        dist[0][0][K] = 0;

        while (!q.isEmpty()) {
            int[] current = q.removeFirst();
            int i = current[0], j = current[1], k = current[2];
            
            if (k < K && arr[i][j] == 2 && dist[i][j][k + 1] > dist[i][j][k] + 1) {
                dist[i][j][k + 1] = dist[i][j][k] + 1;
                q.addLast(new int[] {i, j, k + 1});
            }

            for (int[] d : dir) {
                int ni = i + d[0], nj = j + d[1];

                if (ni < 0 || ni >= N || nj < 0 || nj >= M || arr[ni][nj] == 0) continue;

                if (arr[i][j] == 2 && arr[ni][nj] == 2) {
                    if (dist[ni][nj][k] > dist[i][j][k] + 1) {
                        dist[ni][nj][k] = dist[i][j][k] + 1;
                        q.addLast(new int[] {ni, nj, k});
                    }
                }

                else {
                    if (k > 0 && dist[ni][nj][k - 1] > dist[i][j][k] + 1) {
                        dist[ni][nj][k - 1] = dist[i][j][k] + 1;
                        q.addLast(new int[] {ni, nj, k - 1});
                    }
                }
            }
        }

        for (int k = 0; k <= K; k++) {
            if (dist[N - 1][M - 1][k] <= T) {
                return true;
            }
        }
        return false;
    }
}