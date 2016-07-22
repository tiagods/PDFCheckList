package br.com.tiagods.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javax.swing.JLabel;
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
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally{
            try{if(inputStream!=null)inputStream.close();}catch(IOException e){}
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
}
