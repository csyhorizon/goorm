package study09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class 묶음_상품 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        boolean[] checked = new boolean[N + 1];
        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            arr.add(i, new ArrayList<>());
        }

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            arr.get(a).add(b);
            arr.get(b).add(a);
        }

        int count = 0;
        for(int i = 1; i <= N; i++) {
            if(!checked[i]) {
                checked[i] = true;
                count ++;
                ArrayDeque<Integer> q = new ArrayDeque<>();
                q.addLast(i);

                while(!q.isEmpty()) {
                    int v = q.removeFirst();
                    for(int s : arr.get(v)) {
                        if (!checked[s]) {
                            q.addLast(s);
                            checked[s] = true;
                        }
                    }
                }
            }
        }
        System.out.println(count);
    }
}
