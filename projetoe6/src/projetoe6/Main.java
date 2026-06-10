package projetoe6;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;

public class Main {

    // Senha de acesso ao painel do empregado
    private static final String SENHA_EMPREGADO   = "empregado123";
    // Senha de acesso ao mapa mensal
    private static final String SENHA_MAPA_MENSAL = "bar2025";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        GerirPedidos gestor = new GerirPedidos();

        Empregado emp1 = new Empregado("gerente@gmail.com");
        gestor.registarUtilizador(emp1);

        gestor.registarUtilizador(new Cliente("cliente1@gmail.com"));
        gestor.registarUtilizador(new Cliente("manuel@gmail.com"));

        gestor.adicionarProduto(new Produto(1, "Agua",          1.00, "50cl",               Categoria.BEBIDAS));
        gestor.adicionarProduto(new Produto(2, "Sumo Laranja",  2.50, "Natural 33cl",        Categoria.BEBIDAS));
        gestor.adicionarProduto(new Produto(3, "Coca-Cola",     2.00, "Lata 33cl",           Categoria.BEBIDAS));
        gestor.adicionarProduto(new Produto(4, "Tosta Mista",   3.50, "Fiambre e queijo",    Categoria.LANCHES));
        gestor.adicionarProduto(new Produto(5, "Sandes Frango", 4.00, "Grelhado com alface", Categoria.LANCHES));

        System.out.println("Bem-vindo ao sistema de pedidos!");
        boolean continuarGeral = true;

        while (continuarGeral) {
            System.out.println("\n=================================");
            System.out.println("        LOGIN POR EMAIL          ");
            System.out.println("=================================");
            System.out.print("Introduza o seu E-mail (ou 'sair'): ");
            String emailIntroduzido = sc.next();
            sc.nextLine();

            if (emailIntroduzido.equalsIgnoreCase("sair")) {
                continuarGeral = false;
                System.out.println("Ate breve!");
                break;
            }

            Cliente clienteAutenticado = gestor.encontrarClientePorNome(emailIntroduzido);
            Empregado empregadoAutenticado = gestor.encontrarEmpregadoPorNome(emailIntroduzido);

            if (clienteAutenticado == null && empregadoAutenticado == null) {
                System.out.println("\n[REGISTO] E-mail nao reconhecido.");
                System.out.print("Como quer ser chamado? (introduza o seu nome): ");
                String nomeEscolhido = sc.nextLine().trim();

                if (nomeEscolhido.isEmpty()) {
                    nomeEscolhido = emailIntroduzido;
                }

                clienteAutenticado = new Cliente(emailIntroduzido);
                clienteAutenticado.setNomeApresentacao(nomeEscolhido);
                gestor.registarUtilizador(clienteAutenticado);
                System.out.println("Conta criada com sucesso! Bem-vindo(a), " + nomeEscolhido + "!");
            }

            // --- PERFIL CLIENTE ---
            if (clienteAutenticado != null) {
                String nomeApresentado = clienteAutenticado.getNomeApresentacao() != null
                        ? clienteAutenticado.getNomeApresentacao()
                        : clienteAutenticado.getNome();

                System.out.println("\n-> [SESSAO CLIENTE] Ligado como: " + nomeApresentado);
                PedidoDigital pedido = new PedidoDigital();
                boolean adicionarMais = true;

                while (adicionarMais) {
                    System.out.println("\n--- PRODUTOS DISPONIVEIS ---");
                    gestor.mostrarMenuCompleto();

                    System.out.print("\nIntroduza o ID do produto: ");
                    int idProd = sc.nextInt();
                    Produto escolhido = gestor.procurarProdutoPorId(idProd);

                    if (escolhido != null) {
                        if (!escolhido.isDisponivel()) {
                            System.out.println("Produto indisponivel de momento.");
                        } else {
                            System.out.print("Quantidade: ");
                            int qtd = sc.nextInt();
                            sc.nextLine();

                            if (qtd <= 0) {
                                System.out.println("Quantidade invalida.");
                            } else if (qtd > escolhido.getStock()) {
                                System.out.println("Stock insuficiente. Disponivel: " + escolhido.getStock());
                            } else {
                                System.out.print("Notas: ");
                                String notas = sc.nextLine();
                                pedido.adicionarItem(new ItemPedido(escolhido, qtd, notas));
                                System.out.println("Item adicionado ao pedido.");
                            }
                        }
                    } else {
                        System.out.println("ID de produto invalido!");
                        sc.nextLine();
                    }

                    System.out.print("Adicionar mais produtos? (s/n): ");
                    String resposta = sc.next();
                    adicionarMais = resposta.equalsIgnoreCase("s");
                }

                String nomeParaPedido = clienteAutenticado.getNomeApresentacao() != null
                        ? clienteAutenticado.getNomeApresentacao()
                        : clienteAutenticado.getNome();
                pedido.setNomeCliente(nomeParaPedido);

                System.out.println("\n--- RESUMO DO PEDIDO #" + pedido.getId() + " ---");
                pedido.listarItens();
                System.out.printf("Total a pagar: %.2f EUR%n", pedido.calcularTotal());
                System.out.print("Confirmar pagamento? (s/n): ");
                String confirmPag = sc.next();
                sc.nextLine();

                if (confirmPag.equalsIgnoreCase("s")) {
                    pedido.confirmarPagamento();
                    gestor.adicionarPedido(pedido);
                    clienteAutenticado.adicionarPedido(pedido);
                    System.out.println("Pedido #" + pedido.getId() + " registado com sucesso!");
                } else {
                    System.out.println("Pagamento cancelado. Pedido nao registado.");
                }

            } else if (empregadoAutenticado != null) {
                // --- AUTENTICACAO POR SENHA DO EMPREGADO ---
                boolean senhaCorreta = false;
                boolean voltarLogin = false;
                while (!senhaCorreta && !voltarLogin) {
                    System.out.println("\n--- ACESSO AO PAINEL DO EMPREGADO ---");
                    System.out.print("Introduza a senha de empregado: ");
                    String senhaEmp = sc.nextLine();

                    if (senhaEmp.equals(SENHA_EMPREGADO)) {
                        senhaCorreta = true;
                    } else {
                        System.out.println("\n[ERRO] Senha incorreta. Tente novamente.");
                        System.out.println("1. Tentar novamente");
                        System.out.println("2. Voltar ao login por email");
                        System.out.print("Opcao: ");
                        String opcaoErroEmp = sc.nextLine().trim();
                        if (opcaoErroEmp.equals("2")) {
                            voltarLogin = true;
                            System.out.println("A regressar ao login...");
                        }
                    }
                }

                // --- PERFIL GERENTE ---
                boolean sessaoEmpregado = senhaCorreta;
                while (sessaoEmpregado) {
                    System.out.println("\n-> [PAINEL GESTAO] Empregado: " + empregadoAutenticado.getNome());
                    System.out.println("1. Ver todos os pedidos do sistema");
                    System.out.println("2. Avancar estado de um pedido");
                    System.out.println("3. Adicionar novo produto ao menu");
                    System.out.println("4. Gerir stock");
                    System.out.println("5. Ver mapa mensal");   // <-- NOVA OPCAO
                    System.out.println("6. Fazer Logout");
                    System.out.print("Opcao: ");

                    int opcao = sc.nextInt();
                    sc.nextLine();

                    if (opcao == 1) {
                        ArrayList<Pedido> lista = gestor.listarPorOrdemChegada();
                        if (lista.isEmpty()) {
                            System.out.println("Nenhum pedido em sistema.");
                        } else {
                            System.out.println("\n--- MAPA GERAL DE PEDIDOS ---");
                            for (int i = 0; i < lista.size(); i++) {
                                Pedido p = lista.get(i);
                                System.out.println("Pedido ID: " + p.getId() + " | " + p.getNomeCliente() + " [" + p.getEstado() + "]");
                                p.listarItens();
                            }
                        }

                    } else if (opcao == 2) {
                        System.out.print("ID do pedido a alterar: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        Pedido p = gestor.encontrarPedido(id);

                        if (p == null) {
                            System.out.println("Pedido nao encontrado.");
                        } else if (p.getEstado() == EstadoPedido.PENDENTE) {
                            p.setEstado(EstadoPedido.EM_PREPARACAO);
                            System.out.println("Estado alterado para: EM_PREPARACAO");
                        } else if (p.getEstado() == EstadoPedido.EM_PREPARACAO) {
                            gestor.concluirPedido(id);
                        } else {
                            System.out.println("O pedido ja se encontra concluido.");
                        }

                    } else if (opcao == 3) {
                        System.out.println("\n--- INSERIR NOVO PRODUTO ---");
                        System.out.print("Introduza um ID unico numerico: ");
                        int novoId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Nome do produto: ");
                        String nomeProd = sc.nextLine();

                        System.out.print("Preco (ex: 3.50): ");
                        double precoProd = sc.nextDouble();
                        sc.nextLine();

                        System.out.print("Descricao/Detalhes: ");
                        String descProd = sc.nextLine();

                        System.out.println("Escolha a Categoria:");
                        System.out.println("1. BEBIDAS | 2. LANCHES | 3. PETISCOS");
                        System.out.print("Opcao: ");
                        int catOpcao = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Stock inicial (quantidade disponivel): ");
                        int stockInicial = sc.nextInt();
                        sc.nextLine();

                        Categoria catEscolhida = Categoria.LANCHES;
                        if (catOpcao == 1) catEscolhida = Categoria.BEBIDAS;
                        if (catOpcao == 3) catEscolhida = Categoria.PETISCOS;

                        Produto novoProduto = new Produto(novoId, nomeProd, precoProd, descProd, catEscolhida, stockInicial);
                        empregadoAutenticado.adicionarProdutoAoMenu(gestor, novoProduto);
                        System.out.println("Sucesso! O produto '" + nomeProd + "' foi adicionado com stock de " + stockInicial + ".");

                    } else if (opcao == 4) {
                        boolean gerirStock = true;
                        while (gerirStock) {
                            System.out.println("\n--- GERIR STOCK ---");
                            gestor.mostrarMenuComStock();
                            System.out.println("\n1. Adicionar stock a um produto");
                            System.out.println("2. Marcar produto como indisponivel");
                            System.out.println("3. Voltar ao menu principal");
                            System.out.print("Opcao: ");

                            int opcaoStock = sc.nextInt();
                            sc.nextLine();

                            if (opcaoStock == 1) {
                                System.out.print("ID do produto: ");
                                int idProd = sc.nextInt();
                                sc.nextLine();
                                Produto prod = gestor.procurarProdutoPorId(idProd);
                                if (prod == null) {
                                    System.out.println("Produto nao encontrado.");
                                } else {
                                    System.out.print("Quantidade a adicionar: ");
                                    int qtdAdicionar = sc.nextInt();
                                    sc.nextLine();
                                    prod.adicionarStock(qtdAdicionar);
                                    System.out.println("Stock de '" + prod.getNome() + "' atualizado para " + prod.getStock() + " unidades.");
                                }

                            } else if (opcaoStock == 2) {
                                System.out.print("ID do produto a marcar como indisponivel: ");
                                int idProd = sc.nextInt();
                                sc.nextLine();
                                Produto prod = gestor.procurarProdutoPorId(idProd);
                                if (prod == null) {
                                    System.out.println("Produto nao encontrado.");
                                } else {
                                    prod.marcarIndisponivel();
                                    System.out.println("Produto '" + prod.getNome() + "' marcado como indisponivel.");
                                }

                            } else if (opcaoStock == 3) {
                                gerirStock = false;
                            } else {
                                System.out.println("Opcao invalida.");
                            }
                        }

                    } else if (opcao == 5) {
                        // --- MAPA MENSAL COM AUTENTICACAO POR SENHA ---
                        boolean voltarMenuPrincipal = false;
                        while (!voltarMenuPrincipal) {
                            System.out.println("\n--- ACESSO AO MAPA MENSAL ---");
                            System.out.print("Introduza a senha de acesso: ");
                            String senhaIntroduzida = sc.nextLine();

                            if (senhaIntroduzida.equals(SENHA_MAPA_MENSAL)) {
                                // Senha correta: pedir mes e ano
                                Calendar agora = Calendar.getInstance();
                                int mesAtual = agora.get(Calendar.MONTH) + 1;
                                int anoAtual = agora.get(Calendar.YEAR);

                                System.out.print("Mes (1-12) [ENTER para mes atual " + mesAtual + "]: ");
                                String inputMes = sc.nextLine().trim();
                                int mesEscolhido = inputMes.isEmpty() ? mesAtual : Integer.parseInt(inputMes);

                                System.out.print("Ano [ENTER para ano atual " + anoAtual + "]: ");
                                String inputAno = sc.nextLine().trim();
                                int anoEscolhido = inputAno.isEmpty() ? anoAtual : Integer.parseInt(inputAno);

                                // Validar mes
                                if (mesEscolhido < 1 || mesEscolhido > 12) {
                                    System.out.println("Mes invalido. Tem de ser entre 1 e 12.");
                                } else {
                                    MapaMensal.imprimirMapaMensal(gestor.listarPorOrdemChegada(), mesEscolhido, anoEscolhido);
                                }
                                voltarMenuPrincipal = true; // Apos ver o mapa, volta ao menu

                            } else {
                                // Senha errada
                                System.out.println("\n[ERRO] Senha incorreta. Tente novamente.");
                                System.out.println("1. Tentar novamente");
                                System.out.println("2. Voltar ao menu");
                                System.out.print("Opcao: ");
                                String opcaoErro = sc.nextLine().trim();
                                if (opcaoErro.equals("2")) {
                                    voltarMenuPrincipal = true;
                                    System.out.println("A regressar ao menu...");
                                }
                                // Se escolher 1 (ou qualquer outra coisa), repete o loop e pede senha de novo
                            }
                        }

                    } else if (opcao == 6) {
                        sessaoEmpregado = false;
                        System.out.println("Logout efetuado.");
                    } else {
                        System.out.println("Opcao invalida.");
                    }
                }
            }
        }
        sc.close();
    }
}
