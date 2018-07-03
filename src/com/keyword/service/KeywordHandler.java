package com.keyword.service;

import com.mongodb.BasicDBObject;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Application server for responding to HTTP requests
 */
public class KeywordHandler extends AbstractHandler {

    private static Logger logger = LoggerFactory.getLogger(KeywordHandler.class);

    private static final String ERROR = "error";

    private static final String KEYWORD = "keyword";
    private static final String SCORE = "score";

    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse) {

        if (!"GET".equals(request.getMethod())) {
            respond(request, httpServletResponse, 400, new BasicDBObject(ERROR, "Bad request - only GET supported"));
            return;
        }

        String uri = request.getRequestURI();

        if (uri == null || !uri.equals("/estimate")) {
            respond(request, httpServletResponse, 400, new BasicDBObject(ERROR, "Bad request - Invalid URL"));
            return;
        }

        String keyword = request.getParameter(KEYWORD);

        if (keyword == null || keyword.length() == 0) {
            respond(request, httpServletResponse, 400, new BasicDBObject(ERROR, "Bad request - Keyword not specified"));
            return;
        }

        KeywordProcessor processor = new KeywordProcessor(keyword);
        int score;

        try {
            score = processor.getScore();

            BasicDBObject result = new BasicDBObject(KEYWORD, keyword).append(SCORE, score);

            respond(request, httpServletResponse, 200, result);
        } catch (Throwable throwable) {
            respond(request, httpServletResponse, 500, new BasicDBObject(ERROR, "Oops - An error occurred - "
                    + throwable.getMessage()));
        }


    }

    /**
     * Utility method for writing response body
     */
    private void respond(Request request, HttpServletResponse response, int status, BasicDBObject result) {

        response.setContentType("application/json");

        response.setStatus(status);

        response.setCharacterEncoding("utf-8");

        request.setHandled(true);

        if (result != null) {
            try {
                response.getWriter().write(result.toJson());
            } catch (IOException e) {
                logger.error("Error writing response " + result + " for request " + request.getRequestURI());
            }
        }
    }
}
