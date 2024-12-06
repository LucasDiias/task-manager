# Task Manager API

Task Manager API é uma aplicação Spring Boot para gerenciar tarefas. Esta API permite criar, listar, atualizar e deletar tarefas, além de autenticar e registrar usuários.

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

1. Clone o repositório:

```bash
git clone https://github.com/LucasDiias/task-manager.git
cd task-manager
```

2. Compile e execute a aplicação:

```bash
mvn clean install
mvn spring-boot:run
```

3. Acesse a aplicação em `http://localhost:8080`

### Configuração de Variáveis de Ambiente

Certifique-se de configurar as seguintes variáveis de ambiente:

- `TOKEN_SECRET`: Chave secreta para geração de tokens JWT.

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

#### Deletar Usuário Atual

```http
DELETE /api/users/me
```

**Descrição:** Este endpoint é usado para deletar o usuário autenticado.

**Response:**

- **Status 204 No Content:** Usuário deletado com sucesso.
- **Status 404 Not Found:** Usuário não encontrado.

### Tarefas

#### Criar Tarefa

```http
POST /api/tasks
```

**Descrição:** Este endpoint é usado para criar uma nova tarefa.

**Request Body**

```json
{
  "title": "Nova Tarefa",
  "description": "Descrição da tarefa",
  "priority": 3, // Min: 1, Max: 3
  "done": null, // opcional
  "doneAt": false // opcional
}
```

**Response:**

- **Status 200 OK:** Tarefa criada com sucesso.
- **Status 400 Bad Request:** Dados inválidos.

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
      "title": "Tarefa 1",
      "description": "Descrição da tarefa 1",
      "priority": 1,
      "done": false,
      "doneAt": null,
      "createdAt": "2024-01-01",
      "userId": "user-id"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1,
  "totalPages": 1
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
  "title": "Tarefa 1",
  "description": "Descrição da tarefa 1",
  "priority": 1,
  "done": false,
  "doneAt": null,
  "createdAt": "2024-01-01",
  "user": {
    "id": "user-id",
    "name": "User Name",
    "email": "user@example.com"
  }
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
  "title": "Tarefa Atualizada",
  "description": "Descrição atualizada",
  "priority": 1, // Min: 1, Max: 3
  "done": true, // opcional
  "doneAt": "2024-12-01" // opcional
}
```

**Response:**

- **Status 200 OK:** Tarefa atualizada com sucesso.
- **Status 404 Not Found:** Tarefa não encontrada.

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

- **`ResourceNotFoundException`:** Retorna status 404 Not Found.
- **`UnauthorizedException`:** Retorna status 401 Unauthorized.
- **`BadRequestException`:** Retorna status 400 Bad Request.
- **`ForbiddenException`:** Retorna status 403 Forbidden.
- **`TokenException`:** Retorna status 401 Unauthorized.
- **`UsernameNotFoundException`:** Retorna status 404 Not Found.
- **`Exception` (genérica):** Retorna status 500 Internal Server Error.

## Configuração de CORS

A configuração de CORS permite que a API seja acessada a partir de `http://localhost:5173` com os métodos `GET`, `POST`, `DELETE` e `PUT`.

## Segurança

A segurança da aplicação é gerenciada pelo Spring Security e JWT. Durante o login, um token JWT e um token de atualização são gerados e definidos como cookies HTTP-only. Esses cookies são usados para autenticar o usuário em requisições subsequentes.

- Login: Gera e define os cookies `token` e `refreshToken`.
- Refresh Token: Usa o cookie `refreshToken`v para gerar um novo token de acesso.
- Logout: Remove os cookies `token` e `refreshToken`.

## Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo (LICENSE)[LICENSE] para mais detalhes.
