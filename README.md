
# Restaurante API

API RESTful para gerenciamento do fluxo de pedidos, mesas e cardápio de um restaurante. 

Este projeto foi construído para aplicar conceitos sólidos de engenharia de software, modelagem de domínio e construção de APIs back-end utilizando o ecossistema Spring.

## 💻 Tecnologias e Ferramentas

- **Linguagem:** Java 17+
- **Framework:** Spring Boot 3 (Web, Data JPA, Validation)
- **Banco de Dados:** PostgreSQL (ou MySQL / H2)
- **Documentação:** SpringDoc OpenAPI (Swagger)
- **Testes:** JUnit 5 e Mockito
- **Gerenciador de Dependências:** Maven

## 🧠 Contexto de Negócio (Domínio)

A aplicação resolve problemas reais da operação de um restaurante, cobrindo os seguintes fluxos:

- **Cardápio:** Gerenciamento de itens consumíveis (cadastro, atualização de preços, disponibilidade).
- **Atendimento:** Abertura de mesas e vínculo de comandas aos clientes.
- **Ciclo de Vida do Pedido:** Controle de estado da cozinha. Um pedido transita entre status restritos (ex: `CRIADO` -> `EM_PREPARO` -> `PRONTO` -> `ENTREGUE`).
- **Faturamento:** Fechamento da mesa com cálculo automatizado do valor total consumido.

## ⚙️ Arquitetura e Boas Práticas

O código foi estruturado pensando em manutenibilidade e leitura fácil para outros desenvolvedores:

- **Arquitetura em Camadas (Layered):** Separação estrita entre `Controllers`, `Services` e `Repositories`. As regras de negócio ficam isoladas na camada de serviço.
- **Data Transfer Objects (DTO):** As entidades de banco de dados não são expostas nos endpoints. O uso de DTOs previne a vulnerabilidade de *Over-Posting* e controla o contrato da API.
- **Tratamento de Exceções Global:** Implementação de um `@RestControllerAdvice` para capturar exceções de negócio (ex: "Mesa já está ocupada" ou "Item indisponível") e retornar respostas HTTP padronizadas e limpas para o client.
- **Validações:** Uso de Bean Validation (Jakarta) para garantir a integridade dos dados de entrada antes mesmo de chegarem às regras de negócio.

## 🚀 Como Executar Localmente

### Pré-requisitos
- JDK 17 (ou superior) instalado
- Maven instalado
- Banco de dados rodando (se estiver usando Docker, basta executar o compose)

### Passos

1. Clone o repositório:
```bash
git clone [https://github.com/PedrodeAndradecf/Restaurante.git](https://github.com/PedrodeAndradecf/Restaurante.git)
cd Restaurante
