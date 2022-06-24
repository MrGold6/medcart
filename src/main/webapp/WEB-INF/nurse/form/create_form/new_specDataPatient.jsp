<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../../template/head.jsp" />

<c:url value="/nurse/edit_patient_spec_data" var="addUrl"/>
<body>
<div class="container">

    <div class="row">
        <content>

            <div class="col-xl-7 col-lg-10 col-md-10 col-sm-10 mx-auto p-3 pt-3">

                <div class="card card_form">

                    <div id="return">
                        <button onclick="document.location = '/nurse/patients/1';" type="button" class="btn btn-circle btn-lg d-flex justify-content-center align-items-center">
                            <i class="bi bi-arrow-left ar"></i>
                        </button>
                    </div>


                    <div class="card-body">
                        <legend class="card-title text-center">Характеристики пацієнта</legend>
                        <form:form action="${addUrl}" method="POST" name="patient"  class="was-validated">

                            <input type="hidden" name="id_visit" class="form-control" value="${id_visit}">

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Зріст:</label>
                                <div class="col-sm-6">
                                    <input type="number" name="height" class="form-control" value="${patient.height}" step="0.01">
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Маса тіла:</label>
                                <div class="col-sm-6">
                                    <input type="number" name="width" class="form-control" value="${patient.width}" step="0.01">
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Окружність грудної клітки:</label>
                                <div class="col-sm-6">
                                    <input type="number" name="chest_circumference" class="form-control" value="${patient.chest_circumference}"  step="0.01">
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Окружність живота:</label>
                                <div class="col-sm-6">
                                    <input type="number" name="abdominal_circumference" class="form-control" value="${patient.abdominal_circumference}" step="0.01" >
                                </div>
                            </div>

                            <center>
                                <c:set value="edit_patient_spec_data" var="edit_patient_spec_data"/>
                                <input type="submit" id="in" class="btn btn_form_add" name="${edit_patient_spec_data}" value="Створити">

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
        <footer></footer>
    </div>
</div>




<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>



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