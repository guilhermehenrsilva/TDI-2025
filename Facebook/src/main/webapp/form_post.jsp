<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<title>Cadastro de Post</title>
</head>

<body>

	<div class="container">
		<div class="row">
			<div class="col-2"></div>

			<form action="/facebook/post/save" method="GET" class="col-8">

				<h1>Cadastro de Post</h1>

				<!-- Input escondido para ID do post -->
				<input type="hidden" id="post_id" name="post_id"
					value="${post != null ? post.id : ''}" />

				<!-- Campo para conteúdo do post -->
				<div class="mb-3">
					<label for="post_content_id" class="form-label">Conteúdo</label> <input
						type="text" id="post_content_id" name="post_content"
						class="form-control" value="${post != null ? post.content : ''}"
						required>
				</div>
					
				<div class="mb-3">
					<label for="user_id" class="form-label">Autor</label> <select
						id="user_id" name="user_id" class="form-select" required>
						<option value="">Selecione o autor</option>
						<c:if test="${not empty usuarios}">
							<c:forEach var="usuario" items="${usuarios}">
								<option value="${usuario.id}">${usuario.name}</option>
							</c:forEach>
						</c:if>
					</select>
				</div>

				<button type="submit" class="btn btn-primary">Publicar</button>
				<a href="${pageContext.request.contextPath}/posts"
					class="btn btn-secondary">Cancelar</a>
			</form>

			<div class="col-2"></div>
		</div>
	</div>

	<script
		src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>

</html>