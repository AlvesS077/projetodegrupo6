package projetoe6;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Inicializar o Gestor centralizado
        GerirPedidos gestor = new GerirPedidos();

        // APENAS o Empregado/Gerente é pré-registado no sistema
        Empregado emp1 = new Empregado("gerente@gmail.com");
        gestor.registarUtilizador(emp1);

        // Adicionar os produtos usando o método correto: adicionarProduto
        gestor.adicionarProduto(new Produto(1, "Agua",          1.00, "50cl",               Categoria.BEBIDAS));
        gestor.adicionarProduto(new Produto(2, "Sumo Laranja",  2.50, "Natural 33cl",        Categoria.BEBIDAS));
        gestor.adicionarProduto(new Produto(3, "Coca-Cola",     2.00, "Lata 33cl",           Categoria.BEBIDAS));
        gestor.adicionarProduto(new Produto(4, "Tosta Mista",   3.50, "Fiambre e queijo",    Categoria.LANCHES));
        gestor.adicionarProduto(new Produto(5, "Sandes Frango", 4.00, "Grelhado com alface", Categoria.LANCHES));
        gestor.adicionarProduto(new Produto(6, "Croissant",     2.00, "Simples",             Categoria.LANCHES));
        gestor.adicionarProduto(new Produto(7, "Rissol",        1.50, "Camarao",             Categoria.PETISCOS));
        gestor.adicionarProduto(new Produto(8, "Croquete",      1.20, "Carne",               Categoria.PETISCOS));

        System.out.println("Bem-vindo ao sistema de pedidos!");
        boolean continuarGeral = true;

        while (continuarGeral) {
            System.out.println("\n=================================");
            System.out.println("        LOGIN POR EMAIL          ");
            System.out.println("=================================");
            System.out.print("Introduza o seu E-mail (ou 'sair'): ");
            String emailIntroduzido = sc.next();
            sc.nextLine(); // Limpar o buffer do teclado

            if (emailIntroduzido.equalsIgnoreCase("sair")) {
                continuarGeral = false;
                System.out.println("Ate breve!");
                break;
            }

            // Pesquisa nas listas recorrendo aos métodos do GerirPedidos
            Cliente clienteAutenticado = gestor.encontrarClientePorNome(emailIntroduzido);
            Empregado empregadoAutenticado = gestor.encontrarEmpregadoPorNome(emailIntroduzido);

            // Se não for o gerente e não existir na lista, armazena o cliente automaticamente
            if (clienteAutenticado == null && empregadoAutenticado == null) {
                System.out.println("\n[REGISTO] E-mail nao reconhecido. A guardar novo Cliente no sistema...");
                clienteAutenticado = new Cliente(emailIntroduzido);
                gestor.registarUtilizador(clienteAutenticado); // Fica armazenado na lista do gestor
                System.out.println("Conta gravada com sucesso!");
            }

            // --- PERFIL CLIENTE (Fazer Pedido) ---
            if (clienteAutenticado != null) {
                System.out.println("\n-> [SESSÃO CLIENTE] Ligado como: " + clienteAutenticado.getNome());
                
                // Como Pedido é abstrato, criamos uma subclasse anónima concreta para evitar erros
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
                        sc.nextLine(); // Limpar buffer do Enter
                        
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
                clienteAutenticado.adicionarPedido(pedido); // Guarda também no histórico do cliente
                System.out.println("Pedido #" + pedido.getId() + " registado. Total: " + pedido.calcularTotal() + "EUR");

            } else if (empregadoAutenticado != null) {
                // --- PERFIL EMPREGADO (Ver Pedidos / Avançar Estado) ---
                boolean sessaoEmpregado = true;
                while (sessaoEmpregado) {
                    System.out.println("\n-> [PAINEL GESTÃO] Empregado: " + empregadoAutenticado.getNome());
                    System.out.println("1. Ver todos os pedidos do sistema");
                    System.out.println("2. Avancar estado de um pedido");
                    System.out.println("3. Fazer Logout");
                    System.out.print("Opcao: ");
                    
                    int opcao = sc.nextInt();
                    sc.nextLine(); // Limpar buffer

                    if (opcao == 1) {
                        ArrayList<Pedido> lista = gestor.listarPorOrdemChegada();
                        if (lista.isEmpty()) {
                            System.out.println("Nenhum pedido em sistema.");
                        } else {
                            System.out.println("\n--- MAPA DE PEDIDOS ---");
                            for (int i = 0; i < lista.size(); i++) {
                                System.out.println("Pedido ID: " + lista.get(i).getId() + " [" + lista.get(i).getEstado() + "]");
                                lista.get(i).listarItens();
                            }
                        }
                    } else if (opcao == 2) {
                        System.out.print("ID do pedido a alterar: ");
                        int id = sc.nextInt();
                        sc.nextLine(); // Limpar buffer
                        
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
                        sessaoEmpregado = false;
                        System.out.println("Efetuou logout.");
                    } else {
                        System.out.println("Opcao invalida.");
                    }
                }
            }
        }
        sc.close();
    }
}