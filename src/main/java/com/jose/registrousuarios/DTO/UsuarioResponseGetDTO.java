package com.jose.registrousuarios.DTO;

import com.jose.registrousuarios.Model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseGetDTO {

    private Long id;
    private String nome;
    private String email;

    public static UsuarioResponseGetDTO fromEntity(Usuario usuario) {
        return new UsuarioResponseGetDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }
}
