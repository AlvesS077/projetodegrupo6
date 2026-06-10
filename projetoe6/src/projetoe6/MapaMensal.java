package projetoe6;

import java.util.ArrayList;
import java.util.Calendar;

public class MapaMensal {

    // Filtra os pedidos concluidos no mes e ano indicados
    public static ArrayList<Pedido> filtrarPedidosMes(ArrayList<Pedido> todosPedidos, int mes, int ano) {
        ArrayList<Pedido> resultado = new ArrayList<Pedido>();
        for (int i = 0; i < todosPedidos.size(); i++) {
            Pedido p = todosPedidos.get(i);
            if (p.getEstado() == EstadoPedido.CONCLUIDO) {
                // Usa o Calendar para extrair mes e ano da data do pedido
                Calendar cal = Calendar.getInstance();
                cal.setTime(p.getDataHoraBruta());
                int mesPedido = cal.get(Calendar.MONTH) + 1; // Calendar.MONTH e 0-based
                int anoPedido = cal.get(Calendar.YEAR);
                if (mesPedido == mes && anoPedido == ano) {
                    resultado.add(p);
                }
            }
        }
        return resultado;
    }

    // Calcula o total de vendas (soma dos totais de todos os pedidos do mes)
    public static double calcularTotalVendas(ArrayList<Pedido> pedidosMes) {
        double total = 0;
        for (int i = 0; i < pedidosMes.size(); i++) {
            total += pedidosMes.get(i).calcularTotal();
        }
        return total;
    }

    // Devolve o nome do produto mais vendido (por quantidade total vendida)
    public static String produtoMaisVendido(ArrayList<Pedido> pedidosMes) {
        return encontrarProdutoPorQuantidade(pedidosMes, true);
    }

    // Devolve o nome do produto menos vendido (por quantidade total vendida)
    public static String produtoMenosVendido(ArrayList<Pedido> pedidosMes) {
        return encontrarProdutoPorQuantidade(pedidosMes, false);
    }

    // Devolve o nome do produto que gerou mais lucro (receita bruta)
    public static String produtoMaisLucro(ArrayList<Pedido> pedidosMes) {
        return encontrarProdutoPorLucro(pedidosMes, true);
    }

    // Devolve o nome do produto que gerou menos lucro (receita bruta)
    public static String produtoMenosLucro(ArrayList<Pedido> pedidosMes) {
        return encontrarProdutoPorLucro(pedidosMes, false);
    }

    // Devolve o nome e email do cliente que gastou mais no mes
    public static String melhorCliente(ArrayList<Pedido> pedidosMes) {
        if (pedidosMes.isEmpty()) return "Sem dados";

        // Recolher todos os nomes de cliente distintos
        ArrayList<String> nomes = new ArrayList<String>();
        for (int i = 0; i < pedidosMes.size(); i++) {
            String nome = pedidosMes.get(i).getNomeCliente();
            if (!nomes.contains(nome)) {
                nomes.add(nome);
            }
        }

        // Para cada cliente, somar o total gasto
        String melhor = "";
        double maiorGasto = -1;
        for (int i = 0; i < nomes.size(); i++) {
            String nome = nomes.get(i);
            double gasto = 0;
            for (int j = 0; j < pedidosMes.size(); j++) {
                if (pedidosMes.get(j).getNomeCliente().equals(nome)) {
                    gasto += pedidosMes.get(j).calcularTotal();
                }
            }
            if (gasto > maiorGasto) {
                maiorGasto = gasto;
                melhor = nome;
            }
        }
        return melhor + String.format(" (%.2f EUR)", maiorGasto);
    }

    // --- Metodos auxiliares privados ---

    private static String encontrarProdutoPorQuantidade(ArrayList<Pedido> pedidosMes, boolean querMaior) {
        if (pedidosMes.isEmpty()) return "Sem dados";

        ArrayList<String> nomesProdutos = new ArrayList<String>();
        ArrayList<Integer> quantidades = new ArrayList<Integer>();

        for (int i = 0; i < pedidosMes.size(); i++) {
            ArrayList<ItemPedido> itens = pedidosMes.get(i).getItensPedido();
            for (int j = 0; j < itens.size(); j++) {
                String nome = itens.get(j).getProduto().getNome();
                int qtd = itens.get(j).getQuantidade();
                int idx = nomesProdutos.indexOf(nome);
                if (idx == -1) {
                    nomesProdutos.add(nome);
                    quantidades.add(qtd);
                } else {
                    quantidades.set(idx, quantidades.get(idx) + qtd);
                }
            }
        }

        if (nomesProdutos.isEmpty()) return "Sem dados";

        int idxAlvo = 0;
        for (int i = 1; i < quantidades.size(); i++) {
            if (querMaior ? quantidades.get(i) > quantidades.get(idxAlvo)
                    : quantidades.get(i) < quantidades.get(idxAlvo)) {
                idxAlvo = i;
            }
        }
        return nomesProdutos.get(idxAlvo) + " (" + quantidades.get(idxAlvo) + " unid.)";
    }

    private static String encontrarProdutoPorLucro(ArrayList<Pedido> pedidosMes, boolean querMaior) {
        if (pedidosMes.isEmpty()) return "Sem dados";

        ArrayList<String> nomesProdutos = new ArrayList<String>();
        ArrayList<Double> lucros = new ArrayList<Double>();

        for (int i = 0; i < pedidosMes.size(); i++) {
            ArrayList<ItemPedido> itens = pedidosMes.get(i).getItensPedido();
            for (int j = 0; j < itens.size(); j++) {
                String nome = itens.get(j).getProduto().getNome();
                double subtotal = itens.get(j).getSubtotal();
                int idx = nomesProdutos.indexOf(nome);
                if (idx == -1) {
                    nomesProdutos.add(nome);
                    lucros.add(subtotal);
                } else {
                    lucros.set(idx, lucros.get(idx) + subtotal);
                }
            }
        }

        if (nomesProdutos.isEmpty()) return "Sem dados";

        int idxAlvo = 0;
        for (int i = 1; i < lucros.size(); i++) {
            if (querMaior ? lucros.get(i) > lucros.get(idxAlvo)
                    : lucros.get(i) < lucros.get(idxAlvo)) {
                idxAlvo = i;
            }
        }
        return nomesProdutos.get(idxAlvo) + String.format(" (%.2f EUR)", lucros.get(idxAlvo));
    }

    // Imprime o mapa mensal completo para o mes/ano pedidos
    public static void imprimirMapaMensal(ArrayList<Pedido> todosPedidos, int mes, int ano) {
        ArrayList<Pedido> pedidosMes = filtrarPedidosMes(todosPedidos, mes, ano);

        String[] nomesMeses = {
                "", "Janeiro", "Fevereiro", "Marco", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
        };
        String nomeMes = (mes >= 1 && mes <= 12) ? nomesMeses[mes] : "Mes " + mes;

        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║         MAPA MENSAL - " + nomeMes + " " + ano + "            ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        if (pedidosMes.isEmpty()) {
            System.out.println("  Nao existem pedidos concluidos neste mes.");
            return;
        }

        System.out.printf("  %-30s %d%n",    "Pedidos concluidos:",        pedidosMes.size());
        System.out.printf("  %-30s %.2f EUR%n", "Total de vendas:",         calcularTotalVendas(pedidosMes));
        System.out.println();
        System.out.printf("  %-30s %s%n",    "Produto mais vendido:",      produtoMaisVendido(pedidosMes));
        System.out.printf("  %-30s %s%n",    "Produto menos vendido:",     produtoMenosVendido(pedidosMes));
        System.out.println();
        System.out.printf("  %-30s %s%n",    "Produto com mais lucro:",    produtoMaisLucro(pedidosMes));
        System.out.printf("  %-30s %s%n",    "Produto com menos lucro:",   produtoMenosLucro(pedidosMes));
        System.out.println();
        System.out.printf("  %-30s %s%n",    "Melhor cliente do mes:",     melhorCliente(pedidosMes));
        System.out.println("════════════════════════════════════════════════");
    }
} //
