package org.bjsalcedo.springcloud.svc.courses.dao.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    Long id;
    String name;
    String email;
    String password;
}
