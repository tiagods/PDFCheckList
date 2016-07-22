
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tiago
 */
public class FilesTeste {
    static String extensao ="pdf";
    static List<File> lista = new ArrayList<>();
    static boolean filtrar = true;
    
    public static void main(String[] args){
        File diretorio = new File("C:\\Users\\Tiago\\Desktop\\04-2016");
        File[] files = diretorio.listFiles();
        for(File f : files){
            if(f.isDirectory()){
                carregar(f);
            }
            else{
                adicionar(f);
            }
        }
        
    }
    public static void carregar(File diretorio){
        File[] files = diretorio.listFiles();
        for(File f : files){
            if(f.isDirectory()){
                carregar(f);
            }
            else{
                adicionar(f);
            }
        }
    }
    public static void adicionar(File f){
        if(filtrar==true){
            int pos = f.toString().lastIndexOf(".");
            if(f.toString().substring(pos + 1).equals("pdf"))
                lista.add(f);
        }
        else
            lista.add(f);
    }
}
