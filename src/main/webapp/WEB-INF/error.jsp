<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Error</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">

</head>

<body style="background-color: #f5dec5;">

<div class="container">

    <div class="row">
        <content>
            <div class="col-xl-8 col-lg-12 col-md-12 col-sm-12 mx-auto">
                <div class="card mt-3 front_card">
                    <img src="/res/error_cat.png" alt="not found" class="card-img-top">

                    <div class="card-body">
                        <h5 class="card-title text-center">Сторінка не знайдена</h5>
                        <p class="card-text text-center">Нажаль сторінка не знайдена. Поверніться на початкову сторінку.</p>

                        <center><a href="/logout" class="btn btn-outline-primary">Повернутися на початкову сторінку</a></center>
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