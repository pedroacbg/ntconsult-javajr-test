package com.pedroacbg.ntconsult.service;

import com.pedroacbg.ntconsult.dto.LivroRequest;
import com.pedroacbg.ntconsult.dto.LivroResponse;
import com.pedroacbg.ntconsult.dto.LivroTestDataFactory;
import com.pedroacbg.ntconsult.model.Livro;
import com.pedroacbg.ntconsult.repository.LivroRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LivroServiceTest {

    // faz o mock do repository
    @Mock
    private LivroRepository livroRepository;

    // injeta o mock no service
    @InjectMocks
    private LivroService livroService;

    // faz os testes do metodo de cadastrar um livro
    @Test
    @Order(1)
    void cadastrarLivroDeveriaRetornarLivroResponse(){
        // configuração inicial de request e entidade com ID
        LivroRequest livroRequest = LivroTestDataFactory.criarRequestValido();
        Livro livroSalvo = LivroTestDataFactory.criarEntidadeComId(1L, "Viva a vida");

        // simula o comportamento do repository ao cadastrar livro no banco de dados
        Mockito.when(livroRepository.save(Mockito.any(Livro.class))).thenReturn(livroSalvo);

        // Realiza a chamada para o service
        LivroResponse response = livroService.cadastrarLivro(livroRequest);

        // validações de igualdade de campos entre request, response e funcionalidade de chamadas do repository pelo service
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1L, response.getId());
        Assertions.assertEquals(livroRequest.getTitulo(), response.getTitulo());
        Assertions.assertEquals(livroRequest.getAutor(), response.getAutor());
        Mockito.verify(livroRepository, Mockito.times(1)).save(Mockito.any(Livro.class));
    }

    // faz os testes de buscar os livros já cadastrados
    @Test
    @Order(2)
    void consultarLivrosDeveriaRetornarListaDeLivrosResponse(){
        // configuração inicial de lista mockada
        List<Livro> listaMockada = LivroTestDataFactory.criarListaDeEntidades();

        // simula o comportamento do repository ao buscar os livros cadastrados
        Mockito.when(livroRepository.findAll()).thenReturn(listaMockada);

        // realiza a chamada para o service
        List<LivroResponse> responses = livroService.consultarLivros();

        // validações de igualdade entre campos de diferentes livros
        Assertions.assertNotNull(responses);
        Assertions.assertEquals(3, responses.size());
        Assertions.assertEquals("Viva a vida", responses.get(1).getTitulo());
        Assertions.assertEquals("Viva a vida", responses.get(2).getTitulo());

        Mockito.verify(livroRepository, Mockito.times(1)).findAll();
    }

    // faz o teste de buscar livros já cadastrados no caso de não haver livros cadastrados
    @Test
    @Order(3)
    void consultarLivrosDeveriaRetornarListaDeLivrosResponseVazia(){
        // simula o cenario onde nenhum livro foi cadastrado
        Mockito.when(livroRepository.findAll()).thenReturn(Collections.emptyList());

        // simula a chamada do service
        List<LivroResponse> responses = livroService.consultarLivros();

        // validações
        Assertions.assertNotNull(responses);
        Assertions.assertTrue(responses.isEmpty()); // verifica se a lista está realmente vazia
        Mockito.verify(livroRepository, Mockito.times(1)).findAll();
    }

    // faz o teste de atualizar um livro cadastrado
    @Test
    @Order(4)
    void atualizarLivroDeveriaRetornarLivroResponse() throws Exception {
        // configuração inicial de entidades para o request de atualização
        Livro entidadeAntiga = LivroTestDataFactory.criarEntidadeComId(1L, "Viva a vida");
        LivroRequest requestLivroAtualizado = LivroTestDataFactory.criarRequestValido();
        requestLivroAtualizado.setTitulo("Um dia alegre");

        Livro entidadeAtualizada = LivroTestDataFactory.criarEntidadeComId(1L, "Um dia alegre");
        Mockito.when(livroRepository.findById(1L)).thenReturn(Optional.of(entidadeAntiga));
        Mockito.when(livroRepository.save(Mockito.any(Livro.class))).thenReturn(entidadeAtualizada);

        // simula chamada no service
        LivroResponse response = livroService.atualizarLivro(1L, requestLivroAtualizado);

        // validações
        Assertions.assertEquals("Um dia alegre", response.getTitulo());
    }

    // faz o teste de deletar um livro cadastrado
    @Test
    @Order(5)
    void deletarLivroDeveriaRemoverLivroCadastrado(){
        // configurações
        Livro entidadeDoBanco = LivroTestDataFactory.criarEntidadeComId(1L, "Viva a vida");
        Mockito.when(livroRepository.findById(1L)).thenReturn(Optional.of(entidadeDoBanco));
        Mockito.doNothing().when(livroRepository).deleteById(1L);

        // chamada do service e verificação
        Assertions.assertDoesNotThrow(() -> livroService.deletarLivro(1L));
        Mockito.verify(livroRepository, Mockito.times(1)).findById(1L);
    }

}
