package org.pikalovanna.zuzex_task.controller;

import lombok.RequiredArgsConstructor;
import org.pikalovanna.zuzex_task.dto.UserWrapper;
import org.pikalovanna.zuzex_task.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final UserService service;

    @GetMapping("{id}")
    UserWrapper getUser(@PathVariable Long id){
        return service.getUser(id);
    }

    @DeleteMapping("{id}")
    void deleteUser(@PathVariable Long id){
        service.deleteUser(id);
    }

    @PostMapping
    UserWrapper updateUser(@RequestBody UserWrapper userWrapper){
        return service.update(userWrapper);
    }
}
