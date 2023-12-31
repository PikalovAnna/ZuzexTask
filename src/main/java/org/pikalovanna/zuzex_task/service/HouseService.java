package org.pikalovanna.zuzex_task.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.util.Asserts;
import org.pikalovanna.zuzex_task.dto.*;
import org.pikalovanna.zuzex_task.entity.House;
import org.pikalovanna.zuzex_task.entity.User;
import org.pikalovanna.zuzex_task.enums.Role;
import org.pikalovanna.zuzex_task.repository.HouseRepository;
import org.pikalovanna.zuzex_task.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
            Asserts.notNull(house, "Дом не найден");
        }
        house.setAddress(request.getAddress());
        if (request.getOwnerId() != null) {
            User owner = userRepository.getOne(request.getOwnerId());
            Asserts.notNull(owner, "Хозяин дома по адресу " + house.getAddress() + " не найден");
            house.setOwner(owner);
            if (owner.getRole() != Role.ROLE_OWNER) {
                owner.setRole(Role.ROLE_OWNER);
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
    public HouseWrapper getHouse(Long id) {
        House house = houseRepository.getOne(id);
        Asserts.notNull(house, "Дом не найден");
        return new HouseWrapper(house);
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
    @SneakyThrows
    public void addRoomer(HouseRoomerDto request) {
        House house = houseRepository.getOne(request.getHouseId());
        Asserts.notNull(house, "Дом не найден");
        if (!userService.getCurrentUser().getId().equals(house.getOwner().getId()))
            throw new AccessDeniedException("Добавлять жильцов может только владелец этого дома");
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
    @SneakyThrows
    public void deleteRoomer(HouseRoomerDto request) {
        House house = houseRepository.getOne(request.getHouseId());
        Asserts.notNull(house, "Дом не найден");
        if (!userService.getCurrentUser().getId().equals(house.getOwner().getId()))
            throw new AccessDeniedException("Выселять жильцов может только владелец этого дома");
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
    @SneakyThrows
    public void updateRoomers(HouseRoomersDto request) {
        House house = houseRepository.getOne(request.getHouseId());
        Asserts.notNull(house, "Дом не найден");
        if (!userService.getCurrentUser().getId().equals(house.getOwner().getId()))
            throw new AccessDeniedException("Добавлять жильцов может только владелец этого дома");
        Set<User> houseRoomers = new HashSet<>();
        request.getUserIds().forEach(userId -> {
            houseRoomers.add(userRepository.getOne(userId));
        });
        house.setRoomers(houseRoomers);
        houseRepository.save(house);
    }

    /**
     * Возвращает список жильцов дома
     *
     * @param houseId идентификатор дома
     */
    public List<UserWrapper> getRoomers(Long houseId) {
        House house = houseRepository.getOne(houseId);
        Asserts.notNull(house, "Дом не найден");
        return house.getRoomers().stream().map(UserWrapper::new).collect(Collectors.toList());
    }
}
