package nextstep.jwp.domain.response;

public enum HttpStatus {

    OK(200, "OK"),
    FOUND(302, "Found"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    NOT_FOUND(404, "Not Found");

    private final int code;
    private final String responsePhrase;

    HttpStatus(int code, String responsePhrase) {
        this.code = code;
        this.responsePhrase = responsePhrase;
    }

    public int value() {
        return code;
    }

    public String responsePhrase() {
        return responsePhrase;
    }
}
