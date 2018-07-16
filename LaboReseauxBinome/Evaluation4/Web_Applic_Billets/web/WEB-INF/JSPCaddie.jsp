<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>
        <div class="container">
            <div class="row">
                <div class="col-xs-12 col-sm-4 col-sm-push-8 well">
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
                        <div class="col-xs-2 col-xs-offset-2 text-center">
                            <a href="pay" class="btn btn-sm btn-primary active">
                                <i class="glyphicon glyphicon-eur" aria-hidden="true"></i>
                                Payer!
                            </a>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10 col-xs-offset-1 visible-xs">
                            <hr />
                        </div>
                    </div>
                </div>
                
                <div class="col-xs-12 col-sm-7 col-sm-pull-4 well">
                    <h3>voici les vols disponibles :</h3>
                    <ul class="list-group">
                        <c:forEach items="${availableFlights}" var="mapEntry">
                            <div class="col-xs-12">
                                <li class="list-group-item">
                                    <form class="form-horizontal" method="POST" action="caddie">
                                        <h4>Vol ${mapEntry.key.idAirline}${mapEntry.key.idAirplane}</h4>
                                        <input hidden="true" name="ap" value="${mapEntry.key.idAirplane}"/>
                                        <input hidden="true" name="al" value="${mapEntry.key.idAirline}"/>
                                        <input hidden="true" name="dep" value="${mapEntry.key.departureDate}"/>
                                        <input hidden="true" name="des" value="${mapEntry.key.destination}"/>
                                        
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">Date : </label>
                                            <div class="col-sm-6">
                                                <p class="form-control-static">${mapEntry.key.departureDate}</p>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">heure : </label>
                                            <div class="col-sm-6">
                                                <p class="form-control-static">${mapEntry.key.departureTime}</p>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">Destination : </label>
                                            <div class="col-sm-6">
                                                <p class="form-control-static">${mapEntry.key.destination}</p>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">Prix : </label>
                                            <div class="col-sm-6">
                                                <p class="form-control-static">${mapEntry.key.price} &#8364;/place</p>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-4 control-label" for="rsvNbr">Nbr de places :</label>
                                            <div class="col-xs-5">
                                                <div class="input-group">
                                                    <input type="number" min="0" max="${mapEntry.value - mapEntry.key.seats}" required="" value="1" class="form-control" name="rsvNbr"/>
                                                    <span class="input-group-addon">/${mapEntry.value - mapEntry.key.seats}</span>
                                                </div>
                                            </div>
                                            <div class="col-x-3">
                                                <button type="submit" class="btn btn-info">Reserver</button>
                                            </div>
                                        </div>
                                    </form>
                                </li>
                            </div>
                        </c:forEach>
                        </div>
                    </ul>
                </div>
            </div>
        </div>
<%@ include file="footer.jsp" %>