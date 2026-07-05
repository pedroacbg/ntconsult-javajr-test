package com.pedroacbg.ntconsult.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroRequest {

    private String titulo;
    private String autor;
    private Integer anoPublicacao;

}
