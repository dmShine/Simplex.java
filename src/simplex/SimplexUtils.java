package simplex;

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner;
//import javax.swing.JOptionPane;

public class SimplexUtils {
    public static ArrayList<String> calcularAlgoritmoSimplex(int qtdeVarDecisao, int qtdeRestricoes, int MinMax, List<Double> decisaoValores, int equivalenciaFO, 
    		ArrayList<ArrayList<Double>> restricoesValores, ArrayList<Integer> equivalenciasRestricoes) {
        
        ArrayList<String> resultados = new ArrayList<>();
        
        boolean seguir = true;
        int qtdRestricoes = qtdeVarDecisao;
        double valorComprovar = 0;
        List<Integer> posicoesNegativos = new ArrayList<Integer>();
        double matrizRestricao[][] = new double[qtdRestricoes][decisaoValores.size()];
        int sinalIgualdades[] = new int[qtdRestricoes];

        for (int i = 0; i < qtdRestricoes; i++) {
            for (int j = 0; j < decisaoValores.size() - 1; j++) {
                matrizRestricao[i][j] = restricoesValores.get(i).get(j);
                if (j + 1 == decisaoValores.size() - 1) {
                	sinalIgualdades[i] = equivalenciasRestricoes.get(i);
                    matrizRestricao[i][j + 1] = restricoesValores.get(i).get(j + 1);
                }
            }
        }

        int val = MinMax;

        if (val == 1) {
            for (int j = 0; j < decisaoValores.size(); j++) {
            	decisaoValores.set(j, decisaoValores.get(j) * -1);
            }
        }
        
        String funcaoOriginal = "";
        System.out.println("--------Original-------");
        for (int i = 0; i < matrizRestricao.length; i++) {
            for (int j = 0; j < matrizRestricao[i].length; j++) {
                funcaoOriginal = funcaoOriginal + matrizRestricao[i][j] + "    ";
                
            }
            System.out.print(funcaoOriginal);
            System.out.println();
        }
        
        int numAjustes = 0;
        for (int j = 0; j < sinalIgualdades.length; j++) {
            if (sinalIgualdades[j] != 2) {
                numAjustes++;
            }
        }

        double matrizRest[][] = new double[qtdRestricoes][decisaoValores.size() + numAjustes];
        int contPosim = 0;
        for (int i = 0; i < matrizRest.length; i++) {
            for (int j = 0; j < matrizRest[i].length; j++) {
                if (j + 1 == matrizRest[i].length) {
                    matrizRest[i][j] = matrizRestricao[i][decisaoValores.size() - 1];
                } else {
                    if (j < decisaoValores.size() - 1) {
                        matrizRest[i][j] = matrizRestricao[i][j];
                    } else {
                        if (sinalIgualdades[i] == 2) {
                            matrizRest[i][j] = 0;
                        } else {
                            if (sinalIgualdades[i] == 1) {
                                if (j == decisaoValores.size() - 1 + contPosim) {
                                	sinalIgualdades[i] = 2;
                                    matrizRest[i][j] = 1;
                                    contPosim++;
                                } else {
                                    matrizRest[i][j] = 0;
                                }
                            } else {
                                if (j == decisaoValores.size() - 1 + contPosim) {
                                	sinalIgualdades[i] = 2;
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

        List<Double> coeficientesFO = new ArrayList<Double>();
        double valorInd = 0;
        for (int j = 0; j < decisaoValores.size() + numAjustes; j++) {
            if (j == decisaoValores.size() - 1) {
                valorInd = decisaoValores.get(j);
            } else {
                if (j < decisaoValores.size() - 1) {
                    coeficientesFO.add(decisaoValores.get(j));
                } else {
                    coeficientesFO.add(0.0);
                }
            }
        }
        coeficientesFO.add(valorInd);
        
        String novaFuncaoObjetivo = "";
        String novasRestricoes = "";
        System.out.println("--------Nova função objetivo-------");
        for (int j = 0; j < coeficientesFO.size(); j++) {
            novaFuncaoObjetivo = novaFuncaoObjetivo + coeficientesFO.get(j) + "    ";
            
        }
        resultados.add(novaFuncaoObjetivo);
        System.out.print(novaFuncaoObjetivo);
        System.out.println();
        System.out.println("Novas restrições");
        for (int i = 0; i < matrizRest.length; i++) {
            for (int j = 0; j < matrizRest[i].length; j++) {
                novasRestricoes = novasRestricoes + matrizRest[i][j] + "    ";
                
            }
            resultados.add(novasRestricoes);
            System.out.print(novasRestricoes);
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

            for (int j = 0; j < coeficientesFO.size(); j++) {
                if (coeficientesFO.get(j) == menor) {
                    posicoesNegativos.add(j);
                }
            }

            if (existeNegativo == 0) {
                seguir = false;
            } else {
                existeNegativo = 0;

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
                            novasRestricoes = novasRestricoes + matrizRest[i][j] + "    ";
                            
                        }
                        resultados.add(novasRestricoes);
                        System.out.print(novasRestricoes);
                        System.out.println();
                    }

                    System.out.println("--------Função Objetivo-------");
                    for (int j = 0; j < coeficientesFO.size(); j++) {
                        novaFuncaoObjetivo = novaFuncaoObjetivo + coeficientesFO.get(j) + "    ";
                        
                    }
                    resultados.add(novaFuncaoObjetivo);
                    System.out.print(novaFuncaoObjetivo);
                    System.out.println("");
                }
            }

        }
        
        return resultados;

    }
}
