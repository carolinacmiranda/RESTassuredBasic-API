# RestAssured Test Project

Este projeto é um exemplo de como utilizar a biblioteca **RestAssured** para testar APIs RESTful. Inclui testes de autenticação JWT, upload e download de arquivos, e validação de esquema JSON.

## Estrutura do Projeto

- **src/main/java**: Contém o código-fonte principal do projeto.
- **src/test/java**: Contém os testes unitários e de integração usando **RestAssured** e **JUnit**.
- **src/main/resources**: Contém arquivos de recursos, como esquemas JSON e arquivos para upload/download.

## Requisitos

Para rodar este projeto, você precisará do seguinte:

- **Java 8+**
- **Maven 3.6+**

## Dependências

O projeto utiliza as seguintes dependências principais:

- **JUnit 4.13.2**: Para a execução dos testes.
- **RestAssured 4.4.0**: Para facilitar a escrita de testes para APIs RESTful.
- **Json-Schema-Validator 5.0.0**: Para validar o esquema JSON das respostas da API.
- **Gson 2.10.1**: Para serialização/deserialização de objetos JSON.

## Configuração

Antes de rodar os testes, é necessário configurar o Maven para baixar as dependências:

```bash
mvn clean install
