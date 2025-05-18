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

Utilizei **DTOs (Data Transfer Objects)** para controlar a entrada e saída de dados nas requisições, garantindo que apenas as informações necessárias sejam expostas ou recebidas.

Exemplo de DTO de entrada (cadastro ou atualização):

```java
public class UsuarioRequestDTO {
    private String nome;
    private String email;
    private String senha;
}
```

Exemplo de DTO de saída:

```java
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
}
```
> **Nota:** A senha nunca deve ser exposta em DTOs de resposta.

## Endpoints

- `POST /usuarios` : Cadastrar um novo usuário.
- `GET /usuarios` : Listar todos os usuários.
- `GET /usuarios/{id}` : Buscar usuário pelo ID.
- `PUT /usuarios/{id}` : Editar totalmente um usuário existente.
- `PATCH /usuarios/{id}` : Editar parcialmente um usuário existente (atualização parcial de campos).
- `DELETE /usuarios/{id}` : Excluir um usuário.

## Tratamento de Exceções

Foi implementado um tratamento básico de exceções para garantir respostas apropriadas em casos de erro, como usuário não encontrado ou dados inválidos.

## Como Executar

1. **Clone o repositório:**
    ```bash
    git clone https://github.com/seu-usuario/seu-repositorio.git
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
POST /usuarios
Content-Type: application/json

{
    "nome": "João Silva",
    "email": "joao@email.com",
    "senha": "senhaSuperSecreta"
}
```

### Edição Parcial de Usuário

```http
PATCH /usuarios/1
Content-Type: application/json

{
    "email": "novoemail@email.com",
    "senha": "novaSenha"
}
```

### Resposta

```json
{
    "id": 1,
    "nome": "João Silva",
    "email": "novoemail@email.com"
}
```
> **Nota:** A senha nunca é retornada na resposta.

## Contribuição

Sinta-se à vontade para abrir issues ou pull requests com melhorias, sugestões ou correções.

---

**Autor:** José Rodrigues
