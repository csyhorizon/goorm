package study17;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class 잡초_제거 {

    static class SegmentTree {
        long tree[];
        int treeSize;

        public SegmentTree(int arrSize) {
            int h = (int) Math.ceil(Math.log(arrSize) / Math.log(2));
            this.treeSize = (int) Math.pow(2, h + 1);

            tree = new long[treeSize];
        }

        public long init(long[] arr, int node, int start, int end) {
            if (start == end) return tree[node] = arr[start];

            return tree[node] =
                    init(arr, node * 2, start, (start + end) / 2)
                    + init(arr, node * 2 + 1, (start + end) / 2 + 1, end);
        }

        public void update(int node, int start, int end, int idx, long diff) {
            if (idx < start || end < idx) return;

            tree[node] += diff;

            if (start != end) {
                update(node * 2, start, (start + end) / 2, idx, diff);
                update(node * 2 + 1, (start + end) / 2 + 1, end, idx, diff);
            }
        }

        public long sum(int node, int start, int end, int left, int right) {
            if(left > end || right < start) return 0;
            if(left <= start && end <= right) return tree[node];
            return sum(node * 2, start, (start + end) / 2, left, right) +
                    sum(node * 2 + 1, (start + end) / 2 + 1, end, left, right);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        long[] arr = new long[N + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        SegmentTree segmentTree = new SegmentTree(N);
        segmentTree.init(arr, 1, 1, N);

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int cmd = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            // 1 : 합 출력, 2 : l의 값 r 증가, 3 : l의 값 r 감소
            if (cmd == 1) {
                bw.write(segmentTree.sum(1, 1, N, l, r) + "\n");
            }
            else if (cmd == 2) {
                segmentTree.update(1, 1, N, l, r);
            }
            else {
                segmentTree.update(1, 1, N, l, r * -1);
            }
        }
        bw.close();
    }
}
