package gui;

import controllers.*;
import exceptions.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import models.*;

public class GUI implements ActionListener {
    Biblioteca library;
    JButton moarBook, moarPpl, moarEmp, button_livros, button_usuarios, button_empres, button_editar, button_remover;
    JRadioButton alun, prof;
    JPanel panel_livros, panel_usuarios, panel_empres, panel_alterar_usuario;
    JScrollPane scroll_livros, scroll_usuarios, scroll_empres;
    JTextField campoBuscaLivro;
    JButton botaoBuscarLivro;
    JLabel livTotalEmprestimos;
    ImageIcon blank = new ImageIcon("src/gui/blank.png"), defaultBook = new ImageIcon("src/gui/default_book.png"),
            missingBook = new ImageIcon("src/gui/missing_book.png");
    boolean select1;

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button_livros) {
            scroll_usuarios.setVisible(false);
            scroll_empres.setVisible(false);
            scroll_livros.setVisible(true);
            panel_usuarios.setVisible(false);
            panel_empres.setVisible(false);
            panel_livros.setVisible(true);
        } else if (e.getSource() == button_usuarios) {
            scroll_livros.setVisible(false);
            scroll_empres.setVisible(false);
            scroll_usuarios.setVisible(true);
            panel_usuarios.setVisible(true);
            panel_empres.setVisible(false);
            panel_livros.setVisible(false);
        } else if (e.getSource() == button_empres) {
            scroll_usuarios.setVisible(false);
            scroll_livros.setVisible(false);
            scroll_empres.setVisible(true);
            panel_usuarios.setVisible(false);
            panel_empres.setVisible(true);
            panel_livros.setVisible(false);}
        if(e.getSource() == botaoBuscarLivro){
            String titulo = campoBuscaLivro.getText();
            try {
                Livro livro = library.buscarLivroExato(titulo);
                JOptionPane.showMessageDialog(null, 
                    "Livro encontrado:\nTítulo: " + livro.getTitulo() + 
                    "\nAutor: " + livro.getAutor() + 
                    "\nAno: " + livro.getAnoPublicacao(), 
                    "Resultado da busca", JOptionPane.INFORMATION_MESSAGE);
            } catch (NenhumLivroEncontradoException ex) {
                JOptionPane.showMessageDialog(null, 
                    "Livro não encontrado!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }
        else if(e.getSource()==moarBook){
            JTextField xField = new JTextField(5);
            JTextField yField = new JTextField(5);
            JTextField zField = new JTextField(5);

            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Titulo:"));
            myPanel.add(xField);
            myPanel.add(Box.createHorizontalStrut(15));
            myPanel.add(new JLabel("Autor:"));
            myPanel.add(yField);
            myPanel.add(Box.createHorizontalStrut(15));
            myPanel.add(new JLabel("Ano:"));
            myPanel.add(zField);

            int result = JOptionPane.showOptionDialog(null, myPanel, "Cadastre o livro:", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, blank, null, 0);
            if (result == JOptionPane.OK_OPTION) {
                int maxidx = library.listarLivros().size();
                Livro newlivro = new Livro(xField.getText(), yField.getText(), Integer.parseInt(zField.getText()));
                library.cadastrarLivro(newlivro);
                JLabel newbook = new JLabel();
                newbook.setIcon(defaultBook);
                newbook.setText("<html><pre>Titulo: " + library.listarLivros().get(maxidx).getTitulo() + "<br>Autor: "
                        + library.listarLivros().get(maxidx).getAutor() + "<br>Ano: "
                        + library.listarLivros().get(maxidx).getAnoPublicacao() + "</pre></html>");
                newbook.setFont(new Font("Arial", Font.PLAIN, 20));
                newbook.setPreferredSize(new Dimension(1200, 100));
                panel_livros.add(newbook);
            }
            panel_livros.setVisible(false);
            panel_livros.setVisible(true);
        } else if (e.getSource() == moarPpl) {
            JTextField xField = new JTextField(5);
            JTextField yField = new JTextField(5);
            JTextField zField = new JTextField(5);
            
            JTextField wField = new JTextField(15);
            JComboBox <Departamento> deptoCombo = new JComboBox<>(Departamento.values());
            deptoCombo.setRenderer(new DefaultListCellRenderer(){
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Departamento) {
                        setText(((Departamento) value).getNomeFormatado());
                    }
                    return this;
                }
            });

            JRadioButton alun =new JRadioButton("Aluno");
            JRadioButton prof=new JRadioButton("Docente");
            ButtonGroup alunfessor=new ButtonGroup();
            alun.addActionListener(this);
            prof.addActionListener(this);
            alunfessor.add(alun);
            alunfessor.add(prof);

            deptoCombo.setVisible(false);
            
            alun.addActionListener(evento -> { wField.setVisible(true); deptoCombo.setVisible(false);});
            prof.addActionListener(evento -> { wField.setVisible(false); deptoCombo.setVisible(true); });


            JPanel myPanel = new JPanel();
            myPanel.setLayout(new GridLayout(3, 9));
            myPanel.add(new JLabel("Nome:"));
            myPanel.add(xField);
            myPanel.add(alun);
            myPanel.add(prof);
            myPanel.add(new JLabel("CPF:"));
            myPanel.add(yField);
            myPanel.add(new JLabel("Email:"));
            myPanel.add(zField);
            myPanel.add(new JLabel("Matricula:"));
            myPanel.add(wField);
            myPanel.add(deptoCombo);
            
            int result = JOptionPane.showOptionDialog(null, myPanel, "Cadastre o usuario:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, blank, null, 0);
            if (result == JOptionPane.OK_OPTION) {
                int maxidx=library.listarUsuarios().size();
                if(alun.isSelected()){
                    Aluno newaluno=new Aluno(xField.getText(),yField.getText(),zField.getText(),wField.getText());
                    library.cadastrarUsuario(newaluno);
                    JLabel newppl=new JLabel();
                    newppl.setIcon(randomAvatar());
                    newppl.setText("<html><pre>Nome: "+newaluno.getNome()+"\t\tOcupação: "+newaluno.getClass().getSimpleName()+"<br>CPF: "+newaluno.getCpf()+"\t\tEmail: "+newaluno.getEmail()+"<br>Matricula: "+newaluno.getMatricula()+"</pre></html>");
                    newppl.setFont(new Font("Arial",Font.PLAIN,20));
                    newppl.setPreferredSize(new Dimension(1200,100));
                    panel_usuarios.add(newppl);
                    JButtonHistorico buttoni=new JButtonHistorico(newaluno);
                    buttoni.setText("Historico de emprestimos");
                    buttoni.setPreferredSize(new Dimension(200,100));
                    buttoni.addActionListener(this);
                    panel_usuarios.add(buttoni);}
                else{
                    Departamento departamento = (Departamento) deptoCombo.getSelectedItem();

                    Professor newaluno=new Professor(xField.getText(),yField.getText(),zField.getText(),departamento);
                    library.cadastrarUsuario(newaluno);
                    JLabel newppl=new JLabel();
                    newppl.setIcon(randomAvatar());
                    newppl.setText("<html><pre>Nome: "+newaluno.getNome()+"\t\tOcupação: "+newaluno.getClass().getSimpleName()+"<br>CPF: "+newaluno.getCpf()+"\t\tEmail: "+newaluno.getEmail()+"<br>Departamento: "+newaluno.getDepartamento().getNomeFormatado()+"<pre/></html>");
                    newppl.setFont(new Font("Arial",Font.PLAIN,20));
                    newppl.setPreferredSize(new Dimension(1200,100));   
                    panel_usuarios.add(newppl);
                    JButtonHistorico buttoni=new JButtonHistorico(newaluno);
                    buttoni.setText("Historico de emprestimos");
                    buttoni.setPreferredSize(new Dimension(200,100));
                    buttoni.addActionListener(this);
                    panel_usuarios.add(buttoni);}
            }
            panel_usuarios.setVisible(false);
            panel_usuarios.setVisible(true);
        } else if (e.getSource() == alun) {
            select1 = true;
        } else if (e.getSource() == prof) {
            select1 = false;
        } else if (e.getSource() == moarEmp) {
            JTextField xField = new JTextField(5);
            JTextField yField = new JTextField(5);

            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Livro:"));
            myPanel.add(xField);
            myPanel.add(Box.createHorizontalStrut(15));
            myPanel.add(new JLabel("Usuario (CPF):"));
            myPanel.add(yField);

            int result = JOptionPane.showOptionDialog(null, myPanel, "Cadastre o livro:", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, blank, null, 0);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    library.realizarEmprestimo(xField.getText(), yField.getText());
                    atualizarContadorGeral();
                    atualizarPainelUsuarios();
                    int maxidx = library.listarEmprestimos().size() - 1;
                    JLabel empri = new JLabel();
                    empri.setBorder(BorderFactory.createRaisedBevelBorder());
                    empri.setText("<html><pre>Livro: " + library.listarEmprestimos().get(maxidx).getLivro().getTitulo()
                            + "\t\tUsuario: " + library.listarEmprestimos().get(maxidx).getUsuario().getNome()
                            + "<br>Data emprestimo: " + library.listarEmprestimos().get(maxidx).getDataEmprestimo()
                            + "<br>Data limite: " + library.listarEmprestimos().get(maxidx).getDataPrevista()
                            + "</pre></html>");
                    empri.setFont(new Font("Arial", Font.PLAIN, 20));
                    empri.setPreferredSize(new Dimension(1200, 100));
                    panel_empres.add(empri);
                    JButtonDevolucao buttoni = new JButtonDevolucao(library.listarEmprestimos().get(maxidx));
                    buttoni.setText("Cadastrar devolução");
                    buttoni.setPreferredSize(new Dimension(200, 100));
                    buttoni.addActionListener(this);
                    panel_empres.add(empri);
                    panel_empres.add(buttoni);
                    panel_livros.getComponent(library.listarLivros().indexOf(library.buscarLivroExato(xField.getText()))+3).setEnabled(false);
                }
                catch(NenhumLivroEncontradoException a){
                    JOptionPane.showMessageDialog(null,"Nenhum livro encontrado", null, JOptionPane.OK_OPTION,null);
                }
                catch(LivroIndisponivelException a){
                    JOptionPane.showMessageDialog(null,"Livro indisponivel", null, JOptionPane.OK_OPTION,null);
                }
                catch(UsuarioNaoCadastradoException a){
                    JOptionPane.showMessageDialog(null,"Usuario nao cadastrado", null, JOptionPane.OK_OPTION,null);
                }
                catch(LimiteEmprestimosException a){
                    JOptionPane.showMessageDialog(null,"Usuario atingiu o limite de emprestimos", null, JOptionPane.OK_OPTION,null);
                }
            }
            panel_empres.setVisible(false);
            panel_empres.setVisible(true);
        } else if (e.getSource() instanceof JButtonHistorico) {
            if (((JButtonHistorico) e.getSource()).user.getHistoricoEmprestimos().size() > 0) {
                JOptionPane.showMessageDialog(null, ((JButtonHistorico) e.getSource()).activate(), null,
                        JOptionPane.OK_OPTION, blank);
            } else {
                JOptionPane.showMessageDialog(null, ((JButtonHistorico) e.getSource()).activate(), null,
                        JOptionPane.OK_OPTION, null);
            }
        } else if (e.getSource() instanceof JButtonDevolucao) {
            Emprestimo emprs = ((JButtonDevolucao) e.getSource()).empres;
            try {
                library.registrarDevolucao(emprs.getLivro().getTitulo(), LocalDate.now());
                atualizarContadorGeral();
                atualizarPainelUsuarios();
                Component[] components = panel_empres.getComponents();
                ArrayList<Component> componentList = new ArrayList<>(Arrays.asList(components));
                int idx = componentList.indexOf((JButtonDevolucao) e.getSource());
                panel_empres.remove((JButtonDevolucao) e.getSource());
                panel_empres.remove(idx - 1);
                panel_livros.getComponent(library.listarLivros().indexOf(emprs.getLivro()) + 3).setEnabled(true);
                panel_empres.setVisible(false);
                panel_empres.setVisible(true);
                JOptionPane.showMessageDialog(null, "Multa calculada: R$" + emprs.calcularMulta(LocalDate.now()),
                        "Devolução concluida", JOptionPane.OK_OPTION, blank);
            } catch (NenhumEmprestimoEncontradoException a) {
                JOptionPane.showMessageDialog(null, "Nenhum emprestimo encontrado", null, JOptionPane.OK_OPTION, null);
            }
        }
        else if(e.getSource() instanceof JButtonHistorico){
            if(((JButtonHistorico)e.getSource()).user.getHistoricoEmprestimos().size()>0){
            JOptionPane.showMessageDialog(null,((JButtonHistorico)e.getSource()).activate(), null, JOptionPane.OK_OPTION,blank);}
            else{
            JOptionPane.showMessageDialog(null,((JButtonHistorico)e.getSource()).activate(), null, JOptionPane.OK_OPTION,null);}
        }
        else if(e.getSource() instanceof JButtonDevolucao){
            Emprestimo emprs=((JButtonDevolucao)e.getSource()).empres;
            try{library.registrarDevolucao(emprs.getLivro().getTitulo(),LocalDate.now());
            Component[] components = panel_empres.getComponents();
            ArrayList<Component> componentList=new ArrayList<>(Arrays.asList(components));
            int idx = componentList.indexOf((JButtonDevolucao)e.getSource());
            panel_empres.remove((JButtonDevolucao)e.getSource());
            panel_empres.remove(idx-1);
            panel_livros.getComponent(library.listarLivros().indexOf(emprs.getLivro())+2).setEnabled(true);
            panel_empres.setVisible(false);
            panel_empres.setVisible(true);
            JOptionPane.showMessageDialog(null,"Multa calculada: R$"+emprs.calcularMulta(LocalDate.now()), "Devolução concluida", JOptionPane.OK_OPTION,blank);
        }
        catch(NenhumEmprestimoEncontradoException a){
            JOptionPane.showMessageDialog(null,"Emprestimo não encontrado", null, JOptionPane.OK_OPTION,null);
        }}
    }

    public ImageIcon randomAvatar() {
        Double rng = Math.random();
        if (rng < 0.1) {
            return new ImageIcon("src/gui/pfp1.jpg");
        }
        if (rng < 0.2) {
            return new ImageIcon("src/gui/pfp2.jpg");
        }
        if (rng < 0.3) {
            return new ImageIcon("src/gui/pfp3.jpg");
        }
        if (rng < 0.4) {
            return new ImageIcon("src/gui/pfp4.jpg");
        }
        if (rng < 0.5) {
            return new ImageIcon("src/gui/pfp5.jpg");
        }
        if (rng < 0.6) {
            return new ImageIcon("src/gui/pfp6.jpg");
        }
        if (rng < 0.7) {
            return new ImageIcon("src/gui/pfp7.jpg");
        }
        if (rng < 0.8) {
            return new ImageIcon("src/gui/pfp8.jpg");
        }
        if (rng < 0.9) {
            return new ImageIcon("src/gui/pfp9.jpg");
        }
        return new ImageIcon("src/gui/default_avatar.jpg");
    }

    public void removerUsuario(String cpf){
        try{
            Pessoa usuario = library.buscarUsuarioExato(cpf);
            int idx=library.listarUsuarios().indexOf(usuario);
            panel_usuarios.remove(idx*2+3);
            panel_usuarios.remove(2*idx+3);
        }
        catch(UsuarioNaoCadastradoException a){
            JOptionPane.showMessageDialog(null,"Usuario nao cadastrado", null, JOptionPane.OK_OPTION,null);
        }
    }

    public void editarUsuario(String cpf,String nome,String email,String extra){
        try{
            Pessoa usuario = library.buscarUsuarioExato(cpf);
            int idx=library.listarUsuarios().indexOf(usuario);
            Component u=panel_usuarios.getComponent(idx*2+3);
            if (extra.indexOf("R")==0 && extra.indexOf("A")==1){
                ((JLabel) u).setText("<html><pre>Nome: "+nome+"\t\tOcupação: "+"Aluno"+"<br>CPF: "+cpf+"\t\tEmail: "+email+"<br>Matricula: "+extra+"</pre></html>");
            }
            else{((JLabel) u).setText("<html><pre>Nome: "+nome+"\t\tOcupação: "+"Professor"+"<br>CPF: "+cpf+"\t\tEmail: "+email+"<br>Departamento: "+extra+"</pre></html>");}
        }
        catch(UsuarioNaoCadastradoException a){
            JOptionPane.showMessageDialog(null,"Usuario nao cadastrado", null, JOptionPane.OK_OPTION,null);
        }
    }

    public GUI(Biblioteca library) {
        this.library = library;

        JPanel panel_menus = new JPanel();
        panel_menus.setPreferredSize(new Dimension(1200, 50));
        panel_menus.setBounds(0, 0, 1200, 50);
        panel_menus.setLayout(new GridLayout());
        panel_livros=new JPanel();
        panel_livros.setBounds(0,50,1200,800);
        panel_livros.setLayout(new BoxLayout(panel_livros,BoxLayout.PAGE_AXIS));
        panel_usuarios=new JPanel();
        panel_usuarios.setPreferredSize(new Dimension(1200,8000));
        panel_usuarios.setBounds(0,50,1200,800);
        panel_usuarios.setLayout(new BoxLayout(panel_usuarios,BoxLayout.PAGE_AXIS));
        panel_empres=new JPanel();
        panel_empres.setPreferredSize(new Dimension(1200,8000));
        panel_empres.setBounds(0,50,1200,800);
        panel_empres.setLayout(new BoxLayout(panel_empres, BoxLayout.PAGE_AXIS));

        button_livros = new JButton();
        button_livros.setPreferredSize(new Dimension(1200, 50));
        button_livros.setText("Livros");
        button_livros.setFont(new Font("Arial", Font.BOLD, 50));
        button_livros.addActionListener(this);
        panel_menus.add(button_livros);

        button_usuarios = new JButton();
        button_usuarios.setPreferredSize(new Dimension(400, 50));
        button_usuarios.setText("Usuarios");
        button_usuarios.setFont(new Font("Arial", Font.BOLD, 50));
        button_usuarios.addActionListener(this);
        panel_menus.add(button_usuarios);

        button_empres = new JButton();
        button_empres.setPreferredSize(new Dimension(400, 50));
        button_empres.setText("Emprestimos");
        button_empres.setFont(new Font("Arial", Font.BOLD, 50));
        button_empres.addActionListener(this);
        panel_menus.add(button_empres);

        JPanel painelBusca = new JPanel();
        painelBusca.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelBusca.setPreferredSize(new Dimension(240,100));
        painelBusca.add(new JLabel("Buscar livro pelo título:"));
        campoBuscaLivro = new JTextField(20);
        painelBusca.add(campoBuscaLivro);
        botaoBuscarLivro = new JButton("Buscar Livro");
        botaoBuscarLivro.setFont(new Font("Arial", Font.BOLD, 20));
        botaoBuscarLivro.addActionListener(this);
        painelBusca.add(botaoBuscarLivro);
        panel_livros.add(painelBusca);

        moarBook=new JButton();
        moarBook.setPreferredSize(new Dimension(240,100));
        moarBook.setText("Adicionar Livro");
        moarBook.setFont(new Font("Arial", Font.BOLD, 20));
        moarBook.addActionListener(this);
        panel_livros.add(moarBook);

        livTotalEmprestimos = new JLabel();
        livTotalEmprestimos.setFont(new Font("Arial", Font.PLAIN, 20));
        livTotalEmprestimos.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel_livros.add(livTotalEmprestimos);
        atualizarContadorGeral();

        for (Livro i : library.listarLivros()) {
            JLabel booki = new JLabel();
            booki.setIcon(defaultBook);
            booki.setDisabledIcon(missingBook);
            booki.setText("<html><pre>Titulo: " + i.getTitulo() + "<br>Autor: " + i.getAutor() + "<br>Ano: "
                    + i.getAnoPublicacao() + "</pre></html>");
            booki.setFont(new Font("Arial", Font.PLAIN, 20));
            booki.setPreferredSize(new Dimension(1200, 100));
            if (!i.isDisponivel()) {
                booki.setEnabled(false);
            }
            panel_livros.add(booki);
            panel_livros.add(Box.createRigidArea(new Dimension(0, 20)));
            panel_livros.revalidate();
            panel_livros.repaint();
        }

        moarPpl = new JButton();
        moarPpl.setPreferredSize(new Dimension(240, 100));
        moarPpl.setText("Adicionar Usuario");
        moarPpl.setFont(new Font("Arial", Font.BOLD, 20));
        moarPpl.addActionListener(this);
        panel_usuarios.add(moarPpl);

        button_editar = new JButton();
        button_editar.setPreferredSize(new Dimension(250, 100));
        button_editar.setText("Editar Usuário");
        button_editar.setFont(new Font("Arial", Font.BOLD, 20));
        button_editar.addActionListener(e -> new TelaEditarUsuario(library,this));
        panel_usuarios.add(button_editar);

        button_remover = new JButton();
        button_remover.setPreferredSize(new Dimension(250, 100));
        button_remover.setText("Remover Usuário");
        button_remover.setFont(new Font("Arial", Font.BOLD, 20));
        button_remover.addActionListener(e -> new TelaRemoverUsuario(library,this));
        panel_usuarios.add(button_remover);


        String fill = new String();

        for (Pessoa i : library.listarUsuarios()) {
            JLabel useri = new JLabel();
            useri.setIcon(randomAvatar());

            if(i instanceof Aluno){
                fill="<br>Matricula: "+((Aluno)i).getMatricula();}
            else if(i instanceof Professor){
                fill="<br>Departamento: "+((Professor)i).getDepartamento().getNomeFormatado();}
            
            int emprestimosAtivos = i.getNumeroEmprestimosAtivos();
            useri.setText("<html><pre>Nome: "+i.getNome()+"\t\tOcupação: "+i.getClass().getSimpleName()+"<br>CPF: "+i.getCpf()+"\t\tEmail: "+i.getEmail()+fill+ "\t\tEmpréstimos Ativos: " + emprestimosAtivos + "</pre></html>");
            useri.setFont(new Font("Arial",Font.PLAIN,20));
            useri.setPreferredSize(new Dimension(1200,100));
            JButtonHistorico buttoni=new JButtonHistorico(i);
            buttoni.setText("Historico de emprestimos");
            buttoni.setPreferredSize(new Dimension(200, 100));
            buttoni.addActionListener(this);
            panel_usuarios.add(useri);
            panel_usuarios.add(buttoni);
        }

        moarEmp = new JButton();
        moarEmp.setPreferredSize(new Dimension(240, 100));
        moarEmp.setText("Novo emprestimo");
        moarEmp.setFont(new Font("Arial", Font.BOLD, 20));
        moarEmp.addActionListener(this);
        panel_empres.add(moarEmp);
        for (Emprestimo i : library.listarEmprestimos()) {
            JLabel empri = new JLabel();
            empri.setBorder(BorderFactory.createRaisedBevelBorder());
            empri.setText("<html><pre>Livro: " + i.getLivro().getTitulo() + "\t\tUsuario: " + i.getUsuario().getNome()
                    + "<br>Data emprestimo: " + i.getDataEmprestimo() + "<br>Data limite: " + i.getDataPrevista()
                    + "</pre></html>");
            empri.setFont(new Font("Arial", Font.PLAIN, 20));
            empri.setPreferredSize(new Dimension(1200, 100));
            panel_empres.add(empri);

            JButtonDevolucao buttoni = new JButtonDevolucao(i);
            buttoni.setText("Cadastrar devolução");
            buttoni.setPreferredSize(new Dimension(200, 100));
            buttoni.addActionListener(this);
            panel_empres.add(empri);
            panel_empres.add(buttoni);
        }

        scroll_livros = new JScrollPane(panel_livros);
        scroll_livros.setPreferredSize(new Dimension(1200, 800));
        scroll_livros.setViewportView(panel_livros);
        scroll_livros.setBounds(0, 50, 1200, 800);
        scroll_livros.getVerticalScrollBar().setPreferredSize(new Dimension(50, 0));
        scroll_usuarios = new JScrollPane(panel_usuarios);
        scroll_usuarios.setPreferredSize(new Dimension(1200, 800));
        scroll_usuarios.setViewportView(panel_usuarios);
        scroll_usuarios.setVisible(false);
        scroll_usuarios.setBounds(0, 50, 1200, 800);
        scroll_usuarios.getVerticalScrollBar().setPreferredSize(new Dimension(50, 0));
        scroll_empres = new JScrollPane(panel_empres);
        scroll_empres.setPreferredSize(new Dimension(1200, 800));
        scroll_empres.setBounds(0, 50, 1200, 800);
        scroll_empres.setViewportView(panel_empres);
        scroll_empres.setVisible(false);
        scroll_empres.getVerticalScrollBar().setPreferredSize(new Dimension(50, 0));

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Biblioteca.exe");
        frame.setSize(1200, 900);
        frame.setPreferredSize(new Dimension(1200, 900));
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.getContentPane().setBackground(new Color(230, 230, 255));
        frame.add(panel_menus);
        frame.add(scroll_empres);
        frame.add(scroll_usuarios);
        frame.add(scroll_livros);
    }

    private void atualizarContadorGeral(){
        int total  = Emprestimo.getTotalEmprestimosRealizados();
        livTotalEmprestimos.setText("Total de empréstimos realizados na biblioteca: " + total);
    }

    private void atualizarPainelUsuarios() {
        panel_usuarios.removeAll();

        panel_usuarios.add(moarPpl);
        panel_usuarios.add(button_editar);

        String fill = new String();
        for (Pessoa i : library.listarUsuarios()) {
            JLabel useri = new JLabel();
            useri.setIcon(randomAvatar());

            if (i instanceof Aluno) {
                fill = "<br>Matricula: " + ((Aluno) i).getMatricula();
            } else if (i instanceof Professor) {
                fill = "<br>Departamento: " + ((Professor) i).getDepartamento().getNomeFormatado();
            }

            int emprestimosAtivos = i.getNumeroEmprestimosAtivos();
            useri.setText("<html><pre>Nome: "+i.getNome()+"\t\tOcupação: "+i.getClass().getSimpleName()+"<br>CPF: "+i.getCpf()+"\t\tEmail: "+i.getEmail()+fill+ "\t\tEmpréstimos Ativos: " + emprestimosAtivos + "</pre></html>");
            
            useri.setFont(new Font("Arial", Font.PLAIN, 20));
            useri.setPreferredSize(new Dimension(1200, 120));
            
            JButtonHistorico buttoni = new JButtonHistorico(i);
            buttoni.setText("Historico de emprestimos");
            buttoni.setPreferredSize(new Dimension(200, 100));
            buttoni.addActionListener(this);
            
            panel_usuarios.add(useri);
            panel_usuarios.add(buttoni);
        }

        panel_usuarios.revalidate();
        panel_usuarios.repaint();
    }
}
