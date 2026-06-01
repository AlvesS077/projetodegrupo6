package projetoe6;

public class Produto {
	 
	private int id;
    private String nome;
    private double preco;
    private String descricao;
    private boolean disponivel;
    private Categoria categoria;
 
    public Produto(int id, String nome, double preco, String descricao, boolean disponivel, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.disponivel = disponivel;
        this.categoria = categoria;
    }
 
    public String getNome() {
        return nome;
    }
 
    public double getPreco() {
        return preco;
    }
 
    public boolean isDisponivel() {
        return disponivel;
    }
 
    public void setDisponivel(boolean d) {
        this.disponivel = d;
    }
 
    public Categoria getCategoria() {
        return categoria;
    }
 
    public String toString() {
        return "Produto{id=" + id + ", nome='" + nome + "', preco=" + preco +
               ", categoria=" + categoria + ", disponivel=" + disponivel + "}";
    } /**
}
