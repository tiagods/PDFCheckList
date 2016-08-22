package br.com.tiagods.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
/*
 * Autor : Tiago Dias
 */

public class PlanilhaDao {
    Iterator linhas = null;
    JLabel label;
    int records = 1;
    int rows = 0;
    
    public boolean lerPlanilha(PlanilhaBean bean, JLabel label, File file){
	InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);

            Workbook workbook = null;
            Sheet sheet;
            
            Arquivo trata = new Arquivo();
            workbook = trata.validarWorkbook(workbook, file,trata.pegarExtensao(file), inputStream);//tratar e validar extensão de arquivo
            
            if(file.getName().equals("Cadastro.xls") || file.getName().equals("Cadastro.xlsx"))
                Biff8EncryptionKey.setCurrentUserPassword(null);
            
            sheet = workbook.getSheetAt (0);//pega a primeira pasta de trabalh
            linhas = sheet.rowIterator();
            rows = sheet.getLastRowNum();
            
            records = 1; 
            
            this.label = label;
            
            Time();
            boolean encerrar = false;
            
            while(linhas.hasNext()){
                if(encerrar)break;
                
                Row linha = (Row) linhas.next();
                
                Iterator celulas = linha.cellIterator();//interator para as celulas de cada linha
                
            	while(celulas.hasNext()){
                    Cell celula = (Cell) celulas.next(); //pega cada cada coluna
                    //sair do loop se o codigo for vazio
                    if(records>1 && celula.getColumnIndex()==0){
                        if(trata.tratarTipo(celula).equals("")){
                            encerrar=true;
                            break;
                        }
                    }
                    trata.enviarValores(celula, bean);
                    
                }
                records++;
            }
            inputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    class Contador implements Runnable{
        @Override
        public void run(){
            try{
            for(int i = 0; i<=rows; i++){
                label.setText("Aguarde...carregando "+i+" linhas de "+rows);
                Thread.sleep(1);
            }
            Thread.sleep(3000);
            label.setText("Concluido");
            Thread.sleep(3000);
            label.setText("");
            }catch(InterruptedException e){}
        }
    }
    void Time(){
        Contador contador = new Contador();
        Thread thread = new Thread(contador);
        thread.start();
    }
    public void exportToExcel(List<CadastroBean> lista, File arquivo){
        
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
            workbook = jxl.Workbook.createWorkbook( arquivo, wbSettings );
        } catch (IOException ex) {}
        workbook.createSheet( "Relatorio", 0 );
        excelSheet = workbook.getSheet(0);
        
        Label codigo= new Label(0 ,0, "Codigo" , cf );
        Label status = new Label(1 ,0, "Status"  , cf );
        Label nome = new Label(2, 0, "Nome" , cf );
        Label cnpj  = new Label(3, 0 , "CNPJ" , cf );                  
        Label statusCodigo= new Label( 4, 0, "Status Codigo" , cf );  
        Label statusCNPJ= new Label(5 ,0, "Status CNPJ"  , cf );
        Label observacao= new Label(6,0, "Arquivos Extras"  , cf );
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
        int j=1;
        
        for(CadastroBean cb : lista){
            	codigo= new Label(0 ,j, cb.getCodigo(), cf );
                status = new Label(1 ,j, cb.getStatus(), cf );
                nome = new Label(2, j, cb.getNome(), cf );
                cnpj  = new Label(3, j , cb.getCnpj(), cf );                  
                statusCodigo= new Label( 4, j, cb.getStatusCodigo(), cf ); 
                statusCNPJ= new Label(5 , j, cb.getStatusCnpj(), cf );
                observacao= new Label(6, j, cb.getObservacao(), cf );
            try {
                excelSheet.addCell(codigo);
                excelSheet.addCell(status);
                excelSheet.addCell(nome);
                excelSheet.addCell(cnpj);
                excelSheet.addCell(statusCodigo);
                excelSheet.addCell(statusCNPJ);
                excelSheet.addCell(observacao);
            } catch (WriteException ex) {
            System.err.println(  ex.getMessage() );
            } 
            j++;
        }
        try {
            workbook.write();
            workbook.close();
            JOptionPane.showMessageDialog(null, "Arquivo gerado com sucesso");
        }catch (WriteException ex) {
           System.err.println(  ex.getMessage() );
        }catch (IOException ex) {
            System.err.println(  ex.getMessage() );
        }
}
}
