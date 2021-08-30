package nextstep.jwp.controller;

import nextstep.jwp.model.HttpRequest;
import nextstep.jwp.model.HttpResponse;
import nextstep.jwp.model.HttpStatus;

import java.io.IOException;

public class IndexController implements Controller {

    @Override
    public HttpResponse service(HttpRequest request) throws IOException {
        HttpResponse response = new HttpResponse();
        try {
            String uri = request.getUri();
            return findHttpResponse(request, response, uri);
        } catch (Exception e) {
            return null;
        }
    }

    private HttpResponse findHttpResponse(HttpRequest request, HttpResponse response, String uri) throws IOException {
        if (uri.equals("/401.html")) {
            return response.respond(uri, HttpStatus.UNAUTHORIZED);
        }
        if (uri.equals("/404.html")) {
            return response.respond(uri, HttpStatus.NOT_FOUND);
        }
        return response.respond(request.getUri());
    }
}