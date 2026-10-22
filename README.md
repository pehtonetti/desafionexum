# Cadastro de Pessoas · Desafio Full-Stack Nexum

SPA para **cadastro e gestão de pessoas** com CRUD completo, busca por múltiplos campos e preenchimento automático de endereço via ViaCEP. Desenvolvida como solução do desafio técnico Full-Stack Júnior da Nexum Tecnologia.

![Vue.js](https://img.shields.io/badge/Vue.js-3-4FC08D?logo=vuedotjs&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-5-646CFF?logo=vite&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?logo=springboot&logoColor=white)
![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=white)

## Funcionalidades

- **CRUD completo** de pessoas e endereços
- **ViaCEP:** endereço preenchido automaticamente ao sair do campo de CEP
- **Busca** por nome, CPF, cidade, CEP ou estado (nome ou sigla, ex.: `SP`)
- **Validações** de CPF (11 dígitos) e de campos obrigatórios (`@NotBlank`)
- **Modal reutilizável** para cadastro e edição

## Arquitetura

```text
desafionexum/
├── backend/    # API REST · Spring Boot, Spring Web, Spring Data JPA
└── frontend/   # SPA · Vue 3, Vue Router, Axios, Bootstrap
```

| Arquivo | Responsabilidade |
| :-- | :-- |
| `PessoaRepository.java` | Query methods JPA para a busca por vários campos |
| `PessoaService.java` | Regras de negócio, validação de CPF e integração com o `CepService` |
| `PessoaModal.vue` | Formulário de cadastro/edição; consulta o CEP no `@blur` |
| `PessoasView.vue` | Listagem com campo de busca |

## Como rodar localmente

**Pré-requisitos:** Java 17+, Maven, Node.js 18+ e MySQL.

### 1. Banco de dados

Crie o banco `desafio` e ajuste usuário e senha em `backend/src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/desafio
    username: root
    password: sua_senha
```

### 2. Backend (porta 8080)

```bash
cd backend
mvn spring-boot:run
```

API disponível em `http://localhost:8080/api/pessoas`.

### 3. Frontend (porta 5173)

```bash
cd frontend
npm install
npm run dev
```

Acesse `http://localhost:5173`.

## Endpoints

| Método | Rota | Descrição |
| :-- | :-- | :-- |
| `GET` | `/api/pessoas?q=termo` | Lista as pessoas (`q` opcional filtra por nome, CPF, cidade, CEP ou estado) |
| `GET` | `/api/pessoas/{id}` | Busca um cadastro |
| `POST` | `/api/pessoas` | Cria um cadastro |
| `PUT` | `/api/pessoas/{id}` | Atualiza um cadastro |
| `DELETE` | `/api/pessoas/{id}` | Remove um cadastro |

## Autor

**Pedro Tonetti** · [LinkedIn](https://www.linkedin.com/in/pedrotonetti/) · [Portfólio](https://pehtonetti.github.io/portifolio/)
