<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        <button onclick="document.location = '/';" type="button" class="btn btn-circle btn-lg d-flex justify-content-center align-items-center">
                            <i class="bi bi-arrow-left ar"></i>
                        </button>
                    </div>


                    <div class="card-body">
                        <legend class="card-title text-center pb-1">Мій профіль</legend>
                        <div>


                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">РНОКПП:</label>
                                <div class="col-sm-6">
                                    <input type="number" class="form-control" placeholder="RNTRC" value="${doctor.RNTRC}" maxlength="100" readonly>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Спеціалізація:</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" placeholder="specialization" value="${doctor.specialization.name}" readonly>

                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Прізвище:</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" placeholder="surname" value="${doctor.surname}" readonly>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Ім'я:</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" placeholder="name" value="${doctor.name}" maxlength="100" readonly>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">По-батькові:</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" placeholder="middle_name" value="${doctor.middle_name}" readonly>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Дата народження:</label>
                                <div class="col-sm-6">
                                    <input type="date" class="form-control" placeholder="date_of_birth" value="${doctor.date_of_birth}" maxlength="20" readonly>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Стать:</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" value="${doctor.sex== 0 ? "Чоловік" : "Жінка" }" readonly>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Номер телефону:</label>
                                <div class="col-sm-6">
                                    <input type="number" class="form-control" placeholder="telephone_number" value="${doctor.telephone_number}" readonly>
                                </div>
                            </div>
                            <c:if test="${doctor.specialization.id!=8}">
                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Кількість проведених візитів за цей місяць:</label>
                                <div class="col-sm-6">
                                    <input type="number" class="form-control" placeholder="telephone_number" value="${visitsList.size()}" readonly>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Кількість проведених візитів за сьогодні:</label>
                                <div class="col-sm-6">
                                    <input type="number" class="form-control" placeholder="telephone_number" value="${todayVisitsList.size()}" readonly>
                                </div>
                            </div>
                            </c:if>
                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Стаж:</label>
                                <div class="col-sm-6">
                                    <input type="number" class="form-control" placeholder="standing" value="${standing}" readonly>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Електронна пошта:</label>
                                <div class="col-sm-6">
                                    <input type="email" class="form-control" name="email" value="${doctor.email}" readonly>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Адреса проживання:</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" name="address" value="${doctor.address}" readonly>
                                </div>
                            </div>


                        </div>


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