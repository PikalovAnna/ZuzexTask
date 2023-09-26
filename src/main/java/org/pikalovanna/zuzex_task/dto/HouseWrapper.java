package org.pikalovanna.zuzex_task.dto;

import lombok.Getter;
import lombok.Setter;
import org.pikalovanna.zuzex_task.entity.House;

@Getter
@Setter
public class HouseWrapper {
    Long id;
    String address;
    Long ownerId;

    public HouseWrapper(){}

    public HouseWrapper(House house){
        this.id = house.getId();
        this.address = house.getAddress();
        this.ownerId = house.getOwner() != null ? house.getOwner().getId() : null;
    }
}
