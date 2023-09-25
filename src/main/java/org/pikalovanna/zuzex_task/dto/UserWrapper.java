package org.pikalovanna.zuzex_task.dto;

import lombok.Getter;
import lombok.Setter;
import org.pikalovanna.zuzex_task.entity.User;

@Getter
@Setter
public class UserWrapper {
    Long id;
    String name;
    Integer age;

    public UserWrapper(){}

    public UserWrapper(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.age = user.getAge();
    }
}
