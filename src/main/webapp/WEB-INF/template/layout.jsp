<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
	<title><decorator:title default="TESTANDO-TESTE"/></title>
	
	<!-- Vendor -->
	<script type="text/javascript" src="<spring:url value="/includes/js/vendor/jquery.js" />" ></script>
	<script type="text/javascript" src="<spring:url value="/includes/js/vendor/jquery.inputmask.js" />" ></script>
	<script type="text/javascript" src="<spring:url value="/includes/js/vendor/jquery.blockUI.js" />" ></script>
	<script type="text/javascript" src="<spring:url value="/includes/js/vendor/jquery.placeholder.js" />" ></script>
	<script type="text/javascript" src="<spring:url value="/includes/js/vendor/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.js" />"></script>
	<script type="text/javascript" src="<spring:url value="/includes/js/vendor/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js" />"></script>
	
	<!-- Custom -->
	<script type="text/javascript" src="<spring:url value="/includes/js/wdev-datatypes.js" />" ></script>
	<script type="text/javascript" src="<spring:url value="/includes/js/wdev-ajax.js" />" ></script>
	<script type="text/javascript" src="<spring:url value="/includes/js/wdev-url.js" />" ></script>
	
	<!-- Css Bootstrap -->
	<link href="<spring:url value="/includes/bootstrap/css/bootstrap-theme.min.css" />" rel="stylesheet">
	<link href="<spring:url value="/includes/bootstrap/css/bootstrap.css" />" rel="stylesheet">
	<link href="<spring:url value="/includes/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">	
	<link href="<spring:url value="/includes/css/jquery-ui-1.10.3.custom/css/smoothness/jquery-ui-1.10.3.custom.css" />" rel="stylesheet">
	<link href="<spring:url value="/includes/css/jquery-ui-1.10.3.custom/css/smoothness/jquery-ui-1.10.3.custom.min.css" />" rel="stylesheet">
		
	<!-- Js Bootstrap -->
	<script type="text/javascript" src="<spring:url value="/includes/bootstrap/js/bootstrap.js" />" ></script>
	<script type="text/javascript" src="<spring:url value="/includes/bootstrap/js/bootstrap.min.js" />" ></script>
	
	<!-- CSS padrao -->
	<link href="<spring:url value="/includes/css/estilo.css" />" rel="stylesheet">
		
	<spring:url var="baseURL" value="/"/>
	<script>window.baseURL = "${baseURL}";</script>
	
</head>
<body>
	
	<div class="topo navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				HOME
				<br/>			
			</div>
		</div>
	</div>
	
	<br/>

	<ul id="menu-ul" class="nav nav-tabs">
		<li id="home"><a href="#">Home</a></li>
		<li id="empresa"><a href="#">Menu 1</a></li>
		<li id="parametro"><a href="#">Menu 2</a></li>
		<li id="produto"><a href="#">Menu 3</a></li>
		<li id="relatorio"><a href="#">Menu 4</a></li>			
		<li id="encerrar"><a href="#">Sair</a></li>
	</ul>
	
	<!-- Modal Sucesso -->
	<div class="modal fade" id="myModalSucess" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Sucesso!</h4>
				</div>
				<div id="myModalSucess-body" class="modal-body">
					<c:if test="${not empty requestScope.mensagensSucesso}">
						<c:forEach var="mensagem" items="${requestScope.mensagensSucesso}">
							<c:out value="${mensagem}" />
							<br />
						</c:forEach>
					</c:if>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Fechar</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	<!-- mostra a modal de erros -->
	<c:if test="${not empty requestScope.mensagensSucesso}">
		<script>
			$('#myModalSucess').modal('show');
		</script>
	</c:if>
	
	<!-- Modal Erro -->
	<div class="modal fade" id="myModalErros" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Problemas encontrados</h4>
				</div>
				<div id="myModalErros-body" class="modal-body">
					<c:if test="${not empty requestScope.mensagensErro}">
						<c:forEach var="mensagem" items="${requestScope.mensagensErro}">
							<c:out value="${mensagem}" />
							<br />
						</c:forEach>
					</c:if>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Fechar</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	<!-- mostra a modal de erros -->
	<c:if test="${not empty requestScope.mensagensErro}">
		<script>
			$('#myModalErros').modal('show');
		</script>
	</c:if>

	<br/>
	
	<decorator:body />
	
</body>
</html>