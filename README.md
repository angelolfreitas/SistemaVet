# Sistema de Gestão de Cuidados e Prontuário Veterinário

Backend em Java/Spring Boot para uma clínica veterinária, desenvolvido como projeto final da disciplina de **Banco de Dados** — Bacharelado em Engenharia da Computação, **UEMA** — sob orientação do Prof. Luís Carlos Costa Fonseca.

> ⚠️ Ajuste as seções marcadas com 🔧 para refletir exatamente a configuração real do projeto (versões, nome do pacote, porta, etc.).

## Sobre o projeto

O sistema gerencia os fluxos operacionais e clínicos de uma clínica veterinária: cadastro de usuários (tutores, veterinários e recepcionistas), pets, atendimentos, receitas e medicamentos, consolidando o histórico clínico de cada animal em ordem cronológica.

## Equipe

- Angelo Antônio Barbosa Luz Freitas
- João Vitor Costa Leite Virgínio da Silva
- Paulo Vitor Fontes do Nascimento

## Tecnologias 🔧

- Java 17+
- Spring Boot 3.x
- Spring Data JPA / Hibernate
- MySQL 8+
- Maven
## Como Rodar Pelo Docker

```bash
# 1. Crie um arquivo .env com essas credenciais
DB_USER=teuUsuarioDobanco
DB_PASSWORD=tuaSenhaDoBanco
ALGORITHM_SECRET=senhaArbitrariaParaEncriptar
NAME=usuarioParaAdmin
PASSWORD=senhaParaAdmin
# estamos usando o aiven para conectar o banco 
# em nuvem, então crie uma conta ou contate um 
# administrador do projeto para pedir acessso
# 2. instancie o container pelo docker
docker run --env-file .env -p 8080:8080 angelofr/vet:api
# 3. ao rodar a aplicação, veja nossas requisições pelo swagger:
http://localhost:8080/swagger-ui/index.html#/auth-controller/update
```

## Modelo de dados

O banco `clinica_veterinaria` é composto pelas tabelas: `Endereco`, `Usuario`, `tutor`, `veterinario`, `Recepcionista`, `pets`, `tutor_has_pets`, `Atendimento`, `receita`, `item_receita` e `medicamento`. O modelo entidade-relacionamento completo está em [`docs/modelo-er.png`](docs/modelo-er.png) *(ajuste o caminho conforme onde o arquivo estiver no repositório)*.

- `schema.sql` — script DDL de criação do banco
- `dados.sql` — massa de dados de teste para demonstrar os relatórios

## Como executar 🔧

```bash
# 1. Clone o repositório
git clone https://github.com/angelolfreitas/SistemaVet.git
cd SistemaVet

# 2. Crie o banco e rode os scripts
mysql -u root -p < schema.sql
mysql -u root -p clinica_veterinaria < dados.sql

# 3. Configure as credenciais (NÃO versionadas)
# crie src/main/resources/application-local.properties com:
#   spring.datasource.url=jdbc:mysql://localhost:3306/clinica_veterinaria
#   spring.datasource.username=SEU_USUARIO
#   spring.datasource.password=SUA_SENHA
#   spring.jpa.hibernate.ddl-auto=validate

# 4. Execute a aplicação
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080` por padrão.

## Endpoints principais 🔧

| Recurso | Verbos | Descrição |
|---|---|---|
| `/pets` | GET, GET/{id}, POST, PUT, PATCH, DELETE | CRUD de pets |
| `/atendimentos` | GET, GET/{id}, POST, PUT, PATCH, DELETE | CRUD de atendimentos |
| `/medicamentos` | GET, GET/{id}, POST, PUT, PATCH, DELETE | CRUD de medicamentos |
| `/receitas` | GET, GET/{id}, POST, PUT, PATCH, DELETE | CRUD de receitas |
| `/tutores` | GET, GET/{id}, POST, PUT, PATCH, DELETE | CRUD de tutores |


## Relatórios (consultas SQL nativas)

| Endpoint | Nível | Descrição |
|---|---|---|
| `GET /pets/relatorio/historico-clinico/{idPet}` | 1 | Histórico clínico completo do pet, mais recente primeiro |
| `GET /veterinarios/relatorio/total-atendimentos` | 1 | Quantidade de atendimentos por veterinário |
| `GET /pets/relatorio/sem-atendimento` | 2 | Pets que nunca passaram por consulta |
| `GET /atendimentos/relatorio/multiplos-medicamentos` | 2 | Atendimentos cuja receita tem mais de 1 medicamento |
| `GET /veterinarios/relatorio/top-medicamentos` | 3 | Top 3 medicamentos mais prescritos por veterinário |
| `GET /pets/relatorio/evolucao-peso/{idPet}` | 3 | Evolução do peso do pet entre atendimentos |

Detalhes das consultas SQL em [`docs/consultas-sql-relatorios.md`](docs/consultas-sql-relatorios.md).

## Licença

Trabalho acadêmico — Disciplina de Banco de Dados, UEMA, 2026.
