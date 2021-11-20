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
                    <h2 class="pt-3">Пацієнти</h2>

                    <div class="table-wrapper-scroll-y my-custom-scrollbar table-responsive">

                        <table class="table tableFixHead table-striped">
                            <thead>
                            <tr>
                                <th>РНОКПП</th>
                                <th>ПІП</th>
                                <th>Стать</th>
                                <th>Номер телефону</th>
                                <th>Візити</th>
                                <th>Юзер</th>
                                <th>Направлення</th>
                                <th>Дії</th>

                            </tr>
                            </thead>

                            <c:if test="${!patientsList.isEmpty()}">
                            <tbody>
                            <c:forEach var="patient" items="${patientsList}" varStatus="i">
                                <tr>
                                    <td>${patient.RNTRC}</td>
                                    <td>${patient.surname} ${patient.name.charAt(0)}.${patient.middle_name.charAt(0)}.</td>
                                    <td>${patient.sex == 0 ? "Чоловік" : "Жінка" }</td>
                                    <td>0${patient.telephone_number}</td>
                                    <td><a href="/admin/${patient.RNTRC}/visit" class="btn btn_find"><i class="bi bi-journal-medical"></i></a></td>
                                    <td>
                                        <c:if test="${patient.user.username==null}"><a href="/admin/${patient.RNTRC}/set_user_for_patient/" class="btn btn_add"><i class="bi bi-person"></i></a> </c:if>
                                        <c:if test="${patient.user.username!=null}">${patient.user.username}</c:if></td>

                                    <td>
                                        <a href="/admin/${patient.RNTRC}/direction/" class="btn btn_find_all"><i class="bi bi-signpost"></i></a>
                                    </td>
                                    <td><a href="/admin/${patient.RNTRC}/edit_patient/" class="btn btn_edit"><i class="bi bi-vector-pen "></i></a>
                                        <a href="/admin/${patient.RNTRC}/delete_patient" class="btn btn_delete"><i class="bi bi-trash "></i></a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        </c:if>
                        <c:if test="${patientsList.isEmpty()}">
                            <h2><em><center>Пацієнтів немає</center></em></h2>
                        </c:if>

                    </div>

                    <center>
                        <button onclick="document.location = '/admin/add_patient';" type="button" class="btn my-2 btn_add">
                            Створити
                        </button>
                    </center>
                </content>

            </div>
        </div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>



</body>

</html>