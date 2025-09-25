package br.com.creedev.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.creedev.api.dto.mapper.UsuarioMapper;
import br.com.creedev.api.dto.request.LoginRequest;
import br.com.creedev.api.dto.request.UsuarioRequest;
import br.com.creedev.core.security.JwtUtil;
import br.com.creedev.domain.model.Usuario;
import br.com.creedev.domain.repository.UsuarioRepository;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	private final UsuarioMapper usuarioMapper;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	
	
	public AuthController(UsuarioRepository usuarioRepository,	PasswordEncoder passwordEncoder,UsuarioMapper usuarioMapper,
			AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		this.usuarioMapper = usuarioMapper;
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody UsuarioRequest request) {
	    if (usuarioRepository.findByEmail(request.email()).isPresent()) {
	        return ResponseEntity.badRequest().body("E-mail já cadastrado");
	    }
	    
	    Usuario u = usuarioMapper.toEntity(request);
	    u.setSenha(passwordEncoder.encode(request.senha()));
	    
	    if (u.getRole() == null) {
	        u.setRole("USER");
	    }
	    
	    usuarioRepository.save(u);
	    return ResponseEntity.ok().build();
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
	    try {
	        authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(request.email(), request.senha())
	        );
	        Usuario u = usuarioRepository.findByEmail(request.email()).get();
	        String token = jwtUtil.generateToken(u.getEmail(), u.getRole());
	        
	        return ResponseEntity.ok(token);
	    } catch (AuthenticationException ex) {
	        return ResponseEntity.status(401).body("Credenciais inválidas");
	    }
	}
}