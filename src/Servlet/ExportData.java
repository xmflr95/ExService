package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Util.DBConn;
import Util.JsonUtil;
import VO.NewsVO;

public class ExportData extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ExportData() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<NewsVO> newsList = new ArrayList<NewsVO>();
		JsonUtil jutil = new JsonUtil();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		conn = DBConn.getConnection();
		
		String selectSql = "SELECT TITLE, REQUESTURL, DATE, CONTENT, MEDIA, KEYWORD FROM MA_NEWS";
		
		try {
			pstmt = conn.prepareStatement(selectSql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				NewsVO news = new NewsVO();
				
				news.setTitle(rs.getString("TITLE"));
				news.setRequestURL(rs.getString("REQUESTURL"));
				news.setDate(rs.getDate("DATE").toString());
				news.setContent(rs.getString("CONTENT"));
				news.setMedia(rs.getString("MEDIA"));
				news.setKeyword(rs.getString("KEYWORD"));
				
				newsList.add(news);
			}
			
			// news.json 내보내기
			jutil.setKeyword(newsList);
		} catch (SQLException e) {
			System.out.println("SELECT 쿼리 오류 발생!");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("JSON 변환 오류!");
			e.printStackTrace();
		}
		
		// redirect
		response.sendRedirect("/ExternalService/main?exportSuccess=true");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
