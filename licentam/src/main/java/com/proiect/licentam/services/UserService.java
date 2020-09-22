package com.proiect.licentam.services;


import com.proiect.licentam.model.User;
import com.proiect.licentam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;

    private List<User> userList;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userList = userRepository.findAll();
    }


    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findOneUser(Integer userId) {

        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isPresent()) {
            return foundUser;
        }
        return foundUser;
    }

    public User findUserByUsernamePassword(User loginUser) {
        List<User> result = userList.stream().filter(o -> o.getUserName().equals(loginUser.getUserName())).
                filter(o -> o.getPassword().equals(loginUser.getPassword())).
                collect(Collectors.toList());
        if (result.size() == 1) {
            return result.get(0);
        }
        return null;
    }

    public User saveOneUser(User toBeSavedUser) {

        User user = userRepository.save(toBeSavedUser);
        if (!user.getUserName().isEmpty()) {
            userList.add(toBeSavedUser);
        }
        return user;
    }

    public User registerUser(User toBeRegisteredUser) {
        User registeredUser = new User();
        List<User> result = userList.stream().filter(o -> o.getUserName().equals(toBeRegisteredUser.getUserName())).
                filter(o -> o.getPassword().equals(toBeRegisteredUser.getPassword())).
                collect(Collectors.toList());
        if (result.size() == 0) {
            registeredUser = saveOneUser(toBeRegisteredUser);
        }
        return registeredUser;
    }

    public boolean deleteOneUser(Integer userId) {
        userRepository.deleteById(userId);
        if (!findOneUser(userId).isPresent())
            return true;
        return false;
    }

}
