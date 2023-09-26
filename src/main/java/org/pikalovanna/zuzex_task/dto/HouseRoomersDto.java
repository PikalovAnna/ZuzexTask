package org.pikalovanna.zuzex_task.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class HouseRoomersDto {

    @NotNull(message = "Обязательное поле")
    Long houseId;

    Set<Long> userIds = new HashSet<>();
}
