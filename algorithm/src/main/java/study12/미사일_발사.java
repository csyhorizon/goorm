package study12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class 미사일_발사 {

    public static void main(String[] args) throws IOException {
        // 우주 정거장 좌표 0, 0
        // N개의 외계인이 옴
        // 미사일 1 거리 날아갈 때 2만큼 시간이 걸림
        // 1번에 한해서 시간의 2의 증가값은 1로 조정가능

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());

        long[] start = new long[N];
        long[] end = new long[N];

        long sum = 0;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            // Math.pow 썻다가 부동 소숫점 오류 발생해서 절반 테스트 가량 실패했습니다.
            // 이론은 스스로 맞다고 주장하고 있지만, 소수점 타입에 대해 유의해야한다고 스스로 복기합니다.. (디버킹 오래걸림)
            long ucrd = ((long) Math.abs(x) * Math.abs(x) + (long) Math.abs(y) * Math.abs(y));
            ucrd *= 2;

            long time = Long.parseLong(st.nextToken());
            long moveend = time + ucrd;

            sum += ucrd;

            // 줄일 수 없는 조건분기는 삭제
            if (time == moveend) {
                start[i] = -1;
                end[i] = -1;
                continue;
            }

            start[i] = time;
            end[i] = moveend;
        }

        // 정렬
        Arrays.sort(start);
        Arrays.sort(end);

        // testTime이 end에 도달할때까지
        int startTime = 0;
        int endTime = 0;

        int maxSize = 0;
        int nowSize = 0;

        while(endTime < end.length) {
            if (startTime == start.length) {
                maxSize = Math.max(maxSize, nowSize);
                break;
            }
            if (start[startTime] == -1) {
                startTime ++;
            }
            else if (end[endTime] == -1) {
                endTime ++;
            }

            else if (start[startTime] < end[endTime]) {
                startTime ++;
                nowSize ++;
            }
            else {
                endTime ++;
                maxSize = Math.max(maxSize, nowSize);
                nowSize --;
            }
        }

        System.out.println(sum - maxSize);
    }
}
