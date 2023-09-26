package org.pikalovanna.zuzex_task.dto;

import lombok.Getter;
import lombok.Setter;
import org.pikalovanna.zuzex_task.entity.User;
import org.pikalovanna.zuzex_task.enums.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserWrapper {
    Long id;

    @NotBlank(message = "Обязательное поле")
    @Pattern(regexp = "^[A-Za-z_]{3,50}+$", message = "Поле содержит недопустимые символы, разрешены только латинские буквы в нижнем регистре и нижнее подчеркивание")
    String name;

    Integer age;

    @NotBlank(message = "Пароль не может быть пустым")
    @Pattern(regexp = "^(.{8,10})$", message = "Длинна пароля должна быть от 8 до 10 символов")
    String password;

    Role role = Role.ROLE_ROOMER;

    public UserWrapper() {
    }

    public UserWrapper(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.age = user.getAge();
        this.password = user.getPassword();
        this.role = user.getRole();
    }
}
