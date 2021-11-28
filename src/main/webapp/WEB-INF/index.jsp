<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE HTML>
<html>
<head>
  <title>Главная</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">

  <link rel="icon" type="image/png" href="/res/medicine.png"/>
  <link type="text/css" rel="stylesheet" href="../res/style.css" />
</head>
<body>

  <sec:authorize access="hasRole('ROLE_DOCTOR1')">"
    <c:redirect url="/today_visits"/>
  </sec:authorize>

  <sec:authorize access="hasRole('ROLE_DOCTOR2')">"
    <c:redirect url="/today_visits"/>
  </sec:authorize>


  <sec:authorize access="hasRole('ROLE_ADMIN')">"
    <c:redirect url="/admin/user/1"/>
  </sec:authorize>

  <sec:authorize access="hasRole('ROLE_PATIENT')">"
    <c:redirect url="/patient/1"/>
  </sec:authorize>

  <div class="container">

    <div class="row">
      <content>
        <div class="col-xl-5 col-lg-6 col-md-8 col-sm-10 mx-auto p-4">
          <div class="card mt-4 front_card">
            <div class="card-body">

              <form action="/login" method="POST">
                <fieldset>
                  <legend class="card-title text-center">Вхід у систему</legend>

                  <div class="mb-3">
                    <label  class="form-label">Логін:</label>
                    <input type="text" class="form-control" name="username"  required>
                  </div>

                  <div class="mb-3">
                    <label  class="form-label">Пароль:</label>
                    <input type="password" class="form-control" name="password"  required>
                  </div>

                  <center>
                    <input type="submit" id="in" class="btn btn-outline-primary" value="Увійти">
                  </center>
                </fieldset>
              </form>

            </div>
          </div>
        </div>
      </content>
      <footer></footer>
    </div>
  </div>

  <!-- Modal -->
  <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          <div class="text-center text-justify">
            <i class="bi bi-exclamation-triangle-fill text-danger pl-2 pt-2 dang  position-relative"></i>
            <span class="ml-2" style="font-size:16pt;">Неправильний логін або пароль</span>
          </div>
          <center><button type="button" class="btn btn-outline-primary mt-3" data-bs-dismiss="modal">OK</button></center>
        </div>


      </div>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
</body>
</html>