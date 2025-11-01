

# Nome do Projeto: **BlueTech Ecommerce**

## Visão Geral

Este projeto visa construir uma plataforma de atendimento digital robusta e escalável, utilizando tecnologias modernas # BlueTech-Monolito-Modular
Criado para estudos
como Spring Boot (Java), MySQL, Docker e infraestrutura AWS. A plataforma permitirá o cadastro de usuários, um sistema de chat com bots para resolução de problemas de clientes, e será arquitetada em camadas pensando em alta disponibilidade e performance.

## 🚀 Como Iniciar o Projeto

Siga os passos abaixo para configurar e rodar o projeto em seu ambiente de desenvolvimento.

### Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas:

*   **Java Development Kit (JDK) 17 ou superior:**
    ```bash
    java -version
    # Exemplo de saída: openjdk version "17.0.x"
    ```
*   **Maven 3.x:**
    ```bash
    mvn -v
    # Exemplo de saída: Apache Maven 3.x.x
    ```
*   **MySQL Server 8.x:**
    *   Verifique se o serviço MySQL está rodando.
*   **Docker Desktop (Opcional para começar, mas recomendado para futuras etapas):**
    ```bash
    docker -v
    # Exemplo de saída: Docker version 2x.x.x
    ```
*   **Sua IDE preferida** (IntelliJ IDEA, VS Code com plugins Java, Eclipse).

### 1. Configuração do Banco de Dados MySQL

1.  **Acesse o terminal MySQL:**
    ```bash
    mysql -u root -p
    ```
    (Insira a senha do seu usuário `root` do MySQL)

2.  **Crie o banco de dados e um usuário específico para a aplicação:**
    ```sql
    CREATE DATABASE seuprojeto_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
    CREATE USER 'seuprojeto_user'@'localhost' IDENTIFIED BY 'sua_senha_segura';
    GRANT ALL PRIVILEGES ON seuprojeto_db.* TO 'seuprojeto_user'@'localhost';
    FLUSH PRIVILEGES;
    EXIT;
    ```
    *   Substitua `seuprojeto_db`, `seuprojeto_user` e `sua_senha_segura` pelos valores desejados.
    *   Certifique-se de que `sua_senha_segura` seja forte.

### 2. Clonar o Repositório

```bash
git clone https://github.com/Devwalis/BlueTech-Monolito-Modular.git
cd seu-repositorio
```


### 3. Configurar a Aplicação Spring Boot

1.  **Edite o arquivo `src/main/resources/application.properties`**
    Atualize as credenciais do banco de dados com as que você criou no Passo 1.

    ```properties
    # DataSource Configuration (MySQL)
    spring.datasource.url=jdbc:mysql://localhost:3306/seuprojeto_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    spring.datasource.username=seuprojeto_user
    spring.datasource.password=sua_senha_segura
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

    # JPA/Hibernate Configuration
    spring.jpa.hibernate.ddl-auto=update # Use 'update' para desenvolvimento. Para produção, considere 'none' e migrações.
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
    ```

### 4. Construir e Executar a Aplicação

1.  **Compile o projeto Maven:**
    ```bash
    mvn clean install
    ```

2.  **Execute a aplicação Spring Boot:**
    ```bash
    mvn spring-boot:run
    ```
    A aplicação estará disponível em `http://localhost:8080` (porta padrão do Spring Boot). Você verá logs no terminal indicando que o Hibernate criou ou atualizou as tabelas no seu banco de dados.

## 📁 Estrutura de Pastas (Packages)

A estrutura de pacotes segue uma arquitetura em camadas para organizar o código e separar as preocupações.

```
src/main/java
└── com
    └── seuprojeto
        └── userservice             # Exemplo para um User Service
            ├── config              # Configurações gerais da aplicação (WebSecurityConfig, CORS)
            ├── controller          # Camada de Apresentação/API (REST Controllers)
            │   └── dto             # Data Transfer Objects (Objetos para entrada/saída da API)
            ├── exception           # Classes de exceção personalizadas e handlers
            ├── model               # Camada de Domínio/Entidades (JPA Entities, classes de domínio)
            ├── repository          # Camada de Acesso a Dados (Spring Data JPA Repositories)
            ├── service             # Camada de Lógica de Negócios
            │   └── impl            # Implementações concretas dos serviços (opcional)
            └── security            # Classes relacionadas à segurança (JWT, UserDetails, etc.)
                └── jwt             # Específico para JWT
```

## 🏗️ Arquitetura e Infraestrutura

O projeto é projetado para ser escalável, resiliente e de fácil manutenção, seguindo os princípios de uma arquitetura baseada em microserviços (ou monolito modular) e utilizando a nuvem AWS.

### Visão Geral da Arquitetura

*   **Arquitetura em Camadas (Layered Architecture):**
    *   **Presentation Layer (Controllers/APIs):** Expõe as funcionalidades via APIs RESTful.
    *   **Service Layer (Business Logic):** Contém as regras de negócio e orquestração.
    *   **Repository Layer (Data Access):** Interage com o banco de dados.
    *   **Domain Layer (Models/Entities):** Representa as entidades de dados e suas regras intrínsecas.
*   **Microservices / Monolito Modular:** A aplicação pode começar como um monolito bem modularizado e evoluir para microservices distintos (Ex: `User Service`, `Chat Service`, `Notification Service`) conforme a necessidade de escalabilidade e desacomplamento.
*   **Stateless Services:** As aplicações serão stateless, facilitando a escalabilidade horizontal. O estado de sessão será gerenciado externamente.
*   **APIs RESTful:** Comunicação entre componentes e com o frontend via APIs RESTful bem definidas.

### Componentes de Infraestrutura (AWS)

```
+----------------+       +-------------------+       +-------------------+
|    Frontend    |------>| AWS Route 53 (DNS)|------>| AWS WAF / CloudFront|
| (Web/Mobile)   |       +-------------------+       +-------------------+
+----------------+                                             |
                                                               |
                                                 +-------------v------------+
                                                 | AWS ELB (Application LB) |
                                                 +--------------------------+
                                                               |
                     +--------------------------+      +-------+-------+
                     | AWS ElastiCache (Redis)  |<---->| AWS ECS/Fargate   |
                     |       (Cache)            |      |  (Docker Containers)|
                     +--------------------------+      |    Spring Boot    |
                                                       +---------+---------+
                                                                 |
                                                                 |
                                                 +--------------------------+
                                                 | AWS RDS (MySQL - Primary)|
                                                 |   (Database Replication) |
                                                 +------------+-------------+
                                                              |
                                                 +------------v-------------+
                                                 | AWS RDS (MySQL - Read Replica) |
                                                 +--------------------------+

                                                 +--------------------------+
                                                 | AWS SQS / SNS (Messaging)|
                                                 +--------------------------+
                                                 (Para comunicação assíncrona e eventos)

```

**Descrição dos Componentes:**

*   **AWS Route 53:** Serviço de DNS para roteamento de tráfego.
*   **AWS CloudFront / WAF:** CDN e Firewall de Aplicação Web para proteção e entrega de conteúdo.
*   **AWS Elastic Load Balancer (ELB):** Distribui o tráfego entre as instâncias da aplicação para garantir alta disponibilidade e escalabilidade.
*   **AWS ECS / Fargate:** Orquestração de containers Docker. Nossas aplicações Spring Boot serão empacotadas em containers Docker e executadas aqui. Fargate elimina a necessidade de gerenciar servidores.
*   **AWS RDS (MySQL):** Serviço de banco de dados relacional gerenciado.
    *   **Primary Instance:** Instância principal do MySQL.
    *   **Read Replicas:** Réplicas de leitura para escalar a capacidade de leitura do banco de dados e garantir alta disponibilidade.
    *   **Replicação de Banco de Dados:** Gerenciada pelo RDS para redundância e recuperação de desastres.
*   **AWS ElastiCache (Redis):** Serviço de cache distribuído em memória para reduzir a carga do banco de dados e melhorar a performance de requisições frequentes.
*   **AWS SQS / SNS:** Serviços de mensageria para comunicação assíncrona entre microserviços, processamento de filas e notificações. Utilizado para eventos de sistema, replicação de dados entre serviços, etc.
*   **AWS CloudWatch / Prometheus/Grafana:** Ferramentas para monitoramento e observabilidade da infraestrutura e aplicação (logs, métricas, alertas).
*   **AWS Secrets Manager / Parameter Store:** Para gerenciamento seguro de credenciais, chaves de API e outras configurações sensíveis.

## 📋 Engenharia de Requisitos

Abaixo estão os requisitos funcionais e não funcionais iniciais para o módulo de `Cadastro de Usuário`.

### Requisitos Funcionais (RF)

1.  **RF.001 - Cadastro de Usuário:**
    *   O sistema deve permitir que um novo usuário se cadastre fornecendo: Nome Completo, Email (único e válido), Senha (com requisitos mínimos de segurança).
    *   (Opcional): Telefone.
2.  **RF.002 - Autenticação de Usuário:**
    *   O sistema deve permitir que um usuário existente faça login usando seu email e senha.
3.  **RF.003 - Gerenciamento de Perfil:**
    *   O usuário deve poder visualizar e editar seu próprio nome e telefone.
4.  **RF.004 - Criptografia de Senha:**
    *   As senhas dos usuários devem ser armazenadas de forma criptografada (hashing + salting) utilizando BCrypt.
5.  **RF.005 - Persistência de Dados:**
    *   As informações do usuário (id, nome, email, senha criptografada, telefone) devem ser salvas em um banco de dados relacional (MySQL).

### Requisitos Não Funcionais (RNF)

1.  **RNF.001 - Segurança:**
    *   **RNF.001.01 - Senhas:** As senhas devem ser criptografadas usando um algoritmo forte (BCrypt).
    *   **RNF.001.02 - Autenticação:** A autenticação deve ser segura, utilizando tokens JWT.
    *   **RNF.001.03 - Acesso:** Acesso não autorizado a dados de usuário deve ser impedido por meio de controle de acesso baseado em papéis (RBAC).
2.  **RNF.002 - Escalabilidade:**
    *   O sistema deve ser capaz de escalar horizontalmente para lidar com um aumento no número de usuários e requisições (até X usuários simultâneos, Y requisições/segundo).
3.  **RNF.003 - Disponibilidade:**
    *   O sistema deve ter alta disponibilidade (99.9% de uptime) através de balanceamento de carga e réplicas de componentes.
4.  **RNF.004 - Performance:**
    *   As operações de cadastro e login devem ter tempos de resposta rápidos (ex: < 200ms para 95% das requisições).
    *   Consultas a dados de usuário devem ser otimizadas com cache.
5.  **RNF.005 - Manutenibilidade:**
    *   O código deve ser modular, bem documentado, testável e fácil de entender e modificar, seguindo princípios SOLID.
6.  **RNF.006 - Observabilidade:**
    *   O sistema deve fornecer logs detalhados, métricas de desempenho e health checks para monitoramento.
7.  **RNF.007 - Tolerância a Falhas:**
    *   O sistema deve ser resiliente a falhas de componentes individuais (ex: uma instância de aplicação ou uma réplica de banco de dados cair não deve derrubar o serviço).

---

Sinta-se à vontade para ajustar qualquer seção, adicionar mais detalhes específicos do seu projeto, ou incluir diagramas (com draw.io, mermaid, etc.) para a arquitetura na nuvem para torná-lo ainda mais visual e claro.

O que você achou? Quer adicionar algo ou detalhar alguma parte?