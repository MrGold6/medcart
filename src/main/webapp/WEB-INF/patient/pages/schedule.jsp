<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../template/head.jsp" />
<body onload="validDate()">
<div class="container">

    <div class="row">
        <content>
            <div class="col-xl-7 col-lg-10 col-md-10 col-sm-10 mx-auto p-3 pt-3">

                <div class="card card_form">

                    <div id="return">
                        <button onclick="document.location = '/patient/${doctor.RNTRC}/doctor_info';" type="button" class="btn btn-circle btn-lg d-flex justify-content-center align-items-center">
                            <i class="bi bi-arrow-left ar"></i>
                        </button>
                    </div>


                    <div class="card-body">

                        <div class="col-xl-8 col-lg-8 col-md-8 col-sm-8 pt-1 px-2 pb-2 mx-auto">
                            <form class="d-flex" id="search" method="GET" action="/patient/${doctor.RNTRC}/schedulesByDay">
                                <input class="form-control me-2 shadow bg-body rounded rounded-pill border-0 search_input" id="valid_date" type="date" name="date1" placeholder="номер телефону"  required>
                                <button class="btn btn_find mx-2" id="search_button" type="submit">Знайти</button>
                                <a href="/patient/${doctor.RNTRC}/schedule" class="btn btn_find_all">Cьогодні</a>
                            </form>
                        </div>

                        <h3 class="text-center mt-2" style="font-family:serif;">Дата: ${dateV}</h3>

                        <c:if test="${!schedules.isEmpty()}">
                            <div class="row row-cols-1 row-cols-md-5 g-3 mx-2 mt-1 mb-3">
                                <c:forEach var="record" items="${schedules}" varStatus="i">
                                    <div class="col" onclick='document.location="<c:url value='/patient/${doctor.RNTRC}/add_visit/${date}/${record.id}'/>"'>
                                        <div class="card my_card time" >
                                            <div class="card-body">
                                                <h4 class="card-title">${record.time}</h4>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>

                        <c:if test="${schedules.isEmpty()}">
                            <h2 class="pt-3"><em><center>Доступного часу немає</center></em></h2>
                        </c:if>
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
