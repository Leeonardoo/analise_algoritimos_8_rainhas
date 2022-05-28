package br.furb.analisealgoritimos.rainhas;

public class Main {

    public static void main(String[] args) {
        Rainha ultima;
        ultima = null;
        for (short i = 1; i <= 8; i++) {
            ultima = new Rainha(i, ultima);
            if (!ultima.achaSolucao()) {
                throw new RuntimeException();
            }
        }
        ultima.imprime();
    }
}
