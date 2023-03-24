package com.modsen.software.user_service.controllers;

import com.modsen.software.user_service.domain.User;
import com.modsen.software.user_service.repository.UserJPARepository;
import com.modsen.software.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserJPARepository userJPARepository;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        return userJPARepository.findById(id).get();
    }

//    @GetMapping
//    public List<User> getUsers() {
//        return userRepository.getUsers();
//    }
//
//    @GetMapping("/pageable")
//    public Page<User> getUsersPageable(UserPage page) {
//        return userRepository.getUsersPageable(page);
//    }
//
//    @GetMapping("/{id}")
//    public User getUser(@PathVariable Integer id) {
//        return userRepository.getUserById(id);
//    }
//
//    @GetMapping("/{id}/firstname")
//    public String getUserFirstname(@PathVariable Integer id) {
//        return userRepository.getUserFirstnameById(id);
//    }
//
//    @GetMapping("/{id}/info")
//    public User getUserInfo(@PathVariable Integer id) {
//        return userRepository.getUserInfoById(id);
//    }
//
//    @GetMapping("/{id}/dto")
//    public UserDto getUserDto(@PathVariable Integer id) {
//        return userRepository.getUserDtoById(id);
//    }
//
//    @GetMapping("/{id}/tuple")
//    public User getUserTuple(@PathVariable Integer id) {
//        return userRepository.getUserTupleById(id);
//    }
//
//    @PostMapping
//    public User createUser(@RequestBody User user) {
//        return userRepository.createUser(user);
//    }
//
//    @PutMapping
//    public User updateUser(@RequestBody User user) {
//        return userRepository.updateUser(user);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteUser(@PathVariable Integer id) {
//        userRepository.deleteUserById(id);
//    }
}
