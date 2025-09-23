# DIO-Aplicacacao-financeira-Projeto
Software bancário simples com suas principais funcionalidades.

Sistema Bancário Simples
Este é um projeto de um sistema bancário simples, desenvolvido em Java e executado via console (CLI). A aplicação simula operações básicas de uma conta corrente e uma carteira de investimentos, como criação de contas, depósitos, saques, transferências via PIX e gestão de investimentos.

O objetivo principal é demonstrar conceitos de Programação Orientada a Objetos (POO), manipulação de coleções, tratamento de exceções e arquitetura em camadas (Model-Repository).

✨ Funcionalidades
O sistema oferece as seguintes operações através de um menu interativo:

**Contas:**

Criar uma nova conta com saldo inicial e chaves PIX.

Realizar depósitos em uma conta existente via PIX.

Efetuar saques de uma conta.

Transferir valores entre duas contas.

Consultar o extrato de transações de uma conta.

Listar todas as contas criadas.

**Investimentos:**

Criar um novo tipo de investimento (com taxa e valor de aplicação inicial).

Vincular uma conta a um investimento para criar uma "Carteira de Investimentos".

Depositar (investir) dinheiro da conta na carteira de investimentos.

Sacar (resgatar) dinheiro da carteira de investimentos para a conta.

Listar todos os tipos de investimentos e todas as carteiras de investimentos ativas.


# 🏛️ Estrutura do Projeto
O código-fonte está organizado nos seguintes pacotes:

**MODEL**
Pacote com as classes de domínio, que representam as entidades centrais do sistema.

Wallet: Classe abstrata que representa uma carteira genérica de fundos.

AccountWallet: Especialização de Wallet para uma conta corrente, gerenciando chaves PIX.

InvestimentWallet: Especialização de Wallet para uma carteira de investimentos, vinculada a uma AccountWallet.

Investment: Representa um tipo de investimento com suas regras (taxa, valor inicial).

Money: Representa a unidade monetária. Uma característica importante deste projeto é que o saldo é representado por uma lista de objetos Money, onde o tamanho da lista equivale ao saldo em centavos.

MoneyAudit: Registra o histórico de cada transação (ID, descrição, data).



**REPOSITORY**
Esta camada é responsável pelo acesso e manipulação dos dados. Como o projeto não utiliza um banco de dados, os dados são armazenados em memória (em ArrayLists) e perdidos ao final da execução.

AccountRepository: Gerencia a criação e as operações sobre as contas (AccountWallet).

InvestmentRepository: Gerencia os tipos de investimento e as carteiras (InvestimentWallet).

CommonsRepository: Contém métodos utilitários compartilhados pelos repositórios, como a verificação de saldo.


**EXCEPCTION**
Contém as exceções customizadas para tratar erros específicos da aplicação de forma clara.

AccountNotFoundException: Lançada quando uma conta não é encontrada.

NoFundsEnoughException: Lançada ao tentar realizar uma operação com saldo insuficiente.

PixUseException: Lançada quando uma chave PIX já está em uso.

E outras para casos específicos de investimentos e carteiras.


# 🚀 Como Executar o Projeto
Como este é um projeto Java puro, sem dependências externas, você pode compilá-lo e executá-lo diretamente pelo terminal.

Pré-requisitos:

JDK (versão 17 ou superior).

Passos:

Navegue até o diretório raiz do seu projeto, onde a pasta br está localizada.

Compile todos os arquivos .java com o seguinte comando:

Bash

# No Windows (usando ponto e vírgula como separador)
javac br\com\appbank\*.java br\com\appbank\model\*.java br\com\appbank\repository\*.java br\com\appbank\exception\*.java

# No Linux/macOS (usando dois pontos como separador)
javac br/com/appbank/*.java br/com/appbank/model/*.java br/com/appbank/repository/*.java br/com/appbank/exception/*.java
Se preferir, pode usar um comando mais genérico para compilar todos os arquivos recursivamente.

Execute a classe Main para iniciar a aplicação:

Bash

java br.com.appbank.Main
Após a execução, o menu interativo será exibido no console, e você poderá começar a usar o sistema.

# 💡 Exemplo de Uso
Execute a aplicação.

Escolha a opção 1 para Criar uma conta.

Informe um PIX (ex: 456789).

Informe um saldo inicial (ex: 50000 para R$ 500,00).

Escolha a opção 1 novamente para criar uma segunda conta.

Informe um PIX (ex: 12345678900).

Informe um saldo inicial (ex: 10000 para R$ 100,00).

Escolha a opção 9 para Listar contas e veja as duas contas criadas.

Escolha a opção 6 para Transferencia entre contas.

PIX de origem: 456789

PIX de destino: 12345678900

Valor: 5000 (para R$ 50,00).

Liste as contas novamente com a opção 9 para verificar que os saldos foram atualizados.

Escolha a opção 14 para Sair.

