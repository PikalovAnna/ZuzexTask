package org.pikalovanna.zuzex_task.controller;

import lombok.RequiredArgsConstructor;
import org.pikalovanna.zuzex_task.dto.HouseRoomerDto;
import org.pikalovanna.zuzex_task.dto.HouseRoomersDto;
import org.pikalovanna.zuzex_task.dto.HouseWrapper;
import org.pikalovanna.zuzex_task.entity.House;
import org.pikalovanna.zuzex_task.service.HouseService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("house")
public class HouseController {

    private final HouseService service;

    @GetMapping("{id}")
    House getHouse(@PathVariable Long id) {
        return service.getHouse(id);
    }

    @DeleteMapping("{id}")
    void deleteHouse(@PathVariable Long id){
        service.deleteHouse(id);
    }

    @PostMapping
    House updateHouse(@RequestBody HouseWrapper request){
        return service.updateHouse(request);
    }

    @PostMapping("/roomer")
    void addRoomer(@RequestBody HouseRoomerDto request){
        service.addRoomer(request);
    }

    @DeleteMapping("/roomer")
    void deleteRoomer(@RequestBody HouseRoomerDto request){
        service.deleteRoomer(request);
    }

    @PatchMapping("/roomers")
    void updateRoomers(@RequestBody HouseRoomersDto request){
        service.updateRoomers(request);
    }
}
