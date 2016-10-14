package br.com.tiagods.model;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.text.PDFTextStripper;


public class LeitoraPdf {
	
	StringBuilder sb = new StringBuilder();
    private int totalPages;
	
	public boolean verificarTexto(File arquivo, String texto){
            PDDocument pdfDocumento = null;
            try{    
                    String extensao = arquivo.getName().substring(arquivo.getName().lastIndexOf(".")+1);
                    if(extensao.equals("pdf")){
                        pdfDocumento = PDDocument.load(arquivo);
                        this.totalPages = pdfDocumento.getNumberOfPages();
                        
                        
                        PDFTextStripper stripper = new PDFTextStripper();
                        sb.append(stripper.getText(pdfDocumento));
                        
                        if(sb.toString().toUpperCase().contains(texto.toUpperCase()))
                            return true;
                    }
                    else
                        return false;
            }catch(IOException e){
                    e.printStackTrace();
            }finally{
                    if(pdfDocumento != null) try{pdfDocumento.close();}catch(IOException e){}
            }
            return false;
    }
    public int totalPages(){
    	return this.totalPages;
    }
    
    public boolean conseguiLer(){
    	return !sb.toString().trim().equals("");
    }
}