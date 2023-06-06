package simplex;

import java.util.ArrayList;
import java.util.List;

public class SimplexUtils2 {
    public static void calcularAlgoritmoSimplex(int qtdeVarDecisao, int qtdeRestricoes, List<Double> decisaoValores,
                                                int equivalenciaFO, ArrayList<ArrayList<Double>> restricoesValores,
                                                ArrayList<Integer> equivalenciasRestricoes) {
        int numVariaveis = qtdeVarDecisao;
        int numRestricoes = qtdeRestricoes;

        // Construir a tabela Simplex
        double[][] simplexTableau = new double[numRestricoes + 1][numVariaveis + numRestricoes + 1];

        // Preencher as primeiras colunas com os coeficientes das variáveis de decisão
        for (int i = 0; i < numRestricoes; i++) {
            for (int j = 0; j < numVariaveis; j++) {
                simplexTableau[i][j] = restricoesValores.get(i).get(j);
            }
        }

        // Preencher as colunas adicionais com as variáveis de folga
        for (int i = 0; i < numRestricoes; i++) {
            simplexTableau[i][numVariaveis + i] = 1;
        }

        // Preencher a última coluna com os valores da função objetivo
        for (int j = 0; j < numVariaveis; j++) {
            simplexTableau[numRestricoes][j] = decisaoValores.get(j);
        }

        // Adicionar a última linha com as restrições de igualdade ou desigualdade
        for (int i = 0; i < numRestricoes; i++) {
            simplexTableau[i][numVariaveis + numRestricoes] = equivalenciasRestricoes.get(i);
        }

        // Realizar os passos do algoritmo Simplex
        while (true) {
            // Encontrar a coluna pivô (menor valor negativo)
            int colunaPivo = encontrarColunaPivo(simplexTableau);
            if (colunaPivo == -1) {
                break; // Algoritmo concluído
            }

            // Encontrar a linha pivô (menor valor não negativo do quociente entre a coluna dos resultados e a coluna pivô)
            int linhaPivo = encontrarLinhaPivo(simplexTableau, colunaPivo);
            if (linhaPivo == -1) {
                throw new RuntimeException("Solução ilimitada!"); // Solução ilimitada
            }

            // Reduzir o pivô para 1
            double pivô = simplexTableau[linhaPivo][colunaPivo];
            for (int j = 0; j < numVariaveis + numRestricoes + 1; j++) {
                simplexTableau[linhaPivo][j] /= pivô;
            }

            // Zerar as outras entradas na coluna pivô
            for (int i = 0; i < numRestricoes + 1; i++) {
                if (i != linhaPivo) {
                    double fator = simplexTableau[i][colunaPivo];
                    for (int j = 0; j < numVariaveis + numRestricoes + 1; j++) {
                        simplexTableau[i][j] -= fator * simplexTableau[linhaPivo][j];
                    }
                }
            }
        }

        // Imprimir a solução
        for (int i = 0; i < numRestricoes; i++) {
            System.out.printf("X%d = %.2f\n", i + 1, simplexTableau[i][numVariaveis + numRestricoes]);
        }
        System.out.println("Z = " + simplexTableau[numRestricoes][numVariaveis + numRestricoes]);
    }

    private static int encontrarColunaPivo(double[][] tableau) {
        int numVariaveis = tableau[0].length - 1;
        int colunaPivo = -1;
        for (int j = 0; j < numVariaveis; j++) {
            if (tableau[tableau.length - 1][j] < 0) {
                if (colunaPivo == -1 || tableau[tableau.length - 1][j] < tableau[tableau.length - 1][colunaPivo]) {
                    colunaPivo = j;
                }
            }
        }
        return colunaPivo;
    }

    private static int encontrarLinhaPivo(double[][] tableau, int colunaPivo) {
        int numVariaveis = tableau[0].length - 1;
        int numRestricoes = tableau.length - 1;

        int linhaPivo = -1;
        for (int i = 0; i < numRestricoes; i++) {
            if (tableau[i][colunaPivo] > 0) {
                if (linhaPivo == -1 || tableau[i][numVariaveis + numRestricoes] / tableau[i][colunaPivo] <
                        tableau[linhaPivo][numVariaveis + numRestricoes] / tableau[linhaPivo][colunaPivo]) {
                    linhaPivo = i;
                }
            }
        }
        return linhaPivo;
    }

    public static void main(String[] args) {
        int qtdeVarDecisao = 2;
        int qtdeRestricoes = 2;

        List<Double> decisaoValores = new ArrayList<>();
        decisaoValores.add(3.0);
        decisaoValores.add(2.0);

        int equivalenciaFO = 1;

        ArrayList<ArrayList<Double>> restricoesValores = new ArrayList<>();
        ArrayList<Integer> equivalenciasRestricoes = new ArrayList<>();

        ArrayList<Double> restricao1 = new ArrayList<>();
        restricao1.add(2.0);
        restricao1.add(1.0);
        restricoesValores.add(restricao1);
        equivalenciasRestricoes.add(8);

        ArrayList<Double> restricao2 = new ArrayList<>();
        restricao2.add(1.0);
        restricao2.add(1.0);
        restricoesValores.add(restricao2);
        equivalenciasRestricoes.add(6);

        calcularAlgoritmoSimplex(qtdeVarDecisao, qtdeRestricoes, decisaoValores, equivalenciaFO,
                restricoesValores, equivalenciasRestricoes);
    }
}
