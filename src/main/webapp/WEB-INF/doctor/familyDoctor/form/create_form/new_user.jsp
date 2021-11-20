<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Обязательные метатеги -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
    <link rel="stylesheet" type="text/css" href="<c:url value="/res/style.css"/>">
    <link rel="icon" type="image/png" href="<c:url value="/res/medicine.png"/>"/>

    <title>New patient</title>

</head>
<c:url value="/doctor1/add_user" var="addUrl"/>
<body>
<div class="container">

    <div class="row">
        <content>

            <div class="col-xl-7 col-lg-10 col-md-10 col-sm-10 mx-auto p-3 pt-3">

                <div class="card card_form">

                    <div id="return">
                        <button onclick="document.location = '/doctor1/patients';" type="button" class="btn btn-circle btn-lg d-flex justify-content-center align-items-center">
                            <i class="bi bi-arrow-left ar"></i>
                        </button>
                    </div>


                    <div class="card-body">
                        <legend class="card-title text-center">Пацієнт</legend>
                        <form:form action="${addUrl}" method="POST" name="user"  class="was-validated">
                            <input type="hidden" name="id" class="form-control" value="${user.id}">
                            <input type="hidden" name="id_patient" class="form-control" value="${id_patient}">


                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Назва:</label>
                                <div class="col-sm-6">
                                    <input type="text" name="username" minlength="5" class="form-control" value="${user.username}" required>
                                </div>
                            </div>
                            <form:errors path="username"></form:errors>
                            ${usernameError}

                            <c:if test="${empty user.id}">

                                <div class="row mb-3">
                                    <label class="col-sm-6 col-form-label ln">Пароль:</label>
                                    <div class="col-sm-6">
                                        <input type="password" name="password" minlength="5"  class="form-control" value="${user.password}" required>
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <label class="col-sm-6 col-form-label ln">Підтвердити пароль::</label>
                                    <div class="col-sm-6">
                                        <input type="password" name="passwordConfirm" minlength="5" class="form-control" value="${user.passwordConfirm}" required>
                                    </div>
                                </div>
                                <form:errors path="password"></form:errors>
                                ${passwordError}
                            </c:if>


                            <center>
                                <c:set value="add_user" var="add_user"/>
                                <input type="submit" id="in" class="btn btn_form_add" name="${add_user}" value="Створити">

                            </center>


                        </form:form>
                    </div>
                </div>
            </div>

            <!-- Modal -->
            <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-body">
                            <div class="text-center text-justify">
                                <i class="bi bi-exclamation-triangle-fill text-danger pl-2 pt-2 dang  position-relative"></i>
                                <span class="ml-2" style="font-size:16pt;">Такий пацієнт вже існує</span>
                            </div>
                            <center><button type="button" class="btn btn-outline-primary mt-3" data-bs-dismiss="modal">OK</button></center>
                        </div>


                    </div>
                </div>
            </div>


        </content>
        <footer></footer>
    </div>
</div>




<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>



</body>
</html>

<c:if test="${message!=null}">
    <script type="text/javascript">
        var myModal = new bootstrap.Modal(document.getElementById("staticBackdrop"), {});
        document.onreadystatechange = function () {
            myModal.show();
        };
    </script>
</c:if>