#!/bin/bash
K6_PROMETHEUS_RW_SERVER_URL=http://127.0.0.1:57897/api/v1/write \
k6 run -o experimental-prometheus-rw script.js