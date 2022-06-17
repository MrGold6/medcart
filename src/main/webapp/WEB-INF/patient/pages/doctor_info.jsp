<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../template/head.jsp"/>
<body onload="validDate()">
<div class="container">

    <div class="row">
        <content>
            <div class="col-xl-7 col-lg-10 col-md-10 col-sm-10 mx-auto p-3 pt-3">

                <div class="card card_form">

                    <div id="return">
                        <button onclick="document.location = '/patient/${doctor.specialization.id}/doctorsBySpecialization';"
                                type="button"
                                class="btn btn-circle btn-lg d-flex justify-content-center align-items-center">
                            <i class="bi bi-arrow-left ar"></i>
                        </button>
                    </div>


                    <div class="card-body">

                        <h1 class="pt-4">
                            Доктор: ${doctor.surname} ${doctor.name.charAt(0)}.${doctor.middle_name.charAt(0)}.
                            <a href="/patient/${doctor.RNTRC}/doctor_info/rate/1"><i
                                    class="bi bi-star-fill star"></i></a>
                            <a href="/patient/${doctor.RNTRC}/doctor_info/rate/2"> <i class="bi bi-star-fill star"></i></a>
                            <a href="/patient/${doctor.RNTRC}/doctor_info/rate/3"><i
                                    class="bi bi-star-fill star"></i></a>
                            <a href="/patient/${doctor.RNTRC}/doctor_info/rate/4"><i
                                    class="bi bi-star-fill star"></i></a>
                            <a href="/patient/${doctor.RNTRC}/doctor_info/rate/5"> <i class="bi bi-star-fill star"></i></a>
                            <small>${!doctor.rates.isEmpty() ? doctor.getAverageRate():'-'}</small>
                        </h1>
                        <center>
                            <a href="/patient/${doctor.RNTRC}/schedule" class="btn btn_find_all">Записатись на
                                прийом</a>
                        </center>

                        <div class="col-xl-11 col-lg-10 col-md-10 col-sm-10 mx-auto p-3 pt-3">

                            <form:form method="POST" modelAttribute="comment" action="/patient/add_comment">

                                <div class="comment-bottom px-4">

                                    <div class="d-flex flex-row add-comment-section mt-4 mb-4">
                                        <input type="hidden" name="doctor_id" value="${doctor.RNTRC}">

                                        <input class="form-control" placeholder="Написати коментар"
                                               type="text"
                                               name="text" required>


                                        <input class="btn btn_look_at" type="submit" name="add_comment"
                                               value="Коментувати">

                                    </div>

                                </div>

                            </form:form>


                            <div class="container mt-5 mb-4">
                                <div class="row d-flex justify-content-center">
                                    <div class="col-md-10">
                                        <div class="headings d-flex justify-content-between align-items-center mb-3">
                                            <h5>Коментарі</h5>

                                        </div>
                                        <c:if test="${!doctor.comments.isEmpty()}">
                                            <c:forEach var="comment" items="${doctor.comments}" varStatus="i">
                                                <div class="card  p-3 mt-2">
                                                    <div class="d-flex justify-content-between align-items-center">
                                                        <span><small
                                                                class="font-weight-bold text-primary">${comment.patient.surname} ${comment.patient.name.charAt(0)}.${comment.patient.middle_name.charAt(0)}.</small> <small
                                                                class="font-weight-bold">${comment.text}</small>
                                                        </span>
                                                        <small class="text-secondary">${comment.date}</small>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </c:if>

                                        <c:if test="${doctor.comments.isEmpty()}">
                                            <h2 class="pt-3"><em>
                                                <center>Коментарів немає</center>
                                            </em></h2>
                                        </c:if>
                                    </div>
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
        crossorigin="anonymous"></script>
</body>
</html>
