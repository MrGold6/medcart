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

                        <div class="card-body">
                            <legend class="card-title text-center">Рецепт</legend>
                            <div id="source-html">

                                <h2>РЕЦЕПТ №  <u>${recipe.visit.patient.count_of_recipe}</u></h2>
                                <p><b>дорослий, <u>дитячий</u> (потрібне підкреслити)</b></p>


                                <p>Дата видачі ${date} року</p>

                                <p>Прізвище, ініціали та вік хворого: <u>${recipe.visit.patient.surname} ${recipe.visit.patient.name.charAt(0)}.${recipe.visit.patient.middle_name.charAt(0)}.</u></p>
                                <p>Номер карти амбулаторного чи стаціонарного хворого: <u>${recipe.visit.patient.RNTRC}</u></p>
                                <p>Прізвище та ініціали лікаря: <u>${recipe.visit.doctor.surname} ${recipe.visit.doctor.name.charAt(0)}.${recipe.visit.doctor.middle_name.charAt(0)}.</u></p>                                <hr color="#000000">
                                <c:forEach var="medicine" items="${recipe.medicineList}" varStatus="i">
                                <p>I Rp:</p>
                                <p>I - Назва: ${medicine.name} </p>
                                <p>I - Разова доза: ${medicine.dose} </p>
                                <p>I - Кількість днів: ${medicine.number_of_day}</p>
                                <p>I - Інструкція: ${medicine.instruction}</p>
                                <hr color="#000000">
                                </c:forEach>
                                <p>Підпис та особиста печатка лікаря:__________________________________</p>
                                <center><p>Рецепт дійсний протягом 1 місяця</p></center>
                            </div>
                            <center>
                                <a href="/${id_visit}/choose_action_sickLeave" class="btn btn_form_add">Далі</a>
                            </center>
                        </div>
                    </div>
                </div>



            </content>
            <footer></footer>
        </div>
    </div>
</body>
</html>

<script>
    exportHTML();
</script>