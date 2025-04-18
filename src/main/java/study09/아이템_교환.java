package study09;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class 아이템_교환 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // A = 구름이, B = 친구
        Set<String> A = new TreeSet<>();
        Set<String> B = new HashSet<>();

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        // 빠르게 그냥 만들어버리기
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            A.add(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            B.add(st.nextToken());
        }

        // 빠르게 교환
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            String a = st.nextToken();
            String b = st.nextToken();
            if (A.contains(a) && B.contains(b)) {
                A.remove(a);
                B.add(a);

                A.add(b);
                B.remove(b);
            }
        }

        for (String s : A) {
            bw.write(s + " ");
        }

        bw.close();
    }
}
