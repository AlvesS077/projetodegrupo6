package projetoe6;

public class Produto {

    private int id;
    private String nome;
    private double preco;
    private String descricao;
    private boolean disponivel;
    private Categoria categoria;

    public Produto(int id, String nome, double preco, String descricao, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.categoria = categoria;
        this.disponivel = true;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
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

    @Override
    public String toString() {
        String estado;
        if (disponivel) {
            estado = "disponível";
        } else {
            estado = "indisponível";
        }
        
        // Junta tudo com o símbolo + de forma direta e simples
        return "[" + id + "] " + nome + " - " + preco + "€ (" + descricao + ") [" + estado + "]";
    }
}