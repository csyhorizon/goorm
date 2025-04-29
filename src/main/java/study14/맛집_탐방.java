package study14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 맛집_탐방 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N;
    static boolean[][] edge;
    static boolean[] first;

    public static void main(String[] args) throws IOException {
        /*
        * 문제 분석
        * 입력은 N이 주어지며, 간선은 N - 1
        * 이미 방문한 위치는 방문하지 않으며, 최대한 많은 위치를 방문
        * 입력은 10,000
        * 접근 방식 : 트리 지름 찾기? 양방향이지만 어차피 풀릴거 같다.
        */
        N = Integer.parseInt(br.readLine());

        // edge 배열 생성
        edge = new boolean[N][N];

        // true = 정점 노드가 될 수 없음
        first = new boolean[N];

        for (int i = 0; i < (N - 1); i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            edge[a][b] = true;
            first[b] = true;
        }

        // first가 false라면? 정점 노드로 판단하고 해당 위치에서 반복문 돌리기

        //
    }
}
