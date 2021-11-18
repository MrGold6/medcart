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
            <h1 class="pt-4">Електронна медична картка. Пацієнт: ${patient.surname} ${patient.name.charAt(0)}.${patient.middle_name.charAt(0)}.  </h1>

            <div class="table-wrapper-scroll-y my-custom-scrollbar table-responsive">

                <table class="table tableFixHead table-striped">
                    <thead>
                    <tr>
                        <th>Ід</th>
                        <th>Дата</th>
                        <th>Діагноз</th>
                        <th>Лікар</th>
                        <th>Час</th>
                        <th>Статус</th>
                        <th>Дії</th>


                    </tr>
                    </thead>

                    <c:if test="${!visitsList.isEmpty()}">
                    <tbody>
                    <c:forEach var="visit" items="${visitsList}" varStatus="i">
                        <tr>
                            <td>${visit.number}</td>
                            <td>${visit.date}</td>
                            <td>${visit.disease.name}</td>
                            <td>${visit.doctor.specialization.name}</td>
                            <td>${visit.schedule.time}</td>
                            <td>${visit.status}</td>

                            <td><a href="/admin/${patient.RNTRC}/${visit.number}/edit_visit/" class="btn btn_edit"><i class="bi bi-vector-pen "></i></a>
                                <a href="/admin/${patient.RNTRC}/${visit.number}/delete_visit" class="btn btn_delete"><i class="bi bi-trash "></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                </c:if>
                <c:if test="${visitsList.isEmpty()}">
                    <h2><em><center>Візитів немає</center></em></h2>
                </c:if>

            </div>

            <center>
                <button onclick="document.location = '/admin/${patient.RNTRC}/add_visit';" type="button" class="btn my-2 btn_add">
                    Створити
                </button>
            </center>
        </content>

    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>



</body>

</html>