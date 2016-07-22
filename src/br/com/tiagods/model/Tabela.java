/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiagods.model;

import java.util.Iterator;
import java.util.TreeSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tiago
 */
public class Tabela {
    
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

}
