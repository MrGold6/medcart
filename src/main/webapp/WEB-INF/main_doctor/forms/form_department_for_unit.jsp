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
            <div class="col-xl-7 col-lg-10 col-md-10 col-sm-10 mx-auto p-3 pt-3">
                <div class="card card_form shadow">
                    <div class="card-body ">

                        <title>Create</title>

                        <legend class="card-title text-center">Підрозділ: ${unit.name} </legend>


                        <form:form action="/main_doctor/add_unit_to_department" method="POST" name="department"  class="was-validated">

                            <input type="hidden" name="id_unit" class="form-control" value="${unit.id}">

                            <c:if test="${!empty departmentList}">

                                <div class="row mb-3">
                                    <label class="col-sm-6 col-form-label ln">Відділення:</label>
                                    <div class="col-sm-12">
                                            <select name="department_id" class="form-select">
                                                <c:forEach var="department" items="${departmentList}" varStatus="i">
                                                    <option value="${department.id}">${department.name} </option>
                                                </c:forEach>
                                            </select>
                                    </div>
                                </div>

                            <center>
                                <input type="submit" id="in"  class="btn btn_add" name="add_role" value="Додати">

                            </center>
                            </c:if>

                            <c:if test="${empty departmentList}">
                                <h3><center>Доступних відділень немає</center></h3>
                            </c:if>

                        </form:form>
                    </div>
                </div>
            </div>

        </content>

    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>



</body>

</html>
