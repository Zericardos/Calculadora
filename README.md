# Calculadora
Este projeto tem como objetivo aprender a linguagem Java por meio da criação de uma aplicação simples que realiza operações matemáticas básicas (soma, subtração, multiplicação e divisão). Com o tempo, a ideia é complexificar o projeto e explorar mais recursos da linguagem e boas práticas de desenvolvimento.
## Ambiente de Desenvolvimento
Para garantir um ambiente padronizado e facilitar a configuração para novos desenvolvedores, siga as instruções abaixo:
### Git Hooks
Instale os hooks do Git para automatizar verificações (como testes) antes de cada commit.
- Instale os Git hooks:
No Bash, execute:<br> `./install-hooks.sh`
### Build
- Utilize o Maven Wrapper para build e testes:
   - No Linux/macOS: <br>`./mvnw clean install`
   - No Windows: <br>`mvnw.cmd clean install`
### Testes
- Rodar todos os testes do projeto: `mvn test`
- Rodar todos os testes da classe Calculadora: `mvn -Dtest=CalculadoraTest test`
- Rodar testes específicos: `mvn -Dtest=CalculadoraTest#TESTE test`
### Padronização
Confira o arquivo `.editorconfig` para as regras de formatação.

## Documentação
- A documentação do código (Javadoc) é gerada automaticamente a partir dos comentários no código-fonte e pode ser visualizada no navegador;
- Gerada em *target/reports/apidocs/index.html*
- Geração Normal: `mvn javadoc:javadoc`
- Para limpar o diretório **target** e forçar a geração `mvn clean javadoc:javadoc -Dmaven.javadoc.skip=false`
- Se necessário, forçar atualização (limpando o cache): `mvn clean javadoc:javadoc -U`
