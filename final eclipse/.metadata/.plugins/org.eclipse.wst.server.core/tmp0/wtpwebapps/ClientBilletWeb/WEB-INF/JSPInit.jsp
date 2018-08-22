<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>
        <h1>Bonjour, ${firstname}!</h1>
<c:choose>
    <c:when test="${not empty msgErreur}">
        <h2>Malheureusement une erreur est survenue...</h2>
        <p>Il n'a pas été possible de créer un caddie pour vous.
        </p>
        <h3>Erreur survenue :</h3>
        <p>${msgErreur}</p>
        <p>
            Appuyez sur le bouton suivant pour retenter : <br>
            <a href="init" class="btn btn-primary active">
                <i class="glyphicon glyphicon-repeat" aria-hidden="true"></i>
                Réessayer
            </a>
        </p>
    </c:when>
    <c:otherwise>
        <h3>Votre caddie est prêt à être utilisé!</h3>
        <a href="caddie" class="btn btn-primary active">
            <i class="glyphicon glyphicon-arrow-right" aria-hidden="true"></i>
            Continuer
        </a>
    </c:otherwise>
</c:choose>
<%@ include file="footer.jsp" %>