<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
label {
	display: block;
	margin-bottom: 20px;
}

.wrap {
	width: 800px;
	margin: 100px auto;
	border: 1px dashed #000;
}

.wrap h1 {
	margin: 0 auto;
	padding-bottom: 20px;
	width: fit-content;
	border-bottom: 1px solid #000;
}

.wrap .menu {
	font-size: 24px;
	width: 80%;
	margin: 20px auto;
}

.wrap .menu select {
	width: 250px;
	height: 50px;
	font-size: 28px;
	margin-top: 10px;
}

.clearfix::after {
	content: '';
	display: block;
	clear: both;
}
</style>

</head>
<body>

	<div class="wrap">
		<h1>커피 주문서</h1>

		<div class="menu">
			<form action="/basic/coffee/result" method="post">
				<label> # 주문 목록 <br> <select id="menu-sel" name="menu">
						<option value="americano">아메리카노</option>
						<option value="cafeLatte">카페라떼</option>
						<option value="macchiato">카라멜 마끼아또</option>
				</select>
				</label> <label class="price"># 가격: <span class="price-value">3000</span>원
				</label>

				<!-- 화면에 렌더링은 안되지만 서버로 보낼 수 있음 -->
				<input id="price-tag" type="hidden" name="price"> <label>
					<button type="submit">주문하기</button>
				</label>
			</form>



		</div>
	</div>

	<script>
	
		const coffeePrice = {
			americano: 3000,
			cafeLatte: 4500,
			macchiato: 5000
		};
		// change: input이나 select 태그의 값이 변했을 때
		
		//요소를 취득할 땐 대부분 const를 사용 + name앞에 $ 붙여주는 것이 관례
		const $menu = document.getElementById('menu-sel');
		$menu.onchange = e => { //익명함수를 열거기 때문에 e 라고만 적어주고(매개변수가 1개일때만) 화살표함수방식으로 작성 가능
								//event객체를 알아야 하기 때문에 e라고 적어줌. 받을 게 없으면 ()라고만 적어줘도 된다.
			//커피를 선택하면 가격이 변해야 함.
 			//console.log('이벤트 타겟: ', e.target.value); //e.target.value -> 이벤트가 발생된 곳의 타켓의 주체! 의 value
			//console.log('변겅된 커피 값: ', coffeePrice[e.target.value]); 
 			
 			const price = coffeePrice[e.target.value];
 			document.querySelector('.price-value').textContent = price;
 			
 			const $priceTag = document.getElementById('price-tag');
 			$priceTag = price;
			
		}
		
		

		
		
		
	
	
	
	</script>

</body>
</html>