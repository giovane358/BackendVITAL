# VITAL — Emergency Management API

API responsável pelo gerenciamento de ocorrências de emergência do sistema VITAL.

A API fornece autenticação de usuários, abertura de ocorrências, despacho de equipes, comunicação em tempo real e histórico de atendimentos.

O sistema foi desenvolvido como projeto acadêmico simulando o fluxo básico de atendimento emergencial.

---
## Arquitetura
Arquitetura baseada em camadas:

- Controller → Service → Repository → Database
- Comunicação em tempo real utilizando WebSocket.

### Tecnologias
- Java 17
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Flyway
- WebSocket

### Banco de dados
- SQL Service
- Docker

### Ferramentas
- Lombok
- Maven

---

## Estrutura do projeto

### src/main/java/com/emergency/vital
- config
- controller
- service
- repository
- entity
- dto

### resources
- db/migration
- application.yml

### Raiz do projeto
- .env

---

## Dependências principais

- Spring Web
- Spring Security
- Spring Data JPA
- SQL Service
- Flyway Migration
- WebSocket
- Lombok

---

## Endpoints

### Autenticação
- POST /auth/login
- POST /auth/register
- POST /auth/recover
---
### Usuário
- GET /users/profile
- PUT /users/profile
- GET /users/history
---
### Ocorrências
- POST /occurrences
- GET /occurrences/{id}
- GET /occurrences/active
- GET /occurrences/history
- PUT /occurrences/{id}/status
---
### Central
- GET /central/occurrences
- PUT /central/occurrences/{id}/priority
- PUT /central/occurrences/{id}/dispatch
---
### Equipe
- GET /team/occurrences
- PUT /team/occurrences/{id}/accept
- PUT /team/occurrences/{id}/status
---
# Como rodar o projeto
## 1 Clonar o repositório

```bash
git clone https://github.com/giovane358/BackendVITAL
cd vital
```
---
## 2 Configurar o .env

- Raiz do projeto -> New -> file -> .env
```bash
DB_HOST=
DB_NAME=
DB_USER=
DB_PASSWORD=
DB_PORT=
```
- Preencher com os dados do banco SQL Service
---
## 3 Configuração do Docker + SQL Service

### Subir banco:
```bash
docker compose up -d
```
---
### Conectar no banco
Use uma ferramenta:
- Azure Data Studio (mais leve)
- SQL Server Management Studio

```bash
- Server: (localhost,Port ou IP,port)
  Port: (Porta que colocou no docker)
  User: (Usuario que colocou no docker)
  Password: (senha que colocou no docker)
```
---
### Criar o banco do projeto
```bash
CREATE DATABASE nome_banco;
```
---
### Rodar backend:
```bash
mvn spring-boot:run
```
---
# Equipe
- Giovane Silva dos Santos - https://github.com/giovane358
- Luiz Gustavo Paz de Almeida - https://github.com/Mrluizgustavo
- Júlia Araujo de Souza  - https://github.com/julia1849
- Sthefany Malheiro Correia Gonçalves  - 