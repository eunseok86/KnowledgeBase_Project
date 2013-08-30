package common;

import javax.naming.*;
import javax.sql.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/* Name: JdbcTemplate
 * DESC : DB�� �о���� ��ȯ�ϴ� �� ����� �����ϴ� UtilityŬ����
 * VER : 1.0
 * Copyright 2011 Kitri User
 */


public class JdbcTemplate {
	//������
	public JdbcTemplate(){
		
	}
	//Connection
	public static Connection getConnection(){
		Connection conn = null;
		try {
			Context initContext = new InitialContext();
			//Context envContext  = (Context)initContext.lookup("java:/comp/env");
			//DataSource ds = (DataSource)envContext.lookup("jdbc/myOracle");
			DataSource ds = (DataSource)initContext.lookup("java:/comp/env/jdbc/myMssql");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	//DB�� Connect�� �Ǿ����� ���θ� return
	public static boolean isConnection(Connection conn){
		boolean valid=true;
		try{
			if(conn==null || conn.isClosed()){
				valid=false;
			}
		}catch(SQLException e){
			valid=true;
			e.printStackTrace();
		}
		
		return valid;
	}
	//Connection ��ü ��ȯ----------------
	public static void close(Connection conn){
		if(isConnection(conn)){
			try{
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	//Statement or PreparedStatement��ü ��ȯ
	public static void close(Statement stmt){
		try{
			if(stmt != null) stmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	//ResultSet ��ü��ȯ------------------
	public static void close(ResultSet rs){
		try{
			if(rs != null) rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	//Ʈ������ó��(commit)----------------
	public static void commit(Connection conn){
		if(isConnection(conn)){
			try{
				conn.commit();
				System.out.println("commit OK");
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	//Ʈ������ó��(rollback)--------------
	public static void rollback(Connection conn){
		if(isConnection(conn)){
			try{
				conn.rollback();
				System.out.println("rollback OK");
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
}
