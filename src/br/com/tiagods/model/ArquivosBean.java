/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiagods.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tiago
 */
public class ArquivosBean implements Serializable{
    private final List<File> arquivos = new ArrayList();
    /**
     * @return the arquivos
     */
    public List<File> getArquivos() {
        return arquivos;
    }

    /**
     * @param arquivos the arquivos to set
     */
    public void setArquivos(File arquivos) {
        this.arquivos.add(arquivos);
    }
    
    
}
