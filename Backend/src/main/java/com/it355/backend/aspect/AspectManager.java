package com.it355.backend.aspect;

import com.it355.backend.dto.LoginDTO;
import com.it355.backend.entity.Log;
import com.it355.backend.entity.User;
import com.it355.backend.service.LogService;
import com.it355.backend.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@RequiredArgsConstructor
public class AspectManager {

    private final LogService logService;


    @AfterThrowing(value = "execution(* com.it355.backend..*.*(..))", throwing = "ex")
    public void handleException(JoinPoint joinPoint, Exception ex) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();
        Integer userId = null;
        for (Object arg : args) {
            if (arg instanceof LoginDTO desiredVariable) {
                userId = desiredVariable.getId();
                break;
            }
        }

        if (userId != null) {
            User user = new User();
            user.setId(userId);


            Log log = new Log();
            log.setException(ex.toString());
            log.setClassName(className);
            log.setUser(user);
            log.setMethodName(methodName);
            log.setStackTrace(Arrays.toString(ex.getStackTrace()));
            log.setMessage(ex.getMessage());

            logService.save(log);
        } else {
            Log log = new Log();
            log.setException(ex.toString());
            log.setClassName(className);
            log.setStackTrace(Arrays.toString(ex.getStackTrace()));
            log.setMethodName(methodName);
            log.setMessage(ex.getMessage());
            logService.save(log);

        }
    }


}