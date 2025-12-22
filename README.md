# 📒 Aurora Library
[![Linkedin Badge](https://img.shields.io/badge/-LinkedIn-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/seu-linkedin)](https://www.linkedin.com/in/Dev-Benicio/)
[![GitHub Badge](https://img.shields.io/badge/-GitHub-black?style=flat-square&logo=Github&logoColor=white&link=https://github.com/seu-github)](https://github.com/Dev-Benicio)
[![Gmail Badge](https://img.shields.io/badge/-Gmail-red?style=flat-square&logo=Gmail&logoColor=white&link=mailto:seu-email@gmail.com)](mailto:beniciosantos004@gmail.com)

> 💡 Sistema de Gerenciamento Bibliotecário desenvolvido com arquitetura REST que oferece controle robusto de empréstimos, cadastro de clientes e gestão completa do acervo. Implementa autenticação JWT para segurança, Swagger para a documentação das API, e utilizando boas práticas de desenvolvimento com Spring Boot.

---
<br>

## 📋 Índice
- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Funcionalidades](#-funcionalidades)
- [Como Executar](#-como-executar)
- [Licença](#-licença)
  
<br>
<br>

## 🚀 Sobre o Projeto
> O sistema oferece automação completa dos processos bibliotecários, garantindo controle preciso do acervo em tempo real, validação automática de CEP e CPF dos clientes e cálculo instantâneo de multas. Com uma API documentada e protegida por JWT, proporciona rastreabilidade total das operações, consultas eficientes através de paginação e redução significativa de erros operacionais. Esta solução integrada resulta em maior eficiência operacional e confiabilidade nos processos, beneficiando tanto a equipe da biblioteca quanto seus clientes.

### ✅ Qual problema ele resolve?
> O projeto foi desenvolvido para modernizar bibliotecas locais, transformando processos manuais em um sistema digital integrado e seguro. A plataforma centraliza o gerenciamento completo do acervo, empréstimos e cadastro de clientes, oferecendo uma solução profissional que otimiza as operações diárias de bibliotecas.

### 💡 Por que desenvolvi 
> Desenvolvi este projeto para aplicar conhecimentos avançados em desenvolvimento Java com Spring Boot, criando uma solução real que beneficia bibliotecas locais. O desafio de construir uma API REST completa, implementar segurança com JWT, documentação Swagger e integrações com serviços externos, como a API Brasil para realizar a validação CEP, permitiu aprofundar minhas habilidades técnicas enquanto entrego valor através de uma aplicação prática e útil para o mercado.

### 🎯 Público Alvo
> O sistema é direcionado para bibliotecas locais que buscam modernizar sua gestão através de uma solução digital profissional. Atende especificamente bibliotecários e administradores que necessitam de um sistema confiável para gerenciar empréstimos, controlar acervo e manter cadastro de clientes de forma eficiente e segura. É ideal para organizações que valorizam automação, precisão nos processos e desejam oferecer um serviço de qualidade aos seus frequentadores.
---
<br>

## 💻 Tecnologias Utilizadas
Este projeto foi desenvolvido utilizando as seguintes tecnologias:
- ![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=oracle&logoColor=white)
- ![Postman](https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=postman&logoColor=white)
- ![Spring Boot](https://img.shields.io/badge/-Spring%20Boot-6DB33F?style=flat-square&logo=spring&logoColor=white)
- ![Swagger](https://img.shields.io/badge/-Swagger-85EA2D?style=flat-square&logo=swagger&logoColor=black)
- ![MySQL](https://img.shields.io/badge/-MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)
- ![JWT](https://img.shields.io/badge/-JWT-000000?style=flat-square&logo=json-web-tokens&logoColor=white)
<br>

## ⚙ Funcionalidades
- 📚 Gerenciamento completo do acervo
- 📋 Controle de empréstimos e devoluções de livros
- 👥 Cadastro de clientes com validação automática de CPF e CEP
- ⏰ Controle de prazos de devolução
- 💰 Sistema automático de multas por atraso
- 🔍 Busca avançada de livros e clientes
- 🔒 Autenticação segura para funcionários
- ✅ Validação de regras de empréstimo
- 📚 Documentação
<br>

## 📚 Documentação da API
A aplicação conta com documentação automática via Swagger UI, facilitando a visualização e teste dos endpoints.

### 1. Visão Geral dos Recursos
![Visão Geral Swagger](https://github.com/user-attachments/assets/c70abf6b-17b0-458b-b1ba-cb99b935b8cd)

### 2. Exemplo de Requisição (Endpoints)
Detalhamento de uma requisição `GET` retornando a lista de livros com paginação simplificada e status **200 OK**:
![Detalhe JSON](https://github.com/user-attachments/assets/d54abca6-f27c-4a50-a611-8a06f43f2734)

<br>

# 🚀 Como Executar
Siga estes passos para executar o projeto localmente: 
<br>

### 1. 📝 Pré-requisitos
Antes de começar, você precisará ter instalado:
- Java 17
- Maven
- MySQL

<br>

### 2. Clone o repositório
````
git clone https://github.com/Dev-Benicio/aurora-library-api.git
````

#### Acesse o diretório
```
cd aurora-library-api
```

<br>

### 3. Configuração do Banco de Dados
#### No MySQL, execute:
```sql
CREATE DATABASE aurora_library;
```

<br>

### 4. Configuração de Variáveis
O projeto utiliza variáveis de ambiente para não expor senhas no código.

Localize o arquivo `.env.example` na raiz do projeto.

Renomeie este arquivo para .env.

Abra o arquivo e configure com suas credenciais do MySQL. Exemplo:

> DB_HOST=localhost <br>
> DB_PORT=3306 <br>
> DB_USERNAME=root <br>
> DB_PASSWORD=sua_senha_mysql <br>
> TOKEN_JWT=seu_token_secreto_123 <br>

<br>

### 5. Execute o Projeto

#### Instale as dependências
````
mvn clean install
````

#### Execute o projeto
````
java -jar target/aurora-library-1.0.jar
````

---
<br>

##  📚 Documentação da API
Com a aplicação rodando, acesse a documentação interativa para testar os endpoints:

> Interface de Swagger: [Documentação da API](http://localhost:8080/swagger-ui.html)

<br>

## 💻 Status do Projeto
![Status do Projeto](https://img.shields.io/badge/Status-Concluído-brightgreen)
<br>

## 📄 Licença
Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.


---
