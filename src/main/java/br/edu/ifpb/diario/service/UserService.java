package br.edu.ifpb.diario.service;

import java.util.List;
import java.util.Optional;

import br.edu.ifpb.diario.dto.RegisterUserRequestDTO;
import br.edu.ifpb.diario.exceptions.UserNotFoundException;
import br.edu.ifpb.diario.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ifpb.diario.model.User;
import br.edu.ifpb.diario.repository.UserRepository;
import br.edu.ifpb.diario.util.UserValidations;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        newUser.setPassword(passwordEncoder.encode(user.password()));
        newUser.setRole(Role.USER);

        return userRepository.save(newUser);
    }

    public User updateUser(Long id, RegisterUserRequestDTO user) {
        Optional<User> userToEdit = userRepository.findById(id);
        if (userToEdit.isPresent()) {
            userToEdit.get().setId(id);
            userToEdit.get().setName(user.name());
            userToEdit.get().setEmail(user.email());
            userToEdit.get().setPassword(passwordEncoder.encode(user.password()));
            userToEdit.get().setRole(Role.USER);

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

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário com email " + email + " não encontrado.");
        }
        return user;
    }
}
