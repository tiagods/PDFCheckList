package br.com.tiagods.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.tiago.factory.ConnectionFactory;
import br.com.tiagods.utilitarios.DescricaoVersao;

public class VerificarAtualizacao {
	private String versaoDisponivel = "";
	
	public static void main(String[] args){
		try{
			Runtime.getRuntime().exec("java -jar update.jar");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String receberStatus(DescricaoVersao versao){
		Connection con = new ConnectionFactory().getConnection();
		if(con==null){
			return null;
		}
		else{
			try{
			PreparedStatement ps = con.prepareStatement("select * from ATUALIZACAO");
			ResultSet rs = ps.executeQuery();
			if(rs!=null){
				if(rs.last()){
					if(rs.getString(2).equals(versao.getVersao()))
						return "Atualizado";
					else{
						versaoDisponivel = rs.getString(2);
						return "Desatualizado";
					}
				}
			}
			}catch(SQLException e){
				return null;
			}finally{
				if(con!=null){
					try{con.close();}catch(SQLException e){}
				}
			}
		}
		return null;
	}
	public String versaoDisponivel(){
		return versaoDisponivel;
	}

}
