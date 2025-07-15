package study03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class shot {

    static long gunshot(long gun, int hp) {
        int damage = 0;
        for (int i = 0; i < 4; i++) {
            damage += (int) (((gun - 1 + i) % 4) + 1);
            if (damage >= hp) return i + 1;
        }
        return 4;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int player = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        long gun = 1;

        for (int i = 0; i < player; i++) {
            int hp = Integer.parseInt(st.nextToken());

            int overkill = hp / 10;
            gun += (overkill * 4);

            hp %= 10;
            if (hp > 0) {
                gun += gunshot(gun, hp);
            }
        }
        System.out.println(gun - 1);
    }
}
