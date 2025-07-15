package study04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class gamejam {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static String[][] board;
    static int n;

    static class Node {
        private int x;
        private int y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static HashMap<Character, int[]> directions = new HashMap<>() {{
        put('U', new int[]{-1, 0});
        put('D', new int[]{1, 0});
        put('L', new int[]{0, -1});
        put('R', new int[]{0, 1});
    }};

    public static void main(String[] args) throws IOException {
        n = Integer.parseInt(br.readLine());

        st = new StringTokenizer(br.readLine());
        Node goorm = new Node(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1);

        st = new StringTokenizer(br.readLine());
        Node player = new Node(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1);

        board = new String[n][n];
        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < n; j++) {
                board[i][j] = st.nextToken();
            }
        }

        boolean[][] goormVisited = new boolean[n][n];
        boolean[][] playerVisited = new boolean[n][n];

        int goormScore = move(goorm, goormVisited);
        int playerScore = move(player, playerVisited);

        if (goormScore > playerScore) {
            System.out.println("goorm " + goormScore);
        } else {
            System.out.println("player " + playerScore);
        }
    }

    public static int setPos(int a) {
        if (a == -1) return n - 1;
        if (a == n) return 0;
        return a;
    }

    public static int move(Node start, boolean[][] visited) {
        int x = start.x;
        int y = start.y;
        int score = 1;
        visited[x][y] = true;

        while (true) {
            String command = board[x][y];
            int distance = Integer.parseInt(command.substring(0, command.length() - 1));
            char direction = command.charAt(command.length() - 1);

            for (int i = 0; i < distance; i++) {
                x += directions.get(direction)[0];
                y += directions.get(direction)[1];
                x = setPos(x);
                y = setPos(y);

                if (!visited[x][y]) {
                    visited[x][y] = true;
                    score++;
                } else {
                    return score;
                }
            }
        }
    }
}
