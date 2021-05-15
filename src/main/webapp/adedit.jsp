<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype HTML>
<html lang="en">
<header>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
  <!-- jQuery first, then Popper.js, then Bootstrap JS -->
  <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF" crossorigin="anonymous"></script>
  <script src="func.js"></script>
  <title>MyCarsSales</title>
</header>
<body>
<div class="container-fluid">
  <div class="row">
    <div class="col-md-6 offset-md-3">
      <div class="row p-4"><img src="img/logo.png" class="img-fluid mx-auto d-block" alt="OnlyC@rSales"></div>
      <div class="row no-gutters">
        <div class="col text-center">
          <h5>Sell your car</h5>
        </div>
      </div>
      <div class="row justify-content-center">
        <form id="adForm" class="was-validated" onsubmit="return validateFiles();" method="post" action="<%=request.getContextPath()%>/edit"
              enctype="multipart/form-data">
          <div class="row py-2">
            <div class="col">
              <label for="inputTitle" class="sr-only">Title</label>
              <input type="text" name="title" id="inputTitle" maxlength="30" class="form-control"
                     placeholder="Title" required>
            </div>
          </div>
          <div class="row py-2">
            <div class="col">
              <label for="selectMake" class="sr-only"></label>
              <select class="custom-select mr-sm-2" id="selectMake" onchange="fillCarModelsSelectControl(this, $('#selectModel'))"
                      name="make" required> <option value="" selected disabled>Select make</option>
                <c:forEach items="${makes}" var="make">
                  <option value="${make.id}">"${make.name}"</option>
                </c:forEach>
              </select>
            </div>
            <div class="col">
              <select class="custom-select mr-sm-2" id="selectModel" name="model" required>
                <option value="" selected disabled>Select model</option>
              </select>
            </div>
          </div>
          <div class="row py-2">
            <div class="col">
              <select class="custom-select mr-sm-2" id="selectBody" name="body" required>
                <option value="" selected disabled>Body type</option>
                <c:forEach items="${bodies}" var="body">
                  <option value="${body.id}">"${body.name}"</option>
                </c:forEach>
              </select>
            </div>
            <div class="col">
              <select class="custom-select mr-sm-2" id="selectColor" name="color" required>
                <option value="" selected disabled>Color</option>
                <c:forEach items="${colors}" var="color">
                  <option value="${color.id}">"${color.name}"</option>
                </c:forEach>
              </select>
            </div>
          </div>
          <div class="row py-2">
            <div class="col">
              <select class="custom-select mr-sm-2" id="selectEngine" name="engine" required
                      onchange="disableEngineDispInput()">
                <option value="" selected disabled>Engine type</option>
                <c:forEach items="${engines}" var="engine">
                  <option value="${engine.id}">"${engine.name}"</option>
                </c:forEach>
              </select>
            </div>
            <div class="col">
              <label for="inputEngDisp" class="sr-only">Engine displacement in litres</label>
              <input type="text" pattern="\d{1,2}\.\d{1,2}$|\d{1,2}$" value="0.0" name="engDisp" id="inputEngDisp" maxlength="4" class="form-control"
                     placeholder="Engine displacement" required>
            </div>
          </div>
          <div class="row py-2">
            <div class="col">
              <select class="custom-select mr-sm-2" id="selectTransm" name="transmission" required>
                <option value="" selected disabled>Transmission type</option>
                <c:forEach items="${transmissions}" var="transmission">
                  <option value="${transmission.id}">"${transmission.name}"</option>
                </c:forEach>
              </select>
            </div>
            <div class="col">
              <label for="inputKm" class="sr-only">Mileage, km.</label>
              <input type="number" name="km" id="inputKm" maxlength="7" class="form-control" placeholder="Mileage, km." required>
            </div>
          </div>
          <div class="row py-2">
            <div class="col">
              <label for="inputYear" class="sr-only">Build year</label>
              <input type="text" pattern="19\d\d|20[0-2]\d$" name="year" id="inputYear" maxlength="4" class="form-control" placeholder="Build year" required>
            </div>
            <div class="col">
              <label for="inputPrice" class="sr-only">Price $</label>
              <input type="number" name="price" id="inputPrice" maxlength="8" class="form-control" placeholder="Price, $" required>
            </div>
          </div>
          <div class="row py-2">
            <div class="col">
              <label for="description">Description</label>
              <textarea class="form-control" id="description" name="description" rows="3"
                        minlength="10" maxlength="255" placeholder="Description" required></textarea>
            </div>
          </div>
          <div class="row py-2">
            <div class="col">
              <input id="inputFiles" name="files" type="file" class="custom-file"
                     multiple data-show-upload="true" data-show-caption="true" accept="image/*" maxlength="4">
            </div>
          </div>
          <div class="d-flex p-2"></div>
          <button class="btn btn-lg btn-outline-dark btn-block" id="submit" type="submit">Save</button>
          <a href="<%=request.getContextPath()%>/userads" class="btn btn-lg btn-secondary btn-block" role="button">Cancel</a>
        </form>
      </div>
      <div class="d-flex p-2"></div>
      <div class="row justify-content-center">
        <div class="col" id="msg">
          <c:if test="${err != null}">
            <div class="alert alert-warning d-flex align-items-center" role="alert">
              <c:out value="${err}"/>
            </div>
          </c:if>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
