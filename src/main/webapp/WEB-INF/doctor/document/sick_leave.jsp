<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Обязательные метатеги -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
    <link rel="stylesheet" type="text/css" href="<c:url value="/res/style.css"/>">
    <link rel="icon" type="image/png" href="<c:url value="/res/medicine.png"/>"/>

    <script src="<c:url value="/res/toWord.js"/>"></script>
    <title>Sick leave</title>
</head>

<body>
<div class="container">

    <div class="row">
        <content>

            <div class="col-xl-7 col-lg-10 col-md-10 col-sm-10 mx-auto p-3 pt-3">
                <div class="card card_form">

                    <div class="card-body">
                        <legend class="card-title text-center">Довідка</legend>
                        <div id="source-html">
                            <center>
                                <h1>Довідка № <u>${sick_leave.visit.patient.count_of_sick_leave}</u></h1>
                                <p><b>про тимчасову непрацездатність студента навчального закладу І–ІY рівнів акредитації, про хворобу, карантин і інші причини відсутності дитини, яка відвідує загальноосвітній навчальний заклад, дошкільний навчальний заклад</b></p>
                            </center>

                            <p>Дата видачі: <u>${date}</u>р.</p>
                            <p>1. Студенту, учню, дитині, що відвідує дошкільний навчальний заклад: <u>${sick_leave.school}</u></p>
                            <p>2. Прізвище, ім’я, по батькові хворого: <u>${sick_leave.visit.patient.surname} ${sick_leave.visit.patient.name} ${sick_leave.visit.patient.middle_name}</u></p>
                            <p>3. Дата народження (рік, місяць, число, для дітей до 1 року – день): <u>${sick_leave.visit.patient.date_of_birth}</u></p>
                            <p>4. Діагноз захворювання: <u>${sick_leave.start_disease.name}</u></p>
                            <p>5. Наявність контакту з інфекційним хворим ("${sick_leaveJSP.contact.length()== 0 ? "так,<u>ні</u>" : "<u>так</u>, ні" }"): <u>${sick_leaveJSP.contact}</u></p>
                            <p>6. Звільнений(на) від занять, відвідувань дошкільного навчального закладу:</p>
                            <p>з <u>${start_date}</u> по <u>${date}</u>
                        </div>
                        <center>
                            <a  <c:if test="${sick_leave.visit.doctor.specialization.id==1}">href="/${id_visit}/visits/1"</c:if>
                                <c:if test="${sick_leave.visit.doctor.specialization.id!=1}">href="/doctor2/${id_visit}/choose_action_analysis"</c:if>
                                class="btn btn_form_add">Далі</a>
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