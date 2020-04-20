package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Util.DBConn;
import Util.JsonUtil;
import VO.NewsVO;

public class MainService extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MainService() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	System.out.println("Started init!");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// JSON 읽기 (새로고침)
		JsonUtil jUtil = new JsonUtil();
		ArrayList<String> keywordList = null;
		
		try {
			ArrayList<String> readJson = jUtil.getKeyword();
			if (readJson != null) {
				keywordList = readJson;
			}
		} catch (Exception e) {
			System.out.println(">> json 파일이 없는 에러!");
			e.printStackTrace();
		}
		
		// 데이터 간략보기 클릭시 넘길 리스트 값
		ArrayList<NewsVO> newsPreviewList = new ArrayList<NewsVO>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		conn = DBConn.getConnection();
		
		String SelectSql = "SELECT TITLE FROM MA_NEWS";
		
		try {
			pstmt = conn.prepareStatement(SelectSql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				NewsVO news = new NewsVO();
				
				news.setTitle(rs.getString("TITLE"));
				
				newsPreviewList.add(news);
			}
		} catch (SQLException e) {
			System.out.println("SELECT 쿼리 오류 발생!");
			e.printStackTrace();
		}
		
		// JSP 포워드
		String path = "/board/index.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		
		request.setAttribute("newsList", newsPreviewList);
		request.setAttribute("keywordList", keywordList);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
