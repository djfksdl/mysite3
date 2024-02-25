package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {
	//필드
	private Connection conn = null;
	private PreparedStatement pstmt= null;
	private ResultSet rs= null;
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
		 // 1. JDBC 드라이버(Oracle) 로딩
		Class.forName(driver);

		// 2. Connection 얻어오기
		 conn = DriverManager.getConnection(url, id, pw);
		 
		// 4.결과처리
		} catch (ClassNotFoundException e) {
		 System.out.println("error: 드라이버로딩실패-"+ e);
		 } catch (SQLException e) {
		 System.out.println("error:" + e);
		 } 
		
	}
	//종료
	public void close() {
		try {
		 if (rs!= null) {
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
	public List<BoardVo> selectAll() {
		//리스트 만들기
		List<BoardVo> boardList = new ArrayList<BoardVo>();
		
		this.getConnection();
		try {
		//sql문 준비
		String query = "";
		query += " select b.no " ;
		query += " 		 ,title " ;
		query += " 		 ,u.name " ;
		query += " 		 ,hit " ;
		query += " 		 ,b.reg_date " ;
		query += " from board b join users u " ;
		query += " where b.user_no = u.no " ;
		
		//바인딩
		pstmt = conn.prepareStatement(query);
		//실행
		rs = pstmt.executeQuery();
		//반복문으로 리스트 출력. 쿼리문에서 값들 각각 저장후 리스트에 저장하기
		while(rs.next()) {
			int no = rs.getInt("b.no");
			String title = rs.getString("title");
			String name = rs.getString("u.name");
			int hit = rs.getInt("hit");
			String reg_date = rs.getString("b.reg_date");
			
			//vo로 묶기
			BoardVo boardVo = new BoardVo(no, title, name, hit, reg_date);
			//guestList에 넣어주기
			boardList.add(boardVo);
			
		}
		
		//결과
		}catch(SQLException e) {
			System.out.println("error:" +e);
		}
		this.close();
		return boardList;
		
	}
	//----해당 게시글 불러오기(selectOne)
	public List<BoardVo> selectOne(int no) {
		List<BoardVo> oneList = new ArrayList<BoardVo>();
		this.getConnection();
		try {
			//sql문 준비
			String query="select title, content, u.name, hit, b.reg_date "
                    + "from board b "
                    + "left join users u on b.user_no=u.no "
                    + "where b.no=?";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			//실행
			while(rs.next()) {
				String name = rs.getString("u.name");
				int hit = rs.getInt("hit");
				String reg_date = rs.getString("b.reg_date");
				String title = rs.getString("title");
				String content = rs.getString("content");
				
				//vo로 묶기
				BoardVo boardVo = new BoardVo(name, hit, reg_date, title, content);
				oneList.add(boardVo);
			}
			
		}catch(SQLException e) {
			System.out.println("error:" +e);
		}
		this.close();
		return oneList;
	}
	//----delete
	public void delete(int num) {
		int count =-1;
		this.getConnection();
		try {
		//3.
			//sql문 준비
			String query = "";
			query += " delete ";
			query += " from board ";
			query += " where user_no=? ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, num );
			
			//실행
			count =pstmt.executeUpdate();
			System.out.println(count + "건이 삭제 되었습니다.");
		//결과
		}catch(SQLException e) {
			System.out.println("error:" +e);
		}
		this.close();
	}
	// ----insert
	public void insert(BoardVo boardVo) {
		int count =-1;
		this.getConnection();
		try {
			//sql문 작성
			String query ="";
			query += " insert into board ";
			query += " values(null, ?,? ,?, now(), ?) ";
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardVo.getTitle() );
			pstmt.setString(2, boardVo.getContent() );
			pstmt.setInt(3, boardVo.getHit());
			pstmt.setInt(4,boardVo.getNo());//이거 왜 getUser_num이 아닌지 궁금?
			
			//실행
			count = pstmt.executeUpdate();
			System.out.println(count + "건이 등록되었습니다.");
			
		}catch(SQLException e) {
			System.out.println("error:" +e);
		}
		this.close();
	}
}//Dao끝
