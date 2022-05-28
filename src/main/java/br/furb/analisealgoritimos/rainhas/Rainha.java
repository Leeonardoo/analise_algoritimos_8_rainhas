package br.furb.analisealgoritimos.rainhas;

/**
 * Queen.java
 * basedao no codigo de Timothy Budd
 */
public class Queen {
    private short linha;
    private final short coluna;
    private final Queen vizinho;

    public Queen(short c, Queen q) {
        coluna = c;
        linha = 1;
        vizinho = q;
    }

    public boolean achaSolucao() {
        while (vizinho != null && vizinho.podeAtacar(linha, coluna))
            if (!avanca())
                return false;
        return true;
    }

    public boolean avanca() {
        if (linha < 8) {
            linha++;
            return achaSolucao();
        }
        if (vizinho != null) {
            if (!vizinho.avanca())
                return false;
            if (!vizinho.achaSolucao())
                return false;
        } else
            return false;
        linha = 1;
        return achaSolucao();
    }

    private boolean podeAtacar(int tlin, int tcol) {
        int colDif = tcol - coluna;
        if ((linha == tlin) || (linha + colDif == tlin) ||
                (linha - colDif == tlin))
            return true;
        if (vizinho != null)
            return vizinho.podeAtacar(tlin, tcol);
        return false;
    }

    public String toString() {
        return ("(" + coluna + ", " + linha + ")");
    }

    public void imprime() {
        if (vizinho != null)
            vizinho.imprime();
        System.out.println(this);
    }
}
