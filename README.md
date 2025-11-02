

# 🧾 CheckList API

> **Projeto desenvolvido para demonstrar conhecimentos avançados em arquitetura e desenvolvimento back-end com Spring Boot.**  
> Embora a proposta (um sistema de checklists) seja simples, a implementação foi feita com **boas práticas, segurança e estrutura de nível profissional**, para exemplificar o domínio das principais ferramentas do ecossistema Java moderno.

---

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3**
- **Spring Security**
- **Spring Data JPA**
- **Jakarta Persistence**
- **Lombok**
- **BCrypt** (para criptografia de senhas)
- **H2 / PostgreSQL** (banco de dados configurável)
- **Maven**
- **Docker**

---

## 🐳 Docker

Este projeto possui configuração para execução em container Docker, facilitando a implantação e padronização do ambiente.

### Como rodar com Docker

1. Certifique-se de ter o [Docker](https://www.docker.com/get-started) instalado e em execução.

2. Na raiz do projeto, execute:

```bash
docker build -t checklist-api .
docker run -p 8080:8080 checklist-api
```
3. A aplicação estará disponível em: http://localhost:8080

Você pode configurar também um banco PostgreSQL via Docker e linkar os containers, ajustando as variáveis de ambiente.

    src/main/java/com/DesafioTec/CheckList
    │
    ├── config Configurações gerais (segurança, beans, etc)
    │   └── SecurityConfig.java
    │
    ├── controller/ Controladores REST (camada de API)
    │   ├── checklist/ChecklistController.java
    │   └── UserController.java
    │
    ├── dto/ Objetos de transferência de dados (DTOs)
    │
    ├── model/ Entidades JPA (mapeamento do banco)
    │   ├── list/ CheckListModel e ItemModel
    │   └── user/ UserModel (implementa UserDetails)
    │
    ├── repository/ Repositórios JPA (acesso ao banco)
    │
    └── service/ Camada de regras de negócio
    ├── checkList/CheckListService.java
    └── user/UserService.java

## 🔐 Autenticação e Autorização

A aplicação usa Spring Security com injeção do usuário autenticado via @AuthenticationPrincipal.
Cada endpoint sensível valida se o usuário que está fazendo a requisição é realmente o dono do recurso antes de permitir atualizações ou exclusões.

Senhas são criptografadas com BCrypt no momento do registro.

Exemplo:
```Java

if (!existing.getUser().getUserId().equals(user.getUserId())) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
}

```

## 📚 Endpoints Principais
### 👤 Usuários


| Método | Endpoint             | Descrição               | Acesso           |
|--------|----------------------|-------------------------|------------------|
| POST   | /api/users/register | Cria um novo usuário  | Público      |
| GET    | /api/users   | Lista todos os usuários | ADMIN    |   
| GET    | /api/users/{id}     | Busca usuário por ID   | Próprio ou ADMIN    |
| PUT    | /api/users/{id}      | Atualiza dados do usuário | Próprio ou ADMIN |
| DELETE | /api/users/{id}      | Remove usuário          | Próprio ou ADMIN |

### 📝 Checklists

| Método | Endpoint             | Descrição               | Acesso           |
|--------|----------------------|-------------------------|------------------|
|GET	 | /api/checklist/all   | Lista todos os checklists    | ADMIN    |
|GET	|/api/checklist/myLists|	Lista checklists do usuário logado	|Próprio|
|GET	|/api/checklist/myLists/{id}|	Busca checklist por ID|Próprio|
|POST|	/api/checklist/myLists|	Cria um novo checklist	|Próprio |
|PATCH|	/api/checklist/myLists/{id}|	Atualiza checklist existente|	Próprio|
|DELETE	|/api/checklist/myLists/{id}|	Remove checklist|	Próprio|


## 🧠 Lógica de Negócio

- Cada usuário (UserModel) pode ter múltiplas checklists (CheckListModel)

- Cada checklist contém uma lista de itens (ItemModel).

### Os relacionamentos seguem:

- UserModel 1:N CheckListModel

- CheckListModel 1:N ItemModel

O uso de CascadeType.ALL e orphanRemoval = true garante que os itens sejam removidos junto com sua checklist principal.

## 🧰 Como Rodar o Projeto Localmente
#### Pré-requisitos

- Java 21+

- Maven 3+

- Docker (opcional para container)

### Passos
```
# Clone o repositório
git clone https://github.com/seu-usuario/checklist-api.git
cd checklist-api

# Instale dependências
mvn clean install

# Rode o projeto
mvn spring-boot:run

```
#### ou por docker
```
docker build -t checklist-api .
docker run -p 8080:8080 checklist-api
```

## 📚 Considerações Finais

Este projeto foi desenvolvido com foco em demonstrar domínio técnico, boas práticas e estruturação de código. Mesmo sendo uma aplicação simples, ele foi propositalmente construído com uma arquitetura completa e escalável, mostrando conhecimento em Spring Boot, JPA, segurança, DTOs e integração com Docker.


