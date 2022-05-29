package br.furb.analisealgoritimos.rainhas;

/**
 * @author Larson Kremer Vicente
 * @author Leonardo de Oliveira
 */
public class Rainha {
    private int linha;
    private final int coluna;

    public Rainha(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void moverLinha() {
        linha++;
    }

    //Verifica se uma rainha faz conflito com uma outra (mesma linha, coluna ou diagonal)
    public boolean temConflito(Rainha r) {
        if (coluna == r.getColuna() || linha == r.getLinha()) {
            return true;
        } else {
            return Math.abs(coluna - r.getColuna()) == Math.abs(linha - r.getLinha());
        }
    }

    public static Rainha copiar(Rainha rainha) {
        return new Rainha(rainha.getLinha(), rainha.getColuna());
    }
}