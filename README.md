# Restaurante Service API

API RESTful sendo desenvolvida para a orquestração e gerenciamento do fluxo de atendimento, pedidos e cardápio de um restaurante. O projeto foi desenhado com foco em desacoplamento, separação de responsabilidades e integração com serviços externos.

## Arquitetura e Decisões de Design

O projeto segue uma **Arquitetura em Camadas (Layered Architecture)** clássica, mas com forte ênfase no isolamento do domínio. O fluxo da requisição foi estruturado para proteger a integridade dos dados e evitar o vazamento do modelo de banco de dados (Entidades) para as bordas da aplicação (Controllers/APIs).

### 1. Camada de Apresentação e Contratos (Controllers & DTOs)
Os Controllers atuam exclusivamente como portas de entrada (Endpoints HTTP). 
- **Data Transfer Objects (DTOs):** O modelo de domínio nunca é exposto diretamente. Utilizo DTOs específicos para operações de entrada (`RequestDTO`) e saída (`ResponseDTO`), prevenindo *Over-Posting* e garantindo que o contrato da API possa evoluir independentemente do esquema do banco de dados.

### 2. Mappers (Conversão de Objetos)
Para garantir a transição fluida e segura entre as camadas, o projeto implementa o padrão de **Mappers**.
- A responsabilidade de converter `Entity -> DTO` e `DTO -> Entity` é isolada em classes/componentes dedicados (Mappers). Isso mantém os Controllers e Services limpos, removendo código boilerplate de mapeamento manual e garantindo que a regra de conversão fique centralizada e facilmente testável.

### 3. Integração Externa (Spring Cloud OpenFeign)
A comunicação com APIs e microsserviços externos foi abstraída utilizando o **OpenFeign**.
- **Proxy Declarativo:** Em vez de usar clientes HTTP verbosos (como `RestTemplate` ou `WebClient`), o Feign é utilizado para criar interfaces declarativas. Isso reduz o boilerplate técnico, tratando serialização, deserialização e headers de forma transparente.
- **Isolamento de Falhas:** O uso do Feign prepara a aplicação para futuras implementações de resiliência (como *Circuit Breakers* ou tratamento de *Retries* em caso de instabilidade dos serviços externos).

### 4. Camada de Negócios (Services)
A camada de `Service` é o coração da aplicação. Ela não atua como um mero repassador (pass-through) para o Repository, mas sim como a orquestradora central das regras de negócio.
- **Orquestração e Atomicidade:** Métodos críticos de negócio são anotados com `@Transactional` para garantir o princípio ACID. Se o processo de fechamento de um pedido envolver salvar no banco local e notificar um serviço externo via Feign, qualquer falha garante o *rollback* do estado no banco.
- **Máquina de Estados de Pedidos:** A lógica de Service blinda as transições de estado. Um pedido possui validações rígidas: ele não pode pular de `CRIADO` para `FINALIZADO` sem passar por `EM_PREPARO`. A Service lança exceções de domínio customizadas caso transições inválidas sejam tentadas.
- **Validação de Invariantes:** Antes de qualquer persistência, a Service verifica as regras fundamentais (ex: disponibilidade de um item no estoque/cardápio antes de adicioná-lo à comanda da mesa).

## Stack Tecnológica

- **Java 17+**
- **Spring Boot 3** (Web, Data JPA, Validation)
- **Spring Cloud OpenFeign:** Para comunicação HTTP declarativa com APIs externas.
- **Mapeamento de Dados:** Mappers para segregação DTO/Entity.
- **Banco de Dados:** PostgreSQL (Mapeamento ORM via Hibernate).
- **Tratamento de Exceções:** `@RestControllerAdvice` e `Problem Details` para padronização de erros RFC 7807.

## Instruções de Execução

### Dependências Locais
Certifique-se de ter instalado:
- JDK 17
- Maven 3.8+
- Docker (para subir o contêiner do banco de dados)

### Setup e Inicialização

1. Realize o clone do projeto:
```bash
git clone [https://github.com/PedrodeAndradecf/Restaurante.git](https://github.com/PedrodeAndradecf/Restaurante.git)
cd Restaurante
