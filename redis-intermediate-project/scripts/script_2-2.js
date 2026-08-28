import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  scenarios:{
    stockDecrease:{
      executor: 'ramping-vus',
      startVUs: 0,
      stages: [
        { target: 100, duration: '10s' } // 가상 유저(VUs) 100명으로 설정 / 테스트를 10초 동안 진행
      ],
      gracefulRampDown : '40s' // 지연으로 인한 VUS 감소 시간 설정
    }
  }
};

export default function () {
  // 재고 차감 API
  const url = 'http://localhost:8080/stocks/1/decrease/redis';

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  // POST 요청 전송
  const res = http.post(url, null, params);

  // 1초 간격으로 요청 발송
  sleep(1);

  // 응답 상태 코드 확인 (200 OK가 왔는 지 체크)
  check(res, {
    'status is 200': (r) => r.status === 200,
  });
}