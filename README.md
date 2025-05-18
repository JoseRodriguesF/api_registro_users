# API de Usuários

Esta é uma API RESTful desenvolvida em **Spring Boot** para gerenciamento básico de usuários, permitindo operações de cadastro, consulta, edição (total e parcial) e exclusão de registros em um banco de dados **MySQL**.

## Tecnologias Utilizadas

- **Spring Boot**: Framework principal para criação da API.
- **Spring Web**: Para construção das rotas REST.
- **Spring Data JPA**: Facilita a integração e manipulação dos dados no banco MySQL.
- **Lombok**: Redução de código boilerplate (getters, setters, construtores, etc).
- **DevTools**: Auxilia no desenvolvimento com recarregamento automático.
- **MySQL**: Banco de dados relacional.

## Estrutura do Projeto

O projeto está organizado de forma clara, separando as responsabilidades entre camadas (Controllers, Services, Repositories, Models e DTOs), seguindo boas práticas de desenvolvimento.

## Modelo de Usuário

```java
public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String senha;
}
```

## DTOs

São utilizados **DTOs (Data Transfer Objects)** para controlar a entrada e saída de dados nas requisições, garantindo que apenas as informações necessárias sejam expostas ou recebidas.

- **DTO de entrada:**  
  Utilizado para cadastro e atualização:
  ```java
  public class UsuarioRequestDTO {
      private String nome;
      private String email;
      private String senha;
  }
  ```

- **DTO de entrada parcial:**  
  Utilizado para atualização parcial (PATCH):
  ```java
  public class UsuarioPatchDTO {
      private String nome;
      private String email;
      private String senha;
  }
  ```

- **DTO de saída (resposta):**  
  Nunca retorna a senha:
  ```java
  public class UsuarioResponseDTO {
      private String nome;
      private String email;
  }
  ```
  ```java
  public class UsuarioResponseGetDTO {
      private Long id;
      private String nome;
      private String email;
  }
  ```

## Endpoints

- `POST /api/usuario` : Cadastrar um novo usuário.
- `GET /api/usuario` : Listar todos os usuários.
- `GET /api/usuario/{id}` : Buscar usuário pelo ID.
- `PUT /api/usuario/{id}` : Editar totalmente um usuário existente.
- `PATCH /api/usuario/{id}` : Editar parcialmente um usuário existente (atualização parcial de campos).
- `DELETE /api/usuario/{id}` : Excluir um usuário.

## Tratamento de Erros e Respostas HTTP

A API implementa tratamento de erros específico para cada situação, utilizando os status HTTP apropriados e mensagens descritivas:

- **Validação de dados:**  
  Se algum campo obrigatório estiver ausente ou inválido ao cadastrar/alterar, retorna `400 Bad Request` com uma mensagem explicando o erro.

- **Usuário não encontrado:**  
  Ao buscar, atualizar ou deletar um usuário inexistente, retorna `404 Not Found` com mensagem específica, por exemplo:
  ```json
  {
    "message": "Usuário com ID 5 não encontrado."
  }
  ```
  ou
  ```json
  {
    "message": "Usuário com ID 7 não encontrado para deleção."
  }
  ```

- **Erro interno:**  
  Se ocorrer um erro inesperado, retorna `500 Internal Server Error` e uma mensagem informativa:
  ```json
  {
    "error": "Erro ao alterar o usuário: <detalhe>"
  }
  ```

- **Requisições bem-sucedidas:**  
  - **Cadastro:** `201 Created` ou `200 OK` com o usuário criado (sem a senha).
  - **Busca/atualização:** `200 OK` com os dados do usuário.
  - **Deleção:** `200 OK` com mensagem de sucesso.
  - **Lista vazia:** `200 OK` com mensagem `"Não existem usuários no banco."` e a lista vazia.

### Exemplos de Respostas HTTP

**Cadastro bem-sucedido:**
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
    "nome": "João Silva",
    "email": "joao@email.com"
}
```

**Busca com usuário inexistente:**
```http
HTTP/1.1 404 Not Found
Content-Type: application/json

{
    "message": "Usuário com ID 15 não encontrado."
}
```

**Erro de validação:**
```http
HTTP/1.1 400 Bad Request
Content-Type: application/json

"Não foi possível inserir o usuário: Email é obrigatório"
```

**Erro interno:**
```http
HTTP/1.1 500 Internal Server Error
Content-Type: application/json

{
    "error": "Erro ao deletar o usuário: Falha inesperada"
}
```

**Lista vazia:**
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
    "message": "Não existem usuários no banco.",
    "usuarios": []
}
```

> **Nota:** A senha nunca é retornada em nenhuma resposta.

## Como Executar

1. **Clone o repositório:**
    ```bash
    git clone https://github.com/JoseRodriguesF/api_registro_users.git
    ```
2. **Configure o banco de dados MySQL:**
    - Crie um banco de dados e atualize o `application.properties` ou `application.yml` com suas credenciais.

3. **Execute a aplicação:**
    - Via Maven:
      ```bash
      ./mvnw spring-boot:run
      ```
    - Ou abra o projeto em sua IDE preferida e execute a classe principal.

## Exemplos de Requisições

### Cadastro de Usuário

```http
POST /api/usuario
Content-Type: application/json

{
    "nome": "João Silva",
    "email": "joao@email.com",
    "senha": "senhaSuperSecreta"
}
```

### Edição Parcial de Usuário

```http
PATCH /api/usuario/1
Content-Type: application/json

{
    "email": "novoemail@email.com",
    "senha": "novaSenha"
}
```

### Resposta

```json
{
    "nome": "João Silva",
    "email": "novoemail@email.com"
}
```
> **Nota:** A senha nunca é retornada na resposta.

## Contribuição

Sinta-se à vontade para abrir issues ou pull requests com melhorias, sugestões ou correções.

---

**Autor:** José Rodrigues
