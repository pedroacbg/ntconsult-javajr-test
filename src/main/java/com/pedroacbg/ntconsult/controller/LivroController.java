package com.pedroacbg.ntconsult.controller;

import com.pedroacbg.ntconsult.dto.LivroRequest;
import com.pedroacbg.ntconsult.dto.LivroResponse;
import com.pedroacbg.ntconsult.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
@RequiredArgsConstructor
public class LivroController {

    // Injeta o service de Livro
    private final LivroService livroService;

    // Endpoint para cadastrar um livro no banco de dados
    @PostMapping
    public ResponseEntity<String> cadastrarLivro(@RequestBody @Valid LivroRequest livroRequest){
        // tenta cadastrar o livro e caso tenha alguma exceção no service é capturada e retornada ao usuário com HttpStatus 400
        try{
            // salva o livro no banco de dados e retorna o livro salvo na variavel response
            LivroResponse response = livroService.cadastrarLivro(livroRequest);

            // retorna ao usuario o ID do livro cadastrado e o nome do autor com o HttpStatus 201
            return ResponseEntity.status(HttpStatus.CREATED).body("Livro cadastrado com sucesso! ID: " + response.getId() + " Autor: " + response.getAutor());
        }catch(IllegalArgumentException e){
            // captura a excecão do service e retorna a mensagem de erro para o usuário com HttpStatus 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Endpoint para buscar os livros cadastrados no banco de dados
    @GetMapping
    public ResponseEntity<List<LivroResponse>> buscarLivros(){
        return ResponseEntity.ok().body(livroService.consultarLivros());
    }

    // Endpoint para atualizar um livro já cadastrado
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarLivro(@PathVariable Long id, @RequestBody LivroRequest livroRequest){
        // tenta atualizar o livro a partir do ID passado como path variable
        try{
            // salva o livro atualizado como DTO de response e retorna o ID do livro atualizado com o HttpStatus 200
            LivroResponse response = livroService.atualizarLivro(id, livroRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Livro atualizado com sucesso! ID: " + response.getId());
        } catch (Exception e) {
            // captura a excecão do service e retorna a mensagem de erro para o usuário com HttpStatus 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
