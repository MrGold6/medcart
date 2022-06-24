<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../template/head.jsp" />

<body style="background-color: #ffffff;">
<div class ="container-fluid">
    <div class="row">
        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 p-0">
            <jsp:include page="../template/nav.jsp" />
            <div style="background-color: #ffffff;">
                <content>
                    <h2 class="text-center mt-3">Список направлень на аналізи</h2>
                    <c:if test="${!directionsList.isEmpty()}">
                        <div class="row row-cols-1 row-cols-md-4 g-4 mx-2 mt-1 mb-3">

                            <c:forEach var="directions" items="${directionsList}" varStatus="i">
                                <div class="col">
                                    <div class="card my_card_without_link" >
                                        <div class="card-body">
                                            <center> <h4 class="card-title">${directions.testsType.name}</h4></center>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:if>
                    <c:if test="${directionsList.isEmpty()}">
                        <h2 class="pt-5"><em><center>Направлень немає</center></em></h2>
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