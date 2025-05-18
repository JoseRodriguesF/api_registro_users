package com.jose.registrousuarios.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
			return ResponseEntity.status(200).body(responseDto);
		} catch (RuntimeException e) {
			return ResponseEntity.status(400).body("Não foi possível inserir o usuário: " + e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<?> listarUsers() {
		try {
			List<UsuarioResponseGetDTO> listaDto = usuarioService.listarUsers();
			if (listaDto.isEmpty()) {
				Map<String, Object> response = new HashMap<>();
				response.put("message", "Não existem usuários no banco.");
				response.put("usuarios", listaDto);
				return ResponseEntity.status(200).body(response);
			}
			return ResponseEntity.status(200).body(listaDto);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		try {
			Optional<UsuarioResponseGetDTO> usuarioOpt = usuarioService.buscarPorId(id);
			if (usuarioOpt.isPresent()) {
				return ResponseEntity.status(200).body(usuarioOpt.get());
			} else {
				Map<String, String> response = new HashMap<>();
				response.put("message", "Usuário com ID " + id + " não encontrado.");
				return ResponseEntity.status(404).body(response);
			}
		} catch (Exception e) {
			Map<String, String> error = new HashMap<>();
			error.put("error", "Erro interno: " + e.getMessage());
			return ResponseEntity.status(500).body(error);
		}
	}


	@PutMapping("/{id}")
	public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody @Valid UsuarioRequestDTO usuarioDto) {
		try {
			UsuarioResponseDTO dto = usuarioService.alterar(id, usuarioDto);
			return ResponseEntity.status(200).body(dto);
		} catch (RuntimeException e) {
			if (e.getMessage().contains("não encontrado")) {
				Map<String, String> response = new HashMap<>();
				response.put("message", "Usuário com ID " + id + " não encontrado para atualização.");
				return ResponseEntity.status(404).body(response);
			}
			Map<String, String> error = new HashMap<>();
			error.put("error", "Erro ao alterar o usuário: " + e.getMessage());
			return ResponseEntity.status(500).body(error);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		try {
			usuarioService.deletar(id);
			Map<String, String> response = new HashMap<>();
			response.put("message", "Usuário deletado com sucesso!");
			return ResponseEntity.status(200).body(response);
		} catch (RuntimeException e) {
			if (e.getMessage().contains("não encontrado")) {
				Map<String, String> response = new HashMap<>();
				response.put("message", "Usuário com ID " + id + " não encontrado para deleção.");
				return ResponseEntity.status(404).body(response);
			}
			Map<String, String> error = new HashMap<>();
			error.put("error", "Erro ao deletar o usuário: " + e.getMessage());
			return ResponseEntity.status(500).body(error);
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizarParcialmente(@PathVariable Long id, @RequestBody UsuarioPatchDTO patchDto) {
		try {
			UsuarioResponseDTO dto = usuarioService.atualizarParcialmente(id, patchDto);
			return ResponseEntity.status(200).body(dto);
		} catch (RuntimeException e) {
			if (e.getMessage().contains("não encontrado")) {
				Map<String, String> response = new HashMap<>();
				response.put("message", "Usuário com ID " + id + " não encontrado para atualização parcial.");
				return ResponseEntity.status(404).body(response);
			}
			Map<String, String> error = new HashMap<>();
			error.put("error", "Erro ao atualizar parcialmente o usuário: " + e.getMessage());
			return ResponseEntity.status(500).body(error);
		}
	}
}