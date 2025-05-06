package com.jose.datarelate.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jose.datarelate.Model.Usuario;
import com.jose.datarelate.Service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity<?> inserir(@RequestBody Usuario user){
		try {
		Usuario usuario = usuarioService.inserir(user);
	
		return ResponseEntity.ok(usuario);
	}catch (Exception e) {
		return ResponseEntity.badRequest().body("Não foi possível inserir o usuario" + e.getMessage());
	}
	}
	
	@GetMapping
	public ResponseEntity<List<Usuario>> listarUsers(){
		List<Usuario> listaUsers = usuarioService.listarUsers();
		return ResponseEntity.ok(listaUsers);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id){
		Optional<Usuario> user = usuarioService.buscarPorId(id);
		 return user.map(ResponseEntity::ok)
                 .orElseGet(() -> ResponseEntity.notFound().build());
		
	}
	
}
