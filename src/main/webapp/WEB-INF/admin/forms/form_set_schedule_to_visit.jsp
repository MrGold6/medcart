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
                        <c:if test="${empty visit.schedule.id}">
                            <title>Add</title>
                        </c:if>
                        <c:if test="${!empty visit.schedule.id}">
                            <title>Edit</title>
                        </c:if>
                        <legend class="card-title text-center">Обрати час для візиту</legend>

                        <form:form action="/admin/setSchedule" method="POST" name="visit"  class="was-validated">

                            <input type="hidden" name="id_patient" value="${visit.patient.RNTRC}" class="form-control" required>
                            <input type="hidden" name="id_visit" value="${visit.number}" class="form-control" required>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Дата:</label>
                                <div class="col-sm-6">
                                    <input type="date" name="date" value="${date}" class="form-control" maxlength="20" data-inputmask="'alias': 'date','placeholder': '*'" readonly>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln"> Час:</label>
                                <div class="col-sm-6">
                                    <select  name="selected_schedule"  class="form-select">
                                        <c:forEach var="schedule" items="${schedules}" varStatus="i">
                                            <option value="${schedule.id}" <c:if test="${!empty visit.schedule.id && visit.schedule.id==schedule.id}">
                                                selected
                                            </c:if>>${schedule.time}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <center>

                                <c:if test="${empty visit.schedule.id}">
                                    <input type="submit" id="in"  class="btn btn_add" name="add_visit" value="Створити">
                                </c:if>
                                <c:if test="${!empty visit.schedule.id}">
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