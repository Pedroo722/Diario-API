# Diário API

Projeto back-end simples criado para a disciplina de **Desenvolvimento de Aplicações Web III**. A aplicação foi construída utilizando **Java** e **Spring Boot**, com o **PostgreSQL** como banco de dados, visando criar uma api para a consulta e manipulações de informações de um [projeto de um sistema de registro de atividades do dia a dia.](https://github.com/Pedroo722/Diario-DAW3).

Membros do Projeto:
- Pedro Henrique Alexandre
- Vinicius Cavalcante Pequeno
- Thiago dos Santos Araújo
- Raykkoner Dujhkkovick Silva de Farias

## Tecnologias Usadas

<div align="center">

![Java 17](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot 3](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white) 

</div>

## Funcionalidades

- **Cadastro de Usuários**
- **Criação de postagens**
- **Pesquisa de postagens**
- **Edição de postagens**
- **Remoção de postagens**

## Estrutura dos Arquivos

- **Controller**: Responsáveis por definir os endpoints da API.
- **Model**: Representa as entidades do banco de dados e seus atributos.
- **Repository**: Arquivos que fazem a conexão com o banco de dados.
- **Service**: Contêm a lógica de negócio para manipular e consultar os dados.
- **Util**: Contêm arquivos com metodos de validação dos dados das entidades da aplicação.

## Como Executar
### Pré-requisitos

- **Java 17** ou superior
- **Maven** 3.8.1+
- **PostgreSQL**

### Passos para execução

1. Clone o repositório:
   ```bash
   git clone https://github.com/Pedroo722/Diario-API.git
   cd Diario-API/
   ```

2. Compile o projeto:
   ```bash
   mvn clean install
   ```

3. Execute o projeto:
   ```bash
   mvn spring-boot:run
   ```

4. Acesse a API em: `http://localhost:8080/{endpoint}`.


## Endpoints

A API fornece os seguintes endpoints para a manipulação de dados da coleção livros.

### Usuário
- **GET** `/api/users/list`: Recupera todos os usuários. Permitindo a filtragem pelos parametros *`?name=`*.
- **GET** `/api/users/list/{id}`: Recupera um usuário pelo ID.
- **POST** `/api/users/create`: Cadastra um novo usuáriio.
- **PUT** `/api/users/update/{id}`: Atualiza um usuário existente.
- **DELETE** `/api/users/delete/{id}`: Remove um usuário pelo ID.

### Usuário
- **GET** `/api/posts/list`: Recupera todos as postagens.
- **GET** `/api/posts/list/{id}`: Recupera um postagem pelo ID.
- **POST** `/api/posts/create`: Cria um novo postagem.
- **PUT** `/api/posts/update/{id}`: Atualiza um postagem existente.
- **DELETE** `/api/posts/delete/{id}`: Remove um postagem pelo ID.

<!-- 
## Estrutura dos Dados
### Usuário

```json
```

### Postagem

```json
``` 
