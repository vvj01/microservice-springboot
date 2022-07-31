package com.example.user.service;

import com.example.user.VO.Department;
import com.example.user.VO.ResponseTemplateVO;
import com.example.user.entity.UserEntity;
import com.example.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    public UserEntity saveUser(UserEntity userEntity) {

        log.info("inside saveUser of UserService");
        return userRepository.save(userEntity);

    }

    public ResponseTemplateVO getUserWithDepartment(Long userId) {
        log.info("inside getUserWithDepartment of UserService");
        ResponseTemplateVO vo = new ResponseTemplateVO();
        UserEntity userEntity = userRepository.findByUserId(userId);
        log.info("userEntity" + userEntity.toString());
        Department department = restTemplate.getForObject(
                "http://DEPARTMENT-SERVICE/departments/"+userEntity.getDepartmentId(),
                //"http://localhost:9000/departments/"+userEntity.getDepartmentId(),
                Department.class);
        log.info("department entity" + department);
        vo.setUserEntity(userEntity);
        vo.setDepartment(department);

        return vo;
    }



    public UserEntity getUser(Long userId) {
        log.info("inside getUserWithDepartment of UserService");
        UserEntity user = userRepository.findByUserId(userId);
        return user;
    }
}
