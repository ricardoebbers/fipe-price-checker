# fipe-price-checker
Pequena API que permite cadastrar e consultar preços de veículos de acordo com a tabela FIPE

## Tecnologias utilizadas
* Java
* Kotlin
* Spring Boot
* Spring Data
* Postgres database
* Swagger UI (OpenAPI)
* Flyway
* Docker + Compose
* RabbitMQ

## Pré-requisitos
* Docker

## Configuração inicial
1. Clone o repositório
```git clone https://github.com/ricardoebbers/fipe-price-checker.git```
2. Acesse a pasta do projeto ```cd fipe-price-checker```
3. Levante os containers ```docker-compose up --build```

## Acesso a funcionalidades
1. Levante os containers se não estiverem levantados ```docker-compose up```
2. Acesse a documentação da API [aqui](http://localhost:8090/swagger-ui.html)
