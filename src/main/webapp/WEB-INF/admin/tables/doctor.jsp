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
            <h2 class="pt-3">Лікарі</h2>

            <div class="table-wrapper-scroll-y my-custom-scrollbar table-responsive">

                <table class="table tableFixHead table-striped">
                    <thead>
                    <tr>
                        <th>РНОКПП</th>
                        <th>ПІП</th>
                        <th>Стать</th>
                        <th>Номер телефону</th>
                        <th>Спеціалізація</th>
                        <th>Юзер</th>
                        <th>Розклад</th>
                        <th>Дії</th>

                    </tr>
                    </thead>

                    <c:if test="${!doctorList.isEmpty()}">
                    <tbody>
                    <c:forEach var="doctor" items="${doctorList}" varStatus="i">
                        <tr>
                            <td>${doctor.RNTRC}</td>
                            <td>${doctor.surname} ${doctor.name.charAt(0)}.${doctor.middle_name.charAt(0)}.</td>
                            <td>${doctor.sex== 0 ? "Чоловік" : "Жінка" }</td>
                            <td>0${doctor.telephone_number}</td>
                            <td>${doctor.specialization.name}</td>

                            <td>
                                <c:if test="${doctor.user.username==null}"><a href="/admin/${doctor.RNTRC}/set_user_for_doctor/" class="btn btn_add"><i class="bi bi-person"></i></a> </c:if>
                                <c:if test="${doctor.user.username!=null}">${doctor.user.username}</c:if></td>

                            <td><a href="/admin/${doctor.RNTRC}/schedule" class="btn btn_find_all"><i class="bi bi-calendar4-event"></i></a></td>


                            <td><a href="/admin/${doctor.RNTRC}/edit_doctor/" class="btn btn_edit"><i class="bi bi-vector-pen "></i></a>
                                <a href="/admin/${doctor.RNTRC}/delete_doctor" class="btn btn_delete"><i class="bi bi-trash "></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                </c:if>
                <c:if test="${doctorList.isEmpty()}">
                    <h2><em><center>Пацієнтів немає</center></em></h2>
                </c:if>

            </div>

            <center>
                <button onclick="document.location = '/admin/add_doctor';" type="button" class="btn my-2 btn_add">
                    Створити
                </button>
            </center>
        </content>

    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>



</body>

</html>