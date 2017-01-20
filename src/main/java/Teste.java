import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Teste {
	public static void main(String[] args){
        PDDocument pdfDocumento = null;
        try{    
        	pdfDocumento = PDDocument.load(new File("T:\\DeptoPessoal\\GFIP\\GFIP CONFERÊNCIA\\ATUAL\\12.2016\\1893- PROTOCOLO.pdf"));

        	PDFTextStripper stripper = new PDFTextStripper();
        	StringBuilder sb = new StringBuilder();
        	sb.append(stripper.getText(pdfDocumento));
        	System.out.println(sb.toString());
        	StringTokenizer st = new StringTokenizer(sb.toString(),"\n");
        	
        	int linha = 0;
        	int i = 0;
        	String param = "";
        	while(st.hasMoreTokens()){
        		String frase = st.nextToken();
        		if(frase.toUpperCase().contains("NRA: ")){
        			param = frase;
        			linha = i;
        		}
        		i++;
        	}
        	System.out.println("Achei o valor: "+param+" na linha "+i);
        	pdfDocumento.close();
        	pdfDocumento = PDDocument.load(new File("T:\\DeptoPessoal\\GFIP\\GFIP CONFERÊNCIA\\ATUAL\\12.2016\\1893- DEMONSTRATIVO.pdf"));
        	sb = new StringBuilder();
        	sb.append(stripper.getText(pdfDocumento));
        	
        	param = param.replace("NRA:","").trim();
        	System.out.println("\nArquivo 2\n\n"+sb.toString()+"\n");
        	if(sb.toString().replace("-", "").contains(param)){
        		System.out.println("Encontrei o valor "+param+" no arquivo 1893 - DEMOSTRATIVO");
        	}
        	
        }catch(IOException e){
                e.printStackTrace();
        }finally{
                if(pdfDocumento != null) try{pdfDocumento.close();}catch(IOException e){}
        }
}
}
