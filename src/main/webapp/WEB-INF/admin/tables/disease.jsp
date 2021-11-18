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
            <h2 class="pt-3">Захворювання</h2>

            <div class="table-wrapper-scroll-y my-custom-scrollbar table-responsive">

                <table class="table tableFixHead table-striped">
                    <thead>
                    <tr>
                        <th>ICD_10<a href="/admin/disease/1" class="btn btn-sm "><i class="bi bi-sort-down text-light"></i></a></th>
                        <th>Назва<a href="/admin/disease/2" class="btn btn-sm "><i class="bi bi-sort-down text-light"></i></a></th>
                        <th>Дії</th>
                    </tr>
                    </thead>

                    <c:if test="${!diseaseList.isEmpty()}">
                    <tbody>
                    <c:forEach var="disease" items="${diseaseList}" varStatus="i">
                        <tr>
                            <td>${disease.ICD_10}</td>
                            <td>${disease.name}</td>

                            <td><a href="/admin/${disease.ICD_10}/edit_disease/" class="btn btn_edit"><i class="bi bi-vector-pen "></i></a>
                                <a href="/admin/${disease.ICD_10}/delete_disease" class="btn btn_delete"><i class="bi bi-trash "></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                </c:if>
                <c:if test="${diseaseList.isEmpty()}">
                    <h2><em><center>Захворювань немає</center></em></h2>
                </c:if>

            </div>

            <center>
                <button onclick="document.location = '/admin/add_disease';" type="button" class="btn my-2 btn_add">
                    Створити
                </button>
            </center>
        </content>

    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>



</body>

</html>