
package com.jose.registrousuarios.Controller;

import java.util.List;

import com.jose.registrousuarios.DTO.UsuarioPatchDTO;
import com.jose.registrousuarios.DTO.UsuarioRequestDTO;
import com.jose.registrousuarios.DTO.UsuarioResponseDTO;
import com.jose.registrousuarios.DTO.UsuarioResponseGetDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jose.registrousuarios.Service.UsuarioService;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;

	@PostMapping
	public ResponseEntity<?> inserir(@RequestBody @Valid UsuarioRequestDTO usuarioDto) {
		try {
			UsuarioResponseDTO responseDto = usuarioService.inserir(usuarioDto);
			return ResponseEntity.ok(responseDto);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Não foi possível inserir o usuário: " + e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<List<UsuarioResponseGetDTO>> listarUsers() {
		try {
			List<UsuarioResponseGetDTO> listaDto = usuarioService.listarUsers();
			return ResponseEntity.ok(listaDto);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<UsuarioResponseGetDTO> buscarPorId(@PathVariable Long id) {
		try {
			return usuarioService.buscarPorId(id)
					.map(ResponseEntity::ok)
					.orElseGet(() -> ResponseEntity.notFound().build());
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody @Valid UsuarioRequestDTO usuarioDto) {
		try {
			UsuarioResponseDTO dto = usuarioService.alterar(id, usuarioDto);
			return ResponseEntity.ok(dto);
		} catch (RuntimeException e) {
			if (e.getMessage().contains("não encontrado")) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.status(500).body("Erro ao alterar o usuário: " + e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		try {
			usuarioService.deletar(id);
			return ResponseEntity.ok("Usuário deletado com sucesso!");
		} catch (RuntimeException e) {
			if (e.getMessage().contains("não encontrado")) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.status(500).body("Erro ao deletar o usuário: " + e.getMessage());
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizarParcialmente(@PathVariable Long id, @RequestBody UsuarioPatchDTO patchDto) {
		try {
			UsuarioResponseDTO dto = usuarioService.atualizarParcialmente(id, patchDto);
			return ResponseEntity.ok(dto);
		} catch (RuntimeException e) {
			if (e.getMessage().contains("não encontrado")) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.status(500).body("Erro ao atualizar parcialmente o usuário: " + e.getMessage());
		}
	}
}