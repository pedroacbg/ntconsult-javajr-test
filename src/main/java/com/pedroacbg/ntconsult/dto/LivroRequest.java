package com.pedroacbg.ntconsult.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LivroRequest {

    private String titulo;
    private String autor;
    private Integer anoPublicacao;

}
