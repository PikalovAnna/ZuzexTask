package org.pikalovanna.zuzex_task.dto;

import lombok.Getter;
import lombok.Setter;
import org.pikalovanna.zuzex_task.entity.House;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class HouseWrapper {
    Long id;

    @Pattern(regexp = "^[a-z]{1,2147483647}+$", message = "Поле содержит недопустимые символы, разрешены только латинские буквы в нижнем регистре")
    String address;

    @NotNull(message = "Обязательное поле")
    Long ownerId;

    public HouseWrapper(){}

    public HouseWrapper(House house){
        this.id = house.getId();
        this.address = house.getAddress();
        this.ownerId = house.getOwner() != null ? house.getOwner().getId() : null;
    }
}
