<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<jsp:include page="../template/head.jsp" />

<body class="d-flex flex-column min-vh-100">
<jsp:include page="../template/header.jsp" />
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../template/nav.jsp" />
        <content class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <h2 class="pt-3">
                Лікар: ${doctor.surname} ${doctor.name.charAt(0)}.${doctor.middle_name.charAt(0)}.
                Відділення: <c:if test="${department.name!=''}">${doctor.department.name}</c:if>
                <c:if test="${department.name==null}">
                    <a href="/admin/${doctor.RNTRC}/add_department" class="btn btn_add">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-building" viewBox="0 0 16 16">
                            <path fill-rule="evenodd" d="M14.763.075A.5.5 0 0 1 15 .5v15a.5.5 0 0 1-.5.5h-3a.5.5 0 0 1-.5-.5V14h-1v1.5a.5.5 0 0 1-.5.5h-9a.5.5 0 0 1-.5-.5V10a.5.5 0 0 1 .342-.474L6 7.64V4.5a.5.5 0 0 1 .276-.447l8-4a.5.5 0 0 1 .487.022zM6 8.694 1 10.36V15h5V8.694zM7 15h2v-1.5a.5.5 0 0 1 .5-.5h2a.5.5 0 0 1 .5.5V15h2V1.309l-7 3.5V15z"/>
                            <path d="M2 11h1v1H2v-1zm2 0h1v1H4v-1zm-2 2h1v1H2v-1zm2 0h1v1H4v-1zm4-4h1v1H8V9zm2 0h1v1h-1V9zm-2 2h1v1H8v-1zm2 0h1v1h-1v-1zm2-2h1v1h-1V9zm0 2h1v1h-1v-1zM8 7h1v1H8V7zm2 0h1v1h-1V7zm2 0h1v1h-1V7zM8 5h1v1H8V5zm2 0h1v1h-1V5zm2 0h1v1h-1V5zm0-2h1v1h-1V3z"/>
                        </svg>
                    </a>
                </c:if>
            </h2>

            <ul class="nav nav-tabs ">
                <li class="nav-item">
                    <a href="/main_doctor/${doctor.RNTRC}/doctor_info" class="nav-link link-custom " tabindex="-1" aria-disabled="true">
                        Записи
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link link-custom active" href="/main_doctor/${doctor.RNTRC}/doctor_info/done" aria-current="page" >
                        Проведені візити
                    </a>
                </li>

            </ul>


            <div class="col-xl-5 col-lg-5 col-md-7 col-sm-5 pt-1 px-2 pb-2 mx-auto">
                <form class="d-flex" id="search" method="GET" action="/main_doctor/${doctor.RNTRC}/doctor_info/done">

                    <select name="month" class="form-select">
                        <option value="1"  <c:if test="${date==1}">selected</c:if>>Січень</option>
                        <option value="2" <c:if test="${date==2}">selected</c:if>>Лютий</option>
                        <option value="3" <c:if test="${date==3}">selected</c:if>>Березень</option>
                        <option value="4" <c:if test="${date==4}">selected</c:if>>Квітень</option>
                        <option value="5" <c:if test="${date==5}">selected</c:if>>Травень</option>
                        <option value="6" <c:if test="${date==6}">selected</c:if>>Червень</option>
                        <option value="7" <c:if test="${date==7}">selected</c:if>>Липень</option>
                        <option value="8" <c:if test="${date==8}">selected</c:if>>Серпень</option>
                        <option value="9" <c:if test="${date==9}">selected</c:if>>Вересень</option>
                        <option value="10" <c:if test="${date==10}">selected</c:if>>Жовтень</option>
                        <option value="11" <c:if test="${date==11}">selected</c:if>>Грудень</option>
                        <option value="12" <c:if test="${date==12}">selected</c:if>>Листопад</option>
                    </select>

                    <button class="btn btn_find mx-2" id="search_button" type="submit" name="search">Обрати</button>
                    <a href="/main_doctor/${doctor.RNTRC}/doctor_info/done" class="btn btn_find_all">Поточний</a>
                </form>
            </div>

            <c:if test="${maxCountDisease!=0}">
                <div id="my_dataviz"></div>
            </c:if>
            <c:if test="${maxCountDisease==0}">
                <h2><em>
                    <center>Даних немає</center>
                </em></h2>
            </c:if>

            <script src="https://d3js.org/d3.v4.js"></script>

        </content>

    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>



</body>

</html>

<script>

    var data = [
        <c:forEach var="statistic" items="${countVisits}" varStatus="i">
        {
            Week: "Тиждень ${statistic.weekNum}",
            Value: ${statistic.count}
        },
        </c:forEach>

    ];

    // set the dimensions and margins of the graph
    var margin = {top: 10, right: 30, bottom: 40, left: 250},
        width = 700 - margin.left - margin.right,
        height = 500 - margin.top - margin.bottom;

    // append the svg object to the body of the page
    var svg = d3.select("#my_dataviz")
        .append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform",
            "translate(" + margin.left + "," + margin.top + ")");



    // Add X axis
    var x = d3.scaleLinear()
        .domain([0, ${maxCountDisease}])
        .range([0, width]);
    svg.append("g")
        .attr("transform", "translate(0," + height + ")")
        .call(d3.axisBottom(x))
        .selectAll("text")
        .attr("transform", "translate(-10,0)rotate(-45)")
        .style("text-anchor", "end")
        .style("font-size", "1.1em");


    // Y axis
    var y = d3.scaleBand()
        .range([0, height])
        .domain(data.map(function (d) {
            return d.Week;
        }))
        .padding(1);
    svg.append("g")
        .call(d3.axisLeft(y))
        .style("font-size", "0.8em");

    // Lines
    svg.selectAll("myline")
        .data(data)
        .enter()
        .append("line")
        .attr("x1", x(0))
        .attr("x2", x(0))
        .attr("y1", function (d) {
            return y(d.Week);
        })
        .attr("y2", function (d) {
            return y(d.Week);
        })
        .attr("stroke", "grey")

    // Circles -> start at X=0
    svg.selectAll("mycircle")
        .data(data)
        .enter()
        .append("circle")
        .attr("cx", x(0))
        .attr("cy", function (d) {
            return y(d.Week);
        })
        .attr("r", "7")
        .style("fill", "#85CBD4FF")
        .attr("stroke", "black")

    // Change the X coordinates of line and circle
    svg.selectAll("circle")
        .transition()
        .duration(2000)
        .attr("cx", function (d) {
            return x(d.Value);
        })

    svg.selectAll("line")
        .transition()
        .duration(2000)
        .attr("x1", function (d) {
            return x(d.Value);
        })


</script>