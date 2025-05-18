package com.jose.registrousuarios.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioPatchDTO {
    private String nome;
    private String email;
    private String senha;
}