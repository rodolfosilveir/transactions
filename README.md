# Transactions

`Microserviço responsável pelo gerenciamento de transações de contas`

# Pré Requisitos

Para que seja possível rodar essa aplicação é necessário atender alguns requisitos básicos.

- JDK 21 LTS
- Gradle 8.8+
- PostgreSQL 14+

# Arquivos de documentação

- Transactions.postman_collection.json (Com todas as requisições para a API)
- UML.jpg (Com a estrutura do banco de dados)
- Pismo_Back_Teste_3.0_(1).pdf (Teste recebido)

# Compilando para IDEAs como IntelliJ

Assim como todo projeto *Gradle*, é necessário primeiramente realizarmos a geração dos fontes. Conforme o exemplo abaixo:

```bash
./gradlew clean build
```

# Compilando e inicializando com Docker

Executar os comandos docker abaixo

```bash
docker compose build
docker compose up
```

# Documentação Swagger

É possivel acessar os endpoints disponiveis para visualização no seguinte endereço, depois que o container estiver rodando

http://localhost:8080/swagger-ui/index.html#/

Depois, na sessão 'Autenticação', executar a operação de Login, e com o token, utlizar no botão 'Authorize' no canto superior direito
Colar o token no campo 'Value', clica em 'Authorize' e depois 'Close'
A partir desse momento, qualquer operação da aplicação estará autenticada e poderá ser executada

# Executando e testando com Postman

Existe um arquivo postman 'Transactions.postman_collection.json' na raiz do projeto com todas as operações disponiveis
É necessario autenticar com o usuario admin que ja esta configurado no ambiente, pegar o token e setar nas demais requisições como Bearer

# cURLs de exemplo

- Login (Utilizar o token gerado no campo header Authorization substituindo ••••••)
```bash
curl --location 'http://localhost:8080/auth/login' \
--header 'Content-Type: application/json' \
--data '{
    "login": "admin",
    "password": "admin123"
}'
```
- Cadastrar Conta
```bash
curl --location 'http://localhost:8080/accounts' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLWFwaSIsInN1YiI6ImFkbWluIiwiZXhwIjoxNzM3OTI5MDg4fQ.BF9wtW0int7Wxsoqdxk-A8qn3b8ruY_U6YUVqEJnT5c' \
--data '{
  "document_number": "123456789"
}'
```

- Obter Contas
```bash
curl --location 'http://localhost:8080/accounts' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLWFwaSIsInN1YiI6ImFkbWluIiwiZXhwIjoxNzM3OTM3MzEzfQ.1Ln4Is5TIc2cAMkjSlu9As5rmmvQNFOyacEGxoXXhbs'
```