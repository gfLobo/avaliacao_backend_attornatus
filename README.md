# Projeto Final - Desenvolvedor Back-end Attornatus
Este projeto foi desenvolvido como parte da avaliação da vaga. 

🧪Este é o endereço da interface de testes [Swagger](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/). 
Estes também poderão ser feitos diretamente pelo JUnit a partir da rota:

```
/src
    /test
        /java
            /com
                /attornatus
                    /avaliacaobackend
                        /br
                           ClienteTest.java
```


### Tecnologias utilizadas:

- Java

- Spring Boot

- API REST

- Banco de dados H2

- [ViaCep API](https://viacep.com.br)


`Obs: Para preencher o endereço, apenas preencha o número do local e o campo CEP com 8 dígitos numéricos válidos.`

`Obs: O username padrão é o "user" e a senha é gerada automaticamente no console da aplicação, durante a build.`


## Funcionalidades:

- Criar uma pessoa

- Editar uma pessoa

- Consultar uma pessoa

- Listar pessoas

- Criar endereço para pessoa

- Listar endereços da pessoa

- Informar qual endereço é o principal da pessoa



>### Cliente.java
>+ Nome
>+ Data de nascimento 
>
>>Endereço:
> >+ CEP
> >+ Número
> >+ Complemento
> >+ Bairro
> >+ Localidade
> >+ UF
> >+ DDD
> >+ Principal
