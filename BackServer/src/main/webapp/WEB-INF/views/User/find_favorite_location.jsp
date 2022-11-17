<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <!-- CSS only -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <title>Document</title>
  </head>
  <body>
    <!-- header -->
    <div class="container-fluid bg-primary text-white text-end" style="vertical-align: middle">
      <div class="d-flex flex-row-reverse">
        <div>
          <a href="../index.html"><button type="button" class="btn text-white">Logout</button></a>
        </div>
        <div  class="container-fluid">
          <a href="member_info.html">
            <button type="button" class="btn text-white">회원정보</button>
          </a>
        </div>
        <div class="container-fluid text-start">
          <a href="index_2.html"><img src="../img/logo.png" alt="" width="15%" /></a>
        </div>
      </div>
      </div>
    </div>
    <!-- menu -->
    <div class="d-flex p-3 flex-row-reverse bg-dark text-white">
      <div class="col-sm-2"><a href="look_favorite.html" class="text-decoration-none text-white">관심지역 둘러보기</a></div>
      <div class="col-sm-2"><a href="fav_loc.html" class="text-decoration-none text-white">관심지역 설정</a></div>
      <div class="col-sm-2"><a href="find_apartment.html" class="text-decoration-none text-white">시세조회</a></div>
      <div class="col-sm-2"><a href="notice.html" class="text-decoration-none text-white">공지사항</a></div>
      <div class="col-sm-2"><a href="guide.html" class="text-decoration-none text-white">이용방법</a></div>
    </div>
    <!-- content -->
    <div class="col-sm-12 h-80 bg-secondary w-100" style="height: 85.5vh">
      <div class="col-sm-6 m-auto bg-white p-5 h-100">
        <div>
          <h2>관심 지역</h2>
          <form action="">
            <div class="mb-3 mt-3">
              <select class="form-select mb-2">
                <option selected>도/광역시</option>
              </select>
              <select class="form-select mb-2">
                <option selected>시 / 구 / 군</option>
              </select>
              <select class="form-select mb-2">
                <option selected>동</option>
              </select>
            </div>
            <button type="submit" class="btn btn-primary">등록</button>
          </form>
          <br/>
          <div>
            <h5 class="border-bottom">등록된 관심 지역</h5>
            <span>서울시 강남구 대치동</span>
            <button>수정</button>
            <button>삭제</button>
          </div>
        </div>
      </div>
      
    </div>
    <!-- footer -->
    <div class="d-flex p-3 bg-dark text-white">
      <div class="col-sm-2"><a href="${root}/board/guide.do" class="text-decoration-none text-white">웹사이트 안내</a></div>
    </div>
  </body>
</html>
