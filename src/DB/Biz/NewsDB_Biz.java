package DB.Biz;
import static common.JdbcTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

import entity.NewsSentence;
import DB.DAO.NewsDB_DAO;

public class NewsDB_Biz 
{
	Connection conn;
	public NewsDB_Biz()
	{
		conn = getConnection();
	}
	


	public ArrayList<NewsSentence> selectAll() {
		ArrayList<NewsSentence> result;
		conn=getConnection();
		NewsDB_DAO dao = new NewsDB_DAO(conn);
		
		result = dao.selectAll();
		
		close(conn);
		
		return result;
	}
	
	
	
}
