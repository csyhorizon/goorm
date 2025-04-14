package study01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class exercise_addicted_player {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        double w = Double.parseDouble(st.nextToken());
        double r = Double.parseDouble(st.nextToken());

        System.out.println((int)(w * (1 + (r / 30))));
    }
}