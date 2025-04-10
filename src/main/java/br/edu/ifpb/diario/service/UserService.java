package br.edu.ifpb.diario.service;

import java.util.List;
import java.util.Optional;

import br.edu.ifpb.diario.dto.RegisterUserRequestDTO;
import br.edu.ifpb.diario.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.diario.model.User;
import br.edu.ifpb.diario.repository.UserRepository;
import br.edu.ifpb.diario.util.UserValidations;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(RegisterUserRequestDTO user) {
        UserValidations.validateEmail(user.email());

        User newUser = new User();
        newUser.setName(user.name());
        newUser.setEmail(user.email());
        newUser.setPassword(user.password());
        newUser.setRole(user.role());

        return userRepository.save(newUser);
    }

    public User updateUser(Long id, RegisterUserRequestDTO user) {
        Optional<User> userToEdit = userRepository.findById(id);
        if (userToEdit.isPresent()) {
            userToEdit.get().setId(id);
            userToEdit.get().setName(user.name());
            userToEdit.get().setEmail(user.email());
            userToEdit.get().setPassword(user.password());
            userToEdit.get().setRole(user.role());

            userRepository.save(userToEdit.get());
            return userToEdit.get();
        } else {
            throw new UserNotFoundException();
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
