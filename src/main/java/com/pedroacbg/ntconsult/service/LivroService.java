package com.pedroacbg.ntconsult.service;

import com.pedroacbg.ntconsult.dto.LivroRequest;
import com.pedroacbg.ntconsult.dto.LivroResponse;
import com.pedroacbg.ntconsult.model.Livro;
import com.pedroacbg.ntconsult.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivroService {

    // injeta o repository de Livro
    private final LivroRepository livroRepository;


    // metodo para cadastrar livro no banco de dados
    @Transactional
    public LivroResponse cadastrarLivro(LivroRequest livroRequest){
        // verifica se o livro passado não está nulo e a data de publicação está entre o ano atual e o ano que se iniciou a impressão dos livros
        if(livroRequest == null || livroRequest.getAnoPublicacao() > 2026 || livroRequest.getAnoPublicacao() < 1450){
            // estoura uma exceção caso não cumpra as regras
            throw new IllegalArgumentException("O livro cadastrado não deve estar nulo e o ano de publicação deve ser no máximo 2026 e no mínimo 1450");
        }

        // transforma o DTO de request para a entidade de Livro
        Livro novoLivro = requestToEntity(livroRequest);

        // salva o livro cadastrado no banco em uma variavel
        Livro livroSalvo = livroRepository.save(novoLivro);

        // transforma o livro cadastrado em um DTO de response e retorna o livro cadastrado como response
        return entityToResponse(livroSalvo);
    }

    // metodo para buscar os livros cadastrados no banco de dados
    public List<LivroResponse> consultarLivros(){
        // busca os livros e converte eles para o DTO de response e retorna
        return livroRepository.findAll().stream().map(livro -> entityToResponse(livro)).toList();
    }

    // metodo para converter um DTO de request de livro em uma entidade de livro
    private Livro requestToEntity(LivroRequest livroRequest){
        Livro livro = new Livro();
        livro.setAutor(livroRequest.getAutor());
        livro.setTitulo(livroRequest.getTitulo());
        livro.setAnoPublicacao(livroRequest.getAnoPublicacao());
        return livro;
    }

    // metodo para converter uma entitdade de livro em um DTO de response de livro
    private LivroResponse entityToResponse(Livro entity){
        LivroResponse response = new LivroResponse();
        response.setId(entity.getId());
        response.setTitulo(entity.getTitulo());
        response.setAutor(entity.getAutor());
        response.setAnoPublicacao(entity.getAnoPublicacao());
        response.setDataCriacao(entity.getDataCriacao());
        response.setDataAtualizacao(entity.getDataAtualizacao());
        return response;
    }


}
