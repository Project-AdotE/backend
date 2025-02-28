# AdotE - Plataforma de Adoção de Animais

## Objetivo do Projeto
O objetivo do projeto é facilitar a adoção de animais, permitindo que quem deseja adotar tenha acesso a uma ampla variedade de animais disponíveis. Além disso, busca melhorar a divulgação do trabalho das ONGs, aumentando suas chances de encontrar lares para os animais.

## Funcionalidades Principais
- Listagem de animais disponíveis para adoção com filtros personalizados
- Página dedicada para cada ONG cadastrada
- Autenticação e autorização de usuários com JWT
- Upload e armazenamento de imagens na AWS S3
- Segurança reforçada com Spring Security e AWS Security Manager

## Tecnologias Utilizadas
### Backend
- **Java + Spring Boot**
- **Flyway** (migração de banco de dados)
- **Spring Security + JWT** (autenticação e autorização)
- **MapStruct** (mapeamento de entidades)
- **AWS RDS (PostgreSQL)** (banco de dados na nuvem)
- **AWS S3** (armazenamento de imagens)
- **AWS Security Manager** (gerenciamento seguro de senhas e segredos)

### Frontend
- **React**
- **Axios** (para requisições HTTP)

### Arquitetura
O projeto segue o conceito de **Clean Architecture**, garantindo:
- **Separação entre camadas Core e Infrastructure**
- **Independência do Core em relação à infraestrutura**
- **Ausência de anotações no Core**

## Como Executar o Projeto

### Pré-requisitos
- **Java 17+**
- **PostgreSQL (caso esteja rodando localmente)**
- **AWS CLI instalado**
- **Credenciais da AWS com permissão para acessar o AWS Secrets Manager**

---

### Passos
#### **1. Clone o repositório:**
```sh
 git clone https://github.com/FelipeWai/adoteApi.git
 cd adote
```

#### **2. Configure as credenciais da AWS**
Se você já tem a AWS CLI instalada, pode configurar as credenciais com:
```sh
 aws configure
```

Isso pedirar:
- **AWS Access Key ID**
- **AWS Secret Access Key**
- **Região padrão (ex: us-east-2, região que você estiver usando)**
- **Formato de saída (deixe padrão como JSON)**

💡 **Dica**: Caso você não sabe se possui estas credenciais cadastradas você pode digitar "aws configure list", 
isso irá retornar as credenciais caso existam

#### **3. Execute a aplicação**
```sh
 mvn spring-boot:run
```

---
Agora sua API está pronta para rodar localmente! 🚀



## Documentação da API
A API está documentada utilizando **Swagger**. Para acessar a documentação localmente, inicie a aplicação e acesse:
```
http://localhost:8080/swagger-ui.html
```

## Participantes do projeto
- Felipe Wai: [LinkedIn](https://www.linkedin.com/in/felipewai/)
- Ryan Ferreira: [LinkedIn](https://www.linkedin.com/in/ryanferreira26/)
- João Pedro O. M.: [LinkedIn](https://www.linkedin.com/in/joaopedroom/)
- Samuel Vinicius M.: [LinkedIn](https://www.linkedin.com/in/samuel-vinicius-martins-032927205/)
- Cainã Nuada: [LinkedIn](https://www.linkedin.com/in/cain%C3%A3-nuada-de-ara%C3%BAjo-magalh%C3%A3es-1a4871246/)
- Samuel Prado: [LinkedIn](https://www.linkedin.com/in/samuel-prado-489b051b5/)
---