<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>
        <div class="container">
            <div class="row">
                <div class="col-xs-10 col-xs-offset-1 col-sm-6 col-sm-offset-3 well">
                    <div class="row">
                        <div class="col-xs-12">
                            <h3>Votre caddie contient :</h3>
                            <ul class="list-group">
                                <c:forEach items="${flights}" var="mapEntry">
                                <li class="list-group-item">
                                    <span class="badge">${mapEntry.value.seats} place(s)</span>
                                    <h5 class="list-group-item-heading">Vol ${mapEntry.value.idAirline}${mapEntry.value.idAirplane}</h5>
                                    <p>${mapEntry.value.departureDate} - ${mapEntry.value.departureTime}</p>
                                </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10 col-xs-offset-1">
                            <hr />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10 col-xs-offset-1">
                            <h4>Pour un total de ${totalPrice}&#8364;.</h4>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10 col-xs-offset-1">
                            <hr />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="row">
                                <div class="col-xs-12">
                                    <h3>Que voulez-vous faire?</h3>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-3 col-xs-offset-1">
                                    <a href="payment" class="btn btn-primary active">
                                        <i class="glyphicon glyphicon-ok" aria-hidden="true"></i>
                                        Payer!
                                    </a>
                                </div>
                                <div class="col-xs-3 col-xs-offset-1">
                                    <a href="pay?action=emptyCaddie" class="btn btn-primary active">
                                        <i class="glyphicon glyphicon-ok" aria-hidden="true"></i>
                                        Vider mon caddie
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
<%@ include file="footer.jsp" %>