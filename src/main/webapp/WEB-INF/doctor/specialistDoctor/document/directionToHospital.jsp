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
                        <legend class="card-title text-center">Направлення на госпіталізацію</legend>
                        <div id="source-html">
                            <center>
                                <h1>Направлення/Ордер № <u>${directionToHospital.number}</u></h1>
                                <p>На ${directionToHospital.typeHospital== false ? "екстренну/<u>планову</u>" : "<u>екстренну</u>/планову" } госпіталізаію,</p>
                                <p>видано &nbsp<u>${directionToHospital.dateToString}</u></p>

                            </center>

                            <p>В лікарню:&nbsp;<u>${directionToHospital.hospital}</u>&nbsp;&nbsp;&nbsp;відділення: <u>${directionToHospital.department}</u></p>
                            <p>Прізвище:&nbsp;<u>${directionToHospital.visit.patient.surname}</u>&nbsp;&nbsp;&nbsp;</p>
                            <p>Ім’я:&nbsp;<u>${directionToHospital.visit.patient.name}</u>&nbsp;&nbsp;&nbsp;По-батькові: <u>${directionToHospital.visit.patient.middle_name}</u></p>
                            <p>Дата народження&nbsp;<u>${directionToHospital.visit.patient.dateToString}</u></p>

                            <p>Діагноз: &nbsp;<u>${directionToHospital.disease}</u></p>
                            <p>Непереносимість препаратів: &nbsp;<u>${directionToHospital.bad_medicine}${empty directionToHospital.bad_medicine ? "<u>Немає</u>" : "" }</u></p>
                            <p>Вірусний гепатит ${directionToHospital.is_hepatitis== false ? "ТАК/<u>НІ</u>" : "<u>ТАК</u>/НІ" }</p>
                            <p>Флюрографія: &nbsp;<u>${directionToHospital.fluorography}${empty directionToHospital.fluorography ? "<u>Немає</u>" : "" }</u>&nbsp;&nbsp;Онкопрофогляд: &nbsp;<u>${directionToHospital.oncoprofoglyad}${empty directionToHospital.oncoprofoglyad ? "<u>Немає</u>" : "" }</u></p>

                            <p>t <sup>&deg;</sup>&nbsp;<u>${directionToHospital.temperature}</u>&nbsp;&nbsp;AT &nbsp;<u>${directionToHospital.at}</u>&nbsp;&nbsp;Пульс &nbsp;<u>${directionToHospital.pulse}</u> &nbsp;&nbsp;ЧД &nbsp;<u>${directionToHospital.respiratory_rate}</u></p>
                            <p>Потребує супроводу ${directionToHospital.is_independently== true ? "мед.правцівника, <u>самостійно</u>" : "<u>мед.правцівника</u>, самостійно" }</p>
                            <p>Лікар &nbsp;<u>${directionToHospital.visit.doctor.specialization.name}</u>&nbsp;&nbsp;<u>${directionToHospital.visit.doctor.surname} ${directionToHospital.visit.doctor.name.charAt(0)}.${directionToHospital.visit.doctor.middle_name.charAt(0)}.</u>&nbsp;&nbsp;тел. &nbsp;<u>0${directionToHospital.visit.doctor.telephone_number}</u></p>



                        </div>
                        <center>
                            <a href="/${id_visit}/visits/1" class="btn btn_form_add">Далі</a>
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