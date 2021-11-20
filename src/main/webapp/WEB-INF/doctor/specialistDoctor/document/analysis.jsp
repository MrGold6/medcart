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
    <title>Analysis</title>
</head>

<body>
<div class="container">

    <div class="row">
        <content>

            <div class="col-xl-7 col-lg-10 col-md-10 col-sm-10 mx-auto p-3 pt-3">
                <div class="card card_form">

                    <div class="card-body">
                        <legend class="card-title text-center">Аналіз</legend>
                        <div id="source-html">
                            <center>
                                <h1>НАПРАВЛЕННЯ НА АНАЛІЗ № <u>${analysis_JSP.number}</u></h1>
                                <p><u>${analysis_JSP.date}</u></p>

                            </center>

                            <p>В лабораторію: <u>${analysis_JSP.laboratory}</u></p>
                            <p>Прізвище, ім’я, по батькові: &nbsp;<u>${analysis_JSP.visit.patient.surname} ${analysis_JSP.visit.patient.name} ${analysis_JSP.visit.patient.middle_name}</u></p>
                            <p>Дата народження: &nbsp; <u>${analysis_JSP.visit.patient.date_of_birth}</u></p>
                            <p>Кліничний діагноз:&nbsp;<u>${analysis_JSP.disease}</u></p>
                            <p>Мета дослідження:&nbsp;<u>${analysis_JSP.target}</u></p>

                        </div>
                        <center>
                            <a href="/doctor2/${id_visit}/choose_action_directionToHospital" class="btn btn_form_add">Далі</a>
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