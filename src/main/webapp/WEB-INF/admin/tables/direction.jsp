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
            <h2 class="pt-3">Направлення: ${patient.surname} ${patient.name.charAt(0)}.${patient.middle_name.charAt(0)}.</h2>

            <div class="table-wrapper-scroll-y my-custom-scrollbar table-responsive">
                <c:if test="${!directionList.isEmpty()}">
                <table class="table tableFixHead table-striped">
                    <thead>
                    <tr>
                        <th>Спеціалізація<a href="/admin/${patient.RNTRC}/direction/2" class="btn btn-sm "><i class="bi bi-sort-down text-light"></i></a></th>
                        <th>Статус<a href="/admin/${patient.RNTRC}/direction/3" class="btn btn-sm "><i class="bi bi-sort-down text-light"></i></a></th>
                        <th>Дії</th>
                    </tr>
                    </thead>


                    <tbody>
                    <c:forEach var="direction" items="${directionList}" varStatus="i">
                        <tr>
                            <td>${direction.specialization.name}</td>
                            <td>
                                <c:if test="${direction.status}"><i class="bi bi-check-square text-success"></i></c:if>
                                <c:if test="${!direction.status}"><i class="bi bi-dash-square text-danger"></i> </c:if>
                            </td>

                            <td><a href="/admin/${patient.RNTRC}/${direction.number}/edit_direction/" class="btn btn_edit"><i class="bi bi-vector-pen "></i></a>
                                <a href="/admin/${patient.RNTRC}/${direction.number}/delete_direction" class="btn btn_delete"><i class="bi bi-trash "></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                </c:if>
                <c:if test="${directionList.isEmpty()}">
                    <h2><em><center>Направлень немає</center></em></h2>
                </c:if>

            </div>

            <center>
                <button onclick="document.location = '/admin/${patient.RNTRC}/add_direction';" type="button" class="btn my-2 btn_add">
                    Створити
                </button>
            </center>
        </content>

    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>



</body>

</html>