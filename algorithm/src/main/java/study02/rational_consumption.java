package study02;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

public class rational_consumption {
    public static class Item {
        String name;
        int price;

        public Item(String name, int price) {
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return name + " " + price;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        ArrayList<Item> arr = new ArrayList<>();

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            arr.add(new Item(st.nextToken(), Integer.parseInt(st.nextToken())));
        }

        arr.sort(Comparator.comparingInt(a -> a.price));

        System.out.println(arr.get(N - 1).toString());
        System.out.println(arr.get(0).toString());
    }
}
