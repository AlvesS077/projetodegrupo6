package projetoe6;

import java.util.ArrayList;

public abstract class Pedido {

    private static int proximoId = 1;

    private int id;
    private String dataHora;
    private EstadoPedido estado;
    private ArrayList<ItemPedido> itensPedido;

    public Pedido() {
        this.id = proximoId++;
        this.dataHora = new java.util.Date().toString();
        this.estado = EstadoPedido.PENDENTE;
        this.itensPedido = new ArrayList<ItemPedido>();
    }

    public int getId() {
        return id;
    }

    public String getDataHora() {
        return dataHora;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public void adicionarItem(ItemPedido item) {
        itensPedido.add(item);
    }

    public void listarItens() {
        if (itensPedido.isEmpty()) {
            System.out.println("  (sem itens)");
            return;
        }
        for (int i = 0; i < itensPedido.size(); i++) {
            System.out.println("  " + itensPedido.get(i));
        }
    }

    public ArrayList<ItemPedido> getItensPedido() {
        return itensPedido;
    }

    public double calcularTotal() {
        double total = 0;
        for (int i = 0; i < itensPedido.size(); i++) {
            total += itensPedido.get(i).getSubtotal();
        }
        return total;
    }

    public String toString() {
        return "Pedido #" + id + " | " + dataHora + " | Estado: " + estado + " | Total: " + calcularTotal() + "EUR";
    }
} //..