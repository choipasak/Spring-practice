package com.spring.basic.score.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.spring.basic.score.entity.Grade;
import com.spring.basic.score.entity.Score;

import lombok.RequiredArgsConstructor;

@Repository("spring") // 이젠 이 레파지토리 쓸거임!!
@RequiredArgsConstructor // 이거 안쓰면 JdbcTemplate 매개 값으로 받는다는 생성자 만들어야함.
public class ScoreSpringRepository implements IScoreRepository {

	class ScoreMapper implements RowMapper<Score>{
		
	    //내부(중첩) 클래스 (inner class)
	    //두 클래스가 굉장히 긴밀한 관계가 있을 때 주로 선언.
	    //해당 클래스 안에서만 사용할 클래스를 굳이 새 파일로 선언하지 않고 만들 수 있습니다.
		@Override
		public Score mapRow(ResultSet rs, int rowNum) throws SQLException {

			//확인용
			System.out.println("mapRow 메서드 호출뵤");
			System.out.println("rowNUM: " + rowNum);

			Score score = new Score(
					rs.getInt("stu_num"), //실제 변수의 타입에 맞춰서 get~를 정해야함. stu_num은 Score파일에서 int임
					rs.getString("stu_name"),
					rs.getInt("kor"),
					rs.getInt("eng"),
					rs.getInt("math"),
					rs.getInt("total"),
					rs.getDouble("average"),
					Grade.valueOf(rs.getString("grade"))
					);

			return score;
		}

	}

	//여기서만 사용할 거여서 private
	private final JdbcTemplate template;

	@Override
	public List<Score> findAll() {
		String sql = "SELECT * FROM score";
		//rowMapper: 조회 된 정보를 포장한 객체(클래스)를 전달해주면서 이렇게 포장해라? 알려줘야함
		// template.query(sql, new ScoreMapper()); // ?가 있다면 sql, 하고 작성해 주고 + rowMapper를 구현한 객체를 전달해 줘야함
		//query(조회되는 행이 여러개일 때 사용)라는 객체가 List를 리턴함 -> new ScoreMapper()를 받아도 상관x 아니 받아야 함!
		//리턴이 List<Score> -> List선언해야 하는 것 아니냐? -> 아님. 그것도 JdbcTemplate이 해준다.

		//정리: rowMapper라는 조회된 정보를 포장한 객체를 생성해서 template.query()에게 전달해 준 것을 리턴@!
		return template.query(sql, new ScoreMapper());
		// template의 query메서드에게 그냥 ScoreMapper를 바로 생성해서 넘겨주고
		// template이 원래의 작업이었던 while문처럼 반복해서 데이터가 없을 때 까지 정보를 가져온다.
	}

	@Override
	public void save(Score score) {
		String sql = "INSERT INTO score VALUES(score_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
		template.update(sql, score.getStuName(), score.getKor(),
				score.getEng(), score.getMath(), score.getTotal(),
				score.getAverage(), String.valueOf(score.getGrade()));
		// 이렇게 작성하면 JdbcTemplate이 알아서 객체 호출, 사용, 실행 다~해줌
		// 그리고 update라는 객체도 리턴이 int임 
	}

	@Override
	public Score findByStuNum(int stuNum) {

		String sql = "SELECT * FROM score WHERE stu_num = ?";
		//객체 1개만 가지고 올 때는 queryForObject()를 사용, 리턴타입: Score
		// 얘도 포장을 해야하기 때문에 ScoreMapper()가 필요하다!
		//template.queryForObject(sql, new ScoreMapper(), stuNum);

		try {
			//queryForObject는 없는 PK를 조회하면 값이 없다는 예외가 터짐(성질임) -> 예외 처리()
			//try-catch를 작성하는 이유: queryForObject는 조회 결과가 없을 시 예외가 발생합니다.
			return template.queryForObject(sql, new ScoreMapper(), stuNum);

		} catch (Exception e) {
			//조회 결과가 없다면 catch문이 실행되게 하겠다(예외 처리)
			return null; // null을 전달해서 마지막 단계의 화면 페이지인 score-detail에서 if문으로 조회결과가 없다고 해줘야함
		}
	}

	@Override
	public void deleteByStuNum(int stuNum) {
		String sql = "DELETE FROM score WHERE stu_num = ?";
		template.update(sql, stuNum);
	}

	@Override
	public void modify(Score modScore) {
		// 수정 페이지를 보면 국, 영, 수 점수를 수정함, 국영수 점수를 수정하면 총점이랑 평균 성적이 다 바뀌니까 SET에 다 적어줘야 한다.
		String sql = "UPDATE score SET kor = ?, eng = ?, math = ?, total = ? , average = ?, grade = ? WHERE stu_num = ?";
		template.update(sql, modScore.getKor(), modScore.getEng(), modScore.getMath(), modScore.getTotal(), modScore.getAverage(), String.valueOf(modScore.getGrade()), modScore.getStuNum());

	}

}
