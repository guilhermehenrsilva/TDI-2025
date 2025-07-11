<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav id="menu" class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
		    <span class="sr-only">Toggle navigation</span>
		    <span class="icon-bar"></span>
		    <span class="icon-bar"></span>
		    <span class="icon-bar"></span>
		   </button>
			<a class="navbar-brand" href="${pageContext.request.contextPath}"
				> <span> <img height="30px" width="30px"
					alt="logo" src="https://i.postimg.cc/sGXhx04b/logo.png"> <strong>&nbsp🎮
						Locadora de Jogos</strong>
			</span>
			</a>

		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="${pageContext.request.contextPath}"><span class="glyphicon glyphicon-home" /><strong>&nbspInício</strong></a></li>
				<li><a href="${pageContext.request.contextPath}/users"><span class="glyphicon glyphicon-user" /><strong>&nbspUsuários</strong></a></li>
				<li><a href="${pageContext.request.contextPath}/companies"><span class="glyphicon glyphicon-pushpin" /><strong>&nbspEmpresas</strong></a></li>
				<li><a href="${pageContext.request.contextPath}/jogos"><span class="glyphicon glyphicon-cd" /><strong>&nbspJogos</strong></a></li>
				<li><a href="${pageContext.request.contextPath}/post"><span class="glyphicon glyphicon-pencil" /><strong>&nbspPosts</strong></a></li> 
				<li><a href="${pageContext.request.contextPath}/logout"><span class="glyphicon glyphicon-log-out" /><strong>&nbspSair</strong></a></li>
			</ul>
		</div>
	</div>
</nav>
<br /><br /><br /> 
