package projetoe6;

import java.util.ArrayList;

public class Cliente extends Utilizador {

    private ArrayList<Pedido> listaPedidos;
    private String nomeApresentacao;

    public Cliente(String nome) {
        super(nome);
        this.listaPedidos = new ArrayList<Pedido>();
        this.nomeApresentacao = null;
    }

    public void adicionarPedido(Pedido p) {
        listaPedidos.add(p);
    }

    public ArrayList<Pedido> getListaPedidos() {
        return listaPedidos;
    }

    public String getNomeApresentacao() {
        return nomeApresentacao;
    }

    public void setNomeApresentacao(String nome) {
        this.nomeApresentacao = nome;
    }

    public String toString() {
        String nome = (nomeApresentacao != null) ? nomeApresentacao : getNome();
        return "Cliente: " + nome + " | Pedidos: " + listaPedidos.size();
    }
}