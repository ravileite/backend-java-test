  ## Descrição do Projeto
  
   Trata-se de uma API REST que tem como objetivo ajudar os Rebeldes a combater o Império. Ela disponibiliza alguns serviços que podem
nos ajudar nessa luta, são eles: cadastrar novos rebeldes, atualizar localização de um rebelde, reportar um rebelde como um traidor e negociar itens entre dois rebeldes. 
  Lembrando que itens só podem ser adicionados ou retirados do inventário em negociações e traidores não podem negociar. Informações mais detalhadas podem ser obtidas nos comentários da classe RestController.java.
   Além disso alguns relatórios acerca dos rebeldes, traidores e recursos dos inventários também são disponibilizados.

## Tecnologias e Ferramentas Utilizadas 

  - Spring Boot;
  - Maven;
  - Hibernate (H2);
  
  - SpringToolSuite4;
  - Postman.

## Como executar

  Basta rodar a aplicação pelo SpringToolSuite4 (Versão do Eclipse para trabalhar com Spring Boot). Para acessar os dados basta abrir o banco de dados h2 através do endereço http://localhost:8080/h2-console. Podem ser feitas requisições pelo Postman utilizando o formato JSON.
