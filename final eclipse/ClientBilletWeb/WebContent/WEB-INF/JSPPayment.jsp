<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>
        <div class="container">
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 col-xs-10 col-xs-offset-1 well">
                    <form id="formulaire" method="POST" action="pay?action=validatePayment">
                        <div class="form-group" style="text-align: center;">
                            <h1 id="titre" class="form-signin-heading">Caddie virtuel</h1>
                            <h4 class="form-signin-heading text-muted">Payer</h4>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Prix total : </label>
                            <div class="col-sm-6">
                                <p class="form-control-static">${totalPrice} &#8364;</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="paymentType">Type de payement</label>
                            <select name="paymentType" class="form-control">
                                <option selected="">Carte de Cr&#233;dit</option>
                                <option>Carte de Banque</option>
                                <option>Virement</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <button class="btn btn-lg btn-primary btn-block" type="submit">Payer</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
<%@ include file="footer.jsp" %>