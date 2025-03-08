# Bucket4j를 활용하여 Rate Limiter 구현하기

Rate Limiting을 통해 API 요청을 제한합니다. 

### 허용된 요청 테스트

_HTTP Request_

```shell
curl -v http://localhost:8080/api/rate-limit
```

**예상 응답**

```shell
HTTP/1.1 200 OK
X-Rate-Limit-Remaining: 9 # 남은 요청 횟수
content-length: 0

API 호출 성공!

```

_logging_

```text
RateLimitInterceptor - Success to consume 1 token! Remaining tokens: 9
```

### 거부된 요청 테스트 

허용치를 초과하는 요청을 보내 `429 Too Many Requests` 응답을 받습니다.

_HTTP Request_

```shell
for i in {1..11}; do
    curl -v http://localhost:8080/api/rate-limit
done
```

**예상 응답**

```shell
HTTP/1.1 429 Too Many Requests
X-Rate-Limit-Retry-After-Seconds: 10

# ...

Rate limit exceeded. Try again in 10 seconds.
```