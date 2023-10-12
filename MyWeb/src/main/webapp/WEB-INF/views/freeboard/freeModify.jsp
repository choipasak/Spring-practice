<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../include/header.jsp"%>

<section>
	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-md-9 write-wrap">
				<div class="titlebox">
					<p>수정하기</p>
				</div>
				<!-- form태그에 name을 작성해주면 JS에서 document.updateForm으로 바로 지목할 수 있음. form만 가능. (디자인을 적용시키려면 id나 class가 필요함) -->
				<form action="/myweb/freeboard/modify" method="post"
					name="updateForm">
					<div class="form-group">
						<label>번호</label> <input class="form-control" name="bno"
							value='${gocontent.bno}' readonly>
					</div>
					<div class="form-group">
						<label>작성자</label> <input class="form-control" name='writer'
							value='${gocontent.writer}' readonly>
					</div>
					<div class="form-group">
						<label>제목</label> <input class="form-control" name='title'
							value='${gocontent.title}'>
					</div>

					<div class="form-group">
						<label>내용</label>
						<textarea class="form-control" rows="10" name="content"
							value='${gocontent.content}'></textarea>
					</div>

					<button id="list-btn" type="button" class="btn btn-dark">목록</button>
					<button id="update-btn" type="button" class="btn btn-primary">변경</button>
					<button id="del-btn" type="button" class="btn btn-info">삭제</button>
					<!-- 그래서 이렇게 form이 있는데도 type들이 똑같고 submit이 없다면 JS로 따로처리 했구나~ 라는 생각이 든다 -->
				</form>

			</div>
		</div>
	</div>
</section>

<%@ include file="../include/footer.jsp"%>

<script>
	//목록으로 이동하는 것 처리
	document.getElementById('list-btn').onclick = function() { //property방식으로 이벤트 건 것
		location.href = '/myweb/freeboard/freeList';
	}

	//form 태그는 메서드 없이 form 태그의 name으로 요소를 바로 취득할 수 있습니다.
	const $form = document.updateForm;


	//글 삭제 요청시 목록 페이지로 이동
	document.getElementById('update-btn').onclick = function(){ //update-btn 클릭했을 때 = 수정버튼 눌렀을 때
		
		//form 내부의 요소를 지목할 땐 name 속성으로 바로 지목이 가능합니다.
		if($form.title.value === ''){ //name="updateForm"인 form의 name이 title인 input 태그의 value가 비었니?
			alert('제목은 필수 항목입니다.');
			return;			
		}else if($form.content.value === ''){
			alert('내용을 뭐라도 작성 해 주세요!');
			return;
		}
		// 여기까지 온 것은 submit 해도 된다는 소리.
		// 문제가 없다는 뜻 -> submit
		$form.submit(); //html에서 button의 type을 submit으로 바꾸지 않아도 JS에 submit으로 바꿔주는 메서드가 있다.

		

	}
	
	//삭제 버튼 이벤트 처리
	document.getElementById('del-btn').onclick = () => {
		if(confirm('정말 삭제하시겠습니까?')){ //alert: 그냥 작성한 내용을 띄워주는 알림창, confirm: 작성해준 내용 + 확인/취소 버튼까지 포함 한 창을 띄워주는 알림창
			//특정 태그의 속성을 바꾸고 싶다!
			$form.setAttribute('action', '/myweb/freeboard/delete'); //이 메서드로 form의 method속성도 바꿀 수 있음
			$form.submit();
		}

		//여기까지 오면 위의 confirm에서 '아니오'를 누른 것이기 때문에, 그냥 이벤트가 종료 된 것이다.

	}

</script>
