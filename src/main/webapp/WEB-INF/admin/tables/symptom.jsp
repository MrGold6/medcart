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
            <h2 class="pt-3">${bodyPart.name} симптоми </h2>
            <c:if test="${!symptomList.isEmpty()}">

            <div class="table-wrapper-scroll-y my-custom-scrollbar table-responsive">

                <table class="table tableFixHead table-striped">
                    <thead>
                    <tr>
                        <th>Назва</th>

                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="symptom" items="${symptomList}" varStatus="i">
                        <tr>
                            <td>${symptom.name}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div>
            </c:if>
            <c:if test="${symptomList.isEmpty()}">
                <h2><em><center>Симптомів немає</center></em></h2>
            </c:if>

            <center>
                <button onclick="document.location = '/admin/${bodyPart.id}/add_symptom';" type="button" class="btn my-2 btn_add">
                    Створити
                </button>
            </center>
        </content>

    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>



</body>

</html>