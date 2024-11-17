package cep;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import Atxy2k.CustomTextField.RestrictedTextField;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Iterator;
import java.awt.event.ActionEvent;

public class Cep extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtCep;
    private JTextField txtEndereco;
    private JTextField txtBairro;
    private JTextField txtCidade;
    private JComboBox<String> cboUf;
    private JLabel lblStatus;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Cep frame = new Cep();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public Cep() {
        setTitle("Buscar CEP");
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(Cep.class.getResource("/img/home.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("CEP");
        lblNewLabel.setBounds(30, 43, 46, 14);
        contentPane.add(lblNewLabel);

        txtCep = new JTextField();
        txtCep.setBounds(111, 40, 86, 20);
        contentPane.add(txtCep);
        txtCep.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Endereço");
        lblNewLabel_1.setBounds(30, 85, 72, 14);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Bairro");
        lblNewLabel_2.setBounds(30, 137, 46, 14);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Cidade");
        lblNewLabel_3.setBounds(30, 188, 46, 14);
        contentPane.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("UF");
        lblNewLabel_4.setBounds(327, 188, 46, 14);
        contentPane.add(lblNewLabel_4);

        cboUf = new JComboBox<>();
        cboUf.setModel(new DefaultComboBoxModel<>(new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF",
                "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO",
                "RR", "SC", "SP", "SE", "TO" }));
        cboUf.setBounds(360, 184, 48, 22);
        contentPane.add(cboUf);

        txtEndereco = new JTextField();
        txtEndereco.setBounds(111, 82, 254, 20);
        contentPane.add(txtEndereco);
        txtEndereco.setColumns(10);

        txtBairro = new JTextField();
        txtBairro.setBounds(111, 134, 254, 20);
        contentPane.add(txtBairro);
        txtBairro.setColumns(10);

        txtCidade = new JTextField();
        txtCidade.setBounds(111, 185, 188, 20);
        contentPane.add(txtCidade);
        txtCidade.setColumns(10);

        JButton btnCep = new JButton("Buscar");
        btnCep.addActionListener(e -> {
            if (txtCep.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Favor, antes de buscar informe o CEP!");
                txtCep.requestFocus();
            } else {
                buscarCep();
            }
        });
        btnCep.setBounds(261, 39, 89, 23);
        contentPane.add(btnCep);

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> {
            // Limpar os campos de texto
            txtCep.setText("");
            txtEndereco.setText("");
            txtBairro.setText("");
            txtCidade.setText("");
            
            // Resetar o combo box
            cboUf.setSelectedIndex(0);
            
            // Resetar o ícone de status
            lblStatus.setIcon(null);
            
            // Colocar o foco no campo de CEP
            txtCep.requestFocus();
        });
        btnLimpar.setBounds(30, 227, 89, 23);
        contentPane.add(btnLimpar);

        JButton btnSobre = new JButton("");
        btnSobre.addActionListener(e -> {
            Sobre sobre = new Sobre();
            sobre.setVisible(true);
        });
        btnSobre.setToolTipText("Sobre");
        btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSobre.setBorder(null);
        btnSobre.setBackground(SystemColor.control);
        btnSobre.setIcon(new ImageIcon(Cep.class.getResource("/img/about.png")));
        btnSobre.setBounds(360, 22, 48, 48);
        contentPane.add(btnSobre);

        lblStatus = new JLabel("");
        lblStatus.setBounds(196, 26, 48, 48);
        contentPane.add(lblStatus);

        RestrictedTextField validar = new RestrictedTextField(txtCep);
        validar.setOnlyNums(true);
        validar.setLimit(8);
    }

    private void buscarCep() {
        String logradouro = "";
        String tipoLogradouro = "";
        String resultado = null;
        String cep = txtCep.getText();
        try {
            URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
            SAXReader xml = new SAXReader();
            Document documento = xml.read(url);
            Element root = documento.getRootElement();
            for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
                Element element = it.next();

                if (element.getQualifiedName().equals("cidade")) {
                    txtCidade.setText(element.getText());
                }
                if (element.getQualifiedName().equals("bairro")) {
                    txtBairro.setText(element.getText());
                }
                if (element.getQualifiedName().equals("uf")) {
                    cboUf.setSelectedItem(element.getText());
                }
                if (element.getQualifiedName().equals("tipo_logradouro")) {
                    tipoLogradouro = element.getText();
                }
                if (element.getQualifiedName().equals("logradouro")) {
                    logradouro = element.getText();
                }
                if (element.getQualifiedName().equals("resultado")) {
                    resultado = element.getText();
                    if (resultado.equals("1")) {
                        lblStatus.setIcon(new ImageIcon(Cep.class.getResource("/img/check.png")));
                    } else {
                        JOptionPane.showMessageDialog(null, "CEP não encontrado");
                    }
                }
            }

            txtEndereco.setText(tipoLogradouro + " " + logradouro);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
