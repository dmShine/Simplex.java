package simplex;

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner;
//import javax.swing.JOptionPane;

public class SimplexUtils {
	
	// qtd variaveis de decisao
	// qtd restricoes
	// list com valores de decisao X
	// equivalencia da FO
	// list da list com valores das restricoes
	// list de equivalencia das restricoes
    public static ArrayList<String> calcularAlgoritmoSimplex(int qtdeVarDecisao, int qtdeRestricoes, int MinMax, List<Double> decisaoValores, int equivalenciaFO, 
    		ArrayList<ArrayList<Double>> restricoesValores, ArrayList<Integer> equivalenciasRestricoes) {
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //Scanner scanner = new Scanner(System.in); 
        //JOptionPane input = new JOptionPane();
        
        ArrayList<String> resultados = new ArrayList<>();
        
        boolean seguir = true;
        //int subindice = 1; 
        //double coef = 0;
        //int sim = 0;
        int qtdRestricoes = qtdeVarDecisao;
        double valorComprovar = 0;

        //String apresentacao = "";
        //String funcao = "";

        //List<Double> coeficientesFOS = decisaoValores;
        //ArrayList<ArrayList<Double>> coeficientesFRS = restricoesValores;
        //ArrayList<Integer> sinalIgualdadeFRS = equivalenciasRestricoes;
        List<Integer> posicoesNegativos = new ArrayList<Integer>();
        
        /*
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

        }*/
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
        
        /*int linhas = restricoesValores.size();
        int colunas = restricoesValores.get(0).size();

        for (int i = 0; i < linhas; i++) {
            ArrayList<Double> linha = restricoesValores.get(i);
            for (int j = 0; j < colunas; j++) {
                matrizRestricao[i][j] = linha.get(j);
            }
        }*/
        
        /*for (int i = 0; i < qtdRestricoes; i++) {
        	for (int j = 0; j < coeficientesFOS.size() - 1; j++) {
        		//matrizRestricao[i][j] = restricoesValores.
        		if (j + 1 == coeficientesFOS.size() - 1) {
        			
        		}
        	}
        }*/

        int val = MinMax;
        //System.out.println("Deverá Maximizar ou Minimizar a equação?");
        //System.out.println("(1) Maximizar (2) Minimizar");
        //val = Integer.parseInt(JOptionPane.showInputDialog("Deverá Maximizar ou Minimizar a equação? (1) Maximizar (2) Minimizar"));
        //val = scanner.nextInt();

        if (val == 1) {
            for (int j = 0; j < decisaoValores.size(); j++) {
            	decisaoValores.set(j, decisaoValores.get(j) * -1);
            }
        }
        
        String funcaoOriginal = "";
        System.out.println("--------Original-------");
        for (int i = 0; i < matrizRestricao.length; i++) {
            for (int j = 0; j < matrizRestricao[i].length; j++) {
                //System.out.print(matrizRestricao[i][j] + "\t");
                funcaoOriginal = funcaoOriginal + matrizRestricao[i][j] + "    ";
                
            }
            System.out.print(funcaoOriginal);
            System.out.println();
        }

        // Contem as desigualdades para ajustes
        int numAjustes = 0;
        for (int j = 0; j < sinalIgualdades.length; j++) {
            if (sinalIgualdades[j] != 2) {
                numAjustes++;
            }
        }

        // Adicionar variáveis ​​para padronização de restrições
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
        // Adicionar valores da função objetivo

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
            //System.out.print(coeficientesFO.get(j) + "\t");
            novaFuncaoObjetivo = novaFuncaoObjetivo + coeficientesFO.get(j) + "    ";
            
        }
        resultados.add(novaFuncaoObjetivo);
        System.out.print(novaFuncaoObjetivo);
        System.out.println();
        System.out.println("Novas restrições");
        for (int i = 0; i < matrizRest.length; i++) {
            for (int j = 0; j < matrizRest[i].length; j++) {
                //System.out.print(matrizRest[i][j] + "\t");
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
                    //Mudança dos valores nas restricoes
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
                            //System.out.print(matrizRest[i][j] + "\t");
                            novasRestricoes = novasRestricoes + matrizRest[i][j] + "    ";
                            
                        }
                        resultados.add(novasRestricoes);
                        System.out.print(novasRestricoes);
                        System.out.println();
                    }

                    System.out.println("--------Função Objetivo-------");
                    for (int j = 0; j < coeficientesFO.size(); j++) {
                        //System.out.print(coeficientesFO.get(j) + "\t");
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
