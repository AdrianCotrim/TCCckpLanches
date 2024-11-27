# CKP Lanches

**CKP Lanches** é um **sistema personalizado** de gestão de pedidos, estoque e entregas, desenvolvido para otimizar a operação de lanchonetes e redes de fast-food. O sistema oferece uma interface intuitiva e funcional, dividida em **usuários comuns** e **administradores**, com funcionalidades específicas para cada perfil de acesso.

## Sumário

1. [Visão Geral](#visão-geral)
2. [Funcionalidades para Usuários Comuns](#funcionalidades-para-usuários-comuns)
3. [Funcionalidades para Administradores](#funcionalidades-para-administradores)
4. [Vantagens do Sistema Personalizado](#vantagens-do-sistema-personalizado)
5. [Screenshots](#screenshots)
6. [Como Executar o Projeto](#como-executar-o-projeto)

### Visão Geral

- **CKP Lanches** é um sistema feito sob medida para otimizar a gestão de pedidos e controle de estoque em lanchonetes.
- Possui interfaces dedicadas a **usuários comuns** e **administradores**, com funcionalidades adaptadas a diferentes níveis de permissão.
- O sistema inclui recursos de **controle de insumos**, **gestão de estoque**, **gestão de pedidos**, entre outros.

### Funcionalidades para Usuários Comuns

- **Consulta ao Estoque**: Verificação da quantidade disponível de itens.
- **Registro de Vendas**: Interface simples para registrar vendas realizadas.
- **Gestão de Pedidos**: Acompanhamento de pedidos feitos pelos clientes.
- **Gestão de Entregas**: Visualização do status das entregas e seus detalhes.

### Funcionalidades para Administradores

- **Gerenciamento Completo de Estoque**: Administração dos itens em estoque, inclusão e atualização de produtos.
- **Relatórios de Insumos**: Geração de relatórios detalhados sobre os insumos utilizados e estoque de matérias-primas.
  - **Insumos Abaixo da Média**: Identificação de itens que estão com níveis de consumo ou estoque abaixo da média esperada.
  - **Quantidade de Itens no Estoque**: Monitoramento da quantidade atual dos itens em estoque para garantir que a reposição seja feita de forma eficiente.
- **Gestão de Usuários e Fornecedores**: Controle de acesso de usuários do sistema e gerenciamento de fornecedores.
- **Administração de Lotes**: Controle de lotes de produtos, para garantir o fluxo correto de entradas e saídas.

### Vantagens do Sistema Personalizado

- **Adaptação às Necessidades do Negócio**: Funcionalidades e fluxos de trabalho feitos sob medida para o tipo de operação da lanchonete.
- **Segurança Avançada**: Proteção de dados sensíveis e autenticação via JWT.
- **Facilidade de Uso**: Interface intuitiva tanto para usuários quanto para administradores.
- **Escalabilidade**: O sistema foi desenvolvido com foco em crescimento, podendo ser facilmente adaptado para novas funcionalidades no futuro.

## Screenshots

### Tela de Login
![Tela de Login](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/img1.PNG?raw=true)

### **Telas de Usuário**
As telas para usuários comuns, que possuem permissões limitadas, incluem funcionalidades como consulta ao estoque e registro de vendas.

### Tela Principal Usuário
![Tela Principal Usuário](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/user/img2.jpg?raw=true)

### Tela de Pedidos
![Tela de Pedidos](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/user/img3.PNG?raw=true)

### Tela de Entregas
![Tela de Entregas](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/user/img4.PNG?raw=true)

### Tela de Estoque
![Tela de Estoque](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/user/img5.jpg?raw=true)

### Tela de Cardápio
![Tela de Cardápio](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/user/img6.PNG?raw=true)

### **Telas de Administrador**
As telas para administradores, que possuem permissões totais, incluem funcionalidades como gerenciamento completo de estoque, geração de relatórios e análise de desempenho financeiro.

### Tela Principal Administrador
![Tela Principal Administrador](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/admin/img7.jpg?raw=true)

### Tela de Pedidos
![Tela de Pedidos](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/admin/img8.PNG?raw=true)

### Tela de Entregas
![Tela de Entregas](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/admin/img9.jpg?raw=true)

### Tela de Estoque
![Tela de Estoque](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/admin/img10.PNG?raw=true)

### Tela de Histórico de Vendas
![Tela de Histórico de Vendas](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/admin/img11.PNG?raw=true)

### Tela de Cardápio
![Tela de Cardápio](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/admin/img12.PNG?raw=true)

### Tela de Administrar Usuários
![Tela de Administrar Usuários](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/admin/img13.PNG?raw=true)

### Tela de Fornecedores
![Tela de Fornecedores](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/admin/img14.PNG?raw=true)

### Tela de Lotes
![Tela de Lotes](https://github.com/LuisGustavoDev/TCCckpLanches/blob/main/screenshots/admin/img15.PNG?raw=true)

## Como Executar o Projeto

### Pré-requisitos
- **Java 21** ou superior
- **Maven** para construir o projeto
- **MySQL** ou outro banco de dados compatível configurado

### Instalação

1. **Clone o repositório**:
    ```bash
    git clone https://github.com/LuisGustavoDev/TCCckpLanches.git
    ```

2. **Navegue até o diretório do projeto**:
    ```bash
    cd TCCckpLanches
    ```

3. **Criação do Banco de Dados**:
    - Antes de iniciar o projeto, é necessário criar o banco de dados `ckp_lanches` no MySQL.
    - Execute o seguinte comando no MySQL para criar o banco de dados:
      ```sql
      CREATE DATABASE ckp_lanches;
      ```

4. **Configure o banco de dados MySQL**:
    - Certifique-se de que as credenciais de acesso (usuário e senha) no arquivo `application.properties` estão corretas.
    - Aqui estão as configurações que você deve ajustar, se necessário:

    ```properties
    # Configurações do Banco de Dados
    spring.datasource.url=jdbc:mysql://localhost:3306/ckp_lanches?useTimezone=true&serverTimezone=UTC
    spring.datasource.username=root
    spring.datasource.password=1234
    ```

5. **Configuração do `application.properties`**:
    O arquivo `application.properties` contém as configurações do projeto, como banco de dados, credenciais de acesso e configurações do JWT. Aqui estão algumas das propriedades que você precisa garantir que estão corretamente configuradas:

    ```properties
    # Configurações do Banco de Dados
    spring.datasource.url=jdbc:mysql://localhost:3306/ckp_lanches?useTimezone=true&serverTimezone=UTC
    spring.datasource.username=root
    spring.datasource.password=1234

    # Configurações do JPA (Hibernate)
    spring.jpa.hibernate.ddl-auto=none  # Define o comportamento de criação de banco de dados
    spring.jpa.show-sql=true  # Exibe as consultas SQL no console

    # Inicialização do Banco de Dados
    spring.datasource.initialization-mode=true
    spring.sql.init.mode=always

    # Configurações de Segurança JWT
    my-auth-secret-key=${JWT_SECRET:3dd97dc69115959749508d438d53e9ee}  # Chave secreta para autenticação JWT
    ```

    **Importante**: Substitua `root` e `1234` pelo seu nome de usuário e senha de banco de dados. A chave `JWT_SECRET` é usada para gerar tokens JWT e deve ser mantida secreta.

6. **Dependências**:
    O projeto utiliza as seguintes dependências essenciais:

    - **Spring Boot** (Starter Data JPA, Security, Web)
    - **MySQL Connector**: Para a conexão com o banco de dados MySQL
    - **Lombok**: Para reduzir boilerplate no código
    - **Spring Security**: Para autenticação e autorização
    - **JWT** (Java JWT): Para geração de tokens JWT
    - **Spring Boot DevTools**: Para facilitar o desenvolvimento

    As dependências estão definidas no arquivo `pom.xml` do Maven.

### Rodando o Projeto

Para rodar o projeto, execute o seguinte comando no terminal:

```bash
mvn spring-boot:run
```