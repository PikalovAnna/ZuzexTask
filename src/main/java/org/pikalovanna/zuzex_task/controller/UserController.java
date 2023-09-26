package org.pikalovanna.zuzex_task.controller;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.pikalovanna.zuzex_task.dto.PageFilter;
import org.pikalovanna.zuzex_task.dto.UserCreateRequest;
import org.pikalovanna.zuzex_task.dto.UserWrapper;
import org.pikalovanna.zuzex_task.entity.User;
import org.pikalovanna.zuzex_task.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final UserService service;

    @GetMapping("{id}")
    @Secured({"ROLE_ADMIN"})
    UserWrapper getUser(@PathVariable Long id) {
        return service.getUser(id);
    }

    @PostMapping("/list")
    @Secured("ROLE_ADMIN")
    Page<UserWrapper> list(@RequestBody PageFilter filter) {
        return service.list(filter);
    }

    @DeleteMapping("{id}")
    @Secured({"ROLE_ADMIN"})
    void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }

    @PatchMapping
    @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_ROOMER"})
    UserWrapper updateUser(@Valid @RequestBody UserWrapper userWrapper) {
        return service.update(userWrapper);
    }

    @PostMapping
    UserWrapper createUser(@Valid @RequestBody UserCreateRequest userWrapper) {
        return service.create(userWrapper);
    }

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_ROOMER"})
    User getCurrentUser() throws NotFoundException {
        return service.getCurrentUser();
    }
}
