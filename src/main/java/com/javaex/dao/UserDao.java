package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {//DB관련
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
			//5.자원정리
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
	public int insertUser(UserVo userVo) {//보통 인서트하고 카운터로 return값 주니까 void대신 int!
		int count = -1;
		this.getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			//sql문 준비
			String query = "";
			query += " insert into users ";
			query += " values(null, ?,?,?,?) ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPw());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());
			
			//실행
			count = pstmt.executeUpdate();
			System.out.println(count + "건 등록되었습니다.");
		}catch(SQLException e) {
			System.out.println("error:" +e);
		}
		
		this.close();
		return count;
	}
	public UserVo selectUserByIdPw(UserVo userVo) {//authUser 태생은 UserVo인 자료형이다
		UserVo authUser = null; 
		
		this.getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			//sql문 준비
			String query = "";
			query += " select no ";
			query += " 		 ,name ";
			query += " 		 ,id ";
			query += " from users ";
			query += " where id= ? ";
			query += " and password=? ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPw() );

			//실행
			rs = pstmt.executeQuery() ;//select는 rs로 넘어옴
			
			//4.결과처리
			while(rs.next()) {
//				int no = rs.getInt("no");
//				String name = rs.getString("name");
//				UserVo userVo = new UserVo(no, name);
				int no = rs.getInt("no");
				String id = rs.getString("id");
				String name = rs.getString("name");
				
				
				authUser = new UserVo();//0x777- 생성자 딱 여기서만 쓸거같다. 하면 Vo에서 생성자 안만들고 set으로 집어넣는 방법이 있음!
				authUser.setNo(no);
				authUser.setName(name);
				
			}
			
		}catch(SQLException e) {
			System.out.println("error:" +e);
		}
		this.close();
		return authUser; //위에 정상적이지않으면. 데이터 없으면 while문 안들어가고 return authUser가 됨. 정상적이면 null->주소들어감
	}
	
	//getUser
	public UserVo getUser(UserVo num) {
		UserVo userVo = null;
		this.getConnection();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			//sql문 준비
			String query = "";
			query += " select no ";
			query += " 		 ,id ";
			query += " 		 ,password ";
			query += " 		 ,name ";
			query += " 		 ,gender ";
			query += " from users ";
			query += " where no= ? ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, num.getNo());
			

			//실행
			rs = pstmt.executeQuery() ;//select는 rs로 넘어옴
			
			//4.결과처리
			while(rs.next()) {
				int no = rs.getInt("no");
				String id = rs.getString("id");
				String pw = rs.getString("password");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				userVo = new UserVo(no, id, pw, name, gender);
			}
			
		}catch(SQLException e) {
			System.out.println("error:" +e);
		}
		this.close();
		return userVo;
	}
	//수정
	public int update(UserVo newVo) {
		int count=-1;
		this.getConnection();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			//sql문 준비
			String query = "";
			query += " update users ";
//			query += " set no=? ";
//			query += " set id =? ";
			query += " set password=? ";
			query += " 	  ,name=? ";
			query += " 	  ,gender=? ";
			query += " where no=? ";

			//바인딩
			pstmt = conn.prepareStatement(query);
//			pstmt.setInt(1, userVo.getNo());
//			pstmt.setString(1, newVo.getId() );
			pstmt.setString(1, newVo.getPw() );
			pstmt.setString(2, newVo.getName() );
			pstmt.setString(3, newVo.getGender() );
			pstmt.setInt(4, newVo.getNo() );
			
			//실행
			count = pstmt.executeUpdate() ;//select는 rs로 넘어옴
			
			//4.결과처리
			System.out.println(count +"건의 데이터가 수정되었습니다.");
			
		}catch(SQLException e) {
			System.out.println("error:" +e);
		}
		this.close();
		return count;
	}
}
