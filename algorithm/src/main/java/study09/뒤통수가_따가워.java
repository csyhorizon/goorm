package study09;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class 뒤통수가_따가워 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int N = Integer.parseInt(br.readLine());
        ArrayDeque<Long> dq = new ArrayDeque<>();
        int[] result = new int[N];

        // 없으면 아래 무시하고 0 출력
        // 이전보다 값이 작다면? 그대로 출력
        // 이전보다 값이 커지면? 이 전의 같거나 작은 값들 전부 제거
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            Long input = Long.parseLong(st.nextToken());
            if (dq.isEmpty()) bw.write("0 ");
            else {
                Long lastinput = dq.getLast();
                if (lastinput > input) bw.write(dq.size() + " ");
                else {
                    bw.write(dq.size() + " ");
                    while(!dq.isEmpty() && dq.getLast() <= input) dq.removeLast();
                }
            }
            // 마지막 입력은 어차피 다 들어가야함
            dq.addLast(input);
        }

        bw.close();
    }
}