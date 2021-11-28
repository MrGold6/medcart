<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg navbar-light " style="background-color: rgb(210 193 193);">
    <div class="container-fluid">
        <a class="navbar-brand logo" href="#" style="color: #9b3963">MedCard</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarScroll" aria-controls="navbarScroll" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>


        <div class="collapse navbar-collapse justify-content-end" id="navbarScroll">

            <ul class="navbar-nav  navbar-nav-scroll my-2 my-lg-0 " style="--bs-scroll-height: 100px;">

                <li class="nav-item ">
                    <a class="nav-link link active" aria-current="page" href= '/logout'>Вийти</a>
                </li>
                <c:if test="${doctor.specialization.id==1}">
                    <li class="nav-item">
                        <a class="nav-link link active" href= '/doctor1/patients/1'>Створення пацієнта</a>
                    </li>
                </c:if>

                <li class="nav-item">
                    <a class="nav-link link active" href= '/today_visits'>Візити на сьогодні</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link link active" href= '/patients/doctor'>${doctor.specialization.name} ${doctor.surname} ${doctor.name.charAt(0)}.${doctor.middle_name.charAt(0)}.</a>
                </li>

            </ul>

        </div>
    </div>


</nav>
