package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Util.Crawl;
import Util.DBConn;
import Util.JsonUtil;
import VO.NewsVO;


public class Crawling extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public Crawling() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			JsonUtil jUtil = new JsonUtil();
			ArrayList<String> keyword = null;
			int lastPage = 1;
			int size = 0;
			int result = 0;
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			// Query
			String insertSql = "INSERT INTO MA_NEWS("
					+ "TITLE, REQUESTURL, DATE, CONTENT, MEDIA, KEYWORD) "
					+ "VALUES(?, ?, ?, ?, ?, ?) "
					+ "ON DUPLICATE KEY UPDATE TITLE = ?, REQUESTURL = ?, "
					+ "DATE = ?, CONTENT = ?, MEDIA = ?, KEYWORD = ?";
			
			
			try {
				conn = DBConn.getConnection();
				
				keyword = jUtil.getKeyword();
				size = keyword.size();
				
				for (int i = 0; i < size; i++) {
					// 키워드 수만큼 반복
					Crawl cw = new Crawl(keyword.get(i));
					
					pstmt = conn.prepareStatement(insertSql);
					
					ArrayList<String> reqURL = cw.getUrlList(lastPage);
					ArrayList<NewsVO> newsList = cw.getNewsList(reqURL);
					
					int newsListSize = newsList.size();
					
					for (int j = 0; j < newsListSize; j++) {
						NewsVO news = newsList.get(j);
						
						String newsDate = news.getDate();
						Date date = Date.valueOf(newsDate);
						
						pstmt.setString(1, news.getTitle());
						pstmt.setString(2, news.getRequestURL());
						pstmt.setDate(3, date);
						pstmt.setString(4, news.getContent());
						pstmt.setString(5, news.getMedia());
						pstmt.setString(6, news.getKeyword());
						pstmt.setString(7, news.getTitle());
						pstmt.setString(8, news.getRequestURL());
						pstmt.setDate(9, date);
						pstmt.setString(10, news.getContent());
						pstmt.setString(11, news.getMedia());
						pstmt.setString(12, news.getKeyword());
						
						result = pstmt.executeUpdate();
					}
				}
				
				//redirect
				System.out.println(">>>> 레코드 등록 성공!!");
				response.sendRedirect("/ExternalService/main?success=true");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("번호 중첩 or 크롤링 에러 발생!");
				response.sendRedirect("/ExternalService/main?success=false");
			}
			
			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
