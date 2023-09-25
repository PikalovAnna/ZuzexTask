package org.pikalovanna.zuzex_task.service;

import lombok.RequiredArgsConstructor;
import org.pikalovanna.zuzex_task.dto.UserWrapper;
import org.pikalovanna.zuzex_task.entity.User;
import org.pikalovanna.zuzex_task.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserWrapper update(UserWrapper request){
        User user = new User();
        if (request.getId() != null){
            user = userRepository.getOne(request.getId());
        }
        user.setAge(request.getAge());
        user.setName(request.getName());
        return new UserWrapper(userRepository.save(user));
    }

    public UserWrapper getUser(Long id){
        return new UserWrapper(userRepository.getOne(id));
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
