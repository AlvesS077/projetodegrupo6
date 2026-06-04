package projetoe6;

// Classe abstrata base para Cliente e Empregado
public abstract class Utilizador {

    private String nome;

    public Utilizador(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public String toString() {
        return getClass().getSimpleName() + ": " + nome;
    }
}