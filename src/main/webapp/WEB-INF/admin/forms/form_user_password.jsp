<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<jsp:include page="../template/head.jsp" />

<body class="d-flex flex-column min-vh-100">
<jsp:include page="../template/header.jsp" />
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../template/nav.jsp" />
        <content class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <div class="col-xl-7 col-lg-10 col-md-10 col-sm-10 mx-auto p-3 pt-3">
                <div class="card card_form shadow">
                    <div class="card-body ">
                        <c:if test="${empty user.id}">
                            <title>Add</title>
                        </c:if>
                        <c:if test="${!empty user.id}">
                            <title>Edit</title>
                        </c:if>
                        <legend class="card-title text-center">Юзер</legend>


                        <form:form action="/admin/change_password" method="POST" name="user"  class="was-validated">

                            <input type="hidden" name="id" class="form-control" value="${user.id}" readonly>
                            <input type="hidden" name="username" class="form-control" value="${user.username}" readonly>
                            <input type="hidden" name="my_role_id" class="form-control" value="${my_role_id}" readonly>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Новий пароль:</label>
                                <div class="col-sm-6">
                                    <input type="password" name="password" minlength="5"  class="form-control" required>
                                </div>
                            </div>



                            <center>

                                    <input type="submit" id="edit"  class="btn btn_edit_form" name="edit" value="Змінити">

                            </center>


                        </form:form>
                    </div>
                </div>
            </div>

        </content>

    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>



</body>

</html>
