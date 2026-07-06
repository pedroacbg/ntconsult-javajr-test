# 📚 Desafio Java Jr - API de Gerenciamento de Livros

API REST simples desenvolvida em **Spring Boot** para gerenciamento de livros (CRUD completo), construída como parte de um desafio técnico para vaga de Desenvolvedor Java Júnior.

## 🚀 Tecnologias utilizadas

| Tecnologia | Motivo da escolha |
|---|---|
| **Java 21** | Versão LTS mais recente, com melhorias de performance e sintaxe. |
| **Spring Boot** | Framework padrão de mercado para APIs REST em Java, com forte suporte a injeção de dependência, configuração automática e integração com JPA. |
| **Spring Data JPA** | Abstrai o acesso a dados via `JpaRepository`, reduzindo boilerplate de persistência (CRUD pronto, paginação, etc). |
| **H2 Database** | Banco de dados em memória, ideal para testes e demonstrações — não exige instalação nem configuração externa, sobe junto com a aplicação. |
| **Lombok** | Reduz código repetitivo (getters, setters, construtores) através de anotações, deixando as classes mais enxutas. |
| **Bean Validation (Jakarta Validation)** | Permite validar os dados de entrada da API de forma declarativa. |
| **JUnit 5 + Mockito** | Framework padrão de testes em Java, usado para testes unitários (camada de serviço) e testes de integração da camada web (`@WebMvcTest`). |

## 🏗️ Arquitetura do projeto

O projeto segue uma arquitetura em camadas:

```
src/main/java/com/pedroacbg/ntconsult
├── controller     -> Camada de exposição HTTP (endpoints da API)
├── service        -> Regras de negócio e orquestração
├── repository      -> Acesso a dados (Spring Data JPA)
├── model           -> Entidades JPA
└── dto             -> Objetos de entrada (Request) e saída (Response) da API
```

Essa separação garante que a entidade de persistência (`Livro`) não seja exposta diretamente pela API, e que a lógica de negócio fique isolada em `LivroService`.

## 📋 Entidade Livro

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | Long | Identificador único, gerado automaticamente |
| `titulo` | String | Título do livro |
| `autor` | String | Autor do livro |
| `anoPublicacao` | Integer | Ano de publicação |
| `dataCriacao` | LocalDateTime | Preenchido automaticamente na criação |
| `dataAtualizacao` | LocalDateTime | Atualizado automaticamente a cada alteração |

## 🔌 Endpoints da API

Base path: `/api/livros`

| Método | Endpoint | Descrição | Corpo da requisição |
|---|---|---|---|
| `POST` | `/api/livros` | Cadastra um novo livro | `{ "titulo": "...", "autor": "...", "anoPublicacao": 1965 }` |
| `GET` | `/api/livros` | Lista todos os livros cadastrados | - |
| `PUT` | `/api/livros/{id}` | Atualiza um livro existente pelo `id` | `{ "titulo": "...", "autor": "...", "anoPublicacao": 1965 }` |
| `DELETE` | `/api/livros/{id}` | Remove um livro pelo `id` | - |

### Exemplo de uso (cadastro)

```http
POST /api/livros
Content-Type: application/json

{
  "titulo": "Dom Casmurro",
  "autor": "Machado de Assis",
  "anoPublicacao": 1899
}
```

**Resposta (201 Created):**
```
Livro cadastrado com sucesso! ID: 1 Autor: Machado de Assis
```

> ⚠️ O ano de publicação é validado no backend (deve estar entre 1450 e o ano atual). Requisições fora desse intervalo retornam `400 Bad Request`.

## 🗄️ Banco de dados

A aplicação usa o **H2** em modo memória — o banco é recriado a cada execução, sem necessidade de configuração adicional.

Console H2 disponível em (com a aplicação rodando):
```
http://localhost:8080/h2-console
```

Dados de conexão (definidos em `application.yaml`):
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Usuário:** `sa`
- **Senha:** *(em branco)*

## ▶️ Como executar o projeto

**Pré-requisitos:** Java 21+ e Maven (ou usar o wrapper incluso no projeto).

```bash
# Clonar o repositório
git clone https://github.com/pedroacbg/ntconsult-javajr-test
cd ntconsult

# Rodar a aplicação
./mvnw spring-boot:run
```

A API sobe por padrão em `http://localhost:8080`.

## ✅ Como rodar os testes

```bash
./mvnw test
```

O projeto conta com:
- **Testes unitários** da camada de serviço (`LivroServiceTest`), usando Mockito para isolar o repositório.
- **Testes de integração da camada web** (`LivroControllerTest`), usando `@WebMvcTest` e `MockMvc` para simular requisições HTTP reais sem subir o contexto completo da aplicação.

## 🧠 Decisões técnicas

- **DTOs separados da entidade:** evita expor detalhes internos de persistência na API e permite evoluir o modelo de dados sem quebrar o contrato da API.
- **H2 em memória:** escolhido por ser um desafio de curta duração — elimina a necessidade de configurar um banco externo para avaliação, mas o projeto pode ser adaptado facilmente para PostgreSQL/MySQL trocando apenas a configuração de `datasource`.
- **Validação de regra de negócio na camada de serviço:** a validação do intervalo do ano de publicação fica no `LivroService`, garantindo que a regra seja aplicada independentemente de quem chama o serviço (não só via HTTP).

## 🔮 Possíveis evoluções futuras

- Tratamento de erros centralizado com `@RestControllerAdvice`.
- Documentação interativa com Swagger/OpenAPI (springdoc-openapi).
- Paginação na listagem de livros.

## 👤 Autor

**Pedro Augusto Costa Anjos**
📧 [pedrooaugustoo130@gmail.com]
🔗 [LinkedIn](https://linkedin.com/in/pedroacbg)

Projeto desenvolvido para o desafio técnico **desafio-java-jr** da **NTConsult**.
