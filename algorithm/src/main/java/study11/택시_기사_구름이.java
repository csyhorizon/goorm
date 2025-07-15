package study11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class 택시_기사_구름이 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    // 이동에 사용
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    // 맵(길 = true), 택시의 현재 좌표
    static boolean[][] road;
    static int x = -1, y = -1;

    // 손님을 태웠는가?
    static boolean canIUser = false;

    // 주변탐색용 큐, 방문 체크
    static ArrayDeque<int[]> q = new ArrayDeque<>();
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());

        // 도로 크기 : N, 손님의 수 : M
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        // X = 기본 요금, Y = 추가 요금, Z = 통행료
        int X = Integer.parseInt(st.nextToken());
        int Y = Integer.parseInt(st.nextToken());
        int Z = Integer.parseInt(st.nextToken());

        // 도로 생성
        road = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                if(st.nextToken().equals("0")) road[i][j] = true;
            }
        }

        // 정산 금액
        int result = 0;

        // 운전 시작 (i = 주행 횟수, j = 출발지, 목적지)
        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < 2; j++) {
                if(x == -1) {
                    // 초기 위치 설정
                    x = Integer.parseInt(st.nextToken()) - 1;
                    y = Integer.parseInt(st.nextToken()) - 1;
                    canIUser = true;
                }
                else {
                    int targetX = Integer.parseInt(st.nextToken()) - 1;
                    int targetY = Integer.parseInt(st.nextToken()) - 1;

                    // 초기 설정 (입력 값은 x, y, 비용)
                    visited = new boolean[N][N];
                    visited[x][y] = true;
                    q.clear();
                    q.addLast(new int[] {x, y, 0});

                    int visitedCost = 0;

                    while (!q.isEmpty()) {
                        int[] now = q.removeFirst();
                        int nowX = now[0];
                        int nowY = now[1];
                        int cost = now[2];

                        if (nowX == targetX && nowY == targetY) {
                            visitedCost = cost;
                            x = targetX;
                            y = targetY;
                            break;
                        }


                        for(int a = 0; a < 4; a++) {
                            int ckX = nowX + dx[a];
                            int ckY = nowY + dy[a];

                            if (ckX >= 0 && ckY >= 0 && ckX < N && ckY < N && !visited[ckX][ckY] && road[ckY][ckX]) {

                                visited[ckX][ckY] = true;
                                q.addLast(new int[] {ckX, ckY, cost + 1});
                            }
                        }
                    }

                    if (canIUser) {
                        result += X + (visitedCost > 5 ? (visitedCost - 5) * Y : 0);
                    }
                    result -= visitedCost * Z;

                    canIUser = !canIUser;
                }
            }
        }

        System.out.println(result);
    }
}