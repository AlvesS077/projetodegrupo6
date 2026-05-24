package projetoe6;

public class ItemPedido {

    private Produto produto;
    private int quantidade;
    private String notas;

    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.notas = "";
    }

    public ItemPedido(Produto produto, int quantidade, String notas) {
        this(produto, quantidade);
        if (notas != null) {
            this.notas = notas;
        } else {
            this.notas = ""; // Se for nulo, fica vazio por segurança
        }
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

    public void setNotas(String n) {
        if (n != null) {
            this.notas = n;  
        } else {
            this.notas = ""; 
        }
    }

    public double getSubtotal() {
        return produto.getPreco() * quantidade;
    }

    @Override
    public String toString() {
        
        String linha = "  " + quantidade + "x " + produto.getNome() + " = " + getSubtotal() + "€";
        
        
        if (!notas.isEmpty()) {
            linha = linha + " [nota: " + notas + "]";
        }
        
        return linha;
    }
}	