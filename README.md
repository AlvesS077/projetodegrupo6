# Sistema de Pedidos do Bar da Faculdade

Projeto de grupo desenvolvido em Java para gerir os pedidos do bar da faculdade. O sistema permite que clientes façam pedidos digitais e que empregados gerem o estado dos pedidos, o stock e consultem estatísticas mensais.

---

## Estrutura do Projeto

```
projetoe6/
├── Main.java
├── Utilizador.java
├── Cliente.java
├── Empregado.java
├── Pedido.java
├── PedidoDigital.java
├── ItemPedido.java
├── Produto.java
├── GerirPedidos.java
├── MapaMensal.java
├── Categoria.java
└── EstadoPedido.java
```

---

## Classes

| Classe | Descrição |
|---|---|
| `Main` | Ponto de entrada. Gere o loop de login e os menus de cliente e empregado |
| `Utilizador` | Classe abstrata base para `Cliente` e `Empregado` |
| `Cliente` | Utilizador que faz pedidos. Guarda histórico de pedidos e nome de apresentação |
| `Empregado` | Utilizador com acesso ao painel de gestão (protegido por senha) |
| `Pedido` | Classe abstrata que representa um pedido. Tem ID, data, estado, itens e nome do cliente |
| `PedidoDigital` | Extensão de `Pedido` com suporte a pagamento digital |
| `ItemPedido` | Representa um produto dentro de um pedido, com quantidade e notas opcionais |
| `Produto` | Produto do menu com ID, nome, preço, descrição, categoria, stock e disponibilidade |
| `GerirPedidos` | Classe central que gere pedidos, utilizadores e produtos |
| `MapaMensal` | Classe utilitária que calcula estatísticas de vendas de um determinado mês |
| `Categoria` | Enum com as categorias de produto: `BEBIDAS`, `LANCHES`, `PETISCOS` |
| `EstadoPedido` | Enum com os estados de um pedido: `PENDENTE`, `EM_PREPARACAO`, `CONCLUIDO` |

---

## Funcionalidades

### Login
- O sistema arranca com um ecrã de login por email
- Emails já registados autenticam diretamente
- Emails desconhecidos iniciam o registo automático como cliente
- Empregados têm uma camada extra de autenticação por senha antes de aceder ao painel

### Menu do Cliente
1. Visualizar produtos disponíveis (por ID e categoria)
2. Adicionar produtos ao pedido com quantidade e notas opcionais
3. Ver resumo do pedido com total a pagar
4. Confirmar ou cancelar o pagamento

### Menu do Empregado *(requer senha)*
1. Ver todos os pedidos do sistema com estado atual
2. Avançar o estado de um pedido (`PENDENTE` → `EM_PREPARACAO` → `CONCLUIDO`)
3. Adicionar novos produtos ao menu
4. Gerir stock (adicionar stock ou marcar produto como indisponível)
5. Ver mapa mensal *(requer senha adicional)*
6. Logout

### Mapa Mensal *(requer senha)*
Estatísticas dos pedidos **concluídos** num determinado mês e ano:
- Total de vendas (em EUR)
- Produto mais vendido e menos vendido (em unidades)
- Produto com mais lucro e com menos lucro (receita bruta em EUR)
- Melhor cliente do mês (quem gastou mais)

---

## Senhas

| Acesso | Senha padrão |
|---|---|
| Painel do empregado | `empregado123` |
| Mapa mensal | `bar2025` |

> As senhas estão definidas como constantes no topo da classe `Main.java` e podem ser alteradas diretamente no código.

---

## Gestão de Stock

- Por defeito, produtos criados sem stock explícito ficam com **10 unidades**
- Quando um pedido é **concluído**, o stock dos produtos é decrementado automaticamente
- Produtos com stock a zero ficam automaticamente marcados como **indisponíveis**
- O empregado pode adicionar stock ou marcar manualmente um produto como indisponível

---

## Fluxo de um Pedido

```
Cliente faz login
        ↓
Adiciona produtos ao pedido
        ↓
Confirma pagamento  →  Pedido fica PENDENTE
        ↓
Empregado avança estado  →  EM_PREPARACAO
        ↓
Empregado conclui  →  CONCLUIDO + stock decrementado
```

---

## Como Executar

1. Compilar todas as classes dentro da pasta `projetoe6/`:
   ```bash
   javac projetoe6/*.java
   ```
2. Executar a classe principal:
   ```bash
   java projetoe6.Main
   ```

---

## Tecnologias

- Java (sem dependências externas)
- Programação orientada a objetos (herança, polimorfismo, classes abstratas, enums)
# projetodegrupo6
