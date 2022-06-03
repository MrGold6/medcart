<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<jsp:include page="../template/head.jsp" />
<body style="background-color: #ffffff;">
<div class ="container-fluid">
    <div class="row">
        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 p-0">
            <jsp:include page="../template/nav.jsp" />


<div style="background-color: #ffffff;">
            <content>

                <h1 class="pt-4">Список пацієнтів</h1>


                <div class="col-xl-5 col-lg-5 col-md-7 col-sm-5 pt-1 px-2 pb-2 mx-auto">
                        <form class="d-flex" id="search" method="GET" action="/nurse/patients/searchTelephone_number">
                            <input class="form-control me-2 shadow bg-body rounded rounded-pill border-0" id="search_input" type="number" name="telephone_number" placeholder="номер телефону"  required>
                            <button class="btn btn_find mx-2" id="search_button" type="submit">Знайти</button>
                            <a href="/nurse/patients/1" class="btn btn_find_all">Всі</a>
                        </form>
                </div>


                <div class="table-wrapper-scroll-y my-custom-scrollbar">

                    <table class="table tableFixHead">
                        <thead>
                        <tr>
                            <th>РНОКПП<a href="/nurse/patients/1" class="btn btn-sm "><i class="bi bi-sort-down"></i></a></th>
                            <th>ПІП<a href="/nurse/patients/2" class="btn btn-sm "><i class="bi bi-sort-down"></i></a></th>
                            <th>Стать<a href="/nurse/patients/3" class="btn btn-sm "><i class="bi bi-sort-down"></i></a></th>
                            <th>Номер телефону<a href="/nurse/patients/4" class="btn btn-sm "><i class="bi bi-sort-down"></i></a></th>
                            <th>Юзер</th>

                        </tr>
                        </thead>


                            <c:if test="${!patientsList.isEmpty()}">
                            <tbody>
                                    <c:forEach var="patient" items="${patientsList}" varStatus="i">
                                        <tr onclick='document.location="<c:url value='/nurse/${patient.RNTRC}/visits/1'/>"'>
                                            <td>${patient.RNTRC}</td>
                                            <td>${patient.surname} ${patient.name.charAt(0)}.${patient.middle_name.charAt(0)}.</td>
                                            <td>${patient.sex== 0 ? "Чоловік" : "Жінка" }</td>
                                            <td>0${patient.telephone_number}</td>
                                            <td>${patient.user.username}</td>
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
                        <button onclick="document.location = '/nurse/add_patient';" type="button" class="btn my-2 btn_add">
                            Додати нового пацієнта
                        </button>
                    </center>

            </content>
            <footer></footer>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>