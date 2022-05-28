package br.furb.analisealgoritimos.rainhas;

/**
 * Queen.java
 * basedao no codigo de Timothy Budd
 */
public class Rainha {
    private int linha;
    private final int coluna;
    private final Rainha vizinha;

    public Rainha(int coluna, Rainha vizinha) {
        this.coluna = coluna;
        linha = 1;
        this.vizinha = vizinha;
    }

    public boolean achaSolucao() {
        while (vizinha != null && vizinha.temConflito(linha, coluna))
            if (!avanca())
                return false;
        return true;
    }

    public boolean avanca() {
        if (linha < 8) {
            linha++;
            return achaSolucao();
        }
        if (vizinha != null) {
            if (!vizinha.avanca())
                return false;
            if (!vizinha.achaSolucao())
                return false;
        } else
            return false;
        linha = 1;
        return achaSolucao();
    }

    private boolean temConflito(int tlin, int tcol) {
        int colDif = tcol - coluna;
        if ((linha == tlin) || (linha + colDif == tlin) ||
                (linha - colDif == tlin))
            return true;
        if (vizinha != null)
            return vizinha.temConflito(tlin, tcol);
        return false;
    }

    public String toString() {
        return ("[" + coluna + ", " + linha + "]");
    }

    public void imprime() {
        if (vizinha != null)
            vizinha.imprime();
        System.out.println(this);
    }
}
