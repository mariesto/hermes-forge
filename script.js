import http from 'k6/http';
import { check, sleep } from 'k6';
import { htmlReport } from "https://raw.githubusercontent.com/benc-uk/k6-reporter/2.4.0/dist/bundle.js";

export const options = {
  stages: [
    { duration: '30s', target: 50 },    // Initial ramp-up
    { duration: '1m', target: 50 },     // Steady state
    { duration: '30s', target: 150 },   // Increased load
    { duration: '2m', target: 300 },    // Peak load (longer to ensure HPA responds)
    { duration: '1m', target: 200 },    // Gradual scale down
    { duration: '30s', target: 50 },    // Further scale down
    { duration: '30s', target: 10 },    // Cool down
  ]
};

export function generateUUID() {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
    var r = Math.random() * 16 | 0,
        v = c == 'x' ? r : (r & 0x3 | 0x8);
    return v.toString(16);
  });
}

export function handleSummary(data) {
  return {
    "summary.html": htmlReport(data),
  };
}

export default function () {
  const url = 'http://127.0.0.1:57844/api/v1/orders'; 
  const payload = JSON.stringify({
    customerId: generateUUID(),
  });

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const response = http.post(url, payload, params);
  check(response, {
    'is status 201': (r) => r.status === 201,
  });

  sleep(0.1)
}