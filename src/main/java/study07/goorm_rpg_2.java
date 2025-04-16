package study07;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class goorm_rpg_2 {

    static boolean[] fantastic_slot = new boolean[100_001];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int N = Integer.parseInt(br.readLine());

        for(int i = 2; i < 50000; i++) {
            if (!fantastic_slot[i]) {
                for (int j = i + i; j < 100001; j+=i) {
                    fantastic_slot[j] = true;
                }
            }
        }

        for(int i = 0; i < N; i++) {
            int check = Integer.parseInt(br.readLine());
            if (!fantastic_slot[check]) {
                bw.write("0\n");
            }
            else {
                int count = 0;
                for(int j = check - 1; j != 0; j--) {
                    count ++;
                    if (!fantastic_slot[j]) {
                        bw.write(count + "\n");
                        break;
                    }
                }
            }
        }
        bw.close();
    }
}
