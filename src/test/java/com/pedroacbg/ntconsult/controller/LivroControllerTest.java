package com.pedroacbg.ntconsult.controller;

import com.pedroacbg.ntconsult.dto.LivroRequest;
import com.pedroacbg.ntconsult.dto.LivroResponse;
import com.pedroacbg.ntconsult.dto.LivroTestDataFactory;
import com.pedroacbg.ntconsult.service.LivroService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

@WebMvcTest(LivroController.class)
public class LivroControllerTest {

    // simula as requisições HTTP
    @Autowired
    private MockMvc mockMvc;

    // converte os objetos java para strings JSON
    @Autowired
    private ObjectMapper objectMapper;

    // cria um mock do service no contexto spring
    @MockitoBean
    private LivroService livroService;

    // faz o teste de cadastrar um livro no banco de dados
    @Test
    void testeDeCadastrarLivroDeveriaRetornar201Criado() throws Exception {
        // configura um request valido e um response valido
        LivroRequest request = LivroTestDataFactory.criarRequestValido();
        LivroResponse responseMockado = LivroTestDataFactory.criarResponseValido();

        // simula a chamada do service e retorna o response mockado
        Mockito.when(livroService.cadastrarLivro(Mockito.any(LivroRequest.class))).thenReturn(responseMockado);

        // faz a chamada mockada da requisição e verifica se a resposta está correta de acordo com HttP Status e mensagem de retorno
        mockMvc.perform(MockMvcRequestBuilders.post("/api/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Livro cadastrado com sucesso! ID: 1 Autor: Reginaldo Lucas"));
    }

    // faz o teste de buscar todos os livros cadastrados no banco de dados
    @Test
    void testeDeBuscarLivrosDeveriaRetornarListaDeResponseEStatus200() throws Exception {
        // configurando lista de dados mockados
        List<LivroResponse> listaMockada = List.of(
                LivroTestDataFactory.criarResponseValido(),
                new LivroResponse(2L, "Viva a vida 2", "Reginaldo Lucas", 1965, LocalDateTime.now(), LocalDateTime.now()),
                new LivroResponse(3L, "Viva a vida 3", "Reginaldo Lucas", 1965, LocalDateTime.now(), LocalDateTime.now())
        );

        // simula a chamada do service e retorna lista mockada
        Mockito.when(livroService.consultarLivros()).thenReturn(listaMockada);

        // faz a chamada mockada da requisição e verifica se a resposta está correta de acordo com HTTP Status e body retornado
        mockMvc.perform(MockMvcRequestBuilders.get("/api/livros")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray()) // Valida se a raiz do JSON é uma lista
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3)) // Valida o tamanho da lista
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].titulo").value("Viva a vida"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].titulo").value("Viva a vida 2"));
    }

    // faz o teste de atualizar um livro ja cadastrado
    @Test
    void testeDeAtualizarLivroDeveriaRetornar200Ok() throws Exception {
        // configura um request e um response valido com uma variavel de ID
        Long id = 1L;
        LivroRequest request = LivroTestDataFactory.criarRequestValido();
        LivroResponse responseMockado = LivroTestDataFactory.criarResponseValido();

        // simula a chamada do service e retorna o response mockado
        Mockito.when(livroService.atualizarLivro(Mockito.eq(id), Mockito.any(LivroRequest.class))).thenReturn(responseMockado);

        // faz a chamada mockada da requisição e verifica se a resposta está correta de acordo com HttP Status e mensagem de retorno
        mockMvc.perform(MockMvcRequestBuilders.put("/api/livros/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Livro atualizado com sucesso! ID: 1"));
    }

    // faz o teste de deletar livro cadastrado
    @Test
    void testeDeletarLivroDeveriaRetornar200Ok() throws Exception {
        Long id = 1L;

        // faz a chamada do service com o doNothing por causa do metodo deletar ser void
        Mockito.doNothing().when(livroService).deletarLivro(id);

        // faz a chamada mockada da requisição e verifica se a resposta está correta de acordo com HttP Status e mensagem de retorno
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/livros/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Livro de ID 1 deletado com sucesso!"));

        // verifica quantas vezes o service foi chamado
        Mockito.verify(livroService, Mockito.times(1)).deletarLivro(id);
    }
}
