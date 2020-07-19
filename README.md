# fipe-price-checker
Pequena API que permite cadastrar e consultar preços de veículos de acordo com a tabela FIPE

## Tecnologias utilizadas
* Java
* Kotlin
* Spring Boot
* Spring Data
* H2 database
* Swagger UI (OpenAPI)
* Flyway

## Pré-requisitos
* Java 11

## Configuração inicial
1. Clone o repositório
```git clone https://github.com/ricardoebbers/fipe-price-checker.git```
2. Acesse a pasta do projeto ```cd fipe-price-checker```
3. Leia as variáveis de ambiente ```export $(cat .env | xargs)```
4. Rode os testes ```./gradlew test```

## Acesso a funcionalidades
1. Garanta que a porta 8080 está disponível
2. Levante o servidor com o gradle ```./gradlew bootRun```
3. Acesse a documentação da API [aqui](http://localhost:8080/swagger-ui.html)
