<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../include/header.jsp" %>

<section>
    <div class="container">
        <div class="row">
            <div class="col-lg-6 col-md-9 col-sm-12 join-form">
                <div class="titlebox">
                    회원가입
                </div>
                <form action="/myweb/user/join" method="post" name="joinForm">
                    <div class="form-group">
                        <!--사용자클래스선언-->
                        <label for="id">아이디</label>
                        <div class="input-group">
                            <!--input2탭의 input-addon을 가져온다 -->
                            <input type="text" name="userId" class="form-control" id="userId" placeholder="아이디를 (영문포함 4~12자 이상)">
                            <div class="input-group-addon">
                                <button id="idCheckkBtn" type="button" class="btn btn-primary">아이디중복체크</button>
                            </div>
                        </div>
                        <span id="msgId"></span>
                        <!--자바스크립트에서 추가-->
                    </div>
                    <div class="form-group">
                        <!--기본 폼그룹을 가져온다-->
                        <label for="password">비밀번호</label>
                        <input type="password" name="userPw" class="form-control" id="userPw"
                            placeholder="비밀번호 (영 대/소문자, 숫자 조합 8~16자 이상)">
                        <span id="msgPw"></span>
                        <!--자바스크립트에서 추가-->
                    </div>
                    <div class="form-group">
                        <label for="password-confrim">비밀번호 확인</label>
                        <input type="password" class="form-control" id="pwConfirm" placeholder="비밀번호를 확인해주세요.">
                        <span id="msgPw-c"></span>
                        <!--자바스크립트에서 추가-->
                    </div>
                    <div class="form-group">
                        <label for="name">이름</label>
                        <input type="text" name="userName" class="form-control" id="userName" placeholder="이름을 입력하세요.">
                    </div>
                    <!--input2탭의 input-addon을 가져온다 -->

                    <div class="form-group">
                        <label for="hp">휴대폰번호</label>
                        <div class="input-group">
                            <select name="userPhone2" class="form-control phone1" id="userPhone1">
                                <option>010</option>
                                <option>011</option>
                                <option>017</option>
                                <option>018</option>
                            </select>
                            <input type="text" name="userPhone2" class="form-control phone2" id="userPhone2" placeholder="휴대폰번호를 입력하세요.">
                        </div>
                    </div>
                    <div class="form-group email-form">
                        <label for="email">이메일</label><br>
                        <div class="input-group">
                            <input type="text" name="userEmail1" class="form-control" id="userEmail1" placeholder="이메일">
                            <select name="userEmail2" class="form-control" id="userEmail2">
                                <option>@naver.com</option>
                                <option>@daum.net</option>
                                <option>@gmail.com</option>
                                <option>@hanmail.com</option>
                                <option>@yahoo.co.kr</option>
                            </select>
                            <div class="input-group-addon">
                                <button id="mail-check-btn" type="button" class="btn btn-primary">본인인증</button>
                            </div>
                        </div>
                    </div>
                    <div class="mail-check-box">
                        <input type="text" class="form-control mail-check-input" placeholder="인증번호 6자리를 입력하세요."
                            maxlength="6" disabled>
                        <span id="mail-check-warn"></span>
                    </div>

                    
                    <!--readonly 속성 추가시 자동으로 블락-->
                    <div class="form-group">
                        <label for="addr-num">주소</label>
                        <div class="input-group">
                            <input type="text" name="addrZipNum" class="form-control" id="addrZipNum" placeholder="우편번호" readonly>
                            <div class="input-group-addon">
                                <button type="button" class="btn btn-primary" onclick="searchAddress()">주소찾기</button>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="addrBasic" id="addrBasic" placeholder="기본주소" readonly />
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="addrDetail" id="addrDetail" placeholder="상세주소">
                    </div>

                    <!--button탭에 들어가서 버튼종류를 확인한다-->
                    <div class="form-group">
                        <button type="button" id="joinBtn" class="btn btn-lg btn-success btn-block">회원가입</button>
                    </div>

                    <div class="form-group">
                        <button type="button" class="btn btn-lg btn-info btn-block">로그인</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>

<%@ include file="../include/footer.jsp" %>
<!-- 다음 주소 api 해서 긁어온거임 -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script> 
<script>

    //이메일 전송 인증번호 저장을 위한 변수 -> 보낸 인증번호 = 클라가 입력한 인증번호. 인지를 확인을 위해서
    let code = '';

    //정규 표현식 유효성 검사 여부를 판단하기 위한 변수 선언
    let idFlag, pwFlag;

    /*아이디 중복체크*/

    document.getElementById('idCheckkBtn').onclick = function () {


        const userId = document.getElementById('userId').value;
        if (userId === '') {
            alert('아이디는 필수 값입니다.');
            return; //중복시에 거절한다는 뜻
        }

        //정규표현식 조건을 통과 못하면 비동기 요청도 안보내주겠다! -> 목적: 요청 검사(중복으로 판단)
        if(!idFlag){
            alert('유효하지 않는 아이디 입니다.');
            return;
        }

        /*

        //fetch가 없었을 때의 옛날 문법

        // 아이디 중복 확인 비동기 요청 준비
        const xhr = new XMLHttpRequest();

        //서버 요청 정보 설정, .open('요청 방식 작성', 'url주소') -> url에 목적을 최대한 드러나지 않게 작성해야함. 쉽게 유추 가능
        xhr.open('GET', `/myweb/user/\${userId}`); 
        //원래 익숙한 형태 -> 쿼리스트링방식, 위의 방법은 서버에서 어떻게 값을 따로 뜯을 수 있는가!

        //바로 요청 보내기
        xhr.send(); // ()안에 안써도 된다 -> 이유: url을 통해서 data를 이미 전달했기 때문에!

        xhr.onload = function(){
            console.log(xhr.status);
            console.log(xhr.response);
        }
        */
        ///////////////////////////////////////////////////////////////////////////////////
        /*

        - 향상된 문법

        # fetch API: 자바스크립트에서 제공하는 비동기 통신 함수(= 메서드).
        - Promise라는 객체를 자동으로 리턴하여 손쉽게 통신의 응답 데이터를
          소비할 수 있게 해 줍니다.
          (Promise: 비동기 통신의 결과 및 통신의 순서를 보장하는 객체)
        - fetch 함수가 리턴하는 Promise라는 객체는 단순히 응답 JSON 데이터가 아닌
          전체적이고, 포괄적인 응답 정보를 가지고 있습니다.
        - 따라서, 서버가 응답한 여러 정보 중 JSON 데이터만 소비하려면
          json()이라는 메서드를 사용합니다.
        - 단순 문자열 데이터라면 text() 메서드를 사용합니다.
        */

        /*
        //fetch('url', {요청 관련 정보를 담은 객체(GET방식에서는 따로 전달 안함.)})
        fetch('/myweb/user/' + userId) // -> 연습이니까 userId GET방식으로 진행
        //Promise 객체의 상태가 요청 성공일 시, 데이터 후속 처리가 진행된다.
        .then(res => { //요청에 대한 응답 정보가 res로 옴 + 값이 1개여서 그냥 화살표 함수를 사용 함(+ 원래 함수를 매개 값으로 받음), res는 그냥 매개변수 response, 응답이 res에 옴
            //fetch 함수를 통해 비동기 통신이 실행되고 나서,
            //요청이 완료 된 후 then()의 매개 값으로 응답에 관련 된 함수를
            //콜백 방식으로 전달합니다. 원래 -> (function(res) -> 화살표 함수로 간단히 표현)
            //함수의 매개 변수를 선언하면 해당 매개변수로 응답에 관련된
            //전반적인 정보를 가진 응답 정보가 전달됩니다.
            console.log(res);
            console.log('서버가 전달한 데이터: ' , res.text());
            return res.text(); // 서버가 전달한 데이터를 다시 Promise객체 형태로 리턴 해 줍니다. -> .then이라는 메서드의 결과가 다시 Promise형태로 리턴된다.
        })
        //위에 배치된 then() 함수가 먼저 실행될 것을 강제한다.
        //그 이후에 나중에 배치 된 then()이 실행되게끔 메서드 체인링 방식으로 작성 된 것이다.
        //정리: 위에의 then에서 통신이 성공했다는 것을 먼저 보장 받은 후, 그 다음에 서버에서 데이터를 꺼내는 콜백 함수를 실행 한 과정이다.
        .then(data => {
            console.log('data: ', data);
        })
        //예외처리인 catch문은 필수는 아님
        .catch(error => { // 통신 과정에서 에러가 발생했을 경우에만 실행되는 함수.
            console.log('error: ', error);
        });
        */


        //비동기 요청을 fetch()로 보내고 결과를 확인하기.
        fetch('/myweb/user/id/' + userId)
            //화살표 함수 내의 코드가 한 줄이고, 그것이 return이라면 괄호와 return 생략 가능!
            .then(res => res.text()) //요청 완료 후 응답 정보에서 텍스트 데이터가 담긴 Promise 반환.
            .then(data => { //텍스트 데이터만 담긴 Promise 객체로부터 data를 전달 받음, data에는 ok or duplicated가 담겨 있는 것임.
                if (data === 'ok') {
                    //alert('아이디 사용이 가능합니다!');

                    //더 이상 아이디를 작성할 수 없도록 막아주겠다.
                    document.getElementById('userId').setAttribute('readonly',
                    true); // .setAttribute() 원하는 속성으로 부여 + 활성화/비활성화 설정
                    //더 이상 버튼을 누를 수 없도록 버튼 비활성화.
                    document.getElementById('idCheckkBtn').setAttribute('disabled', true);
                    //메세지 남기기
                    document.getElementById('msgId').textContent = '사용 가능한 아이디 입니다.';

                } else {
                    //alert('아이디가 중복되었습니다.');
                    document.getElementById('msgId').textContent = '아이디가 중복되었습니다.';
                    document.getElementById('userId').value = ''; //입력칸 비워주는 문장
                    document.getElementById('userId').focus(); // 입력칸에 포커싱

                }
            });
    } // 아이디 중복 확인 끝!



    //인증번호 이메일 전송!
    //이메일 인증 버튼을 누르면 -> 이메일에 인증번호 전송을 해달라고 서버에 요청!
    document.getElementById('mail-check-btn').onclick = function(){
        
        // 1. GET방식
        const email = document.getElementById('userEmail1').value + document.getElementById('userEmail2').value
        console.log('완성된 이메일: ' + email);
        //검증은 나중에 마음대로 추가~

        //2. POST방식
        //원래 rest방식에서는 url에 동사를 사용하지 말라. 고 되어 있는데 -> 왜: 동사로 요청하는 일이 무엇인지 유추 가능해서
        fetch('/myweb/user/email', { 
            method: 'post',
            headers: {
                // headers는 객체로 전달되서 {}가 열림
                'Content-Type': 'application/json' //그냥 텍스트를 보낸다 + 보낼 데이터가 1개다 -> text/plain
            },
            body: email
        }) //이렇게 내부에서 바로 작성도 가능. 객체 밖에서 따로 작성하고 대입해도 ㄱㅊ
        .then(res => res.text())
        .then(data => {
            console.log('인증번호: ' + data);

            //비활성화된 인증번호 입력창을 활성화
            document.querySelector('.mail-check-input').disabled = false;

            //서버에서 보낸 data를 저장해야함 -> 그래야 나중에도 사용할 수 있으니까!
            code = data; //서버가 전달한 인증번호를 전역변수(code)에 저장
            alert('인증번호가 전송되었습니다. 확인 후 입력란에 정확히 입력하세요.');
        })
        .catch(error => {
            console.log(error);
            alert('알 수 없는 문제가 발생했습니다. 관리자에게 문의하세요!');
        });// 비동기 통신 끝!
    }; // 이메일 인증 버튼 클릭 이벤트

    //인증번호 검증
    //blur -> focus가 벗어나는 경우 발생. -> 인증번호입력칸에서 focus가 빠지면 blur되는 현상을 발생시킬것임
    document.querySelector('.mail-check-input').onblur = function(e){
        //alert('인증번호를 입력해주세요!');
        const inputCode = e.target.value; //사용자가 입력한 인증번호.

        const $resultMsg = document.getElementById('mail-check-warn'); //span

        if(inputCode === code){
            $resultMsg.textContent = '인증번호가 일치합니다!';
            $resultMsg.style.color = 'green';
            //이메일 인증을 더 이상 못하게 버튼 비활성화.
            document.getElementById('mail-check-btn').disabled = true;
            document.getElementById('userEmail1').setAttribute('readonly', true);
            document.getElementById('userEmail2').setAttribute('readonly', true);

            e.target.style.display = 'none'; //인증번호 입력 창 숨기기!

            //select에 readonly 거는 방법 = 사실 readonly처럼 보이게 하는 방법: select는 readonly 속성이 없음
            //select 태그에서 초기값을 사용자가 선택한 값으로 무조건 설정하는 방법
            //(select에서 readonly 대용으로 사용)
            //항상 2개 같이 쓰셔야 합니다.
            const $email2 = document.getElementById('userEmail2');

            //사용자가 select의 옵션을 처음 선택했을 때의 값을 기억했다가
            //option변경 시도를 할 때마다 초기값으로 강제로 변경해서
            //option이 마치 변하지 않는 것처럼 처리.
            $email2.setAttribute('onFocus', 'this.initialSelect = this.selectedIndex');
            $email2.setAttribute('onChange', 'this.selectedIndex = this.initialSelect');
            /*
            onFucus: 이벤트가 발생했을 때!
            onChange: 변화가 발생할 때!
            */
        }else{
            $resultMsg.textContent = '인증번호를 다시 확인해 주세요!';
            $resultMsg.style.color = 'red';
            e.target.focus();
        }
    }//인증번호 검증 끝.

    //다음 주소 api 사용해 보기(script scr 추가 해야합니다!)
    function searchAddress() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다. (도로명주소 or 지번주소)
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                // id는 우리 사이트의 html에 적힌 input태그의 id값을 주면 된다!
                document.getElementById('addrZipNum').value = data.zonecode;
                document.getElementById("addrBasic").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("addrDetail").focus();
            }
        }).open();
    }//주소 찾기 api 끝!


    //폼 데이터 검증(회원 가입 버튼 눌렀을 시)
    //버튼이 일단 submit도 아님.
    document.getElementById('joinBtn').onclick = function(){
        if(idFlag && pwFlag) {
            //통과했다면 다음으로 무엇을 확인 하느냐!

            //1. 아이디 중복체크 했는지
            //아이디 입력칸이 비활성화 된 것으로 판단.
            if(!document.getElementById('userId').getAttribute('readonly')){ // 활성화일 때 = 아이디 중복 확인을 한 적이 없구나
                alert('아이디 중복 체크는 필수입니다!');
                return;
            }
            //2. 이름 필수 기입 체크
            if(document.getElementById('userName').value === ''){
                alert('이름은 필수값입니다!');
                return;
            }
            //3. 이메일 인증 체크
            //이메일 인증 버튼이 비활성화 되어 있는지로 판단
            if(!document.getElementById('mail-check-btn').disabled){
                alert('이메일 인증을 완료 해 주세요!');
                return;
            }
            //회원가입 버튼을 눌렀을 때 회원가입 진행
            if(confirm('회원가입을 진행합니다.')){
                document.joinForm.submit(); //form의 name속성을 바로 지목 가능.
            }else return;



        } else{
            alert('입력값을 다시 한 번 확인하세요!');
        }
    }



    /*아이디 형식 검사 스크립트*/
    var id = document.getElementById("userId");
    id.onkeyup = function () { //.onkeyup: 사용자가 키를 눌렀다가 뗐을 때 발동!
        /*
            자바스크립트의 정규표현식 입니다
            정규 표현식: 문자열 내의 특정 문자 조합을 찾기 위한 패턴입니다.
            특정 규칙이 있는 문자열의 집합을 대상으로 규칙을 직접 지정하여 탐색할 수 있게 해 주는
            메타 문자입니다.
        */
        /*test메서드를 통해 비교하며, 매칭되면 true, 아니면 false반*/
        var regex = /^[A-Za-z0-9+]{8,12}$/; // -> '정규표현식+메타 문자' 형식임
        // test(문자열) -> 정규 표현식의 규칙에 어긋나지 않는 문자라면 true, 어긋난다면 false를 리턴!
        //아이디 자체가 유효한 값인지를 검사 -> 목적: 정규표현식
        if (regex.test(document.getElementById("userId").value)) {
            document.getElementById("userId").style.borderColor = "green";
            document.getElementById("msgId").innerHTML = "아이디중복체크는 필수 입니다";
            idFlag = true;
        } else {
            document.getElementById("userId").style.borderColor = "red";
            document.getElementById("msgId").innerHTML = "부적합한 아이디 입니다.";
            idFlag = false;
        }
    }
    /*비밀번호 형식 검사 스크립트*/
    var pw = document.getElementById("userPw");
    pw.onkeyup = function () {
        var regex = /^[A-Za-z0-9+]{8,16}$/;
        if (regex.test(document.getElementById("userPw").value)) {
            document.getElementById("userPw").style.borderColor = "green";
            document.getElementById("msgPw").innerHTML = "사용가능합니다";
            pwFlag = true;
        } else {
            document.getElementById("userPw").style.borderColor = "red";
            document.getElementById("msgPw").innerHTML = "비밀번호를 제대로 입력하세요!";
            pwFlag = false;
        }
    }
    /*비밀번호 확인검사*/
    var pwConfirm = document.getElementById("pwConfirm");
    pwConfirm.onkeyup = function () {
        var regex = /^[A-Za-z0-9+]{8,16}$/;
        if (document.getElementById("pwConfirm").value == document.getElementById("userPw").value) {
            document.getElementById("pwConfirm").style.borderColor = "green";
            document.getElementById("msgPw-c").innerHTML = "비밀번호가 일치합니다";
            pwFlag = true;
        } else {
            document.getElementById("pwConfirm").style.borderColor = "red";
            document.getElementById("msgPw-c").innerHTML = "비밀번호 확인란을 확인하세요";
            pwFlag = false;
        }
    }
</script>