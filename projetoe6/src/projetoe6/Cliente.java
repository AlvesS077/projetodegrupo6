package projetoe6;

import java.util.ArrayList;

public class Cliente extends Utilizador {

    private ArrayList<Pedido> listaPedidos;

    public Cliente(String nome) {
        super(nome);
        this.listaPedidos = new ArrayList<Pedido>();
    }

    public void adicionarPedido(Pedido p) {
        listaPedidos.add(p);
    }

    public ArrayList<Pedido> getListaPedidos() {
        return listaPedidos;
    }

    public String toString() {
        return "Cliente: " + getNome() + " | Pedidos: " + listaPedidos.size();
    }
}