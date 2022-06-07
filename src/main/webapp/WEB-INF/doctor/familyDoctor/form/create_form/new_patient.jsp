<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../../../template/head.jsp" />

<c:url value="/doctor1/add_patient" var="addUrl"/>
<body>
<div class="container">

    <div class="row">
        <content>

            <div class="col-xl-7 col-lg-10 col-md-10 col-sm-10 mx-auto p-3 pt-3">

                <div class="card card_form">

                    <div id="return">
                        <button onclick="document.location = '/doctor1/patients/1';" type="button" class="btn btn-circle btn-lg d-flex justify-content-center align-items-center">
                            <i class="bi bi-arrow-left ar"></i>
                        </button>
                    </div>


                    <div class="card-body">
                        <legend class="card-title text-center">Пацієнт</legend>
                        <form:form action="${addUrl}" method="POST" name="patient"  class="was-validated">


                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">РНОКПП:</label>
                                <div class="col-sm-6">
                                    <input class="form-control " type="text" pattern="[0-9]{10}" name="RNTRC" value="${patient.RNTRC}" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Прізвище:</label>
                                <div class="col-sm-6">
                                    <input type="text" name="surname" class="form-control" value="${patient.surname}" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Ім'я:</label>
                                <div class="col-sm-6">
                                    <input type="text" name="name" class="form-control" value="${patient.name}" maxlength="100" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">По-батькові:</label>
                                <div class="col-sm-6">
                                    <input type="text" name="middle_name" class="form-control" value="${patient.middle_name}" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Дата народження:</label>
                                <div class="col-sm-6">
                                    <input type="date" name="date_of_birth" class="form-control" value="${patient.date_of_birth}" maxlength="20" data-inputmask="'alias': 'date','placeholder': '*'" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Стать:</label>
                                <div class="col-sm-6">
                                    <div class="form-check form-check-inline">
                                        <input type="radio" id="sex1" class="form-check-input" name="sex" value="0" required>
                                        <label for="sex1" class="form-check-label" >Чоловік</label>

                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input type="radio" id="sex2" class="form-check-input" name="sex" value="1" required>
                                        <label for="sex2" class="form-check-label">Жінка</label>
                                    </div>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Номер телефону:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="tel" pattern="0[0-9]{9}" name="telephone_number" value="${patient.telephone_number}" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Електронна пошта:</label>
                                <div class="col-sm-6">
                                    <input type="email"  name="email" class="form-control  no-validate" value="${patient.email}">
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Адреса проживання:</label>
                                <div class="col-sm-6">
                                    <input type="text"  name="address" class="form-control" value="${patient.address}" required>
                                </div>
                            </div>

                            <legend class="card-title text-center">Електронна карта</legend>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Група крові:</label>
                                <div class="col-sm-6">
                                    <select  name="Blood_type" class="form-select">
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                    </select>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Резус-фактор:</label>
                                <div class="col-sm-6">
                                    <div class="form-check form-check-inline">
                                        <input type="radio" id="rh1" class="form-check-input" name="rh" value="+" required>
                                        <label for="sex1" class="form-check-label" >+</label>

                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input type="radio" id="rh2" class="form-check-input" name="rh" value="-" required>
                                        <label for="sex1" class="form-check-label" >-</label>

                                    </div>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Хронічне захворювання:</label>
                                <div class="col-sm-6">
                                    <input type="text" name="chronic_disease" class="form-control no-validate" placeholder="немає" value="${patient.chronic_disease}" maxlength="100" >
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Алергічний анамнез:</label>
                                <div class="col-sm-6">
                                    <input type="text" name="allergic_history" class="form-control no-validate"  placeholder="немає" value="${patient.allergic_history}" maxlength="100" >
                                </div>
                            </div>


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

                            <c:set value="edit_patient" var="edit_patient"/>
                            <center>
                                <c:set value="add_patient" var="add_patient"/>
                                <input type="submit" id="in" class="btn btn_form_add" name="${add_patient}" value="Створити">

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