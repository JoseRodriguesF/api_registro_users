package com.jose.registrousuarios.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jose.registrousuarios.DTO.UsuarioPatchDTO;
import com.jose.registrousuarios.DTO.UsuarioRequestDTO;
import com.jose.registrousuarios.DTO.UsuarioResponseDTO;
import com.jose.registrousuarios.DTO.UsuarioResponseGetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jose.registrousuarios.Model.Usuario;
import com.jose.registrousuarios.Repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioResponseDTO inserir(UsuarioRequestDTO dto) {
        try {
            Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(dto.getEmail());
            if (usuarioExistente.isPresent()) {
                throw new RuntimeException("Email já cadastrado.");
            }
            Usuario usuario = dto.toEntity();
            Usuario salvo = usuarioRepository.save(usuario);
            return UsuarioResponseDTO.fromEntity(salvo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir o usuário: " + e.getMessage());
        }
    }

    public List<UsuarioResponseGetDTO> listarUsers() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(UsuarioResponseGetDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioResponseGetDTO> buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(UsuarioResponseGetDTO::fromEntity);
    }

    public UsuarioResponseDTO alterar(Long id, UsuarioRequestDTO dto) {
        try {
            Optional<Usuario> existingUser = usuarioRepository.findById(id);
            if (existingUser.isEmpty()) {
                throw new RuntimeException("Usuário não encontrado para atualização.");
            }
            Usuario usuario = existingUser.get();
            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setSenha(dto.getSenha());

            Usuario salvo = usuarioRepository.save(usuario);
            return UsuarioResponseDTO.fromEntity(salvo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao alterar o usuário: " + e.getMessage());
        }
    }

    public void deletar(Long id) {
        try {
            Optional<Usuario> usuario = usuarioRepository.findById(id);
            if (usuario.isEmpty()) {
                throw new RuntimeException("Usuário não encontrado para exclusão.");
            }
            usuarioRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar o usuário: " + e.getMessage());
        }
    }

    public UsuarioResponseDTO atualizarParcialmente(Long id, UsuarioPatchDTO patchDto) {
        try {
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
            if (usuarioOpt.isEmpty()) {
                throw new RuntimeException("Usuário não encontrado para atualização parcial.");
            }
            Usuario usuario = usuarioOpt.get();

            if (patchDto.getNome() != null) {
                usuario.setNome(patchDto.getNome());
            }
            if (patchDto.getEmail() != null) {
                usuario.setEmail(patchDto.getEmail());
            }
            if (patchDto.getSenha() != null) {
                usuario.setSenha(patchDto.getSenha());
            }

            Usuario salvo = usuarioRepository.save(usuario);
            return UsuarioResponseDTO.fromEntity(salvo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar parcialmente o usuário: " + e.getMessage());
        }
    }
}