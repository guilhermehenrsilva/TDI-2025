<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<%@include file="base-head.jsp"%>
</head>
<body>
	<%@include file="nav-menu.jsp"%>
	
	<div id="container" class="container-fluid">
		<h3 class="page-header">${not empty called ? 'Atualizar' : 'Adicionar'} Chamado</h3>
		
		<form action="${pageContext.request.contextPath}/called/${action}" 
			method="POST">
			
			<input type="hidden" value="${called.getId()}" name="calledId">
			
			<div class="row">
				<div class="form-group col-md-6">
					<label for="titulo">Titulo</label>
					<input type="text" class="form-control" id="titulo" name="titulo" 
						   autofocus="autofocus" placeholder="Titulo do Chamado" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe o titulo do chamado')"
						   oninput="setCustomValidity('')"
						   value="${called.getTitulo()}" />
				</div>
				
				<div class="form-group col-md-6">
					<label for="descricao">Descrição</label>
					<input type="text" class="form-control" id="descricao" name="descricao" 
						   autofocus="autofocus" placeholder="Descrição" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe a descrição')"
						   oninput="setCustomValidity('')" 
						   value="${called.getDescricao()}"/>
				</div>				
			</div>
			
			<div class="row">
				<div class="form-group col-md-4">
					<label for="dataAbertura">Data de abertura</label>
					<input type="date" class="form-control" id="dataAbertura" name="dataAbertura" 
						   autofocus="autofocus" placeholder="Data de abertura" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe a data de abertura')"
						   oninput="setCustomValidity('')"
						   value="${called.getDataAbertura()}" />
				</div>
				
				<div class="form-group col-md-4">
					<label for="status">Status</label>
					<input type="text" class="form-control" id="status" name="status" 
						   autofocus="autofocus" placeholder="Status" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe o status')"
						   oninput="setCustomValidity('')" 
						   value="${called.getStatus()}"/>
				</div>
				
				<div class="form-group col-md-4">
					<label for="user">Usuário</label>
					<select id="user" class="form-control selectpicker" name="user" 
						    required oninvalid="this.setCustomValidity('Por favor, informe o usuário.')"
						    oninput="setCustomValidity('')">
					  
					  <option value="" ${not empty called ? "" : 'selected'} >
					  	Selecione um usuário
					  </option>
					  
					  <c:forEach var="user" items="${users}">
					  	<option value="${user.getId()}" 
					  		${called.getUser().getId() == user.getId() ? 
					  			'selected' : ''}
					  	>
					  		${user.getName()}
					  	</option>	
					  </c:forEach>
					</select>
				</div>
			</div>
			
			<hr />
			<div id="actions" class="row pull-right">
				<div class="col-md-12">
					
					<a href="${pageContext.request.contextPath}/calleds" 
					   class="btn btn-default">Cancelar</a>
					
					<button type="submit" 
						    class="btn btn-primary">${not empty company ? 'Atulizar' : 'Cadastrar' } Chamado</button>
				</div>
			</div>
		</form>
		
	</div>

</body>
</html>