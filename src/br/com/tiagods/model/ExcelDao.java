package br.com.tiagods.model;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import jxl.write.Label;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tiago
 */
public class ExcelDao {
    
    public void exportToExcel(ArrayList lista){
        
        File file = new File(System.getProperty("user.home")+"/Desktop/relatorio.xls");
        
        int row=0;
        //formato fuente para el contenido contenido
        WritableFont wf = new WritableFont( WritableFont.ARIAL, 12, WritableFont.NO_BOLD );
        WritableCellFormat cf = new WritableCellFormat(wf);    
        
        //Interfaz para una hoja de cálculo
        WritableSheet excelSheet = null;
        WritableWorkbook workbook = null;
        
        //Establece la configuración regional para generar la hoja de cálculo
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        
        try {
            workbook = Workbook.createWorkbook( file, wbSettings );
        } catch (IOException ex) {}
        workbook.createSheet( "Lista", 0 );
        excelSheet = workbook.getSheet(0);
        
        for(int i = 0; i< lista.size(); i++){
            Label codigo = null, status = null, nome = null, cnpj = null, 
                    statusCodigo = null, statusCNPJ = null, 
                    observacao = null;
            if(i==0){
                codigo= new Label(0 ,i, "Codigo" , cf );
                status = new Label(1 ,i, "Status"  , cf );
                nome = new Label(2, i, "Nome" , cf );
                cnpj  = new Label(3, i , "CNPJ" , cf );                  
                statusCodigo= new Label( 4, i, "Status Codigo" , cf );  
                statusCNPJ= new Label(5 ,i, "Status CNPJ"  , cf );
                observacao= new Label(6, i, "Outros Arquivos"  , cf );
            }
            else{
                codigo= new Label(0 ,i, (String)((ArrayList)lista.get(i)).get(0) , cf );
                status = new Label(1 ,i, (String)((ArrayList)lista.get(i)).get(1)  , cf );
                nome = new Label(2, i, (String)((ArrayList)lista.get(i)).get(2) , cf );
                cnpj  = new Label(3, i , (String)((ArrayList)lista.get(i)).get(3) , cf );                  
                statusCodigo= new Label( 4, i, (String)((ArrayList)lista.get(i)).get(4) , cf ); 
                statusCNPJ= new Label(5 ,i, (String)((ArrayList)lista.get(i)).get(5)  , cf );
                observacao= new Label(6, i, (String)((ArrayList)lista.get(i)).get(6)  , cf );
            }
            try {
                excelSheet.addCell( codigo );
                excelSheet.addCell( status );
                excelSheet.addCell( nome );
                excelSheet.addCell( cnpj );
                excelSheet.addCell( statusCodigo );
                excelSheet.addCell( statusCNPJ );
                excelSheet.addCell( observacao );
            } catch (WriteException ex) {
            System.err.println(  ex.getMessage() );
            } 
        }
        
        try {
            workbook.write();
            workbook.close();
            JOptionPane.showMessageDialog(null, "Um relatorio em excel foi gerado na sua area de trabalho com nome de Relatorio.xls");
        }catch (WriteException ex) {
           System.err.println(  ex.getMessage() );
        }catch (IOException ex) {
            System.err.println(  ex.getMessage() );
        }
}
}