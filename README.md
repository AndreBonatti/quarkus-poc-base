# Project project-zero 

### Projeto microservice com conceito de reutilização, utilizando como base para próximos projetos.

* Camada de rest-resource path gravando bd (Utilizando busca paginada e queryparm através de mapeamento de classe) - Classes Cliente/ClienteService/ClienteResource


* Camada validação html injection (Interceptor de REST) - Classe HtmlInjection


* Camada validação token (Interceptor de REST) - Classe ValidaToken


* Camada de Auditoria (Interceptor de REST) - Classe AuditService


* Camada Exception - (Para isso utilizado conceito Handler) Criado Enum com as resposta padrões, Constants e criado Business exception. Utilizado um filtro para retorno de reposta no ApplicationExceptionHandler.


* Valiadação de Dados com Hibernate validator - Como conceito de camada exception tem um layout definido, capturado a exception de hibernate-validator e trasnformado no layout padrão. 


* Camada healthcheck (cpu, memoria, database, info)  "br/com/project/negociosdigitais/resource/health"


* Utilizar lombok para gerar - GET/SET/CONSTRUCTOR/BUILDER/TOSTRING/LOGGER


* Variaveis de ambiente para controle (Healtcheck, auditoria, html injection, token)


* Habilitado circuit braker quarkus findall paginado utilizando handler fallback https://www.youtube.com/watch?v=S7oYU8qBKIE


* Camada de teste unitário (Junit5/Mockito) - service


* Teste de integração Postman


* RestClient utizando o HTTPCLient java 11, para buscar informação api publica e armazenado no SimpleCache


* Habilitado Micrometer, gerador de métricas compatível com prometheus


#### Melhorias:

* Utilização de processos Async (processo reativos, processos thread)

* Utilização de Filas e Stream 

* Utilização de banco de dados NOSQL


## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/project-zero-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult ht
