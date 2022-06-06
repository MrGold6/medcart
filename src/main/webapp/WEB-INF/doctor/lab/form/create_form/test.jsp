<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../../../template/head.jsp" />
<body>
<div class="container">

    <div class="row">
        <content>

            <div class="col-xl-7 col-lg-10 col-md-10 col-sm-10 mx-auto p-3 pt-3">

                <div class="card card_form">

                    <div id="return">
                        <button onclick="document.location = '/${patient_id}/tests';" type="button" class="btn btn-circle btn-lg d-flex justify-content-center align-items-center">
                            <i class="bi bi-arrow-left ar"></i>
                        </button>
                    </div>


                    <div class="card-body">
                        <legend class="card-title text-center">Аналіз</legend>
                        <form:form  method="POST" modelAttribute="test" action="/lab/add_test">
                            <input class="form-control" type="hidden" name="id_visit" value="${test.id}">

                            <input class="form-control" type="hidden" name="patient_id" value="${patient_id}">
                            <input class="form-control" type="hidden" name="direction_id" value="${direction_id}">

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Тип аналізу:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="text" name="name" value="${test.testsType.name}" maxlength="50" readonly>
                                </div>
                            </div>


                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Результати:</label>
                                <div class="col-sm-6">
                                    <form:textarea path = "result" class="form-control" rows = "3"/>

                                </div>
                            </div>


                            <c:set value="add_test" var="add_test"/>
                            <center>
                            <input type="submit" id="in" name="${add_test}" class="btn btn_form_add" value="Зберегти">
                            </center>
                            <p>${message}</p>


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
