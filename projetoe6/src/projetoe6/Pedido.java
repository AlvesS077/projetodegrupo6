package projetoe6;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
 
public abstract class Pedido {
 
    private int id;
    private LocalDateTime dataHora;
    private EstadoPedido estado;
    private ArrayList<ItemPedido> itensPedido;
 
    public Pedido(int id) {
        this.id = id;
        this.dataHora = LocalDateTime.now();
        this.estado = EstadoPedido.PENDENTE;
        this.itensPedido = new ArrayList<>();
    }
 
    public int getId() {
        return id;
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
 
    
 
    