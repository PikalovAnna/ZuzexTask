package org.pikalovanna.zuzex_task.service;

import lombok.RequiredArgsConstructor;
import org.pikalovanna.zuzex_task.dto.HouseRoomerDto;
import org.pikalovanna.zuzex_task.dto.HouseRoomersDto;
import org.pikalovanna.zuzex_task.dto.HouseWrapper;
import org.pikalovanna.zuzex_task.entity.House;
import org.pikalovanna.zuzex_task.entity.User;
import org.pikalovanna.zuzex_task.repository.HouseRepository;
import org.pikalovanna.zuzex_task.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Сервис для работы с объектами домов. Предоставляет методы CRUD для домов и заселения в них жильцов.
 */
@Service
@RequiredArgsConstructor
public class HouseService {

    private final HouseRepository houseRepository;
    private final UserRepository userRepository;

    /**
     * Создает/обновляет дом
     * @param request объект запроса для создания/обновления дома
     * @return объект созданного/обновленного дома
     */
    public House updateHouse(HouseWrapper request){
        House house = new House();
        if (request.getId() != null){
            house = houseRepository.getOne(request.getId());
        }
        house.setAddress(request.getAddress());
        if (request.getOwnerId() != null){
            User owner = userRepository.getOne(request.getOwnerId());
            house.setOwner(owner);
        }
        return houseRepository.save(house);
    }

    /**
     * Возвращает объект дома по id
     * @param id уникальный идентификатор дома
     * @return объект дома
     */
    public House getHouse(Long id){
        return houseRepository.getOne(id);
    }

    /**
     * Удаляет объект дома по id
     * @param id уникальный идентификатор дома
     */
    public void deleteHouse(Long id){
        houseRepository.deleteById(id);
    }

    /**
     * Добавляет жильца в дом
     * @param request объект запроса содержащий идентификатор дома и жильца
     */
    public void addRoomer(HouseRoomerDto request){
        House house = houseRepository.getOne(request.getHouseId());
        User roomer = userRepository.getOne(request.getUserId());
        Set<User> houseRoomers = house.getRoomers();
        if (houseRoomers.add(roomer)) {
            house.setRoomers(houseRoomers);
            houseRepository.save(house);
        }
    }

    /**
     * Удаляет жильца из дом
     * @param request объект запроса содержащий идентификатор дома и жильца
     */
    public void deleteRoomer(HouseRoomerDto request){
        House house = houseRepository.getOne(request.getHouseId());
        User roomer = userRepository.getOne(request.getUserId());
        Set<User> houseRoomers = house.getRoomers();
        if (houseRoomers.remove(roomer)) {
            house.setRoomers(houseRoomers);
            houseRepository.save(house);
        }
    }

    /**
     * Полностью обновляет список жильцов дома
     * @param request объект запроса содержащий идентификатор дома и набор жильцов
     */
    public void updateRoomers(HouseRoomersDto request){
        House house = houseRepository.getOne(request.getHouseId());
        Set<User> houseRoomers = new HashSet<>();
        request.getUserIds().forEach(userId -> {
            houseRoomers.add(userRepository.getOne(userId));
        });
        house.setRoomers(houseRoomers);
        houseRepository.save(house);
    }
}
