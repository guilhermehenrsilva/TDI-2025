<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
	<title>Facebook</title>
</head>

<body>
	<div class="container">
		<div class="row pt-5">
			<div class="col-md-1"></div>
			<div class="col-md-10">

				<a href="index.jsp" class="btn btn-secondary">
					<i class="bi bi-house"></i>
				</a>

				<div class="d-flex justify-content-between align-items-center mb-3">
					<h1 class="m-0">Posts</h1>
					<a href="${pageContext.request.contextPath}/post/create" class="btn btn-primary">Novo Post</a>
				</div>

				<table class="table table-hover">
					<thead>
						<tr>
							<th scope="col">Id</th>
							<th scope="col">Título</th>
							<th scope="col">Autor</th>
							<th scope="col">Data</th>
							<th scope="col">Editar</th>
							<th scope="col">Remover</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="post" items="${posts}">
							<tr>
								<td>${post.id}</td>
								<td>${post.content}</td>
								<td>${post.user.name}</td>
								<td><fmt:formatDate value="${post.postDate}" pattern="dd/MM/YYYY"/></td>
								<td>
									<a class="bi bi-pencil-square" href="${pageContext.request.contextPath}/post/update?postId=${post.id}"></a>
								</td>
								<td>
									<a class="bi bi-trash" href="${pageContext.request.contextPath}/post/delete?postId=${post.id}"></a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
			<div class="col-md-1"></div>
		</div>
	</div>
</body>
</html>
