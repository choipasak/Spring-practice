<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>

    <!-- 자바스크립트 라이브러리임 -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.21/lodash.min.js"></script>

    <style type="text/css">
        section {
            margin-top: 70px;
        }

        /*부트스트랩 li의 drophover클래스 호버시에 드롭다운 적용코드*/
        ul.nav li.drophover:hover>ul.dropdown-menu {
            display: block;
            margin: 0;
        }

        .aside-inner {
            position: fixed;
            top: 70px;
            width: 180px;
        }

        .section-inner {
            border-right: 1px solid #ddd;
            border-left: 1px solid #ddd;
            background-color: white;
        }

        .section-inner p,
        .aside-inner p {
            margin: 0;
        }

        .title-inner {
            position: relative;
            padding: 15px 0;
        }

        .title-inner .profile {
            position: absolute;
            /*부모기준으로 위치지정 릴레이티브*/
            top: 15px;
            left: 0;
        }

        .title-inner .title {
            padding-left: 50px;
        }

        /*내용*/
        .content-inner {
            padding: 10px 0;
        }

        /* 이미지영역  */
        .image-inner img {
            width: 100%;
        }

        /*좋아요*/
        .like-inner {
            padding: 10px 0;
            border-bottom: 1px solid #ddd;
            border-top: 1px solid #ddd;
            margin-top: 10px;
        }

        .like-inner img {
            width: 20px;
            height: 20px;
        }

        .link-inner {
            overflow: hidden;
            padding: 10px 0;
        }

        .link-inner a {
            float: left;
            width: 33.3333%;
            text-align: center;
            text-decoration: none;
            color: #333333;
        }

        .link-inner i {
            margin: 0 5px;
        }

        /*767미만 사이즈에서 해당 css가 적용됨*/
        /*xs가 767사이즈   */
        @media (max-width :767px) {
            aside {
                display: none;
            }
        }

        /* 파일업로드 버튼 바꾸기 */
        .filebox label {
            display: inline-block;
            padding: 6px 10px;
            color: #fff;
            font-size: inherit;
            line-height: normal;
            vertical-align: middle;
            background-color: #5cb85c;
            cursor: pointer;
            border: 1px solid #4cae4c;
            border-radius: none;
            -webkit-transition: background-color 0.2s;
            transition: background-color 0.2s;
        }

        .filebox label:hover {
            background-color: #6ed36e;
        }

        .filebox label:active {
            background-color: #367c36;
        }

        .filebox input[type="file"] {
            position: absolute;
            width: 1px;
            height: 1px;
            padding: 0;
            margin: -1px;
            overflow: hidden;
            clip: rect(0, 0, 0, 0);
            border: 0;
        }

        /* sns파일 업로드시 미리보기  */
        .fileDiv {
            height: 100px;
            width: 200px;
            display: none;
            margin-bottom: 10px;
        }

        .fileDiv img {
            width: 100%;
            height: 100%;
        }

        /* 모달창 조절 */
        .modal-body {
            padding: 0px;
        }

        .modal-content>.row {
            margin: 0px;
        }

        .modal-body>.modal-img {
            padding: 0px;
        }

        .modal-body>.modal-con {
            padding: 15px;
        }

        .modal-inner {
            position: relative;
        }

        .modal-inner .profile {
            position: absolute;
            top: 0px;
            left: 0px;
        }

        .modal-inner .title {
            padding-left: 50px;
        }

        .modal-inner p {
            margin: 0px;
        }
    </style>

</head>

<body>
    <%@ include file="../include/header.jsp" %>
    <section>
        <div class="container">
            <div class="row">
                <aside class="col-sm-2">
                    <div class="aside-inner">
                        <div class="menu1">
                            <p>
                                <img src="${pageContext.request.contextPath}/img/profile.png"> 홍길동
                            </p>
                            <ul>
                                <li>사이드메뉴1</li>
                                <li>사이드메뉴2</li>
                                <li>사이드메뉴3</li>
                            </ul>
                        </div>
                        <div class="menu2">
                            <p>둘러보기</p>
                            <ul>
                                <li>링크1</li>
                                <li>링크2</li>
                                <li>링크3</li>
                                <li>링크4</li>
                                <li>링크5</li>
                            </ul>
                        </div>
                    </div>
                </aside>
                <div class="col-xs-12 col-sm-8 section-inner">
                    <h4>게시물 만들기</h4>
                    <!-- 파일 업로드 폼입니다 -->
                    <div class="fileDiv">
                        <img id="fileImg" src="${pageContext.request.contextPath}/img/img_ready.png">
                    </div>
                    <div class="reply-content">
                        <textarea class="form-control" rows="3" name="content" id="content"
                            placeholder="무슨 생각을 하고 계신가요?"></textarea>
                        <div class="reply-group">
                            <div class="filebox pull-left">
                                <label for="file">이미지업로드</label>
                                <input type="file" name="file" id="file">
                            </div>
                            <button type="button" class="right btn btn-info" id="uploadBtn">등록하기</button>
                        </div>
                    </div>
                    <!-- 파일 업로드 폼 끝 -->


                    <div id="contentDiv">

                        <!-- 비동기 방식으로 서버와 통신을 진행한 후
							목록을 만들어서 붙일 예정. -->

                    </div>
                </div>
                <!--우측 어사이드-->
                <aside class="col-sm-2">
                    <div class="aside-inner">
                        <div class="menu1">
                            <p>목록</p>
                            <ul>
                                <li>목록1</li>
                                <li>목록2</li>
                                <li>목록3</li>
                                <li>목록4</li>
                                <li>목록5</li>
                            </ul>
                        </div>
                    </div>
                </aside>
            </div>
        </div>
    </section>
    <%@ include file="../include/footer.jsp" %>


    <!-- 모달 -->
    <div class="modal fade" id="snsModal" role="dialog">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-body row">
                    <div class="modal-img col-sm-8 col-xs-6">
                        <img src="${pageContext.request.contextPath}/img/img_ready.png" id="snsImg" width="100%">
                    </div>
                    <div class="modal-con col-sm-4 col-xs-6">
                        <div class="modal-inner">
                            <div class="profile">
                                <img src="${pageContext.request.contextPath}/img/profile.png">
                            </div>
                            <div class="title">
                                <p id="snsWriter">테스트</p>
                                <small id="snsRegdate">21시간전</small>
                            </div>
                            <div class="content-inner">
                                <p id="snsContent">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam
                                    vulputate elit libero, quis mattis enim tincidunt non. Mauris consequat ante vel
                                    urna posuere consequat. </p>
                            </div>
                            <div class="link-inner">
                                <a href="##"><i class="glyphicon glyphicon-thumbs-up"></i>좋아요</a>
                                <a href="##"><i class="glyphicon glyphicon-comment"></i>댓글달기</a>
                                <a href="##"><i class="glyphicon glyphicon-share-alt"></i>공유하기</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        //등록하기 버튼 클릭 이벤트
        document.getElementById('uploadBtn').onclick = () => {
            regist();
        }

        //등록을 담당하는 함수
        function regist() {
            //로그인 한 사람만 게시물 등록 가능
            //세션에서 현재 로그인 중인 사용자의 정보(아이디)를 얻어오자.
            //검증 -> 로그인 세션 데이터 있/없
            const userId = '${sessionScope.login}';
            if (userId === '') { //세션 데이터가 null -> 로그인 X
                alert('로그인이 필요한 서비스 입니다.');
                return;
            }

            //자바스크립트로 첨부한 파일 확장자 체크 (이미지만 허용함.)
            let file = document.getElementById('file').value;
            console.log(file);
            // .을 제거한 확장자만 얻어낸 후 그것을 소문자로 일괄 변경.
            file = file.slice(file.indexOf('.') + 1)
                .toLowerCase(); //.slice() : 자바의 substring 같은애! (범위추출) + // 자바의 서브스트링 같은 것. 범위 추출해서 붙이여서 그럼

            if (file != 'jpg' && file != 'png' && file != 'jpeg' && file !== 'bmp') {
                alert('이미지 파일만 첨부 가능합니다! ex) jpg, png, jpeg, bmp ...');
                return;
            }

            /*
            비동기 방식 요청에서 Form을 생성해서 보내주는 방법.
            -> FormData 객체를 활용합니다.
            무조건 이 방식이 옳은 것은 아닙니다. FormData는 비동기 방식이 꼭 필요한 경우에만 사용함.
            비동기가 필요하지 않다면 form 태그를 이용해서 전송하는 방식이 더 간편하고 더 자주 사용됨
            */

            //자바스크립트 빌트인 객체. 즉, 내장객체
            const formData = new FormData();
            const $data = document.getElementById('file'); //이미지 첨부 input

            console.log('data: ', $data);

            //한 화면에 파일 업로드 버튼이 여러 개일 경우 요소의 인덱스를 지목해서 가져올 수 있음.
            //우리는 파일 첨부 버튼이 하나고, id를 지목해서 가져오기 때문에 인덱스를 쓸 필요는 없습니다.
            //console.log('dara[0]', $data[0]);

            //파일 태그에 담긴 파일 정보를 확인하는 프로퍼티
            console.log($data.files);
            //유사배열인 FileList에 파일의 정보가 담겨져 있다.
            //프로퍼티가 인덱스와 비슷해서 유사배열이라고 함
            //html에서 mutiple속성을 설정해주면 유사배열이기 때문에

            console.log($data.files[0]); // 사용자가 첨부한 최종 파일의 정보.
            /*
            정리
                만약 우리가 여러 파일을 하나의 태그로 받을 수 있도록 multiple을 제공했다면
                files -> FileList에 여러 파일의 정보가 들어오게 됩니다.
                FileList는 유사 배열이기 때문에 인덱스를 이용해서 여러 파일 중 하나를 지목할 수 있습니다.
                우리는 지금 multiple이 없기 때문에 [0]이라고 작성하면 파일 정보를 얻을 수 있습니다.
            */

            //FormData 객체에 사용자가 업로드한 파일의 정보가 들어있는 객체를 전달.
            //append('파라미터 변수명' , '값');
            formData.append('file', $data.files[0]); //file -> input태그의 name
            formData.append('content', document.getElementById('content').value); // textarea태그 내용 추가!
            formData.append('writer', userId); // 글 작성자명 추가!

            //정보 가지고 비동기 요청!
            fetch('${pageContext.request.contextPath}/snsboard/upload', {
                    method: 'post',
                    //FormData 객체를 보낼 때는 따로 headers 설정을 진행하지 않습니다: 객체가 headers의 성질을 가지고 있기 때문에QQQQQQQQQQQQQQ
                    //headers:
                    body: formData
                })
                //응답 데이터는 text로 전달됩니다.(uploadSuccess)
                //file input 내용을 비워 주시고, 글 영역 비워주시고, class 이름이 fileDiv(미리보기용 태그id)영역을 감춰 주세요 -> display속성을 none으로
                .then(res => res.text())
                .then(data => {
                    console.log(data);
                    if (data === 'uploadSuccess') {
                        alert('글 등록에 성공했습니다!');
                        document.getElementById('file').value = '';
                        document.getElementById('content').value = '';
                        document.querySelector('.fileDiv').style.display = 'none';
                        getList(1, true); //글 목록 함수 호출.
                        //새롭게 페이지 리셋해서 1페이지부터 불러달란 뜻!
                    }
                });
        }

        //글 목록 함수 선언.
        let str = '';
        let page = 1;
        let isFinish = false; // 초기 값도 false
        let reqStatus = false;

        const $contentDiv = document.getElementById('contentDiv');

        //getList()에 좋아요 목록 반영한 글 목록을 호출하는 함수
        getLikeList(1, true);

        //페이지 입장하면 바로 설정처럼 초기화!
        //getList(1, true); -> 이동함. getLikeList() 안으로


        //지금 게시판에 들어온 회원의 좋아요 게시물 목록을 받아오는 함수
        function getLikeList(page, reset) {

            //1. 로그인 유저 정보 가져오기!
            const userId = '${login}';
            console.log('유저 id -> ', userId);

            /*
            특정 데이터를 브라우저가 제공하는 공간에 저장할 수 있습니다.
            localStorage, sessionStorage
            둘의 차이점: 수명
            localStorage: 브라우저가 종료 되더라도 데이터는 유지된다.
                          브라우저 탭이 여러 개 존재 하더라도 데이터가 공유된다.
            sessionStorage: 브라우저가 종료 -> 데이터 소멸
                            브라우저 창(탭) 별로 데이터가 저장된다. -> 데이터가 공유되지 않는다.
            */


            if (userId !== '') { // 로그인 판단 조건문
                if (sessionStorage.getItem('likeList')) { // likeList라는 데이터가 존재하는가?
                    //ㅇㅇ -> 그럼 이전에 데이터를 받아 온 적이 있다 => fetch를 할 필요가X
                    console.log('sessionStorage에 list 존재함!');
                    getList(page, reset, sessionStorage.getItem('likeList'));
                    return;
                }

                //비동기 요청
                fetch('${pageContext.request.contextPath}/snsboard/likeList/' + userId)
                    .then(res => res.json())
                    .then(list => {
                        console.log('요청갓다옴');
                        console.log('좋아요 글 목록 받아옴! : ', list);
                        sessionStorage.setItem('likeList', list); // 서버로부터 받은 list를 세션스토리지에 list를 setItem하겠다.
                        getList(page, reset, list);
                    });
            } else { //로그인 안 한 경우
                getList(page, reset, null);
            }
        }







        function getList(page, reset, likeList) { //매개변수인 likeList는 
            str = '';
            isFinish = false; // 처음 설정도 false
            console.log('page: ', page);
            console.log('reset: ', reset); //확인용 출력

            fetch('${pageContext.request.contextPath}/snsboard/' + page)
                .then(res => res.json())
                .then(list => {
                    console.log(list);
                    console.log(list.length);
                    if (list.length <= 0) {
                        isFinish = true;
                        reqStatus = true;
                        return;
                    }

                    if (reset) {
                        while ($contentDiv.firstChild) {
                            $contentDiv.firstChild.remove();
                        }
                        page = 1;
                    }

                    for (board of list) {
                        str +=
                            `<div class="title-inner">
                            <!--제목영역-->
                            <div class="profile">
                                <img src="${pageContext.request.contextPath}/img/profile.png">
                            </div>
                            <div class="title">
                                <p>` + board.writer + `</p>
                                <small>` + board.regDate + `</small> &nbsp;&nbsp;
                                <a id="download" href="${pageContext.request.contextPath}/snsboard/download/` + board
                            .fileLoca + `/` + board.fileName + `">이미지 다운로드</a>
                            </div>
                        </div>
                        <div class="content-inner">
                            <!--내용영역-->
                            <p>` + board.content + `</p>
                        </div>
                        <div class="image-inner">
                            <!-- 이미지영역 -->
                            <a href="` + board.bno + `">
                                <img data-bno="` + board.bno +
                            `" src="${pageContext.request.contextPath}/snsboard/display/` + board.fileLoca + `/` +
                            board.fileName + `">
                            </a>
                        </div>
                        <div class="like-inner">
                            <!--좋아요-->
                            <img src="${pageContext.request.contextPath}/img/icon.jpg"> <span>` + board.likeCnt + `</span>
                        </div>
                        <div class="link-inner">`;
                        //왜 조건문 -> likeList를 받았음. -> 검사
                        if (likeList) { //로그인 안한 사람도 목록은 봐야함. 로그인 안한사람에겐(list값이 안와서 false) 빈 하트, 로그인 한 사람에겐 채운하트 줄라고 
                            //로그인을 한 사람
                            if (likeList.includes(board.bno)) { //넘어온 list의 값에 list의 글 번호가 포함되어 있냐?
                                //그렇다면 색칠 된 하트를 보여준다
                                str = `<a id="likeBtn" href="` + board.bno +
                                    `"><img src="${pageContext.request.contextPath}/img/like2.png" width="20px" height="20px">좋아요</a>`;
                            } else {
                                //글 번호가 포함X -> 빈 하트
                                str += `<a id="likeBtn" href="` + board.bno +
                                    `"><img src="${pageContext.request.contextPath}/img/like1.png" width="20px" height="20px">좋아요</a>`;
                            }

                        } else {
                            //로그인 안한 사람 -> 빈 하트
                            str += `<a id="likeBtn" href="` + board.bno +
                                `"><img src="${pageContext.request.contextPath}/img/like1.png" width="20px" height="20px">좋아요</a>`;
                        }
                        str += `
                            <a id="delBtn" href="` + board.bno + `"><i class="glyphicon glyphicon-remove"></i>삭제하기</a>
                        </div>`;
                    }
                    if (!reset) {
                        $contentDiv.insertAdjacentHTML('beforeend', str);
                    } else {
                        $contentDiv.insertAdjacentHTML('afterbegin', str);
                    }

                    isFinish = true; // 브라우저가 로딩이 되었는지를 확인.

                }); //end fetch
        } // end getList()

        $contentDiv.addEventListener('click', e => {
            e.preventDefault(); // a태그의 고유 기능 막기

            if (!e.target.matches('.image-inner img') &&
                !e.target.matches('.title #download') &&
                !e.target.matches('.link-inner #comment') &&
                !e.target.matches('.link-inner #delBtn')
            ) {
                console.log('여기는 이벤트 대상이 아니야!');
                return;
            }

            //다운로드 처리
            if (e.target.matches('.title #download')) {
                if (confirm('다운로드를 진행하시겠습니까?')) {
                    location.href = e.target.getAttribute('href'); // 글 번호 가져옴
                }
                return;
            }

            //삭제 처리
            //비동기 방식으로 삭제를 진행해 주세요. (삭제 버튼은 여러 개 입니다! -> 이미 위에서 부모에 걸어줌)
            //서버쪽에서 권한을 확인 해 주세요. (작성자와 로그인 중인 사용자의 id를 비교해서 일치하는지의 여부)
            //일치하지 않는다면 문자열 "noAuth" 리턴, 삭제 완료하면 "success" 리턴
            //url: /snsboard/글번호 method: DELETE
            if (e.target.matches('.link-inner #delBtn')) { //삭제하기 링크를 클릭했을 때 이벤트를 발생 시켜서
                //const userId = '${sessionScope.login}';
                const bno = e.target.getAttribute('href');
                //const bno2 = document.getElementById('delBtn').value;
                //console.log('지금 로그인중인 아이디: ', userId);
                console.log('삭제버튼 누른 글 번호1: ', bno);
                // console.log('삭제버튼 누른 글 번호2: ', bno2);

                fetch('${pageContext.request.contextPath}/snsboard/' + bno, {
                        method: 'delete'
                    })
                    // .then(res => res.text())
                    // .then(data => {
                    // if(data === 'noAuth'){
                    // alert('로그인을 먼저 해주세요!');
                    // }else if(data === 'fail') {
                    // alert('권한이 없습니다. 관리에게 문의하세요.');
                    // }else{
                    // alert('게시물이 성공적으로 삭제되었습니다!');
                    // getList(1, true); // 삭제가 반영된 새로운 글 목록 보여주기!
                    // }

                    // });
                    .then(res => {
                        console.log(res.status); // 서버에서 ResponseEntity를 통해 return할 경우 가능
                        if (res.status === 401) {
                            alert('권한이 없습니다.');
                        } else if (res.status === 500) {
                            alert('관리자에게 문의하세요.');
                        } else {
                            alert('게시물이 정상적으로 삭제되었습니다.');
                            getList(1, true);
                        }
                    })

                return; //삭제처리 완료 후 메서드 종료될 수 있도록 -> 밑에 상세보기 로직이 있기 때문에
            }

            //글 번호 얻기!
            const bno = e.target.dataset.bno;
            console.log('bno: ', bno);


            //fetch함수를 사용하여 글 상세 보기 요청을 비동기 식으로 요청하세요.
            // url: /snsboard/content/글번호 -> GET

            //const img = document.getElementById('');

            fetch('${pageContext.request.contextPath}/snsboard/content/' + bno)

                //전달 받은 글 내용을 미리 준비한 모달창에 뿌릴 겁니다.(모달 위에 있어요.)
                //값을 제 위치에 배치하시고 모달을 열어 주세요. 
                //(부트스트랩 모달이기 때문에 jQuery로 열어주세요.)

                .then(res => res.json())
                .then(data => {
                    //console.log('data 내용 : ', data)
                    //if (e.target.matches('.')) {
                    document.getElementById('snsImg').setAttribute('src',
                        '${pageContext.request.contextPath}/snsboard/display/' + data.fileLoca + '/' +
                        data.fileName);
                    // ㄴ> src에 img경로(요청+파일경로+파일명)을 줘야 한다.
                    document.getElementById('snsContent').textContent = data.content;
                    document.getElementById('snsRegdate').textContent = data.regDate;
                    document.getElementById('snsWriter').textContent = data.writer;
                });

            $('#snsModal').modal('show');
        })





        const handleScroll = _.throttle(() => {
            /*
                쓰로틀링 - 일정한 간격으로 함수를 실행.
                쓰로틀링은 사용자가 이벤트를 몇번이나 발생시키든, 일정한 간격으로
                한 번만 실행하도록 하는 기법.
                마우스 움직임, 스크롤 이벤트 같은 짧은 주기로 자주 발생하는 경우에 사용하는 기법 (lodash 라이브러리를 이용해 구현)
                -> 쓰로틀이 랜더링을 1초에 한번씩만 되도록 강제해줌 -> 랜더링 문제가 사라짐.
                - 이 기능을 브라우저엔 없어서 쓰로틀을 호출해서 사용해야 한다!
            */

            //console.log('throttle activate!');
            const scrollPosition = window.pageYOffset;
            const height = document.body.offsetHeight;
            const windowHeight = window.innerHeight;

            if (isFinish) {
                if (scrollPosition + windowHeight >= height * 0.9) { //스크롤이 전체 바의 길이의 90% 이상 내려왔냐?
                    console.log('next page call!');
                    getLikeList(++page, false); //getList -> getLikeList로 변경
                }
            }
        }, 1000);

        //무한 스크롤 페이징
        //브라우저 창에서 스크롤이 발생할 때마다 이벤트 발생!
        window.addEventListener('scroll', () => {
            if (!reqStatus) handleScroll();
        });

        //좋아요 기능을 구현
        $contentDiv.addEventListener('click', e => {
            e.preventDefault();
            if (!e.target.matches('#likeBtn')) return;
            console.log('좋아요 버튼이 클릭됨!');

            //lno는 seq로 올릴거니까 userId, bno만 서버로 보내주면 된다!
            const id = '${login}'; //현재 로그인 중인 사용자의 아이디.
            if (id === '') {
                //로그인을 안하고 좋아요를 눌렀을 경우
                alert('로그인 후 이용해주세요!');
                return;
            }
            const bno = e.target.getAttribute('href'); //좋아요를 누른 글 번호 가져 옴

            //이러고 보내면 된다! -> fetch
            fetch('${pageContext.request.contextPath}/snsboard/like', {
                    method: 'post', //조회 요청이 아니기 때문에 post
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        'userId': id,
                        'bno': bno
                    })
                })
                .then(res => res.text())
                .then(data => {
                    console.log('data: ', data);
                    if (data === 'like') {
                        //좋아요 클릭 성공
                        //성공했다면 색을 img2.png로 바꾸고 싶음!
                        e.target.firstElementChild.setAttribute('src',
                            '${pageContext.request.contextPath}/img/like2.png');
                        e.target.style.color = 'blue';

                        //span태그 값 가져오기 -> 522
                        const $cnt = e.target.parentNode.previousElementSibling.children[1];
                        $cnt.textContent = Number($cnt.textContent) + 1;
                        //JS 형변환 -> 인트화 시켜주는 함수 Number()
                    } else {
                        e.target.firstElementChild.setAttribute('src',
                            '${pageContext.request.contextPath}/img/like1.png');
                        e.target.style.color = 'black';
                        const $cnt = e.target.parentNode.previousElementSibling.children[1];
                        $cnt.textContent = Number($cnt.textContent) - 1;
                    }
                });





        })










        //자바 스크립트 파일 미리보기 기능
        function readURL(input) {
            if (input.files && input.files[0]) {

                var reader = new FileReader(); //비동기처리를 위한 파읽을 읽는 자바스크립트 객체
                //readAsDataURL 메서드는 컨텐츠를 특정 Blob 이나 File에서 읽어 오는 역할 (MDN참조)
                reader.readAsDataURL(input.files[0]);
                //파일업로드시 화면에 숨겨져있는 클래스fileDiv를 보이게한다
                //$(".fileDiv").css("display", "block");
                document.querySelector('.fileDiv').style.display = 'block';

                reader.onload = function (event) { //읽기 동작이 성공적으로 완료 되었을 때 실행되는 익명함수
                    //$('#fileImg').attr("src", event.target.result);
                    document.getElementById('fileImg').setAttribute('src', event.target.result);

                    console.log(event.target) //event.target은 이벤트로 선택된 요소를 의미
                }
            }
        }

        document.getElementById('file').onchange = function () {
            readURL(this); //this는 #file자신 태그를 의미

        };
    </script>