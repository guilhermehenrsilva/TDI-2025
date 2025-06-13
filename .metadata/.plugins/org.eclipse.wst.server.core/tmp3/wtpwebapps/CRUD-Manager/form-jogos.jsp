<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<%@include file="base-head.jsp"%>
    <title>CRUD Manager - Formulário de Jogos</title>
</head>
<body>
	<%@include file="nav-menu.jsp"%>
	
	<div id="container" class="container-fluid">
		<h3 class="page-header">
            <c:choose>
                <c:when test="${action eq 'insert'}">
                    Cadastrar Jogo
                </c:when>
                <c:otherwise>
                    Atualizar Jogo
                </c:otherwise>
            </c:choose>
        </h3>
		
		<form action="${pageContext.request.contextPath}/jogos/${action}" 
			method="POST">
			
			<c:if test="${action eq 'update'}">
                <input type="hidden" name="jogosId" value="${jogos.id}">
            </c:if>
			
			<div class="row">
				<div class="form-group col-md-6">
					<label for="nome">Nome</label>
					<input type="text" class="form-control" id="nome" name="nome" 
						   autofocus="autofocus" placeholder="Nome do Jogo" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe o nome do jogo.')"
						   oninput="setCustomValidity('')"
						   value="${jogos.nome}" />
				</div>
				
				<div class="form-group col-md-6">
					<label for="genero">Gênero</label>
					<select class="form-control" id="genero" name="genero" 
						    required oninvalid="this.setCustomValidity('Por favor, selecione o gênero.')"
						    oninput="setCustomValidity('')">
					  <option value="">Selecione um Gênero</option>
                      <option value="Acao" <c:if test="${jogos.genero eq 'Acao'}">selected</c:if>>Ação</option>
                      <option value="Aventura" <c:if test="${jogos.genero eq 'Aventura'}">selected</c:if>>Aventura</option>
                      <option value="RPG" <c:if test="${jogos.genero eq 'RPG'}">selected</c:if>>RPG</option>
                      <option value="Esporte" <c:if test="${jogos.genero eq 'Esporte'}">selected</c:if>>Esporte</option>
                      <option value="Estrategia" <c:if test="${jogos.genero eq 'Estrategia'}">selected</c:if>>Estratégia</option>
                      <option value="Simulacao" <c:if test="${jogos.genero eq 'Simulacao'}">selected</c:if>>Simulação</option>
                      <option value="Terror" <c:if test="${jogos.genero eq 'Terror'}">selected</c:if>>Terror</option>
                      <option value="Puzzle" <c:if test="${jogos.genero eq 'Puzzle'}">selected</c:if>>Puzzle</option>
                      <option value="Corrida" <c:if test="${jogos.genero eq 'Corrida'}">selected</c:if>>Corrida</option>
                      <option value="Luta" <c:if test="${jogos.genero eq 'Luta'}">selected</c:if>>Luta</option>
                      <option value="Musical" <c:if test="${jogos.genero eq 'Musical'}">selected</c:if>>Musical</option>
                      <option value="Casual" <c:if test="${jogos.genero eq 'Casual'}">selected</c:if>>Casual</option>
                      <option value="Indie" <c:if test="${jogos.genero eq 'Indie'}">selected</c:if>>Indie</option>
                      <option value="Outro" <c:if test="${jogos.genero eq 'Outro'}">selected</c:if>>Outro</option>
					</select>
				</div>				
			</div>
			
			<div class="row">
                <div class="form-group col-md-4">
                    <label for="plataforma">Plataforma</label>
                    <select class="form-control" id="plataforma" name="plataforma" 
                            required oninvalid="this.setCustomValidity('Por favor, selecione a plataforma.')"
                            oninput="setCustomValidity('')">
                        <option value="">Selecione uma Plataforma</option>
                        <option value="PC" <c:if test="${jogos.plataforma eq 'PC'}">selected</c:if>>PC</option>
                        <option value="PlayStation" <c:if test="${jogos.plataforma eq 'PlayStation'}">selected</c:if>>PlayStation</option>
                        <option value="Xbox" <c:if test="${jogos.plataforma eq 'Xbox'}">selected</c:if>>Xbox</option>
                        <option value="Nintendo Switch" <c:if test="${jogos.plataforma eq 'Nintendo Switch'}">selected</c:if>>Nintendo Switch</option>
                        <option value="Mobile" <c:if test="${jogos.plataforma eq 'Mobile'}">selected</c:if>>Mobile</option>
                        <option value="Outra" <c:if test="${jogos.plataforma eq 'Outra'}">selected</c:if>>Outra</option>
                    </select>
                </div>
				<div class="form-group col-md-4">
					<label for="ano">Ano de Lançamento</label>
					<input type="number" class="form-control" id="ano" name="ano" 
						   placeholder="Ano de Lançamento"
						   oninvalid="this.setCustomValidity('Por favor, informe o ano de lançamento.')"
						   oninput="setCustomValidity('')"
						   value="${jogos.ano}" />
				</div>
				
				<div class="form-group col-md-4">
					<label for="preco">Preço de Locação (R$)</label>
					<input type="number" step="0.01" class="form-control" id="preco" name="preco" 
						   placeholder="0.00" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe o preço de locação.')"
						   oninput="setCustomValidity('')"
						   value="${jogos.preco}"/>
				</div>
			</div>

            <div class="row">
                <div class="form-group col-md-6">
                    <label for="desenvolvedora">Desenvolvedora</label>
                    <input type="text" class="form-control" id="desenvolvedora" name="desenvolvedora" 
                           placeholder="Nome da Desenvolvedora" 
                           required 
                           oninvalid="this.setCustomValidity('Por favor, informe a desenvolvedora.')"
                           oninput="setCustomValidity('')"
                           value="${jogos.desenvolvedora}" />
                </div>
                
                <div class="form-group col-md-6">
					<label for="usuario">Usuário Cadastrante</label>
					<select id="usuario" class="form-control" name="usuario" 
						    required oninvalid="this.setCustomValidity('Por favor, informe o usuário cadastrante.')"
						    oninput="setCustomValidity('')">
					  
					  <option value="" ${empty jogos || empty jogos.usuario ? 'selected' : ''}>
					  	Selecione um usuário
					  </option>
					  
					  <c:forEach var="user" items="${users}">
					  	<option value="${user.id}" 
					  		<c:if test="${not empty jogos.usuario && jogos.usuario.id == user.id}">selected</c:if>>
					  		${user.name}
					  	</option>	
					  </c:forEach>
					</select>
				</div>
            </div>
			
			<hr />
			<div id="actions" class="row pull-right">
				<div class="col-md-12">
					
					<a href="${pageContext.request.contextPath}/jogos" 
					   class="btn btn-default">Cancelar</a>
					
					<button type="submit" 
						    class="btn btn-primary"> 
                            <c:choose>
                                <c:when test="${action eq 'insert'}">
                                    Cadastrar Jogo
                                </c:when>
                                <c:otherwise>
                                    Atualizar Jogo
                                </c:otherwise>
                            </c:choose>
                        </button>
				</div>
			</div>
		</form>
		
	</div>
</body>
</html>