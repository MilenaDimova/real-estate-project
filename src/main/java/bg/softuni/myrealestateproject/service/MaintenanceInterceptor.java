package bg.softuni.myrealestateproject.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalTime;

@Component
public class MaintenanceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var requestURI = request.getRequestURI();
        if (!requestURI.equals("/maintenance")) {
            LocalTime now = LocalTime.now();
            if (now.getHour() == 0 && now.getMinute() < 1) {
                response.sendRedirect("/maintenance");
                return false;
            }
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
