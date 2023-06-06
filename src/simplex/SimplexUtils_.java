package simplex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//import javax.swing.JOptionPane;

public class SimplexUtils {

	// qtd variaveis de decisao
	// qtd restricoes
	// list com valores de decisao X
	// equivalencia da FO
	// list da list com valores das restricoes
	// list de equivalencia das restricoes
    public static void calcularAlgoritmoSimplex(int qtdeVarDecisao, int qtdeRestricoes, List<Double> decisaoValores, int equivalenciaFO, 
    		ArrayList<ArrayList<Double>> restricoesValores, ArrayList<Integer> equivalenciasRestricoes) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Scanner scanner = new Scanner(System.in); 
        //JOptionPane input = new JOptionPane();

        boolean seguir = true;
        int subindice = 1; 
        double coef = 0;
        int sim = 0;
        int qtdRestricoes = 0;
        double valorComprovar = 0;

        String apresentacao = "";
        String funcao = "";

        List<Double> coeficientesFOS = new ArrayList<Double>();
        List<Integer> posicoesNegativos = new ArrayList<Integer>();
        
        System.out.println("Algoritmo Simmplex");
        System.out.println("-- Recepção dos dados --");
        System.out.println("Por favor Insira a função objetivo:");
        while (seguir == true) {
            System.out.print("Valor para x" + subindice + " :");
            coef = scanner.nextInt();
            //coef = Double.parseDouble(JOptionPane.showInputDialog("Valor para x" + subindice + " :"));
            coeficientesFOS.add(coef);
            subindice++;
            System.out.print("Insira 1 para seguir adicionando variáveis: ");
            sim = scanner.nextInt();
            //sim = Integer.parseInt(JOptionPane.showInputDialog("Insira 1 para seguir adicionando variáveis"));
            if (sim != 1) {
                System.out.println("Insira a equivalência da equação: ");
                coef = scanner.nextInt();
                //coef = Double.parseDouble(JOptionPane.showInputDialog("Insira a equivalência da equação: "));
                coeficientesFOS.add(coef);
                seguir = false;
            }
        }

        for (int i = 0; i < coeficientesFOS.size(); i++) {
            apresentacao = String.valueOf(coeficientesFOS.get(i));
            if (i > 0 && coeficientesFOS.get(i) >= 0) {
                apresentacao = "+" + apresentacao;
            }

            if (i + 1 == coeficientesFOS.size()) {
                funcao = funcao + " = " + apresentacao;
            } else {
                funcao = funcao + apresentacao + "x" + (i + 1);
            }

        }
       // JOptionPane.showMessageDialog(null, funcao, "Função", JOptionPane.WARNING_MESSAGE);
        System.out.println("Insira o número de restrições: ");
        qtdRestricoes = scanner.nextInt();
        //qtdRestricoes = Integer.parseInt(JOptionPane.showInputDialog("Insira o número de restrições: "));
        double matrizRestSimnAjustar[][] = new double[qtdRestricoes][coeficientesFOS.size()];
        int simgnoIgualdades[] = new int[qtdRestricoes];

        for (int i = 0; i < qtdRestricoes; i++) {
            System.out.println("------Valores para a restrição " + (i + 1) + ": ");
            for (int j = 0; j < coeficientesFOS.size() - 1; j++) {
                System.out.print("Insira o valor de x" + (j + 1) + ": ");
                matrizRestSimnAjustar[i][j] = scanner.nextInt();
                //matrizRestSimnAjustar[i][j] = Integer.parseInt(JOptionPane.showInputDialog("Insira o valor de x" + (j + 1) + ": "));
                if (j + 1 == coeficientesFOS.size() - 1) {
                    System.out.print("Insira o sinal de igualdade/desigualdade: <=(1), =(2), >=(3) ");
                    simgnoIgualdades[i] = scanner.nextInt();
                	//sinalIgualdade[i] = Integer.parseInt(JOptionPane.showInputDialog("Insira o sinal de igualdade/desigualdade: <=(1), =(2), >=(3) "));
                    System.out.print("Insira o resultado da equação: ");
                    matrizRestSimnAjustar[i][j + 1] = scanner.nextInt();
                }
            }
        }

        int val = 0;
        System.out.println("Deverá Maximizar ou Minimizar a equação?");
        System.out.println("(1) Maximizar (2) Minimizar");
        //val = Integer.parseInt(JOptionPane.showInputDialog("Deverá Maximizar ou Minimizar a equação? (1) Maximizar (2) Minimizar"));
        val = scanner.nextInt();

        if (val == 1) {
            for (int j = 0; j < coeficientesFOS.size(); j++) {
                coeficientesFOS.set(j, coeficientesFOS.get(j) * -1);
            }
        }

        System.out.println("--------Original-------");
        for (int i = 0; i < matrizRestSimnAjustar.length; i++) {
            for (int j = 0; j < matrizRestSimnAjustar[i].length; j++) {
                System.out.print(matrizRestSimnAjustar[i][j] + "\t");
            }
            System.out.println();
        }

        // Contem as desigualdades para ajustes
        int numAjustes = 0;
        for (int j = 0; j < simgnoIgualdades.length; j++) {
            if (simgnoIgualdades[j] != 2) {
                numAjustes++;
            }
        }

        // Adicionar variáveis ​​para padronização de restrições
        double matrizRest[][] = new double[qtdRestricoes][coeficientesFOS.size() + numAjustes];
        int contPosim = 0;
        for (int i = 0; i < matrizRest.length; i++) {
            for (int j = 0; j < matrizRest[i].length; j++) {
                if (j + 1 == matrizRest[i].length) {
                    matrizRest[i][j] = matrizRestSimnAjustar[i][coeficientesFOS.size() - 1];
                } else {
                    if (j < coeficientesFOS.size() - 1) {
                        matrizRest[i][j] = matrizRestSimnAjustar[i][j];
                    } else {
                        if (simgnoIgualdades[i] == 2) {
                            matrizRest[i][j] = 0;
                        } else {
                            if (simgnoIgualdades[i] == 1) {
                                if (j == coeficientesFOS.size() - 1 + contPosim) {
                                    simgnoIgualdades[i] = 2;
                                    matrizRest[i][j] = 1;
                                    contPosim++;
                                } else {
                                    matrizRest[i][j] = 0;
                                }
                            } else {
                                if (j == coeficientesFOS.size() - 1 + contPosim) {
                                    simgnoIgualdades[i] = 2;
                                    matrizRest[i][j] = -1;
                                    contPosim++;
                                } else {
                                    matrizRest[i][j] = 0;
                                }
                            }
                        }
                    }

                }
            }
        }
        // Adicionar valores da função objetivo

        List<Double> coeficientesFO = new ArrayList<Double>();
        double valorInd = 0;
        for (int j = 0; j < coeficientesFOS.size() + numAjustes; j++) {
            if (j == coeficientesFOS.size() - 1) {
                valorInd = coeficientesFOS.get(j);
            } else {
                if (j < coeficientesFOS.size() - 1) {
                    coeficientesFO.add(coeficientesFOS.get(j));
                } else {
                    coeficientesFO.add(0.0);
                }
            }
        }
        coeficientesFO.add(valorInd);

        System.out.println("--------Nova função objetivo-------");
        for (int j = 0; j < coeficientesFO.size(); j++) {
            System.out.print(coeficientesFO.get(j) + "\t");
        }
        System.out.println();
        System.out.println("Novas restrições");
        for (int i = 0; i < matrizRest.length; i++) {
            for (int j = 0; j < matrizRest[i].length; j++) {
                System.out.print(matrizRest[i][j] + "\t");
            }
            System.out.println();
        }

        seguir = true;
        int existeNegativo = 0;
        int posicao = 0;
        double menor = 0;
        double auxiliar;
        boolean ocupado = false;
        int coluna = 0;
        int linha = 0;

        while (seguir == true) {
            posicoesNegativos.clear();

            // Sempre obter o valor mínimo da função objetiva
            // e quando for menor que 0
            for (int j = 0; j < coeficientesFO.size(); j++) {
                valorComprovar = coeficientesFO.get(j);
                if (valorComprovar < 0) {
                    if (ocupado == false) {
                        menor = valorComprovar;
                        posicao = j;
                        ocupado = true;
                    } else {
                        if (valorComprovar <= menor) {
                            menor = valorComprovar;
                            posicao = j;
                        }
                    }
                    existeNegativo++;
                }
            }
            posicoesNegativos.add(posicao);

            // Se possui dois valores negativos, mantem suas duas posições
            for (int j = 0; j < coeficientesFO.size(); j++) {
                if (coeficientesFO.get(j) == menor) {
                    posicoesNegativos.add(j);
                }
            }

            if (existeNegativo == 0) {
                seguir = false;
            } else {
                existeNegativo = 0;

                // Comprovar restrição mediante a razão
                int qtdPositivos = 0;
                for (int h = 0; h < posicoesNegativos.size(); h++) {
                    posicao = posicoesNegativos.get(h);

                    ocupado = false;
                    menor = 0;
                    for (int i = 0; i < matrizRest.length; i++) {
                        for (int k = posicao; k < coeficientesFO.size(); k++) {
                            if (matrizRest[i][k] > 0) {
                                if (k == posicao) {
                                    qtdPositivos++;
                                    auxiliar = matrizRest[i][coeficientesFO.size() - 1] / matrizRest[i][k];
                                    if (ocupado == false) {
                                        menor = auxiliar;
                                        linha = i;
                                        coluna = k;
                                        ocupado = true;
                                    } else {
                                        if (auxiliar < menor) {
                                            menor = auxiliar;
                                            linha = i;
                                            coluna = k;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (qtdPositivos > 0) {
                        h = posicoesNegativos.size();
                    }
                }

                if (qtdPositivos == 0) {
                    seguir = false;
                    System.out.println("Não é possível continuar devido à negatividade nas restrições.");
                }

                qtdPositivos = 0;

                if (seguir == true) {
                    double valorMultiplicar = 0;
                    double auxM = 0;
                    //Intercambio de valores en las restricciones
                    for (int i = 0; i < matrizRest.length; i++) {
                        for (int k = 0; k < coeficientesFO.size(); k++) {
                            if (i != linha) {
                                if (k == 0) {
                                    valorMultiplicar = (matrizRest[i][coluna] / matrizRest[linha][coluna]) * (-1);
                                }
                                matrizRest[i][k] = (matrizRest[linha][k] * valorMultiplicar) + matrizRest[i][k];
                            } else {
                                if (k == 0) {
                                    auxM = matrizRest[linha][coluna];
                                }
                                matrizRest[i][k] = matrizRest[i][k] / auxM;
                            }
                        }
                    }

                    double valorSubstituir = 0;

                    valorMultiplicar = 0;

                    // Cálculo da nova função objetivo
                    for (int j = 0; j < coeficientesFO.size(); j++) {
                        if (j == 0) {
                            valorMultiplicar = coeficientesFO.get(coluna) * (-1);
                        }
                        valorSubstituir = coeficientesFO.get(j) + (valorMultiplicar * matrizRest[linha][j]);
                        coeficientesFO.set(j, valorSubstituir);
                    }
                    
                    System.out.println("");
                    System.out.println("");
                    System.out.println("--------Iteração-------");
                    for (int i = 0; i < matrizRest.length; i++) {
                        for (int j = 0; j < matrizRest[i].length; j++) {
                            System.out.print(matrizRest[i][j] + "\t");
                        }
                        System.out.println();
                    }

                    System.out.println("--------Função Objetivo-------");
                    for (int j = 0; j < coeficientesFO.size(); j++) {
                        System.out.print(coeficientesFO.get(j) + "\t");
                    }
                    System.out.println("");
                }
            }

        }
        
        scanner.close();
        try {
			br.close();
		} catch (IOException e) {
			
		}

    }
}
