<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../template/head.jsp" />
<body>
<div class="container">

    <div class="row">
        <content>

            <div class="col-xl-7 col-lg-10 col-md-10 col-sm-10 mx-auto p-3 pt-3">

                <div class="card card_form">

                    <div id="return">
                        <button onclick="document.location = '/patient/${doctor.specialization.id}/doctorsBySpecialization';" type="button" class="btn btn-circle btn-lg d-flex justify-content-center align-items-center">
                            <i class="bi bi-arrow-left ar"></i>
                        </button>
                    </div>

                    <div class="card-body">
                        <legend class="card-title text-center">Запис</legend>
                        <form:form  method="POST" modelAttribute="visit" action="/patient/add_visit_act">

                            <input name="id_doctor" type="hidden" value="${doctor.RNTRC}" maxlength="100" readonly>

                            <input name="id_schedule" type="hidden" value="${schedule.id}" maxlength="100" readonly>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Спеціалізація лікаря:</label>
                                <div class="col-sm-6">
                                    <input type="text" name="doc" value="${doctor.specialization.name}" class="form-control" maxlength="20" data-inputmask="'alias': 'date','placeholder': '*'" readonly>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">ПІП лікаря:</label>
                                <div class="col-sm-6">
                                    <input type="text" name="doc" value="${doctor.surname} ${doctor.name.charAt(0)}.${doctor.middle_name.charAt(0)}." class="form-control" maxlength="20" data-inputmask="'alias': 'date','placeholder': '*'" readonly>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Дата:</label>
                                <div class="col-sm-6">
                                         <input type="date" name="date" value="${date}" class="form-control" maxlength="20" data-inputmask="'alias': 'date','placeholder': '*'" readonly>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Час:</label>
                                <div class="col-sm-6">
                                    <input type="text" name="t" value="${schedule.time}" class="form-control" maxlength="20" data-inputmask="'alias': 'date','placeholder': '*'" readonly>
                                </div>
                            </div>



                            <c:set value="add_visit_act" var="add_visit_act"/>
                            <center>
                                <input type="submit" id="in" name="${add_visit_act}" class="btn btn_form_add" value="Підтвердити запис">
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
