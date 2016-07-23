package br.com.tiagods.model;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;


public class Leitora {
    public boolean verificarTexto(File arquivo, String texto){
            PDDocument pdfDocumento = null;
            try{    
                    String extensao = arquivo.getName().substring(arquivo.getName().lastIndexOf(".")+1);
                    if(extensao.equals("pdf")){
                        pdfDocumento = PDDocument.load(arquivo);
                        PDFTextStripper stripper = new PDFTextStripper();
                        StringBuilder sb = new StringBuilder();
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
}
