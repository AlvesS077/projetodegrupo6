package projetoe6;

import java.util.ArrayList;

//Armazena e disponibiliza a lista de pedidos do sistema
public class GerirPedidos {

 private ArrayList<Pedido> pedidos;

 public GerirPedidos() {
     this.pedidos = new ArrayList<Pedido>();
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

 public String toString() {
     return "GerirPedidos com " + pedidos.size() + " pedidos.";
 }
}