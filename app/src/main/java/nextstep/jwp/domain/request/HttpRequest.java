package nextstep.jwp.domain.request;

import nextstep.jwp.domain.HttpCookie;
import nextstep.jwp.domain.HttpSession;
import nextstep.jwp.domain.HttpSessions;
import nextstep.jwp.domain.Uri;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class HttpRequest {

    private String method;
    private Uri uri;
    private Map<String, String> httpHeaders;
    private HttpCookie httpCookie;
    private RequestBody requestBody;
    private HttpSession httpSession;

    private HttpRequest(String method, Uri uri, Map<String, String> httpHeaders, HttpCookie httpCookie, RequestBody requestBody, HttpSession httpSession) {
        this.method = method;
        this.uri = uri;
        this.httpHeaders = httpHeaders;
        this.httpCookie = httpCookie;
        this.requestBody = requestBody;
        this.httpSession = httpSession;
    }

    public static HttpRequest of(RequestLine requestLine, Map<String, String> httpHeaders, RequestBody requestBody) {
        HttpCookie httpCookie = extractCookies(httpHeaders);
        HttpSession httpSession = loadSession(httpCookie);
        return new HttpRequest(requestLine.getMethod(), requestLine.getUri(), httpHeaders, httpCookie, requestBody, httpSession);
    }

    private static HttpSession loadSession(HttpCookie httpCookie) {
        if (Objects.isNull(httpCookie)) {
            return null;
        }
        if (Objects.nonNull(httpCookie.getSessionId())) {
            return HttpSessions.getSession(httpCookie.getSessionId());
        }
        return null;
    }

    private static HttpCookie extractCookies(Map<String, String> httpHeaders) {
        if (Objects.nonNull(httpHeaders) && httpHeaders.containsKey("Cookie")) {
            String cookies = httpHeaders.get("Cookie");
            return HttpCookie.from(cookies);
        }
        return new HttpCookie(new HashMap<>());
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri.getUri();
    }

    public Map<String, String> getQueryMap() {
        return uri.getQueryMap();
    }

    public Map<String, String> getHttpHeaders() {
        return httpHeaders;
    }

    public String getHttpHeader(String headerValue) {
        if (Objects.isNull(httpHeaders.get(headerValue))) {
            throw new IllegalArgumentException("해당 헤더 값이 존재하지 않습니다.");
        }
        return httpHeaders.get(headerValue);
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public HttpSession getSession() {
        if (Objects.nonNull(httpSession)) {
            return httpSession;
        }
        if (Objects.nonNull(httpCookie)) {
            return getHttpSessionByJSessionId();
        }
        return new HttpSession(UUID.randomUUID().toString());
    }

    private HttpSession getHttpSessionByJSessionId() {
        String jSessionId = httpCookie.getSessionId();
        if (Objects.isNull(jSessionId)) {
            return new HttpSession(UUID.randomUUID().toString());
        }
        return HttpSessions.getSession(jSessionId);
    }

    public HttpCookie getHttpCookie() {
        if (Objects.isNull(httpCookie)) {
            return new HttpCookie(new HashMap<>());
        }
        return httpCookie;
    }

    public void assignSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }
}
