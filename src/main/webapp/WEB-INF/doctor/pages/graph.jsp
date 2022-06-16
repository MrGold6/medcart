<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../template/head.jsp" />
<body style="background-color: #ffffff;">
<div class ="container-fluid">
    <div class="row">
        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 p-0">
            <jsp:include page="../template/nav.jsp" />
            <div style="background-color: #ffffff;">
                <content>

                    <h1 class="pt-4">Електронна медична картка. Пацієнт: ${patient.surname} ${patient.name.charAt(0)}.${patient.middle_name.charAt(0)}.
                        <a href="/${id_visit}/visits/edit_patient" class="link link-userLink" > <i class="bi bi-person-circle"></i></a></h1>

                    <ul class="nav nav-tabs ">
                        <li class="nav-item">
                            <a href="/${id_visit}/visits/1" class="nav-link link-custom" tabindex="-1" aria-disabled="true">
                                Візити
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link link-custom" href="/${id_visit}/symptoms/1" tabindex="-1" aria-disabled="true">
                                Скарги
                            </a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link link-custom"  href="/${id_visit}/tests/1" tabindex="-1" aria-disabled="true">
                                Аналізи
                            </a>
                        </li>

                        <li class="nav-item">
                            <a href="/${id_visit}/graph" class="nav-link active" aria-current="page">
                                Граф діагнозів
                            </a>
                        </li>
                    </ul>


                    <jsp:include page="../template/graphPatientDisease.jsp"/>


                    <c:if test="${isActive==true}">
                        <center>
                            <button onclick="document.location = '/${id_visit}/add_visit';" type="button" class="btn my-2 btn_find">
                                Розпочати візит
                            </button>
                        </center>
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

