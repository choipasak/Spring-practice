<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../include/header.jsp"%>


<section>
	<div class="container-fluid">
		<div class="row">
			<!--lg에서 9그리드, xs에서 전체그리드-->
			<div class="col-lg-9 col-xs-12 board-table">
				<div class="titlebox">
					<p>자유게시판</p>
				</div>
				<hr>

				<!--form select를 가져온다 -->
				<form action="${pageContext.request.contextPath}/freeboard/freeList"> <!-- 검색에 따른 목록 요청이어서 /freeList -->
					<div class="search-wrap">
						<button type="submit" class="btn btn-info search-btn">검색</button>
						<input type="text" name="keyword" class="form-control search-input" value="${pc.page.keyword }">
						<select name="condition"
							class="form-control search-select">
							<option value="title" ${pc.page.condition == 'title' ? 'selected' : ''}>제목</option>
							<option value="content" ${pc.page.condition == 'content' ? 'selected' : ''}>내용</option>
							<option value="writer" ${pc.page.condition == 'writer' ? 'selected' : ''}>작성자</option>
							<option value="titleContent" ${pc.page.condition == 'titleContent' ? 'selected' : ''}>제목+내용</option>
						</select>
					</div>
				</form>

				<table class="table table-bordered">
					<thead>
						<tr>
							<th>번호</th>
							<th class="board-title">제목</th>
							<th>작성자</th>
							<th>등록일</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="vo" items="${boardList}">
							<tr>
								<td>${vo.bno}</td>
								<td><a href="${pageContext.request.contextPath}/freeboard/content?bno=${vo.bno}&pageNo=${pc.page.pageNo}&amount=${pc.page.amount}&keyword=${pc.page.keyword}&condition=${pc.page.condition}">${vo.title}</a></td>
								<td>${vo.writer}</td>
								<td>${vo.date}</td>
							</tr>
						</c:forEach>
					</tbody>

				</table>


				<!--페이지 네이션을 가져옴-->
				<form action="${pageContext.request.contextPath}/freeboard/freeList" name="pageForm">
					<div class="text-center">
						<hr>
						<ul id="pagination" class="pagination pagination-sm">
							<c:if test="${pc.prev}">
								<li><a href="#" data-pagenum="${pc.begin-1}">이전</a></li>
							</c:if>

							<c:forEach var="num" begin="${pc.begin}" end="${pc.end}">
								<!-- step은 생략(기본값 1) -->
								<li class="${pc.page.pageNo == num ? 'active' : ''}"><a href="#" data-pagenum="${num}">${num}</a></li>
								<!-- ${num} -> forEach문의 제어변수 -->
							</c:forEach>

							<c:if test="${pc.next}">
								<li><a href="#" data-pagenum="${pc.end+1}">다음</a></li>
							</c:if>
						</ul>
						<button type="button" class="btn btn-info"
							onclick="location.href='${pageContext.request.contextPath}/freeboard/freeRegist'">글쓰기</button>
					</div>

					<input type="hidden" name="pageNo" value="${pc.page.pageNo}">
					<input type="hidden" name="amount" value="${pc.page.amount}">
					<input type="hidden" name="keyword" value="${pc.page.keyword}">
					<input type="hidden" name="condition" value="${pc.page.condition}">
					

				</form>

			</div>
		</div>
	</div>
</section>

<%@ include file="../include/footer.jsp"%>

<script>

        // 브라우저 창이 로딩이 완료 된 후에 실행할 것을 보장하는 이벤트.
        window.onload = function(){

            //버튼마다 이벤트 거는거 귀찮음 -> 부모 태그에 이벤트를 건다.
            //사용자가 페이지 관련 버튼(이전, 다음, 1, 2, 3 ...)을 클릭했을 때,
            //a태그의 href에다가 각각 다른 url을 작성해서 요청을 보내기가 귀찮다.
            //클릭한 버튼이 무엇인지를 확인해서 그 버튼에 맞는 페이지 정보를
            //자바스크립트로 끌고 와서 요청을 보내 주겠다.
            document.getElementById('pagination').addEventListener('click', e => { //e: event객체 함수
                if(!e.target.matches('a')){ // 클릭한 버튼인 target이 'a'가 아니라면 = a태그가 아니라면
                    return;
                }

                e.preventDefault(); //a태그의 고유 기능 중지

                //현재 이벤트가 발생한 요소(버튼)의
                //data-pagenum의 값을 얻어서 변수에 저장.
                //data- 으로 시작하는 속성값을 dataset 프로퍼티로 쉽게 끌고 올 수 있다.
                const value = e.target.dataset.pagenum;

                //페이지 버튼들을 감싸고 있는 form태그를 지목해
                //그 안에 숨겨져 있는 input태그의 value에
                //위에서 얻은 data-pagenum의 값을 삽입한 후 submit
                document.pageForm.pageNo.value = value;
                document.pageForm.submit();

            });

            const msg = '${msg}'; // 컨트롤러에서 if문으로 처리한 결과를 model객체에 담아서 보낸 msg
            if(msg === 'searchFail'){
                alert('검색 결과가 없었습니다.');
            }




        }

    </script>



