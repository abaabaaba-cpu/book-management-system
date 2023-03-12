package com.example.demo1.model.res;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.beans.ConstructorProperties;

@Component
@Data
public class StudentInfoRes {
    private String stuId;

    private String stuSex;

    private String stuClass;

    private String stuName;

    private String managerId;

    private String collegeName;

    private String token;
}
