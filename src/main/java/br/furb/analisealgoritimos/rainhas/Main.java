package br.furb.analisealgoritimos.rainhas;

import java.util.Random;
import java.util.Scanner;

/**
 * @author Larson Kremer Vicente
 * @author Leonardo de Oliveira
 * <p>
 * Implementação da solução do problema n-rainhas usando hill climbing
 * e reiniciando ao encontrar planícies.
 *
 * Os tabuleiros são gerados de forma aleatória.
 */
public class Main {
    private static final Random random = new Random();

    private static int n; //Número de rainhas
    private static int numeroConflitos = 0; //Heuristica

    //Informações sobre o resultado
    private static int totalPassos = 0; //Total de passos
    private static int totalReinicios = 0; //Número de reinícios (ao encontrar planícies)

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Digite o número de rainhas: ");
            n = scanner.nextInt();

            if (n != 2 && n != 3) {
                break;
            } else {
                System.out.printf("Nenhuma solução para %d rainhas.\n", n);
            }
        }

        Rainha[] estadoInicial = novoTabuleiro();
        Rainha[] tabuleiroSolucao = buscaSolucao(estadoInicial);

        //Solução
        System.out.println();
        System.out.println("Cenário inicial:");
        mostrarTabuleiro(estadoInicial);

        System.out.println();
        System.out.println("Solução:");
        mostrarTabuleiro(tabuleiroSolucao);

        System.out.println();
        System.out.printf("Número de reinícios: %d\n", totalReinicios);
        System.out.printf("Total de passos: %d\n", totalPassos);
    }

    public static Rainha[] novoTabuleiro() {
        Rainha[] tabuleiro = new Rainha[n];

        for (int i = 0; i < n; i++) {
            tabuleiro[i] = new Rainha(random.nextInt(n), i);
        }

        return tabuleiro;
    }

    //1 = rainha; 0 = vazio
    private static void mostrarTabuleiro(Rainha[] tabuleiro) {
        int[][] posicoes = new int[n][n];

        for (int i = 0; i < n; i++) {
            posicoes[tabuleiro[i].getLinha()][tabuleiro[i].getColuna()] = 1;
        }

        System.out.println();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(posicoes[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-".repeat((n * 4) - 1));
        }
    }

    public static int getConflitos(Rainha[] tabuleiro) {
        int conflitos = 0;

        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = i + 1; j < tabuleiro.length; j++) {
                if (tabuleiro[i].temConflito(tabuleiro[j])) {
                    conflitos++;
                }
            }
        }

        return conflitos;
    }

    public static Rainha[] minimizarConflitos(Rainha[] tabuleiroAtual) {
        Rainha[] melhorTabuleiro = new Rainha[n];
        Rainha[] auxTabuleiro = new Rainha[n];

        for (int i = 0; i < n; i++) {
            Rainha rainha = Rainha.copiar(tabuleiroAtual[i]);
            melhorTabuleiro[i] = rainha;
            auxTabuleiro[i] = rainha;
        }

        int conflitosAtuais = getConflitos(tabuleiroAtual);
        int menorConflitos = conflitosAtuais;
        int auxConflitos;

        for (int i = 0; i < n; i++) {
            if (i > 0) {
                auxTabuleiro[i - 1] = Rainha.copiar(tabuleiroAtual[i - 1]);
            }
            auxTabuleiro[i] = new Rainha(0, auxTabuleiro[i].getColuna());
            for (int j = 0; j < n; j++) {
                auxConflitos = getConflitos(auxTabuleiro);

                if (auxConflitos < menorConflitos) {
                    menorConflitos = auxConflitos;
                    for (int k = 0; k < n; k++) {
                        melhorTabuleiro[k] = Rainha.copiar(auxTabuleiro[k]);
                    }
                }

                if (auxTabuleiro[i].getLinha() != n - 1) {
                    auxTabuleiro[i].moverLinha();
                }
            }
        }

        //Se a heuristica (número de conflitos) for igual a anterior depois de passar
        //por todas as colunas, então geramos um novo tabuleiro pois chegamos em uma planície.
        if (menorConflitos == conflitosAtuais) {
            melhorTabuleiro = novoTabuleiro();
            numeroConflitos = getConflitos(melhorTabuleiro);
            totalReinicios++;
        } else {
            numeroConflitos = menorConflitos;
        }

        totalPassos++;
        return melhorTabuleiro;
    }

    public static Rainha[] buscaSolucao(Rainha[] tabuleiroInicial) {
        Rainha[] tabuleiroAtual = tabuleiroInicial;
        int conflitosAtuais;
        conflitosAtuais = getConflitos(tabuleiroAtual);

        //Reinicia até que nenhuma rainha se ataque
        while (conflitosAtuais != 0) {
            tabuleiroAtual = minimizarConflitos(tabuleiroAtual);
            conflitosAtuais = numeroConflitos;
        }

        return tabuleiroAtual;
    }
}