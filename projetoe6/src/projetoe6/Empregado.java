package projetoe6;

public class Empregado extends Utilizador {

    public Empregado(String nome) {
        super(nome);
    }

    public void adicionarProdutoAoMenu(GerirPedidos gestor, Produto produto) {
        gestor.adicionarProduto(produto);
    }

    public String toString() {
        return "Empregado: " + getNome();
    }
}