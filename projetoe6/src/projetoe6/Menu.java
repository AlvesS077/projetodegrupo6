<<<<<<< HEAD
package projetoe6;

import java.util.ArrayList;

// Armazena os produtos do menu e permite consulta-los
public class Menu {

    private ArrayList<Produto> produtos;

    public Menu() {
        this.produtos = new ArrayList<Produto>();
    }

    public void adicionarProduto(Produto p) {
        produtos.add(p);
    }

    // Mostra todos os produtos no ecra de forma direta
    public void mostrarMenuCompleto() {
        for (int i = 0; i < produtos.size(); i++) {
            Produto p = produtos.get(i);
            System.out.println(p.getId() + ". " + p.getNome() + " (" + p.getCategoria() + ") - " + p.getPreco() + "EUR");
        }
    }

    public Produto procurarProdutoPorId(int id) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId() == id) {
                return produtos.get(i);
            }
        }
        return null;
    }

    public String toString() {
        return "Menu com " + produtos.size() + " produtos.";
    }
} //
=======

>>>>>>> branch 'main' of https://github.com/AlvesS077/projetodegrupo6.git
