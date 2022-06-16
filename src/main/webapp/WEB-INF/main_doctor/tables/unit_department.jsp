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
            <h2 class="pt-3">Відділення підрозділу ${unit.name} </h2>

            <div class="table-wrapper-scroll-y my-custom-scrollbar table-responsive">
                <c:if test="${!departmentList.isEmpty()}">
                    <table class="table tableFixHead table-striped">
                        <thead>
                        <tr>
                            <th>Назва</th>
                            <th>Лікарі</th>
                            <th>Штатна схема</th>
                            <th>Дії</th>
                        </tr>
                        </thead>


                        <tbody>
                        <c:forEach var="department" items="${departmentList}" varStatus="i">
                            <tr>
                                <td>${department.name}</td>

                                <td><a href="/main_doctor/${department.id}/department_doctor/1" class="btn btn_look_at"><i class="bi bi-person"></i></a></td>
                                <td><a href="/main_doctor/${department.id}/staffing_scheme/1" class="btn btn_look_at"><i class="bi bi-file-text"></i></a></td>

                                <td>
                                    <a href="/main_doctor/${unit.id}/${department.id}/delete_department_from_unit" class="btn btn_delete"><i class="bi bi-trash "></i></a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${departmentList.isEmpty()}">
                    <h2><em><center>Відділень немає</center></em></h2>
                </c:if>

            </div>

            <center>
                <button onclick="document.location = '/main_doctor/${unit.id}/add_unit_to_department';" type="button" class="btn my-2 btn_add">
                    Додати відділення
                </button>
            </center>
        </content>

    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>



</body>

</html>