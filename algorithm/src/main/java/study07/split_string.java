package study07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class split_string {
    static String word;
    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        word = br.readLine();

        List<String[]> wordList = new ArrayList<>();
        Set<String> score = new HashSet<>();

        for(int i = 1; i < N; i++) {
            for(int j = i + 1; j < N; j++) {
                String first = word.substring(0, i);
                String second = word.substring(i, j);
                String third = word.substring(j);
                wordList.add(new String[]{first, second, third});
                score.add(first);
                score.add(second);
                score.add(third);
            }
        }

        List<String> tempScoreList = new ArrayList<>(score);
        Collections.sort(tempScoreList);

        Map<String, Integer> wordScore = new HashMap<>();
        for (int i = 0; i < tempScoreList.size(); i++) {
            System.out.println(tempScoreList.get(i) + " = " + (i + 1));
            wordScore.put(tempScoreList.get(i), i + 1);
        }

        int maxScore = -1;
        for (String[] words : wordList) {
            int tempScore = 0;
            for (String word : words) {
                System.out.println(word);
                tempScore += wordScore.get(word);
            }
            maxScore = Math.max(maxScore, tempScore);
        }
        System.out.println(maxScore);
    }
}