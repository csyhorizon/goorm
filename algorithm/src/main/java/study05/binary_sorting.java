package study05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class binary_sorting {
    static class Data implements Comparable<Data> {
        private final int count;
        private final int value;

        public Data(int count, int value) {
            this.count = count;
            this.value = value;
        }

        @Override
        public int compareTo(Data o) {
            if (this.count != o.count) {
                return o.count - this.count;
            }

            return o.value - this.value;
        }

        @Override
        public String toString() {
            return value + "";
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        List<Data> data = new ArrayList<>();

        for (int i = 0; i < N; i ++) {
            int value = Integer.parseInt(st.nextToken());
            int count = Integer.bitCount(value);

            data.add(new Data(count, value));
        }

        Collections.sort(data);
        System.out.println(data.get(K - 1));
    }
}
