<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Lera Kos
  Date: 27.04.2021
  Time: 22:41
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

    <title>Patient</title>
</head>
<body>
<div class="container">

    <div class="row">
        <content>

            <div class="col-xl-7 col-lg-10 col-md-10 col-sm-10 mx-auto p-3 pt-3">

                <div class="card card_form">

                            <div id="return">
                                <button onclick="document.location = '/patient/1';" type="button" class="btn btn-circle btn-lg d-flex justify-content-center align-items-center">
                                    <i class="bi bi-arrow-left ar"></i>
                                </button>
                            </div>


                    <div class="card-body">
                        <legend class="card-title text-center">Пацієнт</legend>
                        <c:url value="/patient/edit_patient" var="editUrl"/>
                        <form action="${editUrl}" name="patient" method="POST">


                                <div class="row mb-3">
                                    <label class="col-sm-6 col-form-label ln">Номер карти:</label>
                                    <div class="col-sm-6">
                                        <input type="number"  name="RNTRC" class="form-control" placeholder="RNTRC" value="${patient.RNTRC}" maxlength="100" readonly>
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <label class="col-sm-6 col-form-label ln">Прізвище:</label>
                                    <div class="col-sm-6">
                                        <input type="text" name="surname" class="form-control" placeholder="surname" value="${patient.surname}" required>
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <label class="col-sm-6 col-form-label ln">Ім'я:</label>
                                    <div class="col-sm-6">
                                        <input type="text" name="name" class="form-control" placeholder="name" value="${patient.name}" maxlength="100" required>
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <label class="col-sm-6 col-form-label ln">По-батькові:</label>
                                    <div class="col-sm-6">
                                        <input type="text" name="middle_name" class="form-control" placeholder="middle_name" value="${patient.middle_name}" required>
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <label class="col-sm-6 col-form-label ln">Дата народження:</label>
                                    <div class="col-sm-6">
                                        <input type="date" name="date_of_birth" class="form-control" placeholder="date_of_birth" value="${patient.date_of_birth}" maxlength="20" readonly>
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <label class="col-sm-6 col-form-label ln">Стать:</label>
                                    <div class="col-sm-6">
                                        <div class="form-check form-check-inline">
                                            <input type="radio" id="sex1" class="form-check-input" name="sex" value="0" <c:if test="${patient.sex== 0}">checked</c:if>>
                                            <label for="sex1" class="form-check-label" >Чоловік</label>

                                        </div>
                                        <div class="form-check form-check-inline">
                                            <input type="radio" id="sex2" class="form-check-input" name="sex" value="1"  <c:if test="${patient.sex== 1}">checked</c:if>>
                                            <label for="sex2" class="form-check-label">Жінка</label>
                                        </div>
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <label class="col-sm-6 col-form-label ln">Номер телефону:</label>
                                    <div class="col-sm-6">
                                        <input type="number" name="telephone_number" class="form-control" value="${patient.telephone_number}" required>
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <label class="col-sm-6 col-form-label ln">Електронна пошта:</label>
                                    <div class="col-sm-6">
                                        <input type="email"  name="email" class="form-control" value="${patient.email}" required>
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <label class="col-sm-6 col-form-label ln">Адреса проживання:</label>
                                    <div class="col-sm-6">
                                        <input type="text"  name="address" class="form-control" value="${patient.address}" required>
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <label class="col-sm-6 col-form-label ln">Група крові:</label>
                                    <div class="col-sm-6">
                                        <input type="number" name="blood_type" class="form-control" value="${patient.blood_type}" maxlength="1" readonly>
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <label class="col-sm-6 col-form-label ln">Резус-фактор:</label>
                                    <div class="col-sm-6">
                                        <input type="text" name="rh" class="form-control" value="${patient.rh}" maxlength="1" readonly>
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <label class="col-sm-6 col-form-label ln">Хронічне захворювання:</label>
                                    <div class="col-sm-6">
                                        <input type="text" name="chronic_disease" class="form-control" placeholder="немає" value="${patient.chronic_disease}" maxlength="100" readonly>
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <label class="col-sm-6 col-form-label ln">Алергічний анамнез:</label>
                                    <div class="col-sm-6">
                                        <input type="text" name="allergic_history" class="form-control"  placeholder="немає" value="${patient.allergic_history}" maxlength="100" readonly>
                                    </div>
                                </div>

                            <c:set value="/patient/edit_patient" var="edit_patient"/>
                            <center>
                                <input type="submit" id="in"  class="btn btn_form_add" name="${edit_patient}" value="Змінити">
                            </center>


                        </form>
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