package org.pikalovanna.zuzex_task.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class HouseRoomerDto {

    @NotNull(message = "Обязательное поле")
    Long userId;

    @NotNull(message = "Обязательное поле")
    Long houseId;
}
