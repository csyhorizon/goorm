package study16;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class 도장_만들기 {

    static ArrayList<Map<Integer, Integer>> arr = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // gooodprogramming
        // 이걸 구간합으로 어떻게..?
        // char[][] 이차원 배열에 저장..? 제거가 안되잖아.. 아니 되나?

        // 구간 합 만들기 (ok)
        // ArrayList<Integer> arr

        // 각 구간합 찾기 (ok)
        // 알파벳 수 = 26, 반복 횟수 = 300

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        String s = st.nextToken();

        for (int i = 0; i <= 26; i++) {
            arr.add(i, new HashMap<>());
        }

        for (int i = 1; i <= N; i++) {
            arr.add(i, new HashMap<>(arr.get(i - 1)));
            arr.get(i).put(s.charAt(i - 1) - 'a', arr.get(i).getOrDefault(s.charAt(i - 1) - 'a', 0) + 1);
        }

        int M = Integer.parseInt(br.readLine());

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken()) - 1;
            int end = Integer.parseInt(st.nextToken());

            int value = Integer.parseInt(st.nextToken());
            int[] caseValue = new int[26];
            String targets = st.nextToken();
            // 배열 생성
            for (int j = 0; j < value; j++) {
                caseValue[targets.charAt(j) - 'a'] ++;
            }

            // 계산 시작
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < 26; j++) {
                if (caseValue[j] == 0) continue;

//                System.out.println(j);
//                System.out.println(arr.get(start).getOrDefault(j, 0));
//                System.out.println(arr.get(end).getOrDefault(j, 0));
//                System.out.println("===");

                int noteCount = arr.get(end).getOrDefault(j, 0) - arr.get(start).getOrDefault(j, 0);
                int betCount = caseValue[j];

                min = Math.min(min, noteCount / betCount);
            }
            bw.write(min + "\n");
        }
        bw.close();
    }
}
