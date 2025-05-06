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
		return usuarioRepository.save(user);
	}

	public List<Usuario> listarUsers() {
		return usuarioRepository.findAll();
	}

	public Optional<Usuario> buscarPorId(Long id) {
		return usuarioRepository.findById(id);
	}
	
	

}
