package projetoe6;

import java.util.ArrayList;
import java.util.Date;

public abstract class Pedido {

    private static int proximoId = 1;

    private int id;
    private Date dataHoraBruta;
    private EstadoPedido estado;
    private ArrayList<ItemPedido> itensPedido;
    private String nomeCliente;

    public Pedido() {
        this.id = proximoId++;
        this.dataHoraBruta = new Date();
        this.estado = EstadoPedido.PENDENTE;
        this.itensPedido = new ArrayList<ItemPedido>();
        this.nomeCliente = "Desconhecido";
    }

    public int getId() {
        return id;
    }

    // Mantido para compatibilidade - devolve a data formatada como String
    public String getDataHora() {
        return dataHoraBruta.toString();
    }

    // Novo metodo: devolve o objeto Date original (necessario para o MapaMensal)
    public Date getDataHoraBruta() {
        return dataHoraBruta;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nome) {
        this.nomeCliente = nome;
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
        return "Pedido #" + id + " | " + nomeCliente + " | " + getDataHora() + " | Estado: " + estado + " | Total: " + calcularTotal() + "EUR";
    }
}//
