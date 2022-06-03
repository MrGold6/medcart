<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../../template/head.jsp" />
<body>
<div class="container">

    <div class="row">
        <content>

            <div class="col-xl-7 col-lg-10 col-md-10 col-sm-10 mx-auto p-3 pt-3">

                <div class="card card_form">

                    <div id="return">
                        <button onclick="document.location = '/nurse/${id_patient}/visits/1';" type="button" class="btn btn-circle btn-lg d-flex justify-content-center align-items-center">
                            <i class="bi bi-arrow-left ar"></i>
                        </button>
                    </div>


                    <div class="card-body">
                        <legend class="card-title text-center">Візит</legend>
                        <form:form  method="POST" modelAttribute="visit" action="/nurse/add_visit_act">
                            <input class="form-control" type="hidden" name="id_patient" value="${id_patient}">

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Діагноз:</label>
                                <div class="col-sm-6">
                                    <select  name="selected_disease"  class="form-select">
                                        <option value="0">Здоровий</option>
                                        <c:forEach var="disease" items="${diseasesList}" varStatus="i">
                                            <c:if test="${disease.ICD_10!='0'}">
                                                <option value="${disease.ICD_10}">${disease.name}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Cкарги:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="text" name="complaints" value="${visit.complaints}" maxlength="50">
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Проведені дії:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="text" name="actions" value="${visit.actions}" maxlength="100">
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Нотатки:</label>
                                <div class="col-sm-6">
                                    <form:textarea path = "notes" class="form-control" rows = "3"/>

                                </div>
                            </div>


                            <c:set value="add_visit_act" var="add_visit_act"/>
                            <center>
                            <input type="submit" id="in" name="${add_visit_act}" class="btn btn_form_add" value="Далі">
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
