package projetoe6;

public class Produto {

    private int id;
    private String nome;
    private double preco;
    private String descricao;
    private boolean disponivel;
    private Categoria categoria;
    private int stock;

    public Produto(int id, String nome, double preco, String descricao, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.categoria = categoria;
        this.disponivel = true;
        this.stock = 10;
    }

    public Produto(int id, String nome, double preco, String descricao, Categoria categoria, int stock) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.categoria = categoria;
        this.stock = stock;
        this.disponivel = stock > 0;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public String getDescricao() { return descricao; }
    public boolean isDisponivel() { return disponivel; }
    public Categoria getCategoria() { return categoria; }
    public int getStock() { return stock; }

    public void setDisponivel(boolean d) {
        this.disponivel = d;
    }

    public void adicionarStock(int quantidade) {
        this.stock += quantidade;
        if (this.stock > 0) {
            this.disponivel = true;
        }
    }

    public void marcarIndisponivel() {
        this.disponivel = false;
        this.stock = 0;
    }

    @Override
    public String toString() {
        String estado = disponivel ? "disponivel" : "indisponivel";
        return "[" + id + "] " + nome + " - " + preco + "EUR (" + descricao + ") [" + estado + "] | Stock: " + stock;
    }
}