package com.pedroacbg.ntconsult.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LivroResponse {

    private Long id;
    private String titulo;
    private String autor;
    private Integer anoPublicacao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

}
