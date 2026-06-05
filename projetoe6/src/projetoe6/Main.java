package projetoe6;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        GerirPedidos gestor = new GerirPedidos();

        // E-mails pré-gravados de fábrica
        Empregado emp1 = new Empregado("gerente@gmail.com");
        gestor.registarUtilizador(emp1);

        gestor.registarUtilizador(new Cliente("cliente1@gmail.com"));
        gestor.registarUtilizador(new Cliente("manuel@gmail.com"));

        // Carregar os produtos iniciais no sistema
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
            sc.nextLine(); // Limpar buffer

            if (emailIntroduzido.equalsIgnoreCase("sair")) {
                continuarGeral = false;
                System.out.println("Ate breve!");
                break;
            }

            Cliente clienteAutenticado = gestor.encontrarClientePorNome(emailIntroduzido);
            Empregado empregadoAutenticado = gestor.encontrarEmpregadoPorNome(emailIntroduzido);

            if (clienteAutenticado == null && empregadoAutenticado == null) {
                System.out.println("\n[REGISTO] E-mail nao reconhecido. A armazenar novo Cliente...");
                clienteAutenticado = new Cliente(emailIntroduzido);
                gestor.registarUtilizador(clienteAutenticado);
                System.out.println("Conta armazenada com sucesso!");
            }

            // --- PERFIL CLIENTE ---
            if (clienteAutenticado != null) {
                System.out.println("\n-> [SESSÃO CLIENTE] Ligado como: " + clienteAutenticado.getNome());
                Pedido pedido = new Pedido() {};
                boolean adicionarMais = true;

                while (adicionarMais) {
                    System.out.println("\n--- PRODUTOS DISPONIVEIS ---");
                    gestor.mostrarMenuCompleto();

                    System.out.print("\nIntroduza o ID do produto: ");
                    int idProd = sc.nextInt();
                    Produto escolhido = gestor.procurarProdutoPorId(idProd);

                    if (escolhido != null) {
                        System.out.print("Quantidade: ");
                        int qtd = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Notas: ");
                        String notas = sc.nextLine();

                        pedido.adicionarItem(new ItemPedido(escolhido, qtd, notas));
                    } else {
                        System.out.println("ID de produto invalido!");
                    }

                    System.out.print("Adicionar mais produtos? (s/n): ");
                    String resposta = sc.next();
                    adicionarMais = resposta.equalsIgnoreCase("s");
                }

                gestor.adicionarPedido(pedido);
                clienteAutenticado.adicionarPedido(pedido);
                System.out.println("Pedido #" + pedido.getId() + " registado. Total: " + pedido.calcularTotal() + "EUR");

            } else if (empregadoAutenticado != null) {
                // --- PERFIL EMPREGADO (GERENTE) ---
                boolean sessaoEmpregado = true;
                while (sessaoEmpregado) {
                    System.out.println("\n-> [PAINEL GESTÃO] Empregado: " + empregadoAutenticado.getNome());
                    System.out.println("1. Ver todos os pedidos do sistema");
                    System.out.println("2. Avancar estado de um pedido");
                    System.out.println("3. ADICIONAR NOVO PRODUTO AO MENU"); // Nova Opção!
                    System.out.println("4. Fazer Logout");
                    System.out.print("Opcao: ");

                    int opcao = sc.nextInt();
                    sc.nextLine(); // Limpar buffer

                    if (opcao == 1) {
                        ArrayList<Pedido> lista = gestor.listarPorOrdemChegada();
                        if (lista.isEmpty()) {
                            System.out.println("Nenhum pedido em sistema.");
                        } else {
                            System.out.println("\n--- MAPA GERAL DE PEDIDOS ---");
                            for (int i = 0; i < lista.size(); i++) {
                                System.out.println("Pedido ID: " + lista.get(i).getId() + " [" + lista.get(i).getEstado() + "]");
                                lista.get(i).listarItens();
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
                        // --- FLUXO PARA ADICIONAR PRODUTO ---
                        System.out.println("\n--- INSERIR NOVO PRODUTO ---");
                        System.out.print("Introduza um ID unico numérico: ");
                        int novoId = sc.nextInt();
                        sc.nextLine(); // Limpar buffer

                        System.out.print("Nome do produto: ");
                        String nomeProd = sc.nextLine();

                        System.out.print("Preco (ex: 3,50): ");
                        double precoProd = sc.nextDouble();
                        sc.nextLine(); // Limpar buffer

                        System.out.print("Descricao/Detalhes: ");
                        String descProd = sc.nextLine();

                        System.out.println("Escolha a Categoria:");
                        System.out.println("1. BEBIDAS | 2. LANCHES | 3. PETISCOS");
                        System.out.print("Opcao: ");
                        int catOpcao = sc.nextInt();
                        sc.nextLine(); // Limpar buffer

                        Categoria catEscolhida = Categoria.LANCHES; // Categoria padrão caso falhe
                        if (catOpcao == 1) catEscolhida = Categoria.BEBIDAS;
                        if (catOpcao == 3) catEscolhida = Categoria.PETISCOS;

                        // Cria o novo produto
                        Produto novoProduto = new Produto(novoId, nomeProd, precoProd, descProd, catEscolhida);

                        // O Empregado adiciona o produto recorrendo ao método do seu diagrama!
                        empregadoAutenticado.adicionarProdutoAoMenu(gestor, novoProduto);

                        System.out.println("Sucesso! O produto '" + nomeProd + "' foi adicionado ao menu.");

                    } else if (opcao == 4) {
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