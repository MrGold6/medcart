<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <title>Visits</title>
</head>

<body style="background-color: #ffffff;">
<div class ="container-fluid">
    <div class="row">
        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 p-0">

            <nav class="navbar navbar-expand-lg navbar-light " style="background-color: rgb(210 193 193);">
                <div class="container-fluid">
                    <a class="navbar-brand logo" href="#" style="color: #9b3963">MedCard</a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarScroll" aria-controls="navbarScroll" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>


                    <div class="collapse navbar-collapse justify-content-end" id="navbarScroll">

                        <ul class="navbar-nav  navbar-nav-scroll my-2 my-lg-0 " style="--bs-scroll-height: 100px;">

                            <li class="nav-item ">
                                <a class="nav-link link active" aria-current="page" href= '/logout'>Вийти</a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link link active" href= '/today_visits'>Візити на сьогодні</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link link active" href= '/patients/doctor'>${doctor.specialization.name} ${doctor.surname} ${doctor.name.charAt(0)}.${doctor.middle_name.charAt(0)}.</a>
                            </li>

                        </ul>

                    </div>
                </div>


            </nav>


            <div style="background-color: #ffffff;">
                <content>

                    <h2 class="text-center">Список візитів</h2>

                    <div class="col-xl-5 col-lg-5 col-md-7 col-sm-5 pt-1 px-2 pb-2 mx-auto">
                        <form class="d-flex" id="search" method="GET" action="/visitsByDay">
                            <input class="form-control me-2 shadow bg-body rounded rounded-pill border-0" id="search_input" type="date" name="date" placeholder="номер телефону"  required>
                            <button class="btn btn_find mx-2" id="search_button" type="submit">Знайти</button>
                            <a href="/today_visits" class="btn btn_find_all">Cьогодні</a>
                        </form>
                    </div>

                    <c:if test="${!visitsList.isEmpty()}">
                        <div class="table-wrapper-scroll-y my-custom-scrollbar">

                            <table class="table tableFixHead">
                                <thead>
                                <tr>
                                    <th>Дата</th>
                                    <th>Діагноз</th>

                                </tr>
                                </thead>



                                <tbody>
                                <c:forEach var="visit" items="${visitsList}" varStatus="i">
                                    <tr onclick='document.location="<c:url value='/${visit.number}/visits'/>"'>
                                        <td>${visit.date}</td>
                                        <td>${visit.patient.name}</td>
                                    </tr>
                                </c:forEach>

                                </tbody>
                            </table>
                        </div>
                    </c:if>

                    <c:if test="${visitsList.isEmpty()}">
                        <h2 class="pt-5"><em><center>Візитів немає</center></em></h2>
                    </c:if>

                </content>
                <footer></footer>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>