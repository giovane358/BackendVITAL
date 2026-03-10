VITAL — Emergency Management API
Descrição

API responsável pelo gerenciamento de ocorrências de emergência do sistema VITAL.

A API fornece autenticação de usuários, abertura de ocorrências, despacho de equipes, comunicação em tempo real e histórico de atendimentos.

O sistema foi desenvolvido como projeto acadêmico simulando o fluxo básico de atendimento emergencial.

Arquitetura

Arquitetura baseada em camadas:

Controller → Service → Repository → Database

Comunicação em tempo real utilizando WebSocket.

Tecnologias

Backend

Java 17

Spring Boot

Spring Security

JWT

Spring Data JPA

Flyway

WebSocket

Banco de dados

PostgreSQL

Docker

Ferramentas

Lombok

Maven

Estrutura do projeto

src/main/java/com/emergency/vital

config
controller
service
repository
entity
dto

resources

db/migration
application.yml

Dependências principais

Spring Web
Spring Security
Spring Data JPA
PostgreSQL Driver
Flyway Migration
WebSocket
Lombok

Endpoints
Autenticação

POST /auth/login
POST /auth/register
POST /auth/recover

Usuário

GET /users/profile
PUT /users/profile
GET /users/history

Ocorrências

POST /occurrences
GET /occurrences/{id}
GET /occurrences/active
GET /occurrences/history

PUT /occurrences/{id}/status

Central

GET /central/occurrences

PUT /central/occurrences/{id}/priority

PUT /central/occurrences/{id}/dispatch

Equipe

GET /team/occurrences

PUT /team/occurrences/{id}/accept

PUT /team/occurrences/{id}/status
