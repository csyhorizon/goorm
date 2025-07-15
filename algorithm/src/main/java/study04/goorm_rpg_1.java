package study04;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class goorm_rpg_1 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int N = Integer.parseInt(br.readLine());

        boolean[] god = new boolean[100_001];
        for(int i = 2; i < 50000; i++) {
            if (!god[i]) {
                for(int j = i * 2; j < 100001; j += i) {
                    god[j] = true;
                }
            }
        }

        for (int i = 0; i < N; i++) {
            if (god[Integer.parseInt(br.readLine())]) {
                bw.write("No\n");
            }
            else {
                bw.write("Yes\n");
            }
        }

        bw.close();
    }
}
