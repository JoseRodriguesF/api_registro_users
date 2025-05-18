package com.jose.datarelate.Controller;

import java.util.List;
import java.util.Optional;

import com.jose.datarelate.DTO.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jose.datarelate.Model.Usuario;
import com.jose.datarelate.Service.UsuarioService;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;


	@PostMapping
	public ResponseEntity<?> inserir(@RequestBody Usuario usuario) {
		try {
			Usuario user = usuarioService.inserir(usuario);
			UsuarioDTO dto = UsuarioDTO.fromEntity(user);

			return ResponseEntity.ok(dto);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Não foi possível inserir o usuario" + e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<List<Usuario>> listarUsers() {
		try {
			List<Usuario> listaUsers = usuarioService.listarUsers();
			return ResponseEntity.ok(listaUsers);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
		try {
			Optional<Usuario> user = usuarioService.buscarPorId(id);
			return user.map(ResponseEntity::ok)
					.orElseGet(() -> ResponseEntity.notFound().build());
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody Usuario usuario) {
		try {
			Optional<Usuario> existingUser = usuarioService.buscarPorId(id);
			if (existingUser.isPresent()) {
				usuario.setId(id);
				Usuario updatedUser = usuarioService.alterar(usuario);
				UsuarioDTO dto = UsuarioDTO.fromEntity(updatedUser);
				return ResponseEntity.ok(dto);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Erro ao alterar o usuário: " + e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		try {
			Optional<Usuario> existingUser = usuarioService.buscarPorId(id);
			if (existingUser.isPresent()) {
				usuarioService.deletar(id);
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Erro ao deletar o usuário: " + e.getMessage());
		}
	}
}
