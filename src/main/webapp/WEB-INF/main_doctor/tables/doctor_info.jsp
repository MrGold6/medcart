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
            <h2 class="pt-3">
                Лікар: ${doctor.surname} ${doctor.name.charAt(0)}.${doctor.middle_name.charAt(0)}.
                Відділення: <c:if test="${department.name!=''}">${doctor.department.name}</c:if>
                <c:if test="${department.name==null}">
                    <a href="/admin/${doctor.RNTRC}/add_department" class="btn btn_add">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-building" viewBox="0 0 16 16">
                            <path fill-rule="evenodd" d="M14.763.075A.5.5 0 0 1 15 .5v15a.5.5 0 0 1-.5.5h-3a.5.5 0 0 1-.5-.5V14h-1v1.5a.5.5 0 0 1-.5.5h-9a.5.5 0 0 1-.5-.5V10a.5.5 0 0 1 .342-.474L6 7.64V4.5a.5.5 0 0 1 .276-.447l8-4a.5.5 0 0 1 .487.022zM6 8.694 1 10.36V15h5V8.694zM7 15h2v-1.5a.5.5 0 0 1 .5-.5h2a.5.5 0 0 1 .5.5V15h2V1.309l-7 3.5V15z"/>
                            <path d="M2 11h1v1H2v-1zm2 0h1v1H4v-1zm-2 2h1v1H2v-1zm2 0h1v1H4v-1zm4-4h1v1H8V9zm2 0h1v1h-1V9zm-2 2h1v1H8v-1zm2 0h1v1h-1v-1zm2-2h1v1h-1V9zm0 2h1v1h-1v-1zM8 7h1v1H8V7zm2 0h1v1h-1V7zm2 0h1v1h-1V7zM8 5h1v1H8V5zm2 0h1v1h-1V5zm2 0h1v1h-1V5zm0-2h1v1h-1V3z"/>
                        </svg>
                    </a>
                </c:if>
            </h2>

            <div class="table-wrapper-scroll-y my-custom-scrollbar table-responsive">
                <c:if test="${!doctor.schedules.isEmpty()}">

                <table class="table tableFixHead table-striped">
                    <thead>
                    <tr>
                        <th>День<a href="/admin/${doctor.RNTRC}/schedule/2" class="btn btn-sm "><i class="bi bi-sort-down text-light"></i></a></th>
                        <th>Час<a href="/admin/${doctor.RNTRC}/schedule/3" class="btn btn-sm "><i class="bi bi-sort-down text-light"></i></a></th>
                        <th>Дії</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="schedule" items="${doctor.schedules}" varStatus="i">
                        <tr>
                            <td>${schedule.dayName}</td>
                            <td>${schedule.time}</td>
                            <td><a href="/admin/${doctor.RNTRC}/${schedule.id}/edit_schedule/" class="btn btn_edit"><i class="bi bi-vector-pen "></i></a>
                                <a href="/admin/${doctor.RNTRC}/${schedule.id}/delete_schedule" class="btn btn_delete"><i class="bi bi-trash "></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                </c:if>
                <c:if test="${doctor.schedules.isEmpty()}">
                    <h2><em><center>Розкладу немає</center></em></h2>
                </c:if>

            </div>

            <center>
                <button onclick="document.location = '/admin/${doctor.RNTRC}/add_schedule';" type="button" class="btn my-2 btn_add">
                    Створити
                </button>
            </center>
        </content>

    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>



</body>

</html>