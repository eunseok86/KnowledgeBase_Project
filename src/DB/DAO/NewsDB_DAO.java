package DB.DAO;
import java.sql.*;
import java.util.ArrayList;

import entity.NewsSentence;
import static common.JdbcTemplate.*;


public class NewsDB_DAO{
	PreparedStatement pstmt;
	ResultSet rs;
	Connection conn;
	
	public NewsDB_DAO()
	{
	}
	
	public NewsDB_DAO(Connection conn)
	{
		this.conn=conn;
	}
	
	
	public ArrayList<NewsSentence> selectAll() {
		ArrayList<NewsSentence> arr = new ArrayList<NewsSentence>();
		String sql="exec sp_GetNewsParagraphByDate '20130810', '2013081003'";
		
		
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				NewsSentence sentenceData = new NewsSentence(rs.getString(1), rs.getString(2), rs.getString(3));
				arr.add(sentenceData);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close(rs);
			close(pstmt);
		}
		
		return arr;
	}


}
