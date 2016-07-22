/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiagods.controller;

import br.com.tiagods.model.*;
import br.com.tiagods.utilitarios.Help;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComboBox;

import static br.com.tiagods.view.Menu.*;
import java.io.File;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author Tiago
 */
public class ControllerMenu implements ActionListener, MouseListener{
    ArrayList cliente;//array pai, nele serão armazenados todos os clientes
    PlanilhaBean bean;
    Tabela tabela;//classe que trata e cuida das tabelas da Classe Menu
    List<String> listaStatus; //lista de todos os status
    String caminho;
    Set<File> morto = new HashSet<>();//lista usada para armazenar arquivos mortos
    Set<String> listaNova;/*lista simples para guardar status não repetidos 
    dos clientes e enviar para tabela*/
    /*
    Colunas para cada tipo de registro / apenas para melhor visualização
    */
    int colunaCodigo=0;
    int colunaStatus=1;
    int colunaNome=2;
    int colunaCnpj=3;
    int colunaStatusCod=4;
    int colunaStatusNome=5;
    int colunaStatusCnpj=6;
    int colunaObservacao=7;
    int delimitador;
    
    public void iniciar(){
        travarCampos(jPanel1);
        travarCampos(jPanel2);
    }
    public void travarCampos(JPanel panel){//metodo para travar campos para não edição
        for(int i =0; i<panel.getComponentCount();i++){
            if(panel.getComponent(i) instanceof JTextField)
                ((JTextField)panel.getComponent(i)).setEnabled(false);
        }
    
    }
    public void carregarCombo(JComboBox box, int combo){
        String[] letra = {"","A","B","C","D","E","F","G","H","I","J","K","L","M",
            "N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        
        for (String l : letra){
            if(l.equals(""))
                box.addItem("");
            else
                box.addItem(l);
        }
        switch(combo){
            case 1:
            box.setSelectedItem("A");
            break;
            case 2:
            box.setSelectedItem("K");
            break;
            case 3:
            box.setSelectedItem("R");
            break;
            case 4:
            box.setSelectedItem("B");
            break;
            case 5:
            box.setSelectedItem("B");
            break;
            default:
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Iniciar"://iniciar tratamento dos campos e liberar procura de dados dos clientes nos diretorios
                caminho = txCaminhoArquivo.getText();
                if(!caminho.equals("") && new File(caminho).isFile()){
                    String dir = txCaminhoPDF.getText();
                    if(!dir.equals("") && new File(dir).isDirectory()){
                        if(validarNumero(txDelimitador.getText())){
                            if(txBuscarNome.getText().equals("")){
                                String comentario = "Se você não informar uma expressão resumida\n "
                                        + "o numero de resultados podem ultrapassar a quantidade de registros além do esperado para cada busca\n"
                                        + "O processo pode demora mais para terminar!\nMesmo assim deseja continuar?";

                                int valor = JOptionPane.showConfirmDialog(null,comentario,"Filtro não informado!",JOptionPane.YES_NO_OPTION);
                                if(valor == JOptionPane.YES_OPTION)
                                    Time();
                                else
                                    txBuscarNome.setFocusable(true);
                            }
                            else
                                Time();
                        }
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Informe um diretorio valido dos arquivos PDF!");
                        
                }
                else
                    JOptionPane.showMessageDialog(null, "Primeiro informe um arquivo valido para importar!");
                break;
            
            case "CarregarArquivo":
                caminho = carregarArquivo(true, "Abrir Arquivo...");
                if(!caminho.equals("")){
                    txCaminhoArquivo.setText(caminho);
                    Arquivo validador = new Arquivo();
                    if(validador.validarArquivo(new File(caminho))){//validar a extensao do arquivo
                        PlanilhaDao planilha = new PlanilhaDao();
                        bean = new PlanilhaBean();
                        if(planilha.lerPlanilha(bean, txStatus, new File(caminho))){
                            carregarCombo(comboCodigo, 1);
                            carregarCombo(comboNome, 2);
                            carregarCombo(comboCNPJ, 3);
                            carregarCombo(comboStatus, 4);
                            carregarCombo(comboStatus2, 5);
                            cbFiltro.setSelected(true);
                            refresh();
                            
                            if(!txView5.getText().equals("")){
                               preencherTabela();
                            }
                            txDelimitador.setText("2");                        
                        }
                        else
                            JOptionPane.showMessageDialog(null, "Não foi possivel ler o arquivo de origem,\n"
                                    + "Verifique se o arquivo é valido!\nArquivo protegidos por senhas não podem ser abertos!");
                    }
                }
                break;
            case "CarregarDir":
                String diretorio = carregarArquivo(false, "Escolher caminho...");
                if(!diretorio.equals(""))
                    txCaminhoPDF.setText(diretorio);
                break;
            case "CarregarOutros":
                String diretorio2 = carregarArquivo(false, "Escolher caminho...");
                if(!diretorio2.equals(""))
                    txCaminhoOutros.setText(diretorio2);
                break;
            case "Refresh":
                refresh();
                if(!txView5.getText().equals("")){
                    preencherTabela();
                }
                break;
            case "Filtro":
                if(cbFiltro.isSelected()){
                    comboStatus2.setEnabled(true);
                    if(!txView5.getText().equals("")){
                        preencherTabela();
                    }
                }
                break;
            case "PreencherTabelas":
                if(!txView5.getText().equals("")){
                    preencherTabela();
                }
                break;
            case "AddUm":
                tabela=new Tabela();
                tabela.addUmOrRemoveUm(jTable2, jTable3, "adicionar", "tabela 1");
                break;
            case "AddTodos":
                tabela=new Tabela();
                tabela.addTudoOrRemoveTudo(jTable2, jTable3);
                break;
            case "RemoveUm":
                tabela=new Tabela();
                tabela.addUmOrRemoveUm(jTable3, jTable2, "remover", "tabela 2");
                break;
            case "RemoveTodos":
                tabela=new Tabela();
                tabela.addTudoOrRemoveTudo(jTable3, jTable2);
                break;
            case "abrirDelimitador":
                Help help = new Help();
                JOptionPane.showMessageDialog(null, help.getDelimitador());
                break;
            case "abrirSobre":
                help = new Help();
                JOptionPane.showMessageDialog(null, help.getSobre());
                break;
            case "abrirNomeArquivo":
                help = new Help();
                JOptionPane.showMessageDialog(null, help.getCuringa());
                break;
        }
        
    
    }
    private void preencherTabela(){
        //preencher tabela de status
        listaStatus = new ArrayList<>();
        listaStatus = bean.retorna((String)comboStatus2.getSelectedItem());//pega o calor do combo e retorno a array que ela pertence
        listaNova = new TreeSet<>();
        for(int i=0; i<listaStatus.size(); i++){
            if(i!=0){
                listaNova.add(listaStatus.get(i).toUpperCase());
            }
        }
        Iterator iterator = listaNova.iterator();
        Tabela tabelas = new Tabela();
        tabelas.limparTabela(jTable3);
        tabelas.carregaTodos(jTable2, iterator);
        
    }
    
    public void refresh(){
        txView1.setText(bean.retorna((String)comboCodigo.getSelectedItem()).get(0));
        txView2.setText(bean.retorna((String)comboNome.getSelectedItem()).get(0));
        txView3.setText(bean.retorna((String)comboCNPJ.getSelectedItem()).get(0));
        txView4.setText(bean.retorna((String)comboStatus.getSelectedItem()).get(0));
        txView5.setText(bean.retorna((String)comboStatus2.getSelectedItem()).get(0));
                
    }
    
    private void Time(){
        Iniciar iniciar = new Iniciar();
        Thread time = new Thread(iniciar);
        time.start();
    }
    public class Iniciar implements Runnable{
        @Override
        public void run(){
            
        cliente = new ArrayList();
        Arquivo arquivo = new Arquivo();
        
        List<File> diretorio1 = arquivo.pegarArquivos(txCaminhoPDF.getText(), true, "pdf");
        List<File> diretorioOpcional=null;
        
        if(!txCaminhoOutros.getText().equals(""))
            diretorioOpcional = arquivo.pegarArquivos(txCaminhoOutros.getText(), false, null);
        
        Tabela tabela = new Tabela();
        tabela.limparTabela(jTable1);
        
        TreeSet<String> mapa;//pega fitro do status

        if(tabela.pegarNumeroDeLinhas(jTable3)>0)
            mapa = tabela.pegarValores(jTable3);
        else
            mapa = tabela.pegarValores(jTable2);
        
        int totalRegistros = 0;
        
        for(String status : bean.retorna((String)comboStatus.getSelectedItem())){
            if(mapa.contains(status.toUpperCase()))
                totalRegistros++;
        }
        
        
        int v = 0; //linha=v, coluna=i
        for(int h = 1; h<bean.retorna((String)comboCodigo.getSelectedItem()).size(); h++){
                String status=bean.retorna((String)comboStatus.getSelectedItem()).get(h);
                if(mapa.contains(status.toUpperCase())){
                    ArquivosBean ab = new ArquivosBean();
                    cliente.add(new ArrayList());
                    String codigo=bean.retorna((String)comboCodigo.getSelectedItem()).get(h);
                    String nome=pegarNovoNome(bean.retorna((String)comboNome.getSelectedItem()).get(h));
                    String cnpj=bean.retorna((String)comboCNPJ.getSelectedItem()).get(h);

                    ((ArrayList)cliente.get(v)).add(codigo);
                    ((ArrayList)cliente.get(v)).add(status);
                    ((ArrayList)cliente.get(v)).add(nome);
                    ((ArrayList)cliente.get(v)).add(cnpj);
                    String retornoNome = pegaNoNome(diretorio1, codigo, false, ab);
                    ((ArrayList)cliente.get(v)).add(retornoNome);//buscar codigo no nome
                    String retornoCNPJ = buscarNoConteudo(diretorio1, cnpj, true, ab);
                    //((ArrayList)cliente.get(v)).add(buscarNoConteudo(diretorio1, nome.trim(), false, ab));//não buscar nome no conteudo
                    ((ArrayList)cliente.get(v)).add(retornoCNPJ);//buscar cnpjnoconteudo
                    
                    if(!txCaminhoOutros.getText().equals("")){//se o caminho outros foi informado ele vai adicionar valor
                        ((ArrayList)cliente.get(v)).add(pegaNoNome(diretorioOpcional, 
                                cnpj.replace("/", "").replace("-", "").replace(".", ""), true, ab));//pegar cnpj do nome do arquivo
                    }
                    else{
                        ((ArrayList)cliente.get(v)).add("");
                    }
                    v++;
                    txStatus.setText("Processando " +v+ " de " +totalRegistros+" registros! Aguarde...");
                    
                }
                
        }      
        try{
            txStatus.setText("Concluido!!!");
            Thread.sleep(3000);
            txStatus.setText("");
            
        }catch(InterruptedException e){
            e.printStackTrace();
        }  
        tabela.preencherTabela(jTable1, cliente);
        ExcelDao excel = new ExcelDao();
        excel.exportToExcel(cliente);
        
//        for(int i = 0; i < cliente.size();i++){
//            for(int j = 0; j < ((ArrayList)cliente.get(i)).size(); j++){
//                System.out.print((String)((ArrayList)cliente.get(i)).get(j)+"\n");
//             }
//        System.out.println();
//        }
        }
    }
    private synchronized String pegaNoNome(List<File> arquivos, String valorProcurado, boolean cnpj, ArquivosBean ab){
        String encontrado = "Não Existe";
        String valor = ".";
        if(!txBuscarNome.getText().equals("")){
            valor = Normalizer.normalize(txBuscarNome.getText().toUpperCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        }
        for(int i = 0; i< arquivos.size(); i++){
            if(arquivos.get(i).isFile()){
                if(cnpj==true){
                    if(arquivos.get(i).getName().contains(valorProcurado)){
                        if(encontrado.equals("Não Existe"))
                            encontrado = arquivos.get(i).getName();
                        else
                            encontrado+=","+arquivos.get(i).getName();
                        ab.setArquivos(arquivos.get(i));
                    }
                    
                }
                else{//buscara nome
                    String arq = Normalizer.normalize(arquivos.get(i).getName().toUpperCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
                    if(arquivos.get(i).getName().toUpperCase().contains(valorProcurado) && arq.contains(valor)){//verifica se no nome do arquivo existe o valor procurado + dentro do filtro informado
                        if(encontrado.equals("Não Existe"))
                            encontrado = arquivos.get(i).getName();
                        else
                            encontrado+=","+arquivos.get(i).getName();
                        ab.setArquivos(arquivos.get(i));
                    }
                }
            }
        }
        
        
        return encontrado;
    }
    private synchronized String buscarNoConteudo(List<File> lista, String valorProcurado, boolean listar, ArquivosBean ab){
        String encontrado = "Não Existe";
        String valor =".";
        if(txBuscarNome.getText().equals("")){
            valor = Normalizer.normalize(txBuscarNome.getText().toUpperCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        }
        Set<File> list = ab.getArquivos();
        for(File f : list){
            if(f.isFile()){
                String arq = Normalizer.normalize(f.getName().toUpperCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
                if(!morto.contains(f) && arq.contains(valor)){
                    Leitora leitoraPDF = new Leitora();
                    if(leitoraPDF.verificarTexto(f, valorProcurado)){
                        if(encontrado.equals("Não Existe"))
                            encontrado = f.getName();
                        else
                            encontrado+=","+f.getName();
                        estaMorto(f);
                    }
                }
            }
        }
        
        if(listar==true && encontrado.equals("Não Existe")){// if(arqEncontrados.isEmpty() || encontrado.equals("Não Existe")){
            for(File f : lista){
                if(f.isFile()){
                    String arq = Normalizer.normalize(f.getName().toUpperCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
                    if(!morto.contains(f) && arq.contains(valor)){
                        Leitora leitoraPDF = new Leitora();
                        if(leitoraPDF.verificarTexto(f, valorProcurado)){
                            if(encontrado.equals("Não Existe"))
                                encontrado = f.getName();
                            else
                                encontrado+=","+f.getName();
                            estaMorto(f);
                        }
                    }
                }
            }
        }
        return encontrado;
    }
    public void estaMorto(File morto){
        this.morto.add(morto);
    }
        
    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String carregarArquivo(boolean mostrarArquivos, String title){
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(mostrarArquivos);
        chooser.setDialogTitle(title);
        String local = "";
        if(mostrarArquivos){
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Planilha do Excel", ".xls,.xlsx"));
            int retorno = chooser.showOpenDialog(null);
            if(retorno==JFileChooser.OPEN_DIALOG)
                local = chooser.getSelectedFile().getPath();
        }
        else{
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int retorno = chooser.showOpenDialog(null);
            if(retorno==JFileChooser.APPROVE_OPTION)
                local = chooser.getSelectedFile().getAbsolutePath();//
        }
        return local;
    }
    private boolean validarNumero(String valor){
        try{
            delimitador = Integer.parseInt(valor);
            return true;
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Numero informado no delimitaddor esta inválido, \n por favor corrija!");
            return false;
        }
    }
    private String pegarNovoNome(String nome){
        String[] valor = nome.split(" ");
        String novoValor="";
        if(valor.length>delimitador){
            for(int i=0; i<delimitador; i++){
                novoValor+=valor[i]+" ";
            }
            return novoValor;
        }
        else
            return nome;
    }
}
