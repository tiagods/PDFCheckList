package br.com.tiagods.utilitarios;

import java.util.Date;

public class DescricaoVersao {
	private String nome="CheckList Obrigações";
	private String versao="1.0";
	private String data="10.10.2016";
	/*
	 * Historico das versoes
	 * 1.0 - Versao Final Entregue
	 */
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @return the versao
	 */
	public String getVersao() {
		return versao;
	}
	
	public String getDate(){
		return data;
	}
	
}
