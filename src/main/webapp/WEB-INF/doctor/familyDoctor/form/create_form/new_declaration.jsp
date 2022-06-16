<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../../../template/head.jsp" />

<c:url value="/doctor1/add_declaration" var="addUrl"/>
<body>
<div class="container">

    <div class="row">
        <content>

            <div class="col-xl-7 col-lg-10 col-md-10 col-sm-10 mx-auto p-3 pt-3">

                <div class="card card_form">

                    <div id="return">
                        <button onclick="document.location = '/doctor1/patients/1';" type="button" class="btn btn-circle btn-lg d-flex justify-content-center align-items-center">
                            <i class="bi bi-arrow-left ar"></i>
                        </button>
                    </div>


                    <div class="card-body">
                        <legend class="card-title text-center">Декларація</legend>

                        <c:if test="${doctor.countOfDeclaration<=0}">
                            <h2><em><center>Всі ваші декларації використані</center></em></h2>
                        </c:if>

                        <c:if test="${doctor.countOfDeclaration>0}">
                        <form:form action="${addUrl}" method="POST" name="declaration"  class="was-validated">

                            <input class="form-control " type="hidden" name="id_patient" value="${id_patient}" readonly>


                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Документ, що посвідчує особу:</label>
                                <div class="col-sm-6">
                                    <input type="text"  name="document_identifier" class="form-control" value="${declaration.document_identifier}" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-6 col-form-label ln">Збір персональних даних:</label>
                                <div class="col-sm-6">
                                    <div class="form-check form-check-inline">
                                        <input type="radio" id="consent1" class="form-check-input" name="consent" value="1" required>
                                        <label for="consent1" class="form-check-label" >Згоден</label>

                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input type="radio" id="consent2" class="form-check-input" name="consent" value="0" required>
                                        <label for="consent2" class="form-check-label">Не згоден</label>
                                    </div>
                                </div>
                            </div>

                            <center>
                                <c:set value="add_declaration" var="add_declaration"/>
                                <input type="submit" id="in" class="btn btn_form_add" name="${add_declaration}" value="Створити">

                            </center>

                        </form:form>

                        </c:if>

                    </div>
                </div>
            </div>

            <!-- Modal -->
            <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-body">
                            <div class="text-center text-justify">
                                <i class="bi bi-exclamation-triangle-fill text-danger pl-2 pt-2 dang  position-relative"></i>
                                <span class="ml-2" style="font-size:16pt;">Такий пацієнт вже існує</span>
                            </div>
                            <center><button type="button" class="btn btn-outline-primary mt-3" data-bs-dismiss="modal">OK</button></center>
                        </div>


                    </div>
                </div>
            </div>


        </content>
        <footer></footer>
    </div>
</div>




<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>



</body>
</html>

<c:if test="${message!=null}">
    <script type="text/javascript">
        var myModal = new bootstrap.Modal(document.getElementById("staticBackdrop"), {});
        document.onreadystatechange = function () {
            myModal.show();
        };
    </script>
</c:if>