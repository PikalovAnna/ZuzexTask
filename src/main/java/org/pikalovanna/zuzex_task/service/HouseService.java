package org.pikalovanna.zuzex_task.service;

import lombok.RequiredArgsConstructor;
import org.pikalovanna.zuzex_task.dto.HouseRoomerDto;
import org.pikalovanna.zuzex_task.dto.HouseRoomersDto;
import org.pikalovanna.zuzex_task.dto.HouseWrapper;
import org.pikalovanna.zuzex_task.dto.PageFilter;
import org.pikalovanna.zuzex_task.entity.House;
import org.pikalovanna.zuzex_task.entity.User;
import org.pikalovanna.zuzex_task.enums.Role;
import org.pikalovanna.zuzex_task.repository.HouseRepository;
import org.pikalovanna.zuzex_task.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    private final UserService userService;

    /**
     * Создает/обновляет дом, если хозяин дома был
     *
     * @param request объект запроса для создания/обновления дома
     * @return объект созданного/обновленного дома
     */
    public House updateHouse(HouseWrapper request) {
        House house = new House();
        if (request.getId() != null) {
            house = houseRepository.getOne(request.getId());

        }
        house.setAddress(request.getAddress());
        if (request.getOwnerId() != null) {
            User owner = userRepository.getOne(request.getOwnerId());
            house.setOwner(owner);
            if (owner.getRole() != Role.OWNER) {
                owner.setRole(Role.OWNER);
                userRepository.save(owner);
            }
        }
        return houseRepository.save(house);
    }

    /**
     * Возвращает постранично объекты домов
     *
     * @param filter фильтр для пролистывания (номер страницы, кол-во записей на странице)
     * @return страницу с домами
     */
    public Page<HouseWrapper> list(PageFilter filter) {
        return houseRepository.findAll(PageRequest.of(
                filter.getPage(),
                filter.getSize(),
                Sort.by("id")))
                .map(HouseWrapper::new);
    }

    /**
     * Возвращает объект дома по id
     *
     * @param id уникальный идентификатор дома
     * @return объект дома
     */
    public House getHouse(Long id) {
        return houseRepository.getOne(id);
    }

    /**
     * Удаляет объект дома по id
     *
     * @param id уникальный идентификатор дома
     */
    public void deleteHouse(Long id) {
        houseRepository.deleteById(id);
    }

    /**
     * Добавляет жильца в дом
     *
     * @param request объект запроса содержащий идентификатор дома и жильца
     */
    public void addRoomer(HouseRoomerDto request) {
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
     *
     * @param request объект запроса содержащий идентификатор дома и жильца
     */
    public void deleteRoomer(HouseRoomerDto request) {
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
     *
     * @param request объект запроса содержащий идентификатор дома и набор жильцов
     */
    public void updateRoomers(HouseRoomersDto request) {
        House house = houseRepository.getOne(request.getHouseId());
        Set<User> houseRoomers = new HashSet<>();
        request.getUserIds().forEach(userId -> {
            houseRoomers.add(userRepository.getOne(userId));
        });
        house.setRoomers(houseRoomers);
        houseRepository.save(house);
    }
}
