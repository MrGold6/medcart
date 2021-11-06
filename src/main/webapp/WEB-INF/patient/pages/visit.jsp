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

    <title>Electronic card</title>
</head>

<body>
<div class="container">

    <div class="row">
        <content>

            <div class="col-xl-7 col-lg-10 col-md-10 col-sm-10 mx-auto p-3 pt-3">
                <div class="card card_form">

                    <div id="return">
                        <button onclick="document.location = '/patient/${id_patient}/visits';" type="button" class="btn btn-circle btn-lg d-flex justify-content-center align-items-center">
                            <i class="bi bi-arrow-left ar"></i>
                        </button>
                    </div>


                    <div class="card-body">
                        <legend class="card-title text-center">Візит</legend>
                        <div class="form" name="visit">


                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Дата:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="date" placeholder="date" value="${visit.date}" maxlength="100" readonly>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Діагноз:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="text" placeholder="disease" value="${visit.disease.name}" readonly>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Спеціалізація лікаря:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="text" placeholder="doctor" value="${visit.doctor.specialization}" readonly>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Виписані ліки:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="text" placeholder="немає" value="${visit.medicine}" maxlength="100" readonly>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Cкарги:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="text"  value="${visit.complaints}" maxlength="50" readonly>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Проведені дії:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="text" value="${visit.actions}" maxlength="100" readonly>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Нотатки:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="text" placeholder="немає" value="${visit.notes}" maxlength="100" readonly>
                                </div>
                            </div>

                        </div>
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