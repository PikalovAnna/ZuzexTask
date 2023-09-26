package org.pikalovanna.zuzex_task.controller;

import lombok.RequiredArgsConstructor;
import org.pikalovanna.zuzex_task.dto.*;
import org.pikalovanna.zuzex_task.entity.House;
import org.pikalovanna.zuzex_task.service.HouseService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("house")
public class HouseController {

    private final HouseService service;

    @GetMapping("{id}")
    @Secured("ROLE_ADMIN")
    House getHouse(@PathVariable Long id) {
        return service.getHouse(id);
    }

    @DeleteMapping("{id}")
    @Secured("ROLE_ADMIN")
    void deleteHouse(@PathVariable Long id){
        service.deleteHouse(id);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    House updateHouse(@Valid @RequestBody HouseWrapper request){
        return service.updateHouse(request);
    }

    @PostMapping("/list")
    @Secured("ROLE_ADMIN")
    Page<HouseWrapper> list(@RequestBody PageFilter filter){
        return service.list(filter);
    }

    @PostMapping("/roomer")
    @Secured({"ROLE_ADMIN", "ROLE_OWNER"})
    void addRoomer(@Valid @RequestBody HouseRoomerDto request){
        service.addRoomer(request);
    }

    @DeleteMapping("/roomer")
    @Secured({"ROLE_ADMIN", "ROLE_OWNER"})
    void deleteRoomer(@Valid @RequestBody HouseRoomerDto request){
        service.deleteRoomer(request);
    }

    @PatchMapping("/roomers")
    @Secured({"ROLE_ADMIN", "ROLE_OWNER"})
    void updateRoomers(@Valid @RequestBody HouseRoomersDto request){
        service.updateRoomers(request);
    }

    @GetMapping("/roomers")
    @Secured({"ROLE_ADMIN", "ROLE_OWNER"})
    List<UserWrapper> getRoomers(Long houseId){
        return service.getRoomers(houseId);
    }
}
