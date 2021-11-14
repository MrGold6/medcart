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
<legend class="card-title text-center">Візит</legend>
                        <form:form  method="POST" modelAttribute="visit" action="/patient/add_visit_act">

                            <input name="id_doctor" type="hidden" value="${doctor.RNTRC}" maxlength="100" readonly>

                            <input name="id_schedule" type="hidden" value="${schedule.id}" maxlength="100" readonly>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Дата:</label>
                                <div class="col-sm-6">
                                         <input type="date" name="date" value="${date}" class="form-control" maxlength="20" data-inputmask="'alias': 'date','placeholder': '*'" readonly>
                                </div>
                            </div>

                            <div class="row mb-9">
                                <div>

                                    <table class="table tableFixHead">
                                        <thead>
                                        <tr>
                                            <th>Час</th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                        <tr>
                                            <td>${schedule.time}</td>
                                        </tr>

                                        </tbody>
                                    </table>
                                </div>
                            </div>



                            <c:set value="add_visit_act" var="add_visit_act"/>
                            <center>
                                <input type="submit" id="in" name="${add_visit_act}" class="btn btn_form_add" value="Далі">
                            </center>
                            <p>${message}</p>

                        </form:form >


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