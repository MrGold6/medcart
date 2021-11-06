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
                        <legend class="card-title text-center">Довідка</legend>
                        <form:form  method="POST" action="/add_sick_leave_act" class="was-validated">

                            <input name="id_visit" type="hidden" value="${id_visit}" maxlength="100" readonly>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Дата початку ліканяного:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="date" name="start_date" placeholder="start_date" value="${sick_leaveJSP.start_date}" maxlength="20" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Дата кінця ліканяного:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="date" name="d" placeholder="d" value="${visit.date}" maxlength="20" readonly>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Навчальний заклад:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="text" name="school" value="${sick_leaveJSP.school}" maxlength="30" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Діагноз:</label>
                                <div class="col-sm-6">
                                    <select  name="selected_disease1" class="form-select">
                                        <c:forEach var="disease" items="${diseasesList}" varStatus="i">
                                            <option value="${disease.ICD_10}">${disease.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Контакт з інфекційним хворим:</label>
                                <div class="col-sm-6">
                                    <input class="form-control no-validate" type="text" name="contact" value="${sick_leaveJSP.contact}" maxlength="50">
                                </div>
                            </div>

                            <c:set value="add_sick_leave_act" var="add_sick_leave_act"/>
                            <center>
                                <input type="submit" id="in" name="${add_sick_leave_act}" class="btn btn_form_add" value="Створити">
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
