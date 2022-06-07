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
                            <legend class="card-title text-center">Декларація</legend>
                            <div id="source-html">

                                <h2>Декларація №  <u>${declaration.id}</u></h2>
                                <h2>про вибір лікаря, який надає первинну медичну допомогу</h2>


                                <p><b>1. Пацієнт</b></p>
                                <p>1.1 Прізвище: <u>${declaration.patient.surname}</u></p>
                                <p>1.2 Ім'я: <u>${declaration.patient.name}</u></p>
                                <p>1.3 По-батькові: <u>${declaration.patient.middle_name}</u></p>
                                <p>1.4 Дата народження: <u>${declaration.patient.date_of_birth}</u></p>
                                <p>1.5 Стать: <u>${declaration.patient.sex== 0 ? "Чоловік" : "Жінка"}</u></p>
                                <p><b>1.7 Контактні дані</b></p>
                                <p>1.7.1 Номер телефону: <u>0${declaration.patient.telephone_number}</u></p>
                                <p>1.7.2 Адреса електронної пошти: <u>${declaration.patient.email}</u></p>
                                <p><b>1.8 Документ,що посвідчує особу:</b></p>
                                <p>1.8.1 РНОКПП: <u>0${declaration.patient.RNTRC}</u></p>
                                <p>1.8.1 РНОКПП: <u>0${declaration.patient.RNTRC}</u></p>

                                <p><b>2. ЛІКАР, ЯКИЙ НАДАЄ ПМД</b></p>
                                <p>2.1 Прізвище: <u>${declaration.doctor_dec.surname}</u></p>
                                <p>2.2 Ім'я: <u>${declaration.doctor_dec.name}</u></p>
                                <p>2.3 По-батькові: <u>${declaration.doctor_dec.middle_name}</u></p>
                                <p>2.4 Номер телефону: <u>0${declaration.doctor_dec.telephone_number}</u></p>
                                <p>2.5 Адреса електронної пошти: <u>${declaration.doctor_dec.email}</u></p>

                                <p><b>3. ЗГОДА НА ЗБІР ТА ОБРОБКУ ПЕРСОНАЛЬНИХ ДАНИХ</b></p>
                                <p>Я, <u>${declaration.patient.surname} ${declaration.patient.name} ${declaration.patient.middle_name}</u>
                                    даю згоду на обробку персональних даних: ${declaration.consent==false ? "так,<u>ні</u>" : "<u>так</u>, ні" } (потрібне підкреслити)</p>


                                <p><b>4. ПІДПИС ПАЦІЄНТА</b></p>
                                <p>Підпис:__________________________________</p>

                                <p><b>5. ДАТА ПОДАННЯ</b></p>
                                <p>${date}</p>

                            </div>
                            <center>
                                <a href="/doctor1/patients/1" class="btn btn_form_add">Далі</a>
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