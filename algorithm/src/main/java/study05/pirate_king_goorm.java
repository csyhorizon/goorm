package study05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class pirate_king_goorm {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static class Node implements Comparable<Node>{
        int x;
        int y;
        int position;

        public Node(int x, int y, int position) {
            this.x = x;
            this.y = y;
            this.position = position;
        }

        @Override
        public int compareTo(Node o) {
            if (this.x != o.x) {
                return this.x - o.x;
            }
            return this.y - o.y;
        }

        @Override
        public String toString() {
            return x + ":" + y + "=" + position;
        }
    }

    public static void main(String[] args) throws IOException {
        int N = Integer.parseInt(br.readLine());
        ArrayList<Node> arr = new ArrayList<>();
        int[] result = new int[N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            arr.add(new Node(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), i));
        }

        Collections.sort(arr);

        for (int i = 0; i < N; i++) {
            result[arr.get(i).position] = N - i - 1;
        }

        for (int i : result) {
            System.out.println(i);
        }
    }
}
