package projetoe6;

import java.util.ArrayList;

public class GerirPedidos {

    private ArrayList<Pedido> pedidos;
    private ArrayList<Utilizador> utilizadoresRegistados;
    private ArrayList<Produto> produtos;

    public GerirPedidos() {
        this.pedidos = new ArrayList<Pedido>();
        this.utilizadoresRegistados = new ArrayList<Utilizador>();
        this.produtos = new ArrayList<Produto>();
    }

    public void registarUtilizador(Utilizador u) {
        utilizadoresRegistados.add(u);
    }

    public Utilizador encontrarUtilizadorPorNome(String nome) {
        for (int i = 0; i < utilizadoresRegistados.size(); i++) {
            if (utilizadoresRegistados.get(i).getNome().equalsIgnoreCase(nome)) {
                return utilizadoresRegistados.get(i);
            }
        }
        return null;
    }

    public Cliente encontrarClientePorNome(String nome) {
        Utilizador u = encontrarUtilizadorPorNome(nome);
        if (u instanceof Cliente) {
            return (Cliente) u;
        }
        return null;
    }

    public Empregado encontrarEmpregadoPorNome(String nome) {
        Utilizador u = encontrarUtilizadorPorNome(nome);
        if (u instanceof Empregado) {
            return (Empregado) u;
        }
        return null;
    }

    public void adicionarPedido(Pedido p) {
        pedidos.add(p);
    }

    public Pedido encontrarPedido(int id) {
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getId() == id) {
                return pedidos.get(i);
            }
        }
        return null;
    }

    public ArrayList<Pedido> listarPorOrdemChegada() {
        return pedidos;
    }

    public void concluirPedido(int id) {
        Pedido p = encontrarPedido(id);
        if (p != null) {
            p.setEstado(EstadoPedido.CONCLUIDO);
            // Decrementar o stock de cada produto do pedido
            for (int i = 0; i < p.getItensPedido().size(); i++) {
                ItemPedido item = p.getItensPedido().get(i);
                Produto prod = item.getProduto();
                prod.adicionarStock(-item.getQuantidade());
                System.out.println("Stock de '" + prod.getNome() + "' atualizado: " + prod.getStock() + " unidades.");
            }
            System.out.println("Pedido #" + id + " concluido.");
        } else {
            System.out.println("Pedido nao encontrado.");
        }
    }

    public int totalPedidosRealizados() {
        return pedidos.size();
    }

    public void adicionarProduto(Produto p) {
        produtos.add(p);
    }

    public void mostrarMenuCompleto() {
        for (int i = 0; i < produtos.size(); i++) {
            Produto p = produtos.get(i);
            System.out.println(p.getId() + ". " + p.getNome() + " (" + p.getCategoria() + ") - " + p.getPreco() + "EUR");
        }
    }

    public void mostrarMenuComStock() {

        System.out.println(String.format("%-4s %-20s %-12s %-8s %-10s %s",

                "ID", "Nome", "Categoria", "Preco", "Stock", "Estado"));

        System.out.println("--------------------------------------------------------------------");

        for (int i = 0; i < produtos.size(); i++) {

            Produto p = produtos.get(i);

            String estado = p.isDisponivel() ? "disponivel" : "INDISPONIVEL";

            System.out.println(String.format("%-4d %-20s %-12s %-8.2f %-10d %s",

                    p.getId(), p.getNome(), p.getCategoria(), p.getPreco(), p.getStock(), estado));
        }
    }

    public ArrayList<Produto> getTodosProdutos() {
        return produtos;
    }

    public Produto procurarProdutoPorId(int id) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId() == id) {
                return produtos.get(i);
            }
        }
        return null;
    }

    public String toString() {
        return "GerirPedidos | Pedidos: " + pedidos.size() +
                " | Utilizadores: " + utilizadoresRegistados.size() +
                " | Produtos: " + produtos.size();
    }
}