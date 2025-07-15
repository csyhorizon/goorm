# Goorm Quest
## 빈 라이프사이클 메서드 활용하기
> @PostConstruct와 @PreDestroy 애너테이션을 사용하여 빈의 라이프사이클을 제어하고 실행 결과 스크린샷을 제출합니다.


## 분석
- [x] @PostConstruct와 @PreDestory 애너테이션이 무엇인가?
  - @PostConstruct : 빈이 생성되고 의존성 주입이 완료된 후 실행되는 메소드를 지정
    - 초기화 작업을 수행할 때 사용
    ~~~
    @Component
    public class MyService {
        @PostConstruct
        public void init() {
            System.out.println("빈이 생성되고 의존성 주입 후 실행됨");
        // 데이터베이스 연결
        // 초기 데이터 로드
        // 등의 초기화 작업
        }
    }
    ~~~
  - @PreDestory : 빈이 소멸되기 직전에 실행되는 메소드를 지정
    - 정리 작업을 수행할 때 사용
    ~~~
    @Component
    public class MyService {
      @PreDestroy
      public void cleanup() {
        System.out.println("빈이 소멸되기 전에 실행됨");
        // 리소스 해제
        // 연결 종료
        // 등의 정리 작업
      }
    }
    ~~~
- [x] 빈 라이프사이클 제어
- [x] 실행 테스트

## 소감
> 실행 조건을 알 거 같은 결과. 진짜 생성할 때 바로 실행되는 메소드였다.
> 하지만 빈이 소멸되기 직전에 동작했다.