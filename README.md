# 🚀 Desafio FullStack Nexum - Cadastro de Pessoas

Este projeto é a solução para o desafio de desenvolvimento FullStack Júnior da Nexum Tecnologia.
É uma Single Page Application (SPA) para o **Cadastro e Gestão de Pessoas** que implementa todas as operações CRUD.

**Desenvolvido por:** Pedro Tonetti | Pehtty Tecnologias

## ✨ Visão Geral da Solução

O projeto é dividido em dois módulos principais: um Backend (API) em Java com Spring Boot e um Frontend (SPA) em Vue.js 3.

### Funcionalidades Chave

* **CRUD Completo:** Criação, Listagem, Edição e Exclusão de registros de Pessoas.
* **Integração ViaCEP:** Preenchimento automático de endereço ao informar o CEP (no modal de cadastro/edição).
* **Busca Avançada:** Permite pesquisar pessoas por **Nome, CPF, Cidade, CEP, ou Estado (por nome ou sigla, ex: 'SP')**.
* **Validação de Dados:** Validação de CPF (lógica de 11 dígitos), validações `@NotBlank` e campos de endereço obrigatórios.
* **Layout Profissional:** Uso do logo da Nexum na tela inicial e na listagem para identidade visual.

## 🛠️ Stack Tecnológica

| Camada | Tecnologia | Detalhes |
| :--- | :--- | :--- |
| **Backend** | **Spring Boot 3.x (Java)** | API RESTful com Spring Web e Spring Data JPA. |
| **Frontend** | **Vue 3 + Vite** | SPA moderna, utilizando Composition API (se aplicável). |
| **Banco de Dados** | **MySQL** | Persistência dos dados de Pessoa e Endereço. |
| **Utilitários** | ViaCEP API | Serviço externo para busca de endereço. |

## ⚙️ Como Executar o Projeto Localmente

Para rodar a aplicação, é necessário iniciar o Backend e o Frontend separadamente.

### Pré-requisitos

* **Java 17** ou superior (para o Backend)
* **Maven** (Gerenciador de dependências do Backend)
* **Node.js** e **npm/Yarn** (para o Frontend)
* **MySQL** (Servidor de Banco de Dados)
* **XAMPP** (Para rodar Localmente)

### 1. Configuração do Banco de Dados

1.  Crie um banco de dados MySQL chamado `desafio`.
2.  Acesse o arquivo de configuração do Backend em: `backend/src/main/resources/application.yml`.
3.  **Ajuste** as credenciais do `spring.datasource` (especialmente `username` e `password`) para as suas configurações locais.
    ```yaml
    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/desafio...
        username: root  # Seu usuário
        password:      # Sua senha //Nesse Projeto, nao utilizamos senha pois estamos apenas testando o cadastro efetivo do crud.
      # ...
    ```

### 2. Iniciando o Backend (API)

O Backend será executado na porta `8080`.

1.  Abra o terminal e navegue até a pasta `backend/`:
    ```bash
    cd backend
    ```
2.  Execute o Spring Boot (usando Maven):
    ```bash
    ./mvnw spring-boot:run
    ```
3.  A API estará acessível em: `http://localhost:8080/api/pessoas`.

### 3. Iniciando o Frontend (SPA)

O Frontend será executado na porta padrão do Vite (geralmente `5173`).

1.  Abra **outro terminal** e navegue até a pasta `frontend/`:
    ```bash
    cd frontend
    ```
2.  Instale as dependências Node (se ainda não fez):
    ```bash
    npm install
    # ou yarn install
    ```
3.  Inicie o servidor de desenvolvimento:
    ```bash
    npm run dev
    # ou yarn dev
    ```
4.  Acesse a aplicação no seu navegador: `http://localhost:5173/`

## 🌟 Detalhes da Implementação

| Arquivo/Classe | Responsabilidade | Destaque Técnico |
| :--- | :--- | :--- |
| `PessoaRepository.java` | Backend | Implementação de query methods do JPA para a busca genérica por múltiplos campos (`Nome`, `CPF`, `Cidade`, `CEP`, `Estado`). |
| `PessoaService.java` | Backend | Contém a regra de negócio central, incluindo a **validação de CPF** e a **integração com `CepService` (ViaCEP)**. |
| `PessoaModal.vue` | Frontend | Componente reutilizável para **Cadastro e Edição**, disparando o método de busca de CEP no evento `@blur`. |
| `PessoasView.vue` | Frontend | Tela de listagem com o input de busca e o **Logo da Nexum posicionado** no canto superior direito. |

---
