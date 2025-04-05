package com.example.Libreria.Controllers;

import com.example.Libreria.Entity.Rol;
import com.example.Libreria.Entity.Usuario;
import com.example.Libreria.Repository.UsuarioRepository;
import com.example.Libreria.Security.JwtUtil;
import com.example.Libreria.Security.UsuarioDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UsuarioDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder, JwtUtil jwtUtil, UsuarioDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }


    @PostMapping("/register/user")
    public String registerUser(@RequestBody Usuario usuario) {
        usuario.setRol(Rol.USUARIO); // Asigna el rol directamente
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
        return "Usuario registrado con éxito";
    }

    @PostMapping("/register/admin")
    public String registerAdmin(@RequestBody Usuario usuario) {
        usuario.setRol(Rol.ADMIN); // Asigna el rol directamente
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
        return "Administrador registrado con éxito";
    }

    @PostMapping("/login")
    public String login(@RequestBody Usuario usuario) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getPassword()));

        Usuario usuarioDB = usuarioRepository.findByEmail(usuario.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return jwtUtil.generateToken(usuarioDB.getEmail(), usuarioDB.getRol().name());
    }


}
