/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiagods.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**imp
 *
 * @author Tiago
 */
public class Tabela {
    public void preencherTabela(JTable table, ArrayList lista){
        DefaultTableModel tbm = (DefaultTableModel)table.getModel();
        int linha =0;
        while(linha<lista.size()){
            tbm.addRow(new Object[1]);
            Iterator<String> iterator = ((ArrayList)lista.get(linha)).iterator();
            int coluna = 0;
            boolean achei = false;
            while(iterator.hasNext()){
            	String valor = iterator.next();
            	if(coluna==4){//se coluna for 4 retornará verdadeiro para a proxima coluna
            		if(!valor.equals("Não Existe")){
            			achei = true;
            		}
            	}//enviar novo valor para a coluna de acordo com o criterio recebido
            	if(coluna==5 && achei==true){
            		if(valor.equals("Não Existe")){
            			valor="Erro na Leitura do Arquivo PDF";
            		}
            	}
            	tbm.setValueAt(valor, linha, coluna);
                coluna++;
            }
            linha++;
            
        }
        
    }
    public int pegarNumeroDeLinhas(JTable table){
        DefaultTableModel tbm = (DefaultTableModel)table.getModel();
        return tbm.getRowCount();
    }
    public TreeSet<String> pegarValores(JTable table){
        DefaultTableModel tbm = (DefaultTableModel)table.getModel();
        int i=0;
        TreeSet<String> mapa = new TreeSet<>();
        while(i<tbm.getRowCount()){
            mapa.add((String)tbm.getValueAt(i, 0));
            i++;
        }
        return mapa;
    }
    
    public void carregaTodos(JTable table, Iterator iterator){
        table.getColumnModel().getColumn(0);
        DefaultTableModel tbm = (DefaultTableModel)table.getModel();
        limparTabela(table);
        int i = 0;
        do{
            tbm.addRow(new String[1]);
            tbm.setValueAt(iterator.next(), i, 0);
            i++;
        }while(iterator.hasNext());
}
public void limparTabela(JTable table){
    DefaultTableModel tbm = (DefaultTableModel)table.getModel();
    for(int i=tbm.getRowCount()-1; i>=0; i--){
        tbm.removeRow(i);
    }
}
public void addTudoOrRemoveTudo(JTable tabela1, JTable tabela2){
    DefaultTableModel tb1 = (DefaultTableModel)tabela1.getModel();
    DefaultTableModel tb2 = (DefaultTableModel)tabela2.getModel();
   
    while(tb1.getRowCount()>0){
        int linha = tb2.getRowCount();

        for(int i=0; i<tb1.getRowCount(); i++){
            String codigo = (String) tb1.getValueAt(i, 0);
            
            tb2.addRow(new Object[1]);
            tb2.setValueAt(codigo, linha, 0);
            linha++;

            tb1.removeRow(i);
        }
    }
}
public void addUmOrRemoveUm(JTable tabela1, JTable tabela2, String action, String tabela){
    DefaultTableModel tb1 = (DefaultTableModel)tabela1.getModel();
    DefaultTableModel tb2 = (DefaultTableModel)tabela2.getModel();
    
    if(tb1.getRowCount()>0){
        int linha = tabela1.getSelectedRow();
        if(linha!=-1){
            String codigo = (String) tabela1.getValueAt(linha, 0);
            
            int linhaAux = tabela2.getRowCount();
            tb2.addRow(new Object[1]);
            tb2.setValueAt(codigo, linhaAux, 0);
            tb1.removeRow(linha);
        }
        else
            JOptionPane.showMessageDialog(null, "Selecione um valor da "+tabela+" para "+action+"!");
    }
    else
        JOptionPane.showMessageDialog(null, "Não há valores na tabela para "+action+"!");
}
public List<CadastroBean> pegarDadosTabela(JTable tabela){
    DefaultTableModel tb = (DefaultTableModel)tabela.getModel();
    List<CadastroBean> lista = new ArrayList();
    
    for(int i =0; i<tb.getRowCount(); i++){
        CadastroBean cb = new CadastroBean();
        cb.setCodigo((String)tb.getValueAt(i, 0));
        cb.setStatus((String)tb.getValueAt(i, 1));
        cb.setNome((String)tb.getValueAt(i, 2));
        cb.setCnpj((String)tb.getValueAt(i, 3));
        cb.setStatusCodigo((String)tb.getValueAt(i, 4));
        cb.setStatusCnpj((String)tb.getValueAt(i, 5));
        cb.setObservacao((String)tb.getValueAt(i, 6));
        lista.add(cb);
    }
    return lista;
}
}
