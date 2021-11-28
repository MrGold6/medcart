<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../../template/head.jsp" />
<body>
<div class="container">

    <div class="row">
        <content>
            <div class="col-xl-7 col-lg-10 col-md-10 col-sm-10 mx-auto p-3 pt-3">
                <div class="card card_form">
                    <div class="card-body">

                        <legend class="card-title text-center">Потрібна довідка?</legend>
                        <div class="form">
                            <ul class="border">
                                <li>
                                    <a href="/${id_visit}/add_sick_leave">Так</a>
                                </li>
                                <li>
                                    <a <c:if test="${is_family_role}">href="/${id_visit}/visits/1"</c:if>
                                       <c:if test="${!is_family_role}">href="/doctor2/${id_visit}/choose_action_analysis"</c:if>>Ні</a>
                                </li>
                            </ul>
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
