package com.pedroacbg.ntconsult.dto;

import com.pedroacbg.ntconsult.model.Livro;

import java.time.LocalDateTime;
import java.util.List;

public class LivroTestDataFactory {

    // retorna um LivroRequest pronto
    public static LivroRequest criarRequestValido(){
        return new LivroRequest("Viva a vida", "Reginaldo Lucas", 1965);
    }

    // retorna uma entidade de Livro sem ID (para antes de salvar)
    public static Livro criarEntidadeSemId(){
        return new Livro(null, "Viva a vida", "Reginaldo Lucas", 1965, null, null);
    }

    // retorna a entidade de Livro com ID (simulando entidade cadastrada no banco)
    public static Livro criarEntidadeComId(Long id, String titulo){
        return new Livro(id, titulo, "Reginaldo Lucas", 1965, LocalDateTime.now(), LocalDateTime.now());
    }

    // retorna um LivroResponse pronto
    public static LivroResponse criarResponseValido(){
        return new LivroResponse(1L, "Viva a vida", "Reginaldo Lucas", 1965, LocalDateTime.now(), LocalDateTime.now());
    }

    // retorna uma lista de LivroResponse
    public static List<Livro> criarListaDeEntidades(){
        return List.of(
          criarEntidadeComId(1L, "Viva a vida"),
          criarEntidadeComId(2L, "Viva a vida"),
          criarEntidadeComId(3L, "Viva a vida")
        );
    }

}
