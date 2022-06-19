<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<jsp:include page="../template/head.jsp"/>

<body class="d-flex flex-column min-vh-100">
<jsp:include page="../template/header.jsp"/>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../template/nav.jsp"/>
        <content class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <h2 class="pt-3">Захворювання</h2>

            <div class="col-xl-5 col-lg-5 col-md-7 col-sm-5 pt-1 px-2 pb-2 mx-auto">
                <form class="d-flex" id="search" method="GET" action="/main_doctor/disease">

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
                    <a href="/main_doctor/disease" class="btn btn_find_all">Поточний</a>
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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
        crossorigin="anonymous"></script>


</body>

</html>

<script>

    var data = [
        <c:forEach var="statistic" items="${countDiseases}" varStatus="i">
        {
            Disease: "${statistic.disease.name}",
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


    // sort data
    data.sort(function (b, a) {
        return a.Value - b.Value;
    });

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
            return d.Disease;
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
            return y(d.Disease);
        })
        .attr("y2", function (d) {
            return y(d.Disease);
        })
        .attr("stroke", "grey")

    // Circles -> start at X=0
    svg.selectAll("mycircle")
        .data(data)
        .enter()
        .append("circle")
        .attr("cx", x(0))
        .attr("cy", function (d) {
            return y(d.Disease);
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