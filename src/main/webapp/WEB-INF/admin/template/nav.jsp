<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
    <div class="position-sticky pt-3">
        <ul class="nav flex-column">

            <li class="nav-item">
                <a class="nav-link"  href="/admin/user/1">
                    <div class="responsive__offer">
                        <div class="responsive__offer-icon">
                            <div class="c">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-app" viewBox="0 0 16 16">
                                    <path d="M11 2a3 3 0 0 1 3 3v6a3 3 0 0 1-3 3H5a3 3 0 0 1-3-3V5a3 3 0 0 1 3-3h6zM5 1a4 4 0 0 0-4 4v6a4 4 0 0 0 4 4h6a4 4 0 0 0 4-4V5a4 4 0 0 0-4-4H5z"/>
                                </svg>
                            </div>
                        </div>
                        <p class="responsive__offer-text">Користувач</p>
                    </div>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="/admin/patients">
                    <div class="responsive__offer">
                        <div class="responsive__offer-icon">
                            <div class="c">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-clock" viewBox="0 0 16 16">
                                    <path d="M8 3.5a.5.5 0 0 0-1 0V9a.5.5 0 0 0 .252.434l3.5 2a.5.5 0 0 0 .496-.868L8 8.71V3.5z"/>
                                    <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm7-8A7 7 0 1 1 1 8a7 7 0 0 1 14 0z"/>
                                </svg>
                            </div>
                        </div>
                        <p class="responsive__offer-text">Пацієнти</p>
                    </div>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="/admin/medicineCatalog/1">
                    <div class="responsive__offer">
                        <div class="responsive__offer-icon">
                            <div class="c">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-calendar-x" viewBox="0 0 16 16">
                                    <path d="M6.146 7.146a.5.5 0 0 1 .708 0L8 8.293l1.146-1.147a.5.5 0 1 1 .708.708L8.707 9l1.147 1.146a.5.5 0 0 1-.708.708L8 9.707l-1.146 1.147a.5.5 0 0 1-.708-.708L7.293 9 6.146 7.854a.5.5 0 0 1 0-.708z"/>
                                    <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V4H1z"/>
                                </svg>
                            </div>
                        </div>
                        <p class="responsive__offer-text">Препарати</p>
                    </div>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link"  href="/admin/disease/1">
                    <div class="responsive__offer">
                        <div class="responsive__offer-icon">
                            <div class="c">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-app" viewBox="0 0 16 16">
                                    <path d="M11 2a3 3 0 0 1 3 3v6a3 3 0 0 1-3 3H5a3 3 0 0 1-3-3V5a3 3 0 0 1 3-3h6zM5 1a4 4 0 0 0-4 4v6a4 4 0 0 0 4 4h6a4 4 0 0 0 4-4V5a4 4 0 0 0-4-4H5z"/>
                                </svg>
                            </div>
                        </div>
                        <p class="responsive__offer-text"> Захворювання</p>
                    </div>
                </a>
            </li>



            <li class="nav-item">
                <a class="nav-link"  href="/admin/specialization/1">
                    <div class="responsive__offer">
                        <div class="responsive__offer-icon">
                            <div class="c">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-app" viewBox="0 0 16 16">
                                    <path d="M11 2a3 3 0 0 1 3 3v6a3 3 0 0 1-3 3H5a3 3 0 0 1-3-3V5a3 3 0 0 1 3-3h6zM5 1a4 4 0 0 0-4 4v6a4 4 0 0 0 4 4h6a4 4 0 0 0 4-4V5a4 4 0 0 0-4-4H5z"/>
                                </svg>
                            </div>
                        </div>
                        <p class="responsive__offer-text">Спеціалізація</p>
                    </div>
                </a>
            </li>


        </ul>


    </div>
</nav>
