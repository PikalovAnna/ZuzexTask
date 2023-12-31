package org.pikalovanna.zuzex_task.service;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.util.Asserts;
import org.pikalovanna.zuzex_task.dto.PageFilter;
import org.pikalovanna.zuzex_task.dto.UserCreateRequest;
import org.pikalovanna.zuzex_task.dto.UserWrapper;
import org.pikalovanna.zuzex_task.entity.User;
import org.pikalovanna.zuzex_task.enums.Role;
import org.pikalovanna.zuzex_task.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByName(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Пользователь с именем " + username + " не найден");
        }
    }

    public User getCurrentUser() throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null)
            throw new NotFoundException("Пользователь не найден");

        User user = (User) authentication.getPrincipal();
        Asserts.notNull(user, "Пользователь не найден");

        return user;
    }

    @SneakyThrows
    public UserWrapper update(UserWrapper request) {
        User user = userRepository.getOne(request.getId());
        Asserts.notNull(user, "Пользователь не найден");

        if (!getCurrentUser().getId().equals(request.getId()) && !getCurrentUser().getRole().equals(Role.ROLE_ADMIN))
            throw new AccessDeniedException("Доступ запрещен");
        user.setAge(request.getAge());
        user.setName(request.getName());
        user.setPassword(encoder.encode(request.getPassword()));
        return new UserWrapper(userRepository.save(user));
    }

    public UserWrapper create(UserCreateRequest request) {
        User user = new User();
        user.setAge(request.getAge());
        user.setName(request.getName());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : Role.ROLE_ROOMER);
        return new UserWrapper(userRepository.save(user));
    }

    /**
     * Возвращает постранично объекты пользователей
     *
     * @param filter фильтр для пролистывания (номер страницы, кол-во записей на странице)
     * @return страницу с пользователями
     */
    public Page<UserWrapper> list(PageFilter filter) {
        return userRepository.findAll(PageRequest.of(
                filter.getPage(),
                filter.getSize(),
                Sort.by("id")))
                .map(UserWrapper::new);
    }

    public UserWrapper getUser(Long id) {
        User user = userRepository.getOne(id);
        Asserts.notNull(user, "Пользователь не найден");
        return new UserWrapper(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
