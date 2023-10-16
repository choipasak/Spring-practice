<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    
    이름: <input type="text" name="name" > <br>
    
    나이: <input type="text" name="age" > <br>
    
    취미: 
    <input type="checkbox" name="hobby" value="soccer" > 축구
    <input type="checkbox" name="hobby" value="music" > 음악감상
    <input type="checkbox" name="hobby" value="game" > 게임

    <button type="button" id="send">요청 보내기!</button>


    <script>

        const $sendBtn = document.getElementById('send');

        $sendBtn.onclick = function(e){
            const name = document.querySelector('input[name=name]').value; //id 값이나 class가 없으면 querySelector를 통해 name 값을 []안에 지목해서 가져 올 순 있다!
            const age = document.querySelector('input[name=age]').value; //querySelector는 요소를 1개만 선택해서 가져옴!
            const $hobby = document.querySelectorAll('input[name=hobby]'); //리턴타입 -> NodeList: 유사 배열 -> 배열처럼 hobby들이 요소로 들어 있음
        
            const arr = []; //체크가 된 요소를 넣기 위한 배열.

            //일단 유사 배열을 실제 배열로 바꿔준다: 배열 메서드 사용하고 싶어서!\
            //정리: querySelectorAll의 리턴값은 NodeList라는 유사 배열 객체다.
            //배열의 메서드를 사용하기 위해 실제 배열로 변환하는 문법.
            [...$hobby].forEach($check => { // 반복문 1바퀴 당 함수 1번씩 수행, 마치 향상for문처럼 수행, $를 붙이는 것은 요소인지(= html에서 가져온 값이냐) 그냥 일반적인 값인지를 구분 해 주는 관례적 표현!
                if($check.checked) { // .checked 라는 기능: check되어 있다면 true, 안되어 있다면 false
                    arr.push($check.value);
                }
            });          

            console.log(name);
            console.log(age);
            console.log(arr);
            
            // # http 요청을 서버로 보내는 방법
            // 1. XMLHttpRequest 객체를 생성.
            const xhr = new XMLHttpRequest();
            
            /*
            2. http 요청 설정 (요청 방식, 요청 URL)
            - 요청 방식
            - rest 통신 : 요청을 보낼 때 요청하는 방식에 따라서 요청에 따른 규약을 지켜줘야 한다.
            a. GET - 조회
            b. POST - 등록
            c. PUT - 수정
            d. DELETE - 삭제
            */

            xhr.open('POST', '/myweb/rest/getObject');

            // 3. 서버로 전송할 데이터를 제작합니다.
            //제작하는 데이터의 형식은 JSON 형태여야 합니다.
            /*
            # JSON 표기법 (Java Script Object Notation) - 자바 스크립트 객체 표기법
            - JSON은 그냥 데이터를 표기하는 방식만 JS를 따라한 것이지 JS가 아님
            - JAVA와 연결할 때 데이터를 주고 받는 데이터의 표기 방법을 JSON을 통해 작성 해 준다.
            */
           //먼저 객체로 포장해야 함
           const data = {
                'name' : name,
                'age' : age,
                'hobby' : arr,
           }; // 이 객체는 아직 JSON이 아니라 JS 객체이다.

           //JS객체 -> JSON으로 변경: JSON.stringfy(arg)
           const sendData = JSON.stringify(data);

           //요청 받는 서버에게 JSON형태라고 정보를 전달해서 알려주기 -> 요청 헤더에 지정.
           xhr.setRequestHeader('content-type', 'application/json'); // 내가 지금 보내는 content-type은 json형태야~

           //4. 서버에 요청을 전송한다!
           xhr.send(sendData);

           //5. 응답된 정보 확인하기
           xhr.onload = function(){//응답이 되었다면 = 요청이 완료가 되었다면
                console.log(xhr.status); //요청이 잘 완료 되었다면 200이 뜸
                console.log(xhr.response);
           };
        
        }

    </script>

</body>
</html>