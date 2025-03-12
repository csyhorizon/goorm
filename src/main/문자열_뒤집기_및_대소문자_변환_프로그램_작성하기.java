package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class 문자열_뒤집기_및_대소문자_변환_프로그램_작성하기 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input = br.readLine();

        // 문자열 뒤집기
        String reserve = "";
        for (int i = input.length() - 1; i >= 0; i--) {
            reserve = reserve + input.charAt(i);
        }

        // 대소문자 변경
        String word = "";
        for (int i = 0; i < reserve.length(); i++) {
            if (Character.isUpperCase(reserve.charAt(i))) {
                word += Character.toLowerCase(reserve.charAt(i));
            }
            else {
                word += Character.toUpperCase(reserve.charAt(i));
            }
        }

        System.out.println(word);
    }
}
