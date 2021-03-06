package com.moneelab.assignment.web.adapter;

import com.moneelab.assignment.config.session.SessionUserService;
import com.moneelab.assignment.dto.ResponseEntity;
import com.moneelab.assignment.util.HttpMethods;
import com.moneelab.assignment.web.HandlerAdapter;
import com.moneelab.assignment.web.controller.like.LikeController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

//TODO requestBody 객체 유효성 검사 (instanceof)
//TODO PathVariable 타입 검사
public class LikeControllerHandlerAdapter extends HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof LikeController);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        LikeController controller = (LikeController) handler;

        Map<String, String> paramMap = createParamMap(request);

        ResponseEntity result;
        HttpSession session = request.getSession();
        switch (request.getMethod()) {
            case HttpMethods.GET:
                result = controller.findLike(paramMap);

                break;
            case HttpMethods.POST:
                result = controller.doLike(paramMap, new SessionUserService(session));

                break;
            case HttpMethods.DELETE:
                result = controller.cancelLike(paramMap, new SessionUserService(session));

                break;
            default:
                throw new IllegalArgumentException("존재하지 않는 경로입니다. uri=" + request.getRequestURI() + ", method=" + request.getMethod());
        }

        result.setHttpResponse(response);
    }
}
