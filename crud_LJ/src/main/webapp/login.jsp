<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Login - CRUD Manager</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/images/favicon2.ico" />
<style>
body {
	background-color: #f8f9fa;
}

.login-container {
	max-width: 400px;
	margin: 5% auto;
	padding: 2rem;
	background: white;
	border-radius: 8px;
	box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
}

.logo {
	width: 100px;
	margin-bottom: 1rem;
}
</style>
</head>
<body>
	<div class="login-container">
		<div class="text-center">
			<a href='https://postimages.org/' target='_blank'><img
				src='https://i.postimg.cc/sGXhx04b/logo.png' alt='logo' class="logo" /></a>
			<h2 class="mb-4">Entrar</h2>
		</div>
		<form action="login" method="post">
			<div class="mb-3">
				<label for="user_login" class="form-label">E-mail</label> <input
					type="text" class="form-control" id="user_login" name="user_login"
					placeholder="Digite seu e-mail" required>
			</div>
			<div class="mb-3">
				<label for="user_pw" class="form-label">Senha</label> <input
					type="password" class="form-control" id="user_pw" name="user_pw"
					placeholder="Digite sua senha" required>
			</div>
			<button type="submit" class="btn btn-danger pull-right w-100">Entrar</button>

			<%
                String message = (String) request.getAttribute("message");
                if (message != null) {
            %>
			<div class="alert alert-danger mt-3" role="alert">
				<%= message %>
			</div>
			<%
                }
            %>
		</form>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>