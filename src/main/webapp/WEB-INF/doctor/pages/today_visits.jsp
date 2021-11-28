<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../template/head.jsp" />
<body style="background-color: #ffffff;" onload="validDate()">
<div class ="container-fluid">
    <div class="row">
        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 p-0">
            <jsp:include page="../template/nav.jsp" />
            <div style="background-color: #ffffff;">
                <content>

                    <h2 class="text-center">Список візитів</h2>

                    <div class="col-xl-5 col-lg-5 col-md-7 col-sm-5 pt-1 px-2 pb-2 mx-auto">
                        <form class="d-flex" id="search" method="GET" action="/visitsByDay">
                            <input class="form-control me-2 shadow bg-body rounded rounded-pill border-0" id="valid_date" type="date" name="date1" required>
                            <button class="btn btn_find mx-2" id="search_button" type="submit">Знайти</button>
                            <a href="/today_visits" class="btn btn_find_all">Cьогодні</a>
                        </form>
                    </div>
                    <c:if test="${!visitsList.isEmpty()}">
                        <div class="row row-cols-1 row-cols-md-4 g-4 mx-2 mt-1 mb-3">


                            <c:forEach var="visit" items="${visitsList}" varStatus="i">
                                <div class="col">
                                    <div class="card my_card_without_link bg-white rounded" >
                                        <div class="card-body">
                                            <h4 class="card-title">${visit.patient.surname} ${visit.patient.name.charAt(0)}.${visit.patient.middle_name.charAt(0)}.</h4>
                                            <h5 id="dateTime" class="card-title"><i class="bi bi-clock"></i> ${visit.schedule.time} &nbsp <i class="bi bi-calendar4-week"></i> ${visit.dateToString}</h5>
                                            <c:if test="${visit.isTodayVisit}"><center><a href="/${visit.number}/visits/1" class="btn btn_add_outline">Увійти в медичну карту</a></center></c:if>
                                        </div>
                                    </div>
                                </div>

                            </c:forEach>
                        </div>
                    </c:if>

                    <c:if test="${visitsList.isEmpty()}">
                        <h2 class="pt-5"><em><center>Візитів немає</center></em></h2>
                    </c:if>

                </content>
                <footer></footer>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>