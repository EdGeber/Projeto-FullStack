## Introdução



## Tecnologias

### Infra
- Docker

### Backend
- Java Spring Boot
- Maven
- MySQL

### Frontend
- Vue.js
- Vite.js
- Primevue
- Typescript
- Tailwind CSS


## Instruções para rodar

1. Crie um arquivo `.env` na raiz do projeto para definir variáveis de ambiente associadas à autenticação com o MySQL. Por conter informações sensíveis, esse arquivo não deve fazer parte do repositório, e por isso está no `.gitignore`. Um exemplo do conteúdo que precisa estar nesse arquivo é:
```
MYSQL_DATABASE=fornecimento
MYSQL_USER=usuario
MYSQL_PASSWORD=senha
MYSQL_ROOT_PASSWORD=senhasecreta
```
1. Inicie a Docker Engine.
2. Rode `docker-compose -f docker-compose.yaml up -d` e espere a build terminar.
3. Acesse `http://localhost:8080/` para usar o aplicativo (frontend).
4. Acesse `http://localhost:8081/` no browser para visualizar o banco de dados.
5. A API de acesso ao banco de dados pode ser acessada em `http://localhost:8082/`. Veja `api-server/src/main/java/projeto_fullstack/api/resource/` para descobrir quais são os endpoints.
6. Para modificar o código fonte, rebuildar e ver as mudanças, é necessário executar `docker-compose up` novamente indicando que as imagens devem ser recriadas: `docker-compose -f docker-compose.yaml up -d --build api frontend`. Não é necessário rodar `docker-compose down` previamente.
7. Ao terminar de usar, execute `docker-compose -f docker-compose.yaml down`.

## Possíveis pontos de melhoria
Para que o projeto esteja pronto para produção, ainda é necessário desenvolver os seguintes pontos:
- Configurar logging e monitoramento no servidor da API.
- Configurar autenticação e autorização com JWT no servidor da API (possivelmente escrevendo ou usando um outro servidor para autenticar e autorizar usuários da organização).
- Escrever uma documentação da API.
- Escrever testes unitários.
- Adicionar validações de dados de formulário também no frontend.
- Usar `nginx` para servir o frontend em vez de `http-server`.
