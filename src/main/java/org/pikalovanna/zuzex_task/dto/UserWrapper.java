package org.pikalovanna.zuzex_task.dto;

import lombok.Getter;
import lombok.Setter;
import org.pikalovanna.zuzex_task.entity.User;
import org.pikalovanna.zuzex_task.enums.Role;

@Getter
@Setter
public class UserWrapper {
    Long id;
    String name;
    Integer age;
    String password;
    Role role = Role.ROOMER;

    public UserWrapper(){}

    public UserWrapper(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.age = user.getAge();
        this.password = user.getPassword();
        this.role = user.getRole();
    }
}
