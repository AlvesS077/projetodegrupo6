	package projetoe6;
	
	import java.util.Scanner;
	import java.util.ArrayList;
	
	public class Main {
	
	    public static void main(String[] args) {
	
	        Scanner sc = new Scanner(System.in);
	
	        // Criar o menu com os produtos
	        Menu menu = new Menu();
	        menu.adicionarProduto(new Produto(1, "Agua",          1.00, "50cl",               Categoria.BEBIDAS));
	        menu.adicionarProduto(new Produto(2, "Sumo Laranja",  2.50, "Natural 33cl",        Categoria.BEBIDAS));
	        menu.adicionarProduto(new Produto(3, "Coca-Cola",     2.00, "Lata 33cl",           Categoria.BEBIDAS));
	        menu.adicionarProduto(new Produto(4, "Tosta Mista",   3.50, "Fiambre e queijo",    Categoria.LANCHES));
	        menu.adicionarProduto(new Produto(5, "Sandes Frango", 4.00, "Grelhado com alface", Categoria.LANCHES));
	        menu.adicionarProduto(new Produto(6, "Croissant",     2.00, "Simples",             Categoria.LANCHES));
	        menu.adicionarProduto(new Produto(7, "Rissol",        1.50, "Camarao",             Categoria.PETISCOS));
	        menu.adicionarProduto(new Produto(8, "Croquete",      1.20, "Carne",               Categoria.PETISCOS));
	
	        GerirPedidos gestor = new GerirPedidos();
	
	        System.out.println("Bem-vindo ao sistema de pedidos!");
	        boolean continuar = true;
	
	        while (continuar) {
	            System.out.println("\n1. Fazer pedido");
	            System.out.println("2. Ver pedidos");
	            System.out.println("3. Avancar estado");
	            System.out.println("4. Sair");
	            System.out.print("Opcao: ");
	            
	         
	            int opcao = sc.nextInt(); 
	
	            if (opcao == 1) {
	                PedidoDigital pedido = new PedidoDigital();
	                boolean adicionarMais = true;
	
	                while (adicionarMais) {
	                    System.out.println("\n--- PRODUTOS DISPONIVEIS ---");
	                    menu.mostrarMenuCompleto();
	                    
	                    System.out.print("\nIntroduza o ID do produto: ");
	                    int idProd = sc.nextInt();
	                    Produto escolhido = menu.procurarProdutoPorId(idProd);
	
	                    if (escolhido != null) {
	                        System.out.print("Quantidade: ");
	                        int qtd = sc.nextInt();
	                        
	                        //tira o "Enter" do teclado antes de ler texto
	                        sc.nextLine(); 
	                        
	                        System.out.print("Notas: ");
	                        String notas = sc.nextLine();
	
	                        pedido.adicionarItem(new ItemPedido(escolhido, qtd, notas));
	                    } else {
	                        System.out.println("ID de produto invalido!");
	                    }
	
	                    System.out.print("Adicionar mais produtos? (s/n): ");
	                    String resposta = sc.next();
	                    //  verifica se o texto digitado e igual a "s"
	                    adicionarMais = resposta.equals("s"); 
	                }
	
	                gestor.adicionarPedido(pedido);
	                System.out.println("Pedido #" + pedido.getId() + " registado. Total: " + pedido.calcularTotal() + "EUR");
	
	                System.out.print("Confirmar pagamento imediato? (s/n): ");
	                String pagar = sc.next();
	                if (pagar.equals("s")) {
	                    pedido.confirmarPagamento();
	                }
	
	            } else if (opcao == 2) {
	                // --- VER PEDIDOS ---
	                ArrayList<Pedido> lista = gestor.listarPorOrdemChegada();
	                if (lista.isEmpty()) {
	                    System.out.println("Nenhum pedido em sistema.");
	                } else {
	                    for (int i = 0; i < lista.size(); i++) {
	                        System.out.println(lista.get(i));
	                    }
	                }
	
	            } else if (opcao == 3) {
	                // --- AVANCAR ESTADO ---
	                System.out.print("ID do pedido a alterar: ");
	                int id = sc.nextInt();
	                Pedido p = gestor.encontrarPedido(id);
	
	                if (p == null) {
	                    System.out.println("Pedido nao encontrado.");
	                } else if (p.getEstado() == EstadoPedido.PENDENTE) {
	                    p.setEstado(EstadoPedido.EM_PREPARACAO);
	                    System.out.println("Estado alterado para: EM_PREPARACAO");
	                } else if (p.getEstado() == EstadoPedido.EM_PREPARACAO) {
	                    p.setEstado(EstadoPedido.CONCLUIDO);
	                    System.out.println("Estado alterado para: CONCLUIDO");
	                } else {
	                    System.out.println("O pedido ja se encontra concluido.");
	                }
	
	            } else if (opcao == 4) {
	                continuar = false;
	                System.out.println("Ate breve!");
	            }
	        }
	        sc.close();
	    }
	} //.