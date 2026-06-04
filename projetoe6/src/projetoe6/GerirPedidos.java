package projetoe6;

import java.util.ArrayList;

// Armazena e disponibiliza a lista de pedidos, utilizadores e produtos do sistema
public class GerirPedidos {

    private ArrayList<Pedido> pedidos;
    private ArrayList<Utilizador> utilizadoresRegistados;
    private ArrayList<Produto> produtos;

    public GerirPedidos() {
        this.pedidos = new ArrayList<Pedido>();
        this.utilizadoresRegistados = new ArrayList<Utilizador>();
        this.produtos = new ArrayList<Produto>();
    }

    // ---------- Gestao de Utilizadores ----------

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

    // ---------- Gestao de Pedidos ----------

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
            System.out.println("Pedido #" + id + " concluido.");
        } else {
            System.out.println("Pedido nao encontrado.");
        }
    }

    public int totalPedidosRealizados() {
        return pedidos.size();
    }

    // ---------- Gestao do Menu (antes na classe Menu) ----------

    public void adicionarProduto(Produto p) {
        produtos.add(p);
    }

    public void mostrarMenuCompleto() {
        for (int i = 0; i < produtos.size(); i++) {
            Produto p = produtos.get(i);
            System.out.println(p.getId() + ". " + p.getNome() + " (" + p.getCategoria() + ") - " + p.getPreco() + "EUR");
        }
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