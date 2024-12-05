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

## Endpoints

### Autenticação

#### Login

```http
POST /api/auth/login
```

**Request Body:**

```json
{
  "email": "user@example.com",
  "password": "password"
}
```

#### Registro

```http
POST /api/auth/register
```

**Request Body:**

```json
{
  "email": "user@example.com",
  "password": "password"
}
```

### Usuários

#### Atualizar Usuário Atual

```http
PUT /api/users/me
```

**Request Body**

```json
{
  "email": "updatedUser@example.com",
  "password": "updatedPassword"
}
```

#### Deletar Usuário Atual

```http
DELETE /api/users/me
```

### Tarefas

#### Criar Tarefa

```http
POST /api/tasks
```

**Request Body**

```json
{
  "title": "Nova Tarefa",
  "description": "Descrição da tarefa",
  "priority": 5,
  "done": null, // optional
  "doneAt": false // optional
}
```

#### Listar Tarefas

```http
GET /api/tasks?page=0&size=10
```

#### Obter Tarefa por ID

```http
GET /api/tasks/{id}
```

#### Atualizar Tarefa

```http
PUT /api/tasks/{id}
```

**Request Body**

```json
{
  "title": "Tarefa Atualizada",
  "description": "Descrição atualizada",
  "priority": 1,
  "done": true, //optional
  "doneAt": "2024-12-01" //optional
}
```

#### Deletar Tarefa

```http
DELETE /api/tasks/{id}
```

## Tratamento de Exceções

O projeto possui um controlador de exceções global (`RestExceptionHandler`) que lida com as seguintes exceções:

- `ResourceNotFoundException`
- `UnauthorizedException`
- `BadRequestException`
- `ForbiddenException`
- `TokenException`
- `UsernameNotFoundException`
- `Exception` (genérica)

## Configuração de CORS

A configuração de CORS permite que a API seja acessada a partir de `http://localhost:5173` com os métodos `GET`, `POST`, `DELETE`, `PUT` e `OPTIONS`.

## Segurança

A segurança da aplicação é gerenciada pelo Spring Security e JWT. O token JWT é gerado durante o login e deve ser incluído no cabeçalho `Authorization` das requisições subsequentes.

## Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.
