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
                        <c:if test="${empty visit.number}">
                            <title>Add</title>
                        </c:if>
                        <c:if test="${!empty visit.number}">
                            <title>Edit</title>
                        </c:if>
                        <legend class="card-title text-center">Візит</legend>


                        <form:form action="/admin/add_visits" method="POST" name="visit"  class="was-validated">

                            <input type="hidden" name="id_patient" value="${id_patient}" class="form-control" required>
                            <input type="hidden" name="number" value="${visit.number}" class="form-control" required>
                            <input type="hidden" name="id_schedule" value="${visit.schedule.id}" class="form-control" required>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Дата:</label>
                                <div class="col-sm-6">
                                    <input type="date" name="date" value="${visit.date}" class="form-control" maxlength="20" data-inputmask="'alias': 'date','placeholder': '*'" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Лікар:</label>
                                <div class="col-sm-6">
                                    <select  name="selected_doctor"  class="form-select">
                                        <c:forEach var="doctor" items="${doctorList}" varStatus="i">
                                            <option value="${doctor.RNTRC}" <c:if test="${!empty visit.number && visit.doctor.RNTRC==doctor.RNTRC}">
                                                selected
                                            </c:if>>${doctor.specialization.name} - ${doctor.surname} ${doctor.name.charAt(0)}.${doctor.middle_name.charAt(0)}.</option>

                                        </c:forEach>
                                    </select>
                                </div>
                            </div>


                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Діагноз:</label>
                                <div class="col-sm-6">
                                    <select  name="selected_disease"  class="form-select">
                                        <c:forEach var="disease" items="${diseasesList}" varStatus="i">

                                            <option value="${disease.ICD_10}" <c:if test="${!empty visit.number && visit.disease.ICD_10==disease.ICD_10}">
                                                selected
                                            </c:if>>${disease.name}</option>
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
                                    <input class="form-control" type="text" name="notes" value="${visit.notes}" maxlength="100">
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Ліки:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="text" name="medicine" value="${visit.medicine}" maxlength="100">
                                </div>
                            </div>

                            <c:if test="${!empty visit.number}">
                                <div class="row mb-3">
                                    <label class="col-sm-6 col-form-label ln">Статус:</label>
                                    <div class="col-sm-6">
                                        <input type="number" name="status" class="form-control" value="${visit.status ? 1 : 0 }" required>
                                    </div>
                                </div>
                            </c:if>



                            <center>
                                <c:if test="${!empty visit.number}">
                                    <a href="/admin/${visit.number}/${id_patient}/setSchedule/" class="btn btn_find_all">Змінити час</a>
                                </c:if>

                                <c:if test="${empty visit.number}">
                                    <input type="submit" id="in"  class="btn btn_add" name="add_visit" value="Створити">
                                </c:if>
                                <c:if test="${!empty visit.number}">
                                    <input type="submit" id="edit"  class="btn btn_edit_form" name="edit" value="Змінити">
                                </c:if>


                            </center>


                        </form:form>
                    </div>
                </div>
            </div>

            <!-- Modal -->
            <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-body">
                            <div class="text-center text-justify">
                                <i class="bi bi-exclamation-triangle-fill text-danger pl-2 pt-2 dang  position-relative"></i>
                                <span class="ml-2" style="font-size:16pt;">Такий пацієнт вже існує</span>
                            </div>
                            <center><button type="button" class="btn btn-outline-primary mt-3" data-bs-dismiss="modal">OK</button></center>
                        </div>


                    </div>
                </div>
            </div>



        </content>

    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>



</body>

</html>

<c:if test="${message!=null}">
    <script type="text/javascript">
        var myModal = new bootstrap.Modal(document.getElementById("staticBackdrop"), {});
        document.onreadystatechange = function () {
            myModal.show();
        };
    </script>
</c:if>