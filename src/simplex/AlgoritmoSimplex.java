package simplex;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Label;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
//import java.awt.TextField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class AlgoritmoSimplex {

	private JFrame frame;
	private JTextField txVarQtdDecisao;
	private JTextField txQtdRestricoes;
	List<JTextField> ListVarDecisao = new ArrayList<>();
	List<JTextField> ListVarRestricao = new ArrayList<>();
	ArrayList<ArrayList<Double>> restricoesValores = new ArrayList<>();
	ArrayList<Double> decisaoValores = new ArrayList<>();
	ArrayList<Integer> cmbsRestricoes = new ArrayList<>();
	ArrayList<Double> formValores = new ArrayList<>();
	JComboBox<String> cmbVarDecisaoEquivalencia = new JComboBox<>();
	JComboBox<String> cmbVarRestricaoEquivalencia = new JComboBox<>();
	JComboBox<String> cmbMaxMin = new JComboBox<String>();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AlgoritmoSimplex window = new AlgoritmoSimplex();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AlgoritmoSimplex() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 379, 239);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Label lblMaxMin = new Label("Maximizar/Minimizar");
		lblMaxMin.setBounds(10, 26, 178, 22);
		frame.getContentPane().add(lblMaxMin);
		
		cmbMaxMin.setModel(new DefaultComboBoxModel<String>(new String[] {"Selecione...", "Maximizar", "Minimizar"}));
		cmbMaxMin.setSelectedIndex(0);
		cmbMaxMin.setBounds(244, 26, 109, 22);
		frame.getContentPane().add(cmbMaxMin);
		
		Label lblQtdVarDecisao = new Label("Quantidade de variáveis de decisão:");
		lblQtdVarDecisao.setBounds(10, 73, 216, 22);
		frame.getContentPane().add(lblQtdVarDecisao);
		
		txVarQtdDecisao = new JTextField();
		txVarQtdDecisao.setBounds(244, 75, 109, 20);
		frame.getContentPane().add(txVarQtdDecisao);
		txVarQtdDecisao.setColumns(10);
		
		JLabel lblQtdRestricoes = new JLabel("Quantidade de restrições:");
		lblQtdRestricoes.setBounds(10, 118, 216, 14);
		frame.getContentPane().add(lblQtdRestricoes);
		
		txQtdRestricoes = new JTextField();
		txQtdRestricoes.setBounds(244, 118, 109, 20);
		frame.getContentPane().add(txQtdRestricoes);
		txQtdRestricoes.setColumns(10);
		
		JButton btnProximo = new JButton("Próximo");
		btnProximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txVarQtdDecisao.getText().isBlank()) {
					JOptionPane.showMessageDialog(frame, "A quantidade de variáveis de decisão não pode ser nula!");
				} else if (!Funcoes.isNumericInteger(txVarQtdDecisao.getText())) {
					JOptionPane.showMessageDialog(frame, "Insira apenas números como quantidade de variáveis de decisão!");
				}
				
				if (txQtdRestricoes.getText().isBlank()) {
					JOptionPane.showMessageDialog(frame, "A quantidade de restrições não pode ser nula!");
				} else if (!Funcoes.isNumericInteger(txQtdRestricoes.getText())) {
					JOptionPane.showMessageDialog(frame, "Insira apenas números como número de restrições!");
				}
				
				abrirFormDecisao(Integer.parseInt(txVarQtdDecisao.getText()));
			}
		});
		btnProximo.setBounds(137, 166, 89, 23);
		frame.getContentPane().add(btnProximo);
	}
	
    public void abrirFormDecisao(int quantidade) {
        JFrame FormDecisao = new JFrame();
    	
        FormDecisao.setBounds(150, 150, 350, 300);
        FormDecisao.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FormDecisao.getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("Insira o valor das variáveis de decisão");
        lblTitle.setBounds(10, 10, 250, 22);
        FormDecisao.getContentPane().add(lblTitle);

        int yPos = 50;

        for (int i = 1; i <= quantidade; i++) {
            JLabel lblValor = new JLabel("Valor para X" + i + ":");
            lblValor.setBounds(10, yPos, 100, 22);
            FormDecisao.getContentPane().add(lblValor);

            JTextField txValor = new JTextField();
            txValor.setBounds(150, yPos, 100, 20);
            FormDecisao.getContentPane().add(txValor);
            ListVarDecisao.add(txValor);

            yPos += 30;
        }
        
        JLabel lblValor = new JLabel("Valor da equivalência");
        lblValor.setBounds(10, yPos, 130, 22);
        FormDecisao.getContentPane().add(lblValor);

        JTextField txValor = new JTextField();
        txValor.setBounds(150, yPos, 100, 20);
        FormDecisao.getContentPane().add(txValor);
        ListVarDecisao.add(txValor);
        
        yPos += 30;
        
        JLabel lblEquivalencia = new JLabel("Defina a equivalência da equação:");
        lblEquivalencia.setBounds(10, yPos, 250, 22);
        FormDecisao.getContentPane().add(lblEquivalencia);
        
        cmbVarDecisaoEquivalencia.setModel(new DefaultComboBoxModel<>(new String[]{"<=", "=", "=>"}));
        cmbVarDecisaoEquivalencia.setBounds(210, yPos, 80, 22);
        FormDecisao.getContentPane().add(cmbVarDecisaoEquivalencia);

        JButton btnNext = new JButton("Próximo");
        btnNext.setBounds(90, yPos + 40, 89, 23);
        FormDecisao.getContentPane().add(btnNext);
        
        FormDecisao.setBounds(150, 150, 350, 300 + yPos / quantidade);

        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean NumerosValidos = true;
                for (JTextField textField : ListVarDecisao) {
                    String value = textField.getText();
                    if (value.isBlank()) {
                        JOptionPane.showMessageDialog(FormDecisao, "Os valores das variáveis de decisão não podem ser nulos!");
                        NumerosValidos = false;
                        break;
                    } else if (!Funcoes.isNumericDouble(value)) {
                        JOptionPane.showMessageDialog(FormDecisao, "Insira apenas números reais!");
                        NumerosValidos = false;
                        break;
                    }
                }
                if (NumerosValidos) {
                    for (JTextField textField : ListVarDecisao) {
                        String value = textField.getText();
                        double coeficiente = Double.parseDouble(value);
                        decisaoValores.add(coeficiente);
                    }
                    
                    FormDecisao.dispose();
                    AbrirFormsRestricoes(Integer.parseInt(txQtdRestricoes.getText()), quantidade);
                }
            }
        });

        FormDecisao.setVisible(true);
    }
    
    private int restricaoCount = 1;
    
    private void AbrirFormsRestricoes(int qtdRestricoes, int quantidade) {
        
    	
    	JFrame FormRestricao = new JFrame();
    	FormRestricao.setBounds(150, 150, 350, 300);
    	FormRestricao.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	FormRestricao.getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("Insira os valores da restrição " + restricaoCount + ":");
        lblTitle.setBounds(10, 10, 250, 22);
        FormRestricao.getContentPane().add(lblTitle);

        int yPos = 50;
        

        for (int i = 1; i <= quantidade; i++) {
            JLabel lblValor = new JLabel("Valor para X" + i + ":");
            lblValor.setBounds(10, yPos, 100, 22);
            FormRestricao.getContentPane().add(lblValor);

            JTextField txValor = new JTextField();
            txValor.setBounds(150, yPos, 100, 20);
            FormRestricao.getContentPane().add(txValor);
            ListVarRestricao.add(txValor);

            yPos += 30;
        }
        
        JLabel lblValor = new JLabel("Valor da equivalência");
        lblValor.setBounds(10, yPos, 130, 22);
        FormRestricao.getContentPane().add(lblValor);

        JTextField txValor = new JTextField();
        txValor.setBounds(150, yPos, 100, 20);
        FormRestricao.getContentPane().add(txValor);
        ListVarRestricao.add(txValor);
        
        yPos += 30;

        JLabel lblEquivalencia = new JLabel("Defina a equivalência da equação:");
        lblEquivalencia.setBounds(10, yPos, 250, 22);
        FormRestricao.getContentPane().add(lblEquivalencia);
        
        cmbVarRestricaoEquivalencia.setModel(new DefaultComboBoxModel<>(new String[]{"<=", "=", "=>"}));
        cmbVarRestricaoEquivalencia.setBounds(210, yPos, 80, 22);
        FormRestricao.getContentPane().add(cmbVarRestricaoEquivalencia);
        
        FormRestricao.setBounds(150, 150, 350, 300 + yPos / quantidade);

        JButton btnNext = new JButton("Próximo");
        btnNext.setBounds(100, yPos + 40, 89, 23);
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	FormRestricao.dispose();
            	if (qtdRestricoes == restricaoCount) {

            	    for (int formIndex = 0; formIndex < qtdRestricoes; formIndex++) {
            	        for (int i = 1; i <= quantidade; i++) {
            	            JTextField textField = ListVarRestricao.get(formIndex);
            	            String value = textField.getText();
            	            double valor = Double.parseDouble(value);
            	            formValores.add(valor); 
            	            cmbsRestricoes.add(cmbVarRestricaoEquivalencia.getSelectedIndex());
            	            
            	        }
            	        restricoesValores.add(formValores);
            	    }
            	    
            	    abrirFormResultado(
            	    		SimplexUtils.calcularAlgoritmoSimplex(Integer.parseInt(txVarQtdDecisao.getText()), Integer.parseInt(txQtdRestricoes.getText()), 
            	    				cmbMaxMin.getSelectedIndex(), decisaoValores, cmbVarDecisaoEquivalencia.getSelectedIndex(), restricoesValores, cmbsRestricoes
            	    		)
            	    );
                } else { 
                	restricaoCount++;
                	AbrirFormsRestricoes(qtdRestricoes, quantidade);               	
                }
            }
        });
        FormRestricao.getContentPane().add(btnNext);

        FormRestricao.setVisible(true);
    }
    
    private void abrirFormResultado(ArrayList<String> resultados) {
    	JFrame FormResultado = new JFrame();
    	FormResultado.setBounds(150, 150, 350, 300);
    	FormResultado.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	FormResultado.getContentPane().setLayout(null);	
    	
        JLabel lblTitle = new JLabel("Resultados:");
        lblTitle.setBounds(10, 10, 250, 22);
        FormResultado.getContentPane().add(lblTitle);
        
        int yPos = 50;
        

        for (int i = 1; i <= resultados.size(); i++) {
            JTextArea txAreaTexto = new JTextArea(resultados.get(i - 1));
            txAreaTexto.setBounds(10, yPos, 500, 22);
            FormResultado.getContentPane().add(txAreaTexto);

            yPos += 30;
        }
        
        FormResultado.setVisible(true);
        
    }
}
