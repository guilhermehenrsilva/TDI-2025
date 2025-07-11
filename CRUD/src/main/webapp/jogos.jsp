<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="base-head.jsp"%>
    <title>CRUD Manager - Jogos</title>
</head>
<body>
    <%@include file="modal.html"%>
    <%@include file="nav-menu.jsp"%>
    
    <div id="container" class="container-fluid">
        <div id="alert" style="${not empty message ? 'display: block;' : 'display: none;'}" class="alert alert-dismissable ${alertType eq 1 ? 'alert-success' : 'alert-danger'}">
          <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
          ${message}
        </div>
        
        <div id="top" class="row">
 			<div class="col-md-3">
		        <h3>Jogos</h3>
		    </div>
		 
		    <div class="col-md-6">
		        <div class="input-group h2">
		        </div>
		    </div>
		 
		    <div class="col-md-3">
		        <a href="${pageContext.request.contextPath}/jogos/form" class="btn btn-danger pull-right h2">
                    <span class="glyphicon glyphicon-plus"></span>&nbspAdicionar Jogo
                </a>
		    </div>
     	</div>
     	
     	<hr />
     	
     	<div id="list" class="row">
     		<div class="table-responsive col-md-12">
		        <table class="table table-striped table-hover" cellspacing="0" cellpadding="0">
		            <thead>
		                <tr>
		                    
                            <th>Nome</th>
                            <th>Gênero</th>
                            <th>Plataforma</th>
                            <th>Ano</th>
                            <th>Preço (R$)</th>
                            <th>Desenvolvedora</th>
                            <th>Usuário Cadastrante</th>
		                    <th>Editar</th>
		                    <th>Excluir</th>
		                 </tr>
		            </thead>
		            <tbody>
		            	<c:forEach var="jogo" items="${jogos}">
							<tr>

                                <td><c:out value="${jogo.nome}"/></td>
                                <td><c:out value="${jogo.genero}"/></td>
                                <td><c:out value="${jogo.plataforma}"/></td>
                                <td><c:out value="${jogo.ano}"/></td>
                                <td><c:out value="${jogo.preco}"/></td>
                                <td><c:out value="${jogo.desenvolvedora}"/></td>
                                <td><c:out value="${jogo.usuario.name}"/></td>
			                    
			                    <td class="actions">
			                        <a class="btn btn-info btn-xs" <%-- ALTERADO AQUI: de btn-warning para btn-info --%>
			                           href="${pageContext.request.contextPath}/jogos/update?jogosId=${jogo.id}" >
			                           <span class="glyphicon glyphicon-edit"></span>
			                        </a>
			                    </td>
			                    
			                    <td class="actions">
			                        <a class="btn btn-danger btn-xs modal-remove"
			                           jogos-id="${jogo.id}"
			                           jogos-name="${jogo.nome}" data-toggle="modal"
			                           data-target="#delete-modal"  href="#"><span
			                           class="glyphicon glyphicon-trash"></span></a>
			                    </td>
			                </tr>
						</c:forEach>
		            </tbody>
		         </table>
		 
		     </div>
     	</div>
     	<div id="bottom" class="row">
     		<div class="col-md-12">
		        <ul class="pagination">
		            <li class="disabled"><a>&lt; Anterior</a></li>
		            <li class="disabled"><a>1</a></li>
		            <li><a href="#">2</a></li>
		            <li><a href="#">3</a></li>
		            <li class="next"><a href="#" rel="next">Próximo &gt;</a></li>
		        </ul>
		    </div>
     	</div>
	</div>
	
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
		    setTimeout(function() {
		        $("#alert").slideUp(500);
		    }, 3000);
		    
		    $(".modal-remove").click(function () {
	            var jogosName = $(this).attr('jogos-name');
	            var jogosId = $(this).attr('jogos-id');
                var entityName = "jogos";

	            $(".modal-body #hiddenValue").text("o jogo '"+jogosName+"'");
	            $("#id").attr( "value", jogosId);
	            $("#form").attr( "action","${pageContext.request.contextPath}/" + entityName + "/delete");
	        })
		});
	</script>
</body>
</html>