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
    <title>Sick leave</title>
</head>

<body>
<div class="container">

    <div class="row">
        <content>

            <div class="col-xl-7 col-lg-10 col-md-10 col-sm-10 mx-auto p-3 pt-3">

                <div class="card card_form">


                    <div class="card-body">
                        <legend class="card-title text-center">Направлення до стаціонару</legend>
                        <form:form  method="POST" action="/doctor2/add_directionToHospital" class="was-validated">

                            <input name="id_visit" type="hidden" value="${id_visit}" maxlength="100" readonly>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Номер направлення:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="number" name="number" value="${directionToHospital_JSP.number}" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Тип госпіталізації:</label>
                                <div class="col-sm-6">
                                    <div class="form-check form-check-inline">
                                        <input type="radio" id="typeHospital1" class="form-check-input" name="typeHospital" value="0" required>
                                        <label for="typeHospital1" class="form-check-label" >Планова</label>

                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input type="radio" id="typeHospital2" class="form-check-input" name="typeHospital" value="1" required>
                                        <label for="typeHospital2" class="form-check-label">Екстренна</label>
                                    </div>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Дата:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="date" name="date"  value="${directionToHospital_JSP.date}" maxlength="20" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Лікарня:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="text" name="hospital"  value="${directionToHospital_JSP.hospital}" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Відділення:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="text" name="department"  value="${directionToHospital_JSP.department}" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Діагноз:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="text" name="disease" value="${visit.disease.name}" maxlength="20" readonly>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Непереносимість препаратів:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="text" name="bad_medicine" value="${directionToHospital_JSP.bad_medicine}" maxlength="20" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Наявний гепатит:</label>
                                <div class="col-sm-6">
                                    <div class="form-check form-check-inline">
                                        <input type="radio" id="is_hepatitis1" class="form-check-input" name="is_hepatitis" value="1" required>
                                        <label for="is_hepatitis1" class="form-check-label" >Так</label>

                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input type="radio" id="is_hepatitis2" class="form-check-input" name="is_hepatitis" value="0" required>
                                        <label for="is_hepatitis2" class="form-check-label">Ні</label>
                                    </div>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Флюрографія:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="text" name="fluorography" value="${directionToHospital_JSP.fluorography}" maxlength="20" required>
                                </div>
                            </div>


                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Онкопрофогляд:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="text" name="oncoprofoglyad" value="${directionToHospital_JSP.oncoprofoglyad}" maxlength="20" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Температура:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="number" name="temperature" value="${directionToHospital_JSP.temperature}"  step="0.01" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">АТ:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="number" name="at" value="${directionToHospital_JSP.at}" step="0.01" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Пульс:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="number" name="pulse" value="${directionToHospital_JSP.pulse}" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">ЧД:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="number" name="respiratory_rate" value="${directionToHospital_JSP.respiratory_rate}" step="0.01" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Потребує супроводу:</label>
                                <div class="col-sm-6">
                                    <div class="form-check form-check-inline">
                                        <input type="radio" id="is_independently1" class="form-check-input" name="is_independently" value="0" required>
                                        <label for="is_independently1" class="form-check-label" >Так</label>

                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input type="radio" id="is_independently2" class="form-check-input" name="is_independently" value="1" required>
                                        <label for="is_independently2" class="form-check-label">Ні</label>
                                    </div>
                                </div>
                            </div>

                            <c:set value="add_directionToHospital" var="add_directionToHospital"/>
                            <center>
                                <input type="submit" id="in" name="${add_directionToHospital}" class="btn btn_form_add" value="Створити">
                            </center>

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
