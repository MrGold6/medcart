<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Lera Kos
  Date: 27.04.2021
  Time: 19:24
  To change this template use File | Settings | File Templates.
--%>
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
    <title>New visit</title>

</head>

<body>
<div class="container">

    <div class="row">
        <content>


            <div class="col-xl-7 col-lg-10 col-md-10 col-sm-10 mx-auto p-3 pt-3">

                <div class="card card_form">

                    <div id="return">
                        <button onclick="document.location = '/patient/${doctor.specialization.id}/doctorsBySpecialization';" type="button" class="btn btn-circle btn-lg d-flex justify-content-center align-items-center">
                            <i class="bi bi-arrow-left ar"></i>
                        </button>
                    </div>


                    <div class="card-body">

                        <div class="col-xl-8 col-lg-8 col-md-8 col-sm-8 pt-1 px-2 pb-2 mx-auto">
                            <form class="d-flex" id="search" method="GET" action="/patient/${doctor.RNTRC}/schedulesByDay">
                                <input class="form-control me-2 shadow bg-body rounded rounded-pill border-0" id="search_input" type="date" name="date1" placeholder="номер телефону"  required>
                                <button class="btn btn_find mx-2" id="search_button" type="submit">Знайти</button>
                                <a href="/patient/${doctor.RNTRC}/schedule" class="btn btn_find_all">Cьогодні</a>
                            </form>
                        </div>

                    <c:if test="${!schedules.isEmpty()}">

                        <div class="table-wrapper-scroll-y my-custom-scrollbar">

                            <table class="table tableFixHead">
                                <thead>
                                <tr>
                                    <th>Час</th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach var="record" items="${schedules}" varStatus="i">
                                <tr onclick='document.location="<c:url value='/patient/${doctor.RNTRC}/add_visit/${date}/${record.id}'/>"'>
                                    <td>${record.time}</td>
                                </tr>

                                </c:forEach>

                                </tbody>
                            </table>
                        </div>
                    </c:if>

                    <c:if test="${schedules.isEmpty()}">
                        <h2 class="pt-5"><em><center>Доступного часу немає</center></em></h2>
                    </c:if>
                    </div>
                </div>
            </div>
        </content>
        <footer></footer>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
</body>
</html>
