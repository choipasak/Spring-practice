package com.spring.basic.score.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.basic.score.controller.ScoreController;
import com.spring.basic.score.dto.ScoreListResponseDTO;
import com.spring.basic.score.dto.ScoreRequestDTO;
import com.spring.basic.score.entity.Score;
import com.spring.basic.score.repository.IScoreRepository;
import com.spring.basic.score.repository.ScoreRepositoryImpl;

import lombok.RequiredArgsConstructor;

//컨트롤러와 레파지토리 사이에 배치되어 기타 비즈니스 로직 처리
//ex) 값을 가공, 예외 처리, dto로 변환, 트랜잭션 등등 ...

@Service //서비스 계층은 이렇게 빈 등록 해준다! <- @Controller와 다른점: 없음. 이름만 다름.
@RequiredArgsConstructor
public class ScoreService {


	private final IScoreRepository scoreRepository;

	//score에서 받은 점수로 총합점과 평균을 내는 서비스계층
	//등록 중간처리
	//레파지토리에 데이터를 넘기기 전에 가공한다.
	//컨트롤러는 나에게 DTO를 줬어.
	//하지만, 온전한 학생의 정보를 가지는 객체는 -> Score(Entity)
	//그럼 내가 Entity를 만들어서 넘겨야겠다!
	public void insertScore(ScoreRequestDTO dto) {//데이터 타입으로 boolean으로 하는 사람도 있다!


		/*
		 DTO (Data Transfer Object): 데이터 전송(이동) 객체 라는 의미
		 - 계층간(3티어) 데이터 교환을 위한 객체.
		 - 로직을 갖고 있지 않은 순수한 데이터 객체로 활용. getter / setter 메서드만 갖는다.
		 - 사용 빈도 多
		이어서 제대로 모든 정보를 여기 메서드에서 가공하려면 entity가 필요함
		 */

		Score score = new Score(dto);
		//Entity를 완성했으니, Repository에게 전달해서 DB에 넣자.(Entity에게 전달)
		scoreRepository.save(score);

	}
	

	/*
	 컨트롤러는 나에게 DB를 통해
	 성적 정보 리스트를 가져오길 원하고 있어.
	 근데 Repository(= DB)는 학생 정보가 모두 포함된 리스트를 주네?
	 현재 요청에 어울리는 응답 화면에 맞는 DTO로 변경해서 주자!
	 */
	public List<ScoreListResponseDTO> getList() {//등록된 정보를 가진 목록을 보여줄 수 있는 메서드

		List<ScoreListResponseDTO> dtoList = new ArrayList<>();//정보 받아서 저장해준 다음 컨트롤러로 넘겨줄 객체 생성
		List<Score> scoreList = scoreRepository.findAll();//일단 score객체가 담긴 List를 받음(전체 정보 가져옴)

		for(Score s : scoreList) {
			ScoreListResponseDTO dto = new ScoreListResponseDTO(s);
			//ㄴ> Entity를 DTO로 변환해주는 과정, dto생성자에게 Entity객체를 넣어서(원하는 정보로 변환처리) dto변수에 담겠다.
			dtoList.add(dto);//정보를 넘겨줄 용도로 미리 선언해놓은 dtoList에 위에서 처리한 dto를 넣어주겠다.

		}//나중에는 이 메서드의 내용을 한줄로 요약이 가능해짐. 나중에!

		return dtoList;
	}

	//학생 점수 개별 조회
	public Score retrieve(int stuNum) {
		//응답하는 화면에 맞는 DTO를 선언해서 주어야 하는 것이 원칙!
		//만약에 Score 전체 데이터가 필요한 것이 아니라면
		//몇 개만 추리고 가공할 수 있는 DTO를 설계해서 리턴하는 것이 맞습니다!
		return scoreRepository.findByStuNum(stuNum);
		//학번을 넘겨서 학번에 해당하는 사람의 정보를 전부 달라! -> Entity를 리턴,,하면 안되지만 이럴땐! 하기도^^ -> 원래라면 DTO를 따로 만들어서 리턴해줘야 함!
	}

	public void delete(int stuNum) {
		scoreRepository.deleteByStuNum(stuNum);
	}

	/*
	 * 내가 한 버전 post-modify용도
	public void insertScore(ScoreRequestDTO dto, int stuNum) {//데이터 타입으로 boolean으로 하는 사람도 있다!
		
		Score score = new Score(dto);
		//Entity를 완성했으니, Repository에게 전달해서 DB에 넣자.(Entity에게 전달)
		scoreRepository.resave(score, stuNum);
	}
	 */
	
	public void modify(int stuNum, ScoreRequestDTO dto) {
		
		Score score = scoreRepository.findByStuNum(stuNum);//번호 줄테니까 해당하는 기존의 score객체 돌려주라
		score.changeScore(dto);//점수를 다시 계산해 달라고 하면 된다. 그럼 아래 3줄을 안해줘도 됨
		//dto.setName(score.getStuName());//해당하는 score의 이름을 뽑아서 dto에 저장해주겠다(이름은 없어서 기존꺼를 뽑아서 저장)
		//Score modScore = new Score(dto);//새로운 Score 객체를 생성해서 dto를 다시 저장(기존의 정보와 교체)
		//modScore.setStuNum(stuNum);//교체한 객체에 번호를 달아주겠다
		
		scoreRepository.modify(score);//학번과 수정된(교체된) 정보를 다 전달!
	}


}



































