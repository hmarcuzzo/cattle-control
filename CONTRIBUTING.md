# Contribuir com o Cattle-Control

Cattle-Control segue a licenç do Apache 2.0. Se você gostaria de contribuir com alguma coisa, esse documento deve ser útil para você.

## Issues do GitHub
Utilizamos as issues do GitHub para localizar bugs e pontos para melhoramento.

Se estiver reportando um bug, por favor ajude a acelerar o diagnóstico do problema, fornecendo o máximo de informação possível. Idealmente, sugerimos que descreva passo a passo para reproduzirmos o problema.

## Convenções de Código
Nenhum destes é essencial para um pull request, mas todos eles irão ajudar.  Podem também ser acrescentado após o PR original, mas antes de um merge.

* Nós utilizamos para este projeto, como estrutura o padrão MVC.
* Certifique-se de que todos os novos arquivos `.java` tenham um comentário com pelomenos uma tag `@author` te idenficando, e pelo menos um parágrafo para descrever o que a classe faz.
* Adicione você mesmo com um `@author` para os novos arquivos `.java` que você modificar consideravelmente (quando as mudanças forem mais do que cosmética).
* Para todo novo método na camada de serviço é essencial que haja alguns testes para ele.
* Faça um fork do projeto e crie uma branch para o Pull Request que pretende fazer.
* Certifique-se de estar utilizando a versão mais recente do projeto na sua branch.
* Quando estiver escrevendo o commit, por favor, utilize [esta conveção](https://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html) como padrão, se estiver concertando o problema de uma issue já existe, por favor, não se esqueça de adicionar `[ISSUE-XXXX]` (`XXXX` é o número da issue)no começo da menssagem do commit e faça o link do pull request com a issue, caso o GutHub não o faça 
