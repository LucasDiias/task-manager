# Task Manager API

Task Manager API é uma aplicação Spring Boot para gerenciar tarefas. Esta API permite criar, listar, atualizar e deletar tarefas, além de autenticar e registrar usuários.

## Sumário

- [Task Manager API](#task-manager-api)
  - [Sumário](#sumário)
  - [Tecnologias Utilizadas](#tecnologias-utilizadas)
  - [Pré-requisitos](#pré-requisitos)
  - [Configuração do Projeto](#configuração-do-projeto)
    - [Instalação](#instalação)
  - [Endpoints](#endpoints)
    - [Autenticação](#autenticação)
      - [Login](#login)
      - [Registro](#registro)
      - [Refresh Token](#refresh-token)
      - [Logout](#logout)
    - [Usuários](#usuários)
      - [Obter Usuário Atual](#obter-usuário-atual)
      - [Atualizar Usuário Atual](#atualizar-usuário-atual)
      - [Deletar Usuário Atual](#deletar-usuário-atual)
      - [Atualizar Configurações do Usuário Atual](#atualizar-configurações-do-usuário-atual)
    - [Tarefas](#tarefas)
      - [Criar Tarefa](#criar-tarefa)
      - [Listar Tarefas](#listar-tarefas)
      - [Obter Tarefa por ID](#obter-tarefa-por-id)
      - [Atualizar Tarefa](#atualizar-tarefa)
      - [Deletar Tarefa](#deletar-tarefa)
  - [Tratamento de Exceções](#tratamento-de-exceções)
  - [Configuração de CORS](#configuração-de-cors)
  - [Segurança](#segurança)
  - [Licença](#licença)

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT (JSON Web Token)
- SQLite Database
- Lombok

## Pré-requisitos

- Java 17
- Maven

## Configuração do Projeto

### Instalação

1. Compile e execute a aplicação:

```bash
mvn clean install
mvn spring-boot:run
```

2. Acesse a aplicação em `http://localhost:8080`

## Endpoints

### Autenticação

#### Login

```http
POST /api/auth/login
```

**Descrição:** Este endpoint é usado para autenticar um usuário com e-mail e senha.

**Request Body:**

```json
{
  "email": "user@example.com",
  "password": "password"
}
```

**Response:**

- **Status 200 OK:** Autenticação bem-sucedida. Os tokens são definidos nos cookies HTTP-only.
- **Status 400 Bad Request:** Credenciais inválidas

#### Registro

```http
POST /api/auth/register
```

**Descrição:** Este endpoint é usado para registrar um novo usuário.

**Request Body:**

```json
{
  "name": "User Name",
  "email": "user@example.com",
  "password": "password"
}
```

**Response:**

- **Status 200 OK:** Registro bem-sucedido. Os tokens são definidos nos cookies HTTP-only.
- **Status 400 Bad Request:** Usuário já existe.

#### Refresh Token

```http
POST /api/auth/refresh
```

**Descrição:** Este endpoint é usado para obter um novo token de acesso usando um token de atualização.

**Request Body:** O token de atualização deve ser enviado como um cookie HTTP-only. Esse cookie é adicionado automaticamente ao realizar login ou registro.

**Response:**

- **Status 200 OK:** Atualização de token bem-sucedida. Os novos tokens são definidos nos cookies HTTP-only.
- **Status 400 Bad Request:** Token de atualização inválido ou não encontrado.

#### Logout

```http
POST /api/auth/logout
```

**Descrição:** Este endpoint é usado para deslogar o usuário, removendo os cookies de autenticação.

**Response:**

- **Status 200 OK:** Logout bem-sucedido. Os cookies HTTP-only de token e refreshToken são definidos como `null`.

### Usuários

#### Obter Usuário Atual

```http
GET /api/users/me
```

**Descrição:** Este endpoint é usado para obter os dados públicos do usuário atual.

**Response:**

- **Status 200 OK:** Usuário retornado com sucesso.
- **Status 404 Not Found:** Usuário não encontrado.

```json
{
  "name": "User",
  "email": "user@example.com",
  "phone": "(99) 90000-0000", // opcional
  "birthDate": "1964-04-01", // opcional
  "settings": {
    "notificationsEnabled": true, // padrão: true
    "darkMode": false, // padrão: false
    "language": "pt-BR" // padrão: "pt-BR"
  }
}
```

#### Atualizar Usuário Atual

```http
PUT /api/users/me
```

**Descrição:** Este endpoint é usado para atualizar as informações do usuário autenticado.

**Request Body**

```json
{
  "email": "updatedUser@example.com",
  "password": "updatedPassword"
}
```

**Response:**

- **Status 200 OK:** Informações do usuário atualizadas com sucesso
- **Status 404 Not Found:** Usuário não encontrado.

```json
{
  "name": "User",
  "email": "user@example.com",
  "phone": "(99) 90000-0000", // opcional
  "birthDate": "1964-04-01", // opcional
  "settings": {
    "notificationsEnabled": true, // padrão: true
    "darkMode": false, // padrão: false
    "language": "pt-BR" // padrão: "pt-BR"
  }
}
```

#### Deletar Usuário Atual

```http
DELETE /api/users/me
```

**Descrição:** Este endpoint é usado para deletar o usuário autenticado.

**Response:**

- **Status 204 No Content:** Usuário deletado com sucesso.
- **Status 404 Not Found:** Usuário não encontrado.

#### Atualizar Configurações do Usuário Atual

```http
PUT /api/users/me/settings
```

**Descrição:** Este endpoint é usado para atualizar as configurações do usuário atual.

**Request Body:**

```json
{
  "notificationsEnabled": false,
  "darkMode": false,
  "language": "pt-BR"
}
```

**Response:**

- **Status 200 OK:** Informações do usuário atualizadas com sucesso
- **Status 404 Not Found:** Usuário não encontrado.

```json
{
  "name": "User",
  "email": "user@example.com",
  "phone": "(99) 90000-0000",
  "birthDate": "1964-04-01",
  "settings": {
    "notificationsEnabled": false,
    "darkMode": false,
    "language": "pt-BR"
  }
}
```

### Tarefas

#### Criar Tarefa

```http
POST /api/tasks
```

**Descrição:** Este endpoint é usado para criar uma nova tarefa.

**Request Body**

```json
{
  "title": "Tarefa",
  "description": "Descrição da Tarefa",
  "priority": 1
}
```

**Response:**

- **Status 200 OK:** Tarefa criada com sucesso.
- **Status 400 Bad Request:** Dados inválidos.

```json
{
  "id": 1,
  "title": "Tarefa",
  "description": "Descrição da Tarefa",
  "priority": 1,
  "done": false,
  "doneAt": null,
  "createdAt": "2024-01-01"
}
```

#### Listar Tarefas

```http
GET /api/tasks?page=0&size=10
```

**Descrição:** Este endpoint é usado para listar as tarefas do usuário autenticado.

**Response:**

- **Status 200 OK:** Lista de tarefas retornada com sucesso.

```json
{
  "content": [
    {
      "id": 1,
      "title": "Tarefa",
      "description": "Descrição da tarefa",
      "priority": 1,
      "done": false,
      "doneAt": null,
      "createdAt": "2024-01-01"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 1,
  "totalElements": 1,
  "last": true,
  "size": 10,
  "number": 0,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "numberOfElements": 1,
  "first": true,
  "empty": false
}
```

#### Obter Tarefa por ID

```http
GET /api/tasks/{id}
```

**Descrição:** Este endpoint é usado para obter uma tarefa específica pelo ID.

**Response:**

- **Status 200 OK:** Tarefa retornada com sucesso.
- **Status 404 Not Found:** Tarefa não encontrada.

```json
{
  "id": 1,
  "title": "Tarefa",
  "description": "Descrição da tarefa",
  "priority": 1,
  "done": false,
  "doneAt": null,
  "createdAt": "2024-01-01"
}
```

#### Atualizar Tarefa

```http
PUT /api/tasks/{id}
```

**Descrição:** Este endpoint é usado para atualizar uma tarefa existente.

**Request Body**

```json
{
  "id": 1,
  "title": "Tarefa Atualizada",
  "description": "Descrição da tarefa atualizada",
  "priority": 3,
  "done": true,
  "doneAt": "2024-07-13",
  "createdAt": "2024-01-01"
}
```

**Response:**

- **Status 200 OK:** Tarefa atualizada com sucesso.
- **Status 404 Not Found:** Tarefa não encontrada.

```json
{
  "id": 1,
  "title": "Tarefa Atualizada",
  "description": "Descrição da tarefa atualizada",
  "priority": 3,
  "done": true,
  "doneAt": "2024-07-13",
  "createdAt": "2024-01-01"
}
```

#### Deletar Tarefa

```http
DELETE /api/tasks/{id}
```

**Descrição:** Este endpoint é usado para deletar uma tarefa específica pelo ID.

**Response:**

- **Status 204 No Content:** Tarefa deletada com sucesso.
- **Status 404 Not Found:** Tarefa não encontrada.

## Tratamento de Exceções

O projeto possui um controlador de exceções global (`RestExceptionHandler`) que lida com as seguintes exceções:

| **Exceção**                 | **Código HTTP**           | **Descrição**                   |
| --------------------------- | ------------------------- | ------------------------------- |
| `ResourceNotFoundException` | 404 Not Found             | Recurso não encontrado.         |
| `UnauthorizedException`     | 401 Unauthorized          | Usuário não autenticado.        |
| `BadRequestException`       | 400 Bad Request           | Requisição inválida.            |
| `ForbiddenException`        | 403 Forbidden             | Acesso negado a um recurso.     |
| `TokenException`            | 401 Unauthorized          | Token JWT inválido ou expirado. |
| `UsernameNotFoundException` | 404 Not Found             | Nome de usuário não encontrado. |
| `Exception` (genérica)      | 500 Internal Server Error | Erro interno do servidor.       |

## Configuração de CORS

A configuração de CORS permite que a API seja acessada a partir de `http://localhost:5173` com os métodos `GET`, `POST`, `DELETE` e `PUT`.

## Segurança

A segurança da aplicação é gerenciada pelo Spring Security e JWT. Durante o login, um token JWT e um token de atualização são gerados e definidos como cookies HTTP-only. Esses cookies são usados para autenticar o usuário em requisições subsequentes.

- Login: Gera e define os cookies `token` e `refreshToken`.
- Refresh Token: Usa o cookie `refreshToken`v para gerar um novo token de acesso.
- Logout: Remove os cookies `token` e `refreshToken`.

## Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
