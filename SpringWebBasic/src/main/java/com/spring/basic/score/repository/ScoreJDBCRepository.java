package com.spring.basic.score.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.spring.basic.score.entity.Grade;
import com.spring.basic.score.entity.Score;

/*
 생성 이유: DB사용 해 볼라고 + 서비스는 레파지토리가 반드시 필요함
 그래서 처음에는 @RequiredArgsConstructor를 통해 레파지토리 파일로 저장하는 척 하는 객체(IScoreRepository)를 사용했었음
 근데 지금은 이 파일을 만듦으로서 IScoreRepository로 등록되어 있는 타입이 2개가 됬다.
 -> 사용 시에는 생성자에 매개값을 입력해주면서 @Qualifier("jdbc")를 적어주면서
 IScoreRepository타입 변수를 받아야 함 -> 사실 원래 사용하던 레파지토리 파일 지우면 이럴 일 없음
 근데 우린 이제 DB를 배웠음 -> 지금 파일을 DB랑 연결함(DB 접속에 필요한 정보들을 변수화 시켜서)
 */


@Repository("jdbc")
public class ScoreJDBCRepository implements IScoreRepository {
	
	
	//DB 접속에 필요한 정보들을 변수화. (DB 주소, 계정명, 비밀번호) - 고정
	private String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 리스너가 볼 주소
	private String username = "hr";
	private String password = "hr";
	
	//finally에서 객체를 종료하기 위해서 밖에서 변수 따로 선언!
	private Connection conn = null; 
	private PreparedStatement pstmt = null; // 이 클래스에서만 사용할거여서 private를 사용함
	private ResultSet rs = null;
	
	// 원래 DB 연동을 전담하는 객체는 무분별한 객체 생성을 막기 위해
	// 싱글톤 디자인 패턴을 구축하는 것이 일반적.
	// 하지만 우리는 Spring Framework를 사용 중 -> 컨테이너 내의 객체들을 기본적으로 Singleton으로 유지.
	// 그래서 굳이 할 필요x
	
	// 생성자를 이용해서 객체(OracleDriver)가 생성될 때, 오라클 데이터베이스 커넥터 드라이버를 강제 구동해서 연동 준비.
	public ScoreJDBCRepository() {
		
		//드라이버(객체들이 드나들 수 있는 길 뚫는다 의미) 클래스를 호출하기 위한 생성자 -> DB연결 되면 객체끼리 드나들게 길 뚫는 과정
		try {
			// Maven에서 ojdbc6을 받아왔음! (OracleDriver 쓸라고!)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 오라클이 제공하는 클래스인 OracleDriver를 자바의 클래스인 Class.forName[객체생성없이 클래스 이름으로 부를 수 있는 클래스]에게 위치를 전달해서 호출
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} // OracleDriver강제구동 -> 가동 시켜야 DB와 연결해서 모든 일들을 할 수 있음
		
	}
	
	////////////////////// 여기까지 DB연동 준비! //////////////////////
	
	@Override
	public List<Score> findAll() {
		
		//조회된 정보를 모두 담아서 한번에 리턴하기 위한 리스트. [미리 선언 -> 밑의 while문에서 사용하려고]
		List<Score> scoreList = new ArrayList<>();
		
		//조회해서 다 가지고 온다음 List로 return해주자!
		//목록 요청이와서 이거 완성시키러 왔음
		
		//1.
		String sql = "SELECT * FROM score"; // 검색요청이였으면 ?가 들어갔을테지만, 조회여서 ?가 없음
		
		try {
			
			// 2.
			conn = DriverManager.getConnection(url, username, password);
			
			//3.
			pstmt = conn.prepareStatement(sql);
			
			//4. ? 없어서 생략
			//5. 실행하고자 하는 sql이 SELECT인 경우에는
			// INSERT, UPDATE, DELETE와는 다른 메서드를 호출합니다.
			//메서드의 실행 결과는 sql의 조회 결과를 들고 있는 객체 ResultSet이 리턴됩니다.
			rs = pstmt.executeQuery(); //ResultSet 타입을 리턴함
			//ResultSet이 들고 있는 조회된 정보가 몇갠지 모름 -> 메서드로 물어봐야함(조회할 데이터 있?) -> 조회된 결과에서 첫번째 행 한개만 리턴해줌 -> 이걸 score객체로 포장해야 함 -> 이걸 반복(무한) -> 가져올 테이터가 없으면 그만둔다.
			
			while(rs.next()) { //rs.next() -> 리턴타입: boolean, 조회된 행이 하나라도 존재한다면 true, 존재하지 않는다면 false.
				//타겟으로 잡힌 행의 데이터를 얻어옵니다.
				Score s = new Score(
							rs.getInt("stu_num"), //실제 변수의 타입에 맞춰서 get~를 정해야함. stu_num은 Score파일에서 int임
							rs.getString("stu_name"),
							rs.getInt("kor"),
							rs.getInt("eng"),
							rs.getInt("math"),
							rs.getInt("total"),
							rs.getDouble("average"),
							//rs.getString("grade") // grade -> enum / DB에서의 grade -> VARCHAR2 : VARCHAR2, String -> enum인 Grade 
							Grade.valueOf(rs.getString("grade")) // Grade타입으로 형변환 시켜준 것임
						);
				scoreList.add(s);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return scoreList;
		
	}

	// 테이블에 정보 INSERT하는 메서드
	@Override
	public void save(Score score) {
		//DB연동 어떻게 하는지~
		//예전엔 이렇게 했었다
		
		
		
		try {
			// 1. sql을 문자열로 준비해 주세요.
			// 변수 또는 객체(Score)에 들어있는 값으로 sql을 완성해야 한다면, 해당 자리에 ?를 찍어 주세요.
			String sql = "INSERT INTO score VALUES(score_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
			
			// 2. DB에 접속을 담당하는 Connection 객체를 메서드를 통해 받아옵니다.(new 사용x)
			// 접속 정보를 함께 전달합니다.
			conn = DriverManager.getConnection(url, username, password); //오라클에서 제공하는 것/ 예외처리가 강제되어 있어서 TRY-CATCH 필요!
			// conn이 나 대신 DB에 접속할 수 있는 대리인(DB의 접속 까지만. 실행하는 얘는 따로있음!). 이다!
			// 커넥션 객체를 받아서 밑에서 pstmt에게 넘겨줄거임!(pstmt가 DB에서의 실행객체니까)
			
			// 3. 이제 접속을 할 수 있게 됐으니, SQL을 실행할 수 있는 PreparedStatement를 받아옵시다!
			// 직접 생성하지 않고, 메서드를 통해 받아옵니다.
			pstmt = conn.prepareStatement(sql); // 맨 위에 선언한 sql 변수가 매개값
			
			// 4. sql이 아직 완성되지 않았기 때문에, 물음표를 채워서 sql을 완성 시킵니다.
			// sql을 pstmt에게 전달했기 때문에 pstmt 객체를 이용해서 ?를 채웁니다.
			// 채울 때 채울 데이터의 타입에 유의해 줘야 함(오라클타입,자바타입)
			pstmt.setString(1, score.getStuName()); //pstmt에게 일 시키는 거임
			pstmt.setInt(2, score.getKor());
			pstmt.setInt(3, score.getEng());
			pstmt.setInt(4, score.getMath());
			pstmt.setInt(5, score.getTotal());
			pstmt.setDouble(6, score.getAverage());
			pstmt.setString(7, String.valueOf(score.getGrade()));
			
			// 5. sql을 다 완성했다면, pstmt에게 sql을 실행하라는 명령을 내립니다.
			int rn = pstmt.executeUpdate(); //리턴 타입이 int -> sql 실행 성공 시 1, 실패 시 0 / 이걸로 트랜잭션 처리 가능(1: 커밋,0: 롤백)
			if(rn == 1) {
				System.out.println("INSERT 성공!");
			} else {
				System.out.println("INSERT 실패!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			// 6. sql 실행까지 마무리가 되었다면, 사용했던 객체를들 해제합니다.
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}

	@Override
	public Score findByStuNum(int stuNum) {
		return null;
	}

	@Override
	public void deleteByStuNum(int stuNum) {
		
		try {
			String sql = "DELETE FROM score WHERE stu_num = ?";
			conn = DriverManager.getConnection(url, username, password);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, stuNum); 
			
			pstmt.executeUpdate();
			// 여기서 원래 pstmt.executeUpdate(sql); 이라고 작성해 줬었음
			// 당연히 에러남: 실행한다고 executeUpdate해줬는데 ?가 대입된 문장이 아닌
			// "DELETE FROM score WHERE stu_num = ?"를 다시 줬기 때문에
			// 값이 부족하다고 에러남
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
		
		

	}

	@Override
	public void modify(Score modScore) {

	}

}
