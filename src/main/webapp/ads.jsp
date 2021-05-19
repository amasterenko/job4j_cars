<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html>
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
            <div class="row">
                <%--filter--%>
                <c:if test="${!userCars}">
                <div class="col my-auto">
                    <div class="dropdown">
                        <button class="btn btn-link dropdown-toggle text-dark" type="button" id="dropdownMenu"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Filters
                        </button>
                        <form class="dropdown-menu px-3 py-3" onsubmit="return setDefaultFilterFieldsAndSaveSettings();"
                              action="<%=request.getContextPath()%>/ads" method="post" id="ddform">
                            <div class="row">
                                <div class="col-6">
                                    <div class="form-group input-group-sm">
                                        <label for="selectDays">Created</label>
                                        <select class="custom-select mr-sm-2" id="selectDays" name="daysAgo">
                                            <option value="" selected disabled>-------</option>
                                            <option value="0">today</option>
                                            <option value="7">in 7 past days</option>
                                            <option value="30">in 30 past days</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <div class="form-group input-group-sm">
                                        <label for="selectMake">Make</label>
                                        <select class="custom-select mr-sm-2" id="selectMake" name="makeId"
                                                onchange="fillCarModelsSelectControl(this, $('#selectModel'))">
                                            <option value="" selected disabled>-------</option>
                                            <c:forEach items="${makes}" var="make">
                                                <option value="${make.id}">"${make.name}"</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="form-group input-group-sm">
                                        <label for="selectModel">Model</label>
                                        <select class="custom-select mr-sm-2" id="selectModel" name="modelId">
                                            <option value="" selected disabled>-------</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-6">
                                    <div class="form-group input-group-sm">
                                        <label for="selectEngine">Engine</label>
                                        <select class="custom-select mr-sm-2" id="selectEngine" name="engineId">
                                            <option value="" selected disabled>-------</option>
                                            <c:forEach items="${engines}" var="engine">
                                                <option value="${engine.id}">"${engine.name}"</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <div class="form-group input-group-sm">
                                        <label for="minPrice">Min price</label>
                                        <input type="number" class="form-control" name="minPrice" id="minPrice"
                                               placeholder="$" min="0">
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="form-group input-group-sm">
                                        <label for="maxPrice">Max price</label>
                                        <input type="number" class="form-control" name="maxPrice" id="maxPrice"
                                               max="100000000" placeholder="$">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <div class="form-group input-group-sm">
                                        <label for="minYear">Min build year</label>
                                        <input type="number" class="form-control" name="minYear" id="minYear"
                                               placeholder="" min="1900" maxlength="4">
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="form-group input-group-sm">
                                        <label for="maxYear">Max build year</label>
                                        <input type="number" class="form-control" name="maxYear" id="maxYear"
                                               maxlength="4" placeholder="">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <div class="form-group input-group-sm">
                                        <label for="minKm">Min mileage</label>
                                        <input type="number" class="form-control" name="minKm" id="minKm"
                                               placeholder="km." min="0">
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="form-group input-group-sm">
                                        <label for="maxKm">Max mileage</label>
                                        <input type="number" class="form-control" name="maxKm" id="maxKm"
                                               placeholder="km.">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col text-center">
                                    <button type="submit" class="btn btn-outline-dark btn-sm">Apply</button>
                                </div>
                                <div class="col text-center">
                                    <button type="button" class="btn btn-outline-dark btn-sm" onclick="resetFilterSettings();">Reset</button>
                                </div>
                            </div>
                    </form>
                </div>
                </div>
                </c:if>
                <%--nav bar--%>
                <div class="col">
                    <ul class="nav float-right">
                        <c:if test="${sessionScope.user == null}">
                            <li class="nav-item">
                                <a class="nav-link-sm text-dark" href="<%=request.getContextPath()%>/auth">Sign in</a>
                            </li>
                        </c:if>
                        <c:if test="${sessionScope.user != null}">
                            <c:if test="${userCars}">
                                <li class="nav-item-sm">
                                    <a class="nav-link text-dark" href="<%=request.getContextPath()%>/ads">All cars</a>
                                </li>
                            </c:if>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle text-dark" href="#" id="navbarDDMenu" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <c:out value="${sessionScope.user.name}'s menu"/>
                                </a>
                                <div class="dropdown-menu text-center" aria-labelledby="navbarDDMenu">
                                    <a class="dropdown-item" href="<%=request.getContextPath()%>/edit">Sell car</a>
                                    <a class="dropdown-item" href="<%=request.getContextPath()%>/userads">My cars</a>
                                    <a class="dropdown-item" href="<%=request.getContextPath()%>/auth?cmd=out">Sign out</a>
                                </div>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>
            <div class="row p-4"></div>
            <c:forEach items="${ads}" var="ad" varStatus="adsLoop">
            <c:set var="textMute" value=""/>
            <c:set var="sold" value=""/>
            <c:if test="${ad.sold}">
                <c:set var="textMute" value="text-muted"/>
                <c:set var="sold" value="SOLD"/>
            </c:if>
                <%--ad's card--%>
            <div class="card">
                <div class="card-body ${textMute}">
                    <div class="row">
                        <div class="col-md-5">
                            <button type="button" class="btn" data-toggle="modal" data-target="#modal-${ad.id}">
                                <c:set var="photo" value="${ad.photos[0].id}"/>
                                <c:if test="${empty ad.photos}">
                                    <c:set var="photo" value="0"/>
                                </c:if>
                                <img src='<%=request.getContextPath()%>/photo?id=<c:url value="${photo}"/>' width="360" height="240"
                                     class="img-fluid rounded width: 100%;" alt="">
                            </button>
                        </div>
                        <div class="col-md-7 px-lg-4">
                            <div class="row ">
                                <div class="col text-left">
                                    <h5><c:out value="${ad.title}"/></h5>
                                </div>
                                <div class="col-md-3 text-right">
                                    <fmt:setLocale value="en_US"/>
                                    <h5><fmt:formatNumber value="${ad.price}" type="currency"
                                                          maxFractionDigits="0"/></h5>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col text-left">
                                    <ul>
                                        <li><c:out value="Model: ${ad.model.make.name} ${ad.model.name}"/></li>
                                        <li><c:out value="Engine: ${ad.engine.name} ${ad.engineDisplacement}L"/></li>
                                        <li><c:out value="Transmission: ${ad.transmission.name}"/></li>
                                        <li>Mileage: <fmt:formatNumber pattern="#,##0" value="${ad.km}"/> km</li>
                                        <li><c:out value="Build year: ${ad.year}"/></li>
                                        <li><c:out value="Body type: ${ad.body.name}"/></li>
                                        <li><c:out value="Color: ${ad.color.name}"/></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row p-2 border-bottom"></div>
                    <div class="row p-2"></div>
                    <div class="row border-bottom">
                        <div class="col text-left">
                            <h5>Description</h5>
                            <p><c:out value="${ad.description}"/></p>
                        </div>
                        <div class="col-md-auto text-left px-md-5">
                            <h5>Contacts</h5>
                            <p><c:out value="Seller: ${ad.user.name}"/><br>
                                <c:out value="Phone: ${ad.user.phone}"/> <br>
                                Email: <a href='mailto:<c:out value="${ad.user.email}"/>'><c:out
                                        value="${ad.user.email}"/></a></p>
                        </div>
                    </div>
                    <div class="row p-2"></div>
                    <div class="row">
                        <div class="col text-left">
                            <p class="font-weight-bold"><c:out value="${sold}"/></p>
                            <c:if test="${sessionScope.user.id == ad.user.id && !ad.sold}">
                                <form method="post" action="<%=request.getContextPath()%>/userads?sold=${ad.id}">
                                    <button type="submit" class="btn btn-light">Mark as sold</button>
                                </form>
                            </c:if>
                        </div>
                        <div class="col text-right px-md-5">
                            <p class="text-muted"><small>Created: <fmt:formatDate value="${ad.created}" pattern="dd-MMM-yyyy"/></small></p>
                        </div>
                    </div>
                </div>
                    <%--photo's carousel--%>
                <div class="modal fade" id="modal-${ad.id}" tabindex="-1" aria-labelledby="ModalCenterTitle"
                     style="display: none;" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5><c:out value="${ad.title}"/></h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">×</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <div id="carouselControls-${ad.id}" class="carousel slide" data-ride="carousel">
                                    <ol class="carousel-indicators">
                                        <c:forEach items="${ad.photos}" var="photo" varStatus="adPhotosLoop">
                                            <li data-target="#carouselIndicators" data-slide-to="${adPhotosLoop.index}"></li>
                                        </c:forEach>
                                    </ol>
                                    <div class="carousel-inner">
                                        <c:forEach items="${ad.photos}" var="photo" varStatus="adPhotosLoop">
                                        <div class=
                                             <c:if test="${adPhotosLoop.index == 0}">
                                                     "carousel-item active"
                                        </c:if>
                                        <c:if test="${adPhotosLoop.index != 0}">
                                            "carousel-item"
                                        </c:if>
                                        ><img src="<%=request.getContextPath()%>/photo?id=${photo.id}" class="d-block w-100" alt="">
                                    </div>
                                    </c:forEach>
                                </div>
                                <a class="carousel-control-prev" href="#carouselControls-${ad.id}" role="button"
                                   data-slide="prev">
                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                    <span class="sr-only">Previous</span>
                                </a>
                                <a class="carousel-control-next" href="#carouselControls-${ad.id}" role="button"
                                   data-slide="next">
                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                    <span class="sr-only">Next</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row p-2"></div>
        </c:forEach>
    </div>
</div>
<script>
    $(document).ready(restoreFilterSettings());
</script>
</body>
</html>
