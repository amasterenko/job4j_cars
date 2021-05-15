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
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF" crossorigin="anonymous"></script>
    <title>MyCarsSales</title>
</header>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-4 offset-md-4">
            <div class="py-5 text-center">
                <h2>OnlyC@rSales</h2>
            </div>
            <div class="row justify-content-center">
            <form id="form" class="was-validated" method="post" action="<%=request.getContextPath()%>/auth">
                <label for="inputEmail" class="sr-only">Email</label>
                <input type="text" name="email" id="inputEmail" class="form-control" placeholder="Email" required>
                <div class="d-flex p-2"></div>
                <label for="inputPassword" class="sr-only">Password</label>
                <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
                <div class="d-flex p-2 justify-content-center">
                    <a href="<%=request.getContextPath()%>/reg" class="text-dark"><small>Sign up</small></a>
                </div>

                <button class="btn btn-lg btn-outline-dark btn-block " id="submit" type="submit">Sign in</button>
                <div class="d-flex p-2"></div>
                <a href="<%=request.getContextPath()%>/ads" class="btn btn-lg btn-secondary btn-block" role="button">Cancel</a>
                <div class="d-flex p-2"></div>
                <div class="row" id="msg">
                    <c:if test="${err != null}">
                        <div class="alert alert-warning d-flex align-items-center" role="alert">
                            <c:out value="${err}"/>
                        </div>
                    </c:if>
                </div>
            </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
