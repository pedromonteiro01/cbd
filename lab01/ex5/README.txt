Estruturas de dados utilizadas:
- Set
  - Foram criados dois sets
    - usersSet: contém todos os utlizadores
    - following: contém todos os utlizadores que são seguidos
- List
  - Foi criada uma lista, messages, que contém todas as mensagens de um utilizador
- Hash
  - Foi criado um hash, usersHash, que contém as informações de um utilizador, isto é, o nome e o nmec

Com estas estruturas de dados o sistema permite:
- Criar utilizadores (com nome  e nmec)
- Ver as informações dos utilizadores
- Listar todos os utilizadores
- Subscrever um utilizador
- Enviar mensagens
- Ver todas as mensagens dos utilizadores

Para compilar:
mvn package

Para correr:
mvn exec:java -Dexec.mainClass="cbd.ex5.Main"
