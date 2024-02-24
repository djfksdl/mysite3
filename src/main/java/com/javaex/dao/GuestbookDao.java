package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestbookVo;

public class GuestbookDao {
	//필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/web_db";
	private String id = "web";
	private String pw = "web";
	
	
	//생성자
	//메소드-gs
	//메소드-일반
	//연결
	public void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);
			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

			
			// 4.결과처리
			} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
			} catch (SQLException e) {
			System.out.println("error:" + e);
			}
	}
	
	//종료
	public void close() {
		// 5. 자원정리
		try {
		if (rs != null) {
		rs.close();
		} 
		if (pstmt != null) {
		pstmt.close();
		}
		if (conn != null) {
		conn.close();
		}
		} catch (SQLException e) {
		System.out.println("error:" + e);
		}
	}
		
	//전체 가져오기
	public List<GuestbookVo> guestSelect() {
		
		//리스트 만들기
		List<GuestbookVo> guestList = new ArrayList<GuestbookVo>();
		
		this.getConnection();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			//sql문 준비
			String query = "";
			query += " select no ";
			query += "  ,name ";
			query += "  ,password ";
			query += "  ,content ";
			query += "  ,reg_date ";
			query += " from guestbook ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			
			//실행
			rs = pstmt.executeQuery() ;
			
			//리스트에 값 넣기
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String pw = rs.getString("password");
				String content = rs.getString("content");
				String reg_date = rs.getString("reg_date");
				
				//db에서 가져온 데이터를 vo로 묶기
				GuestbookVo guestVo = new GuestbookVo(no, name, pw, content,reg_date);
				guestList.add(guestVo);
			}
			
		}catch(SQLException e) {
			System.out.println("error:" +e);
		}
		this.close();
		//만든 리스트를 return 해준다.
		return guestList;
	}//리스트 가져오기 끝
	
	//-----등록(inster)
	public int insert(GuestbookVo guestVo) {
		int count =-1;
		this.getConnection();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			//sql문 준비
			String query =" ";
			query += " insert into guestbook ";
			query += " values(null, ? ,?,? ,now()) ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, guestVo.getName());
			pstmt.setString(2, guestVo.getPw() );
			pstmt.setString(3, guestVo.getContent());
			
			//실행
			count = pstmt.executeUpdate();

			//결과
			System.out.println(count+ "건이 등록되었습니다.");
		}catch(SQLException e) {
			System.out.println("error:" +e);
		}
		this.close();
		return count;
		
	}
	//------삭제(delete)
	public void delete(GuestbookVo guestVo) {
		int count = -1;
		this.getConnection();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			//sql문 준비
			String query =" ";
			query += " delete ";
			query += " from guestbook ";
			query += " where no=? ";
			query += " and password=? ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, guestVo.getNo());
			pstmt.setString(2, guestVo.getPw() );
			
			//실행
			count = pstmt.executeUpdate();

			//결과
			System.out.println(count+ "건이 등록되었습니다.");
		}catch(SQLException e) {
			System.out.println("error:" +e);
		}
		this.close();
	}
	
	
	

}//Dao 끝
