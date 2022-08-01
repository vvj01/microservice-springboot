package com.example.user.controller;

import com.example.user.VO.ResponseTemplateVO;
import com.example.user.entity.UserEntity;
import com.example.user.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    private static final String USER_SERVICE = "getUserWithDepartment";
    private static final String USER_SERVICE_CIRCUIT = "getUserWithDepartmentCircuit";
    private static final String USER_SERVICE_RETRY = "getUserWithDepartmentRetry";

    @PostMapping("/")
    public UserEntity saveUser(@RequestBody UserEntity userEntity){

        log.info("Inside saveUser of UserController");
        return userService.saveUser(userEntity);
    }

    @GetMapping("/user/{id}")
    public UserEntity getUserById(@PathVariable("id") Long userId){
         log.info("Inside getUserById of UserController");
        return userService.getUser(userId);

    }

    @GetMapping("/{id}")
    @CircuitBreaker(name= USER_SERVICE_CIRCUIT, fallbackMethod = "getUserWithDepartmentFallback")
    //@Retry(name = USER_SERVICE_RETRY, fallbackMethod = "getUserWithDepartmentFallback")
    //@RateLimiter(name = USER_SERVICE)
    public ResponseTemplateVO getUserWithDepartment(@PathVariable("id") Long userId){
        log.info("Inside getUserWithDepartment of UserController");
        return userService.getUserWithDepartment(userId);
    }

    public ResponseTemplateVO getUserWithDepartmentFallback(Exception e){

        log.warn("getUserWithDepartment failing, running with fallback method!");

        return new ResponseTemplateVO();

    }

}
