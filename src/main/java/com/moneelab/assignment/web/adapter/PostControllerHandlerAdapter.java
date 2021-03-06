package com.moneelab.assignment.web.adapter;

import com.moneelab.assignment.config.session.SessionUserService;
import com.moneelab.assignment.dto.ResponseEntity;
import com.moneelab.assignment.dto.post.PostRequest;
import com.moneelab.assignment.util.HttpMethods;
import com.moneelab.assignment.web.HandlerAdapter;
import com.moneelab.assignment.web.controller.post.PostController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

//TODO requestBody 객체 유효성 검사 (instanceof)
//TODO PathVariable 타입 검사
public class PostControllerHandlerAdapter extends HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof PostController);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        PostController controller = (PostController) handler;

        Map<String, String> paramMap = createParamMap(request);
        String requestBody = inputStreamToString(request.getInputStream());

        ResponseEntity result;
        switch (request.getMethod()) {
            case HttpMethods.GET:
                result = controller.findPost(paramMap);

                break;
            case HttpMethods.POST:
                result = controller.save(objectMapper.readValue(requestBody, PostRequest.class), new SessionUserService(request.getSession()));

                break;
            case HttpMethods.PUT:
                PostRequest postRequest = objectMapper.readValue(requestBody, PostRequest.class);
                result = controller.update(paramMap, postRequest, new SessionUserService(request.getSession()));

                break;
            case HttpMethods.DELETE:
                result = controller.delete(paramMap, new SessionUserService(request.getSession()));

                break;
            default:
                throw new IllegalArgumentException("존재하지 않는 경로입니다. uri=" + request.getRequestURI() + ", method=" + request.getMethod());
        }

        result.setHttpResponse(response);
    }


}