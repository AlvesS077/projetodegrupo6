package projetoe6;


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