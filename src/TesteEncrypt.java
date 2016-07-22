
import br.com.tiagods.model.Arquivo;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tiago
 */
public class TesteEncrypt {
    
    public static void main(String[] args){
	try {
            File file = new File("C:\\Users\\Tiago\\Desktop\\Cadastro.xls");
            InputStream inputStream = new FileInputStream(file);

            Biff8EncryptionKey.setCurrentUserPassword("PLKCONTRATOS");
            NPOIFSFileSystem fs = new NPOIFSFileSystem(file, true);
            HSSFWorkbook workbook = new HSSFWorkbook(fs.getRoot(), true);
            Biff8EncryptionKey.setCurrentUserPassword(null);

            Sheet sheet = null;
            
            Arquivo trata = new Arquivo();
            
            //workbook = trata.validarWorkbook(workbook, trata.pegarExtensao(file), inputStream);//tratar e validar extensão de arquivo
            
            sheet = workbook.getSheetAt (0);//pega a primeira pasta de trabalh
            Iterator linhas = sheet.rowIterator();
            while(linhas.hasNext()){
                
                Row linha = (Row) linhas.next();
                
                Iterator celulas = linha.cellIterator();//interator para as celulas de cada linha
                
            	while(celulas.hasNext()){
                    Cell celula = (Cell) celulas.next(); //pega cada cada coluna
                    System.out.println(trata.tratarTipo(celula));
                }
            }
            inputStream.close();//fechando arquivo        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
