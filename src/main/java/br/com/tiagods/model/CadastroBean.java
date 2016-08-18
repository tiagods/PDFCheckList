/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiagods.model;

import java.io.Serializable;

/**
 *
 * @author Tiago
 */
public class CadastroBean implements Serializable{
    private String codigo;
    private String status;
    private String nome;
    private String cnpj;
    private String statusCodigo;
    private String statusCnpj;
    private String observacao;

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the cnpj
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * @param cnpj the cnpj to set
     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }  

    /**
     * @return the statusCodigo
     */
    public String getStatusCodigo() {
        return statusCodigo;
    }

    /**
     * @param statusCodigo the statusCodigo to set
     */
    public void setStatusCodigo(String statusCodigo) {
        this.statusCodigo = statusCodigo;
    }

    /**
     * @return the statusCnpj
     */
    public String getStatusCnpj() {
        return statusCnpj;
    }

    /**
     * @param statusCnpj the statusCnpj to set
     */
    public void setStatusCnpj(String statusCnpj) {
        this.statusCnpj = statusCnpj;
    }

    /**
     * @return the observacao
     */
    public String getObservacao() {
        return observacao;
    }

    /**
     * @param observacao the observacao to set
     */
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
