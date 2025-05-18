package com.jose.datarelate.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jose.datarelate.Model.Usuario;
import com.jose.datarelate.Repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;

	public Usuario inserir(Usuario user) {
		try {
			return usuarioRepository.save(user);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao inserir o usuário: " + e.getMessage());
		}
	}

	public List<Usuario> listarUsers() {
		try {
			return usuarioRepository.findAll();
		} catch (Exception e) {
			System.err.println("Erro ao listar os usuários: " + e.getMessage());
			throw new RuntimeException("Erro ao listar os usuários. Por favor, tente novamente.");
		}
	}

	public Optional<Usuario> buscarPorId(Long id) {
		try {
			return usuarioRepository.findById(id);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar o usuário por ID: " + e.getMessage());
		}
	}


	public Usuario alterar(Usuario usuario) {
		try {
			Optional<Usuario> existingUser = usuarioRepository.findById(usuario.getId());
			if (existingUser.isPresent()) {
				return usuarioRepository.save(usuario);
			} else {
				throw new RuntimeException("Usuário não encontrado para atualização.");
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao alterar o usuário: " + e.getMessage());
		}
	}

	public void deletar(Long id) {
		try {
			Optional<Usuario> usuario = usuarioRepository.findById(id);
			if (usuario.isPresent()) {
				usuarioRepository.deleteById(id);
			} else {
				throw new RuntimeException("Usuário não encontrado para exclusão.");
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao deletar o usuário: " + e.getMessage());
		}
	}
}
