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

                <li class="nav-item">
                    <a class="nav-link link active" href= '/patient/directions'>Доступні лікарі</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link link active" href= '/patient/allActiveVisits'>Мої записи</a>
                </li>

                <li class="nav-item ">
                    <a class="nav-link link active" aria-current="page" href= '/patient/1'>Медична карта</a>
                </li>


                <li class="nav-item">
                    <a class="nav-link link active" href= '/patient/profile'>${patient.surname} ${patient.name.charAt(0)}.${patient.middle_name.charAt(0)}.</a>
                </li>
                <li class="nav-item">
                </li>

            </ul>

        </div>
    </div>


</nav>