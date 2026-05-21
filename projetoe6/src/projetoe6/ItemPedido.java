package projetoe6;

public class ItemPedido {
	private Produto produto;
    private int quantidade;
    private String notas;
 
    public ItemPedido(Produto produto, int quantidade, String notas) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.notas = notas;
    }
 
    public Produto getProduto() {
        return produto;
    }
 
    public int getQuantidade() {
        return quantidade;
    }
 
    public String getNotas() {
        return notas;
    }
 
    public void setNotas(String n) { /* pode ser corrigida/atualizada */
        this.notas = n;
    }
 
  
    public String toString() {
        return "ItemPedido{produto=" + produto.getNome() +
               ", quantidade=" + quantidade + ", notas='" + notas + "'}";
    }
}
 

