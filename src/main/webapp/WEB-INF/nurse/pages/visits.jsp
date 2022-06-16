<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../../doctor/template/head.jsp" />
<body style="background-color: #ffffff;">
<div class ="container-fluid">
    <div class="row">
        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 p-0">
            <jsp:include page="../template/nav.jsp" />
            <div style="background-color: #ffffff;">
                <content>

                    <h1 class="pt-4">Електронна медична картка. Пацієнт: ${patient.surname} ${patient.name.charAt(0)}.${patient.middle_name.charAt(0)}.
                        <a href="/nurse/${patient.RNTRC}/visits/edit_patient" class="link link-userLink" > <i class="bi bi-person-circle"></i></a></h1>

                    <ul class="nav nav-tabs ">
                        <li class="nav-item">
                            <a href="/nurse/${patient.RNTRC}/visits/1" class="nav-link active" aria-current="page">
                                Візити
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link link-custom" href="/nurse/${patient.RNTRC}/symptoms/1" tabindex="-1" aria-disabled="true">
                                Скарги
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="/nurse/${patient.RNTRC}/graph" class="nav-link link-custom" tabindex="-1"  aria-disabled="true">
                                Граф діагнозів
                            </a>
                        </li>
                    </ul>


                    <c:if test="${!visitsList.isEmpty()}">
                    <div class="table-wrapper-scroll-y my-custom-scrollbar">

                        <table class="table tableFixHead">
                            <thead>
                            <tr>
                                <th>Дата<a href="/nurse/${patient.RNTRC}/visits/1" class="btn btn-sm "><i class="bi bi-sort-down"></i></a></th>
                                <th>Діагноз<a href="/nurse/${patient.RNTRC}/visits/2" class="btn btn-sm "><i class="bi bi-sort-down"></i></a></th>
                                <th>Лікар<a href="/nurse/${patient.RNTRC}/visits/3" class="btn btn-sm "><i class="bi bi-sort-down"></i></a></th>
                                <th>Виписані ліки</th>
                            </tr>
                            </thead>



                            <tbody>
                            <c:forEach var="visit" items="${visitsList}" varStatus="i">
                                <tr onclick='document.location="<c:url value='/nurse/${patient.RNTRC}/${visit.number}/visit'/>"'>
                                    <td>${visit.date}</td>
                                    <td>${visit.disease.name}</td>
                                    <td>${visit.doctor.specialization.name}</td>
                                    <td>${empty visit.medicine ? "-" : visit.medicine }</td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>
                    </div>
                    </c:if>

                    <c:if test="${visitsList.isEmpty()}">
                        <h2 class="pt-5"><em><center>Візитів немає</center></em></h2>
                    </c:if>

                    <center>
                        <button onclick="document.location = '/nurse/${patient.RNTRC}/add_visit';" type="button" class="btn my-2 btn_find">
                            Розпочати візит
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