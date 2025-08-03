# 💰 Sistema de Gestão Bancária - API REST

Projeto desenvolvido como parte de um desafio técnico para a vaga de Desenvolvedor(a) Java Pleno na NG Billing.

Esta aplicação simula uma API bancária que permite criar contas e realizar transações financeiras (Pix, débito e crédito), aplicando regras de negócio com cálculo de taxas, validações de saldo e tratamento de erros.

---

## 🚀 Tecnologias e Ferramentas

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL 
- JUnit 5
- Mockito
- Maven

---

## 📚 Funcionalidades

### 📌 Endpoints disponíveis:

#### ➕ Criar Conta

- `POST /conta`
```json
{
  "numeroConta": 123,
  "saldo": 100.00
}
```

- Retorna `201 Created` com os dados da conta criada
- Retorna `409 Conflict` se a conta já existir
- Valida número da conta e saldo não negativos

---

#### 📥 Buscar Conta

- `GET /conta?numero_conta=123`

- Retorna `200 OK` com os dados da conta
- Retorna `404 Not Found` se a conta não existir

---

#### 💸 Realizar Transação

- `POST /transacao`
```json
{
  "formaPagamento": "D",  // P = Pix, D = Débito, C = Crédito
  "numeroConta": 123,
  "valor": 10.00
}
```

- Retorna `201 Created` com novo saldo
- Retorna `404 Not Found` se a conta não existir
- Retorna `422 Unprocessable Entity` se saldo for insuficiente
- Taxas aplicadas:
    - Débito: 3%
    - Crédito: 5%
    - Pix: Isento

---

## 🧪 Testes

Testes unitários foram implementados para os principais fluxos de negócio:

- `ContaServiceTest`: criação e validação de contas
- `TransacaoServiceTest`: transações com taxa, saldo e erros
- Cobertura de cenários positivos e negativos

---

## ⚙️ Como executar o projeto

### ✅ Com Docker e PostgreSQL

1. Clone o repositório
```bash
git clone https://github.com/banzak1/ngbilling_task.git
cd ngbilling_task
```

2. Suba o banco de dados com Docker Compose
```bash
docker-compose up -d
```

3. Execute a aplicação (via IDE ou terminal)
```bash
./mvnw spring-boot:run
```
