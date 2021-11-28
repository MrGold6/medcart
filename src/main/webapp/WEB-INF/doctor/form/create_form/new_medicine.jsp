<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

                        <legend class="card-title text-center">Препарат</legend>

                        <c:url value="/add_new_medicine_act" var="addUrl"/>
                        <form action="${addUrl}" method="POST" name="medicine" class="was-validated">
                            <input name="id_visit" type="hidden" value="${id_visit}" maxlength="100" readonly>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Назва:</label>
                                <div class="col-sm-6">
                                    <select name="selected_medicine" class="form-select">
                                        <c:forEach var="medItem" items="${medicineCatalogList}" varStatus="i">
                                            <option value="${medItem.name}">${medItem.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Разова доза:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="text" name="dose" value="0" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Кількість днів:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="number" name="number_of_day" value="0" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Інструкція:</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="text" name="instruction">
                                </div>
                            </div>

                            <center>
                                <button class="btn btn_form_add d-block mb-2" name="add_new_medicine" value="add_new_medicine">Додати у рецепт</button>
                                <button id="rec" class="btn btn_form_safe d-block" name="safe_Recipe" value="safe_Recipe">Зберегти рецепт</button>
                            </center>

                            <c:if test="${!recipeJSP.medicineList.isEmpty()}">
                            <div class="table-wrapper-scroll-y my-custom-scrollbar">
                                <legend class="card-title text-center">Список вибраних ліків:</legend>
                                <table class="table tableFixHead">
                                    <thead>
                                    <tr>
                                        <th>Назва</th>
                                        <th>Разова доза</th>
                                        <th>Кількість днів</th>
                                        <th>Інструкція</th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:forEach var="medicine" items="${recipeJSP.medicineList}" varStatus="i">
                                        <tr id="medicine_table">
                                            <td>${medicine.name}</td>
                                            <td>${medicine.dose}</td>
                                            <td>${medicine.number_of_day}</td>
                                            <td>${medicine.instruction}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>

                            </div>

                        </form>
                        </c:if>
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