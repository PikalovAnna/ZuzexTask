package org.pikalovanna.zuzex_task.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class HouseRoomersDto {
    Long houseId;
    Set<Long> userIds = new HashSet<>();
}
