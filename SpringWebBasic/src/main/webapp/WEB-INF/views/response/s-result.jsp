<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<h1 class="result-title">
		<%-- <c:if test="${result == 'f-id'}">아이디가 존재하지 않습니다!</c:if>
		ㄴ> 이미 jsp파일로 넘어온 순간 result라는 정보가 html로 변환이 완료 된것임
		ㄴ> 그래서 밑에서 자바 스크립트에서도 사용이 가능해진 것임.
		ㄴ> 자바->자바스크립트 로 변한게아님
		<c:if test="${result == 'f-pw'}">비밀번호가 존재하지 않습니다!</c:if>
		<c:if test="${result == 'success'}">~로그인 성공~</c:if> --%>
	</h1>
	
	<script>
		
		/*
			브라우저가 HTML을 해석하고, CSS를 해석하고, javascript 코드를 실행해서
			화면에 표현하는 과정을 렌더링 이라고 합니다.
			지금 우리가 작성하는 파일 확장자는 jsp 입니다.
			파일형태 -> jsp -> 서버 내에서 클래스로 변환 -> 응답은 HTML
			jsp파일이 클래스로 변환하는 과정에서 작성한 el문법은 자바 코드로 변환 -> 값을 표현 -> 응답은 HTML로 표현.
			EL표현석이 서버에서 먼저 평가되고, 그 결과를 클라이언트에게 전달하여 브라우저로 표현하기 때문에
			script에서도 el표현이 가능하다.
			대신, javascript에서 EL표현식을 작성할 때는 문자열로 를 감싸주세요.
		*/
		const result = '${result}';
		//console.log('result: ' + result);
		const $h1 = document.querySelector('.result-title');
		switch(result){
		case 'f-id':
			$h1.textContent = '아이디가 존재하지 않습니다.';
			break;
			
		case 'f-pw':
			$h1.textContent = '비밀번호가 틀렸습니다.';
			break;
			
		case 'success':
			$h1.textContent = '로그인 성공!';
			break;
		}
		
		
		
		
		
		
		
		
		
	</script>

</body>
</html>