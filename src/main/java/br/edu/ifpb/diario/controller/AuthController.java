package br.edu.ifpb.diario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.diario.dto.LoginDTO;
import br.edu.ifpb.diario.dto.LoginResponseDTO;
import br.edu.ifpb.diario.dto.RegisterUserRequestDTO;
import br.edu.ifpb.diario.model.User;
import br.edu.ifpb.diario.security.JWTUtils;
import br.edu.ifpb.diario.security.PasswordEncoderConfig;
import br.edu.ifpb.diario.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoderConfig passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password()));

            User userDetails = (User) authentication.getPrincipal();

            String jwtToken = jwtUtils.generateJwtToken(userDetails.getUsername());

            return ResponseEntity.ok(new LoginResponseDTO("Autenticação realizada com sucesso!", jwtToken));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponseDTO("Credenciais inválidas", null));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponseDTO("Erro de autenticação", null));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserRequestDTO request) {
        User response = userService.saveUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}