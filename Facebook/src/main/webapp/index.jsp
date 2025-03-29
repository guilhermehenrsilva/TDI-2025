<!DOCTYPE html>
<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/bootstrap-icons.css">
</head>
<body>
	<div class="container">
		<h1>usuarios</h1>
		<div class="row">
			<table class="table">
				<thead>
				  <tr>
					<th scope="col">id</th>
					<th scope="col">nome</th>
					<th scope="col">sexo</th>
					<th scope="col">email</th>
				  </tr>
				</thead>
				<tbody>
				  <c:forEach var="usuario" items="${usuarios}">
				    <tr>
				      <td>${usuario.getId()}</td>
				      <td>${usuario.getName()}</td>
				      <td>${usuario.getGender()}</td>
				      <td>${usuario.getEmail()}</td>
				    </tr>
				  </c:forEach>
				</tbody>
			  </table>

			  <a href="form_user.jsp" class="btn btn-primary">Novo Usuário</a>



		</div>
	</div>

	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
</body>
</html>