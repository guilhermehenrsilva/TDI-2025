<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="base-head.jsp"%>
    <title>Locadora de Jogos - Início</title>
    
</head>
<body>
<style>
        /* Estilos para os cards da página Home (antigo CSS, agora embutido) */
        .card {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
            text-align: center;
            box-shadow: 0 2px 4px rgba(0,0,0,.05);
            transition: transform .2s ease-in-out;
            height: 100%; /* Garante que cards na mesma linha tenham a mesma altura */
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }
        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 8px rgba(0,0,0,.1);
        }
        .card .glyphicon {
            font-size: 3em;
            margin-bottom: 10px;
            color: #000; /* Ícones pretos */
        }
        .card-title {
            font-size: 1.5em;
            margin-bottom: 10px;
        }
        .card-text {
            font-size: 0.9em;
            color: #555;
            flex-grow: 1;
        }
        .card-link {
            margin-top: 15px;
        }
    </style>
    <%@include file="nav-menu.jsp"%>
    
    <div id="container" class="container-fluid">
        <div class="row" style="margin-top: 20px;">
            <div class="col-sm-6 col-md-4 col-lg-3">
                <div class="card">
                    <span class="glyphicon glyphicon-user"></span>
                    <h4 class="card-title">Gerenciar Usuários</h4>
                    <p class="card-text">Cadastre e edite informações dos usuários do sistema.</p>
                    <a href="${pageContext.request.contextPath}/users" class="btn btn-danger card-link">Acessar Usuários</a>
                </div>
            </div>

            <div class="col-sm-6 col-md-4 col-lg-3">
                <div class="card">
                    <span class="glyphicon glyphicon-briefcase"></span>
                    <h4 class="card-title">Gerenciar Empresas</h4>
                    <p class="card-text">Mantenha o registro de empresas parceiras e clientes.</p>
                    <a href="${pageContext.request.contextPath}/companies" class="btn btn-danger card-link">Acessar Empresas</a>
                </div>
            </div>


            <div class="col-sm-6 col-md-4 col-lg-3">
                <div class="card">
                    <span class="glyphicon glyphicon-cd"></span>
                    <h4 class="card-title">Gerenciar Jogos</h4>
                    <p class="card-text">Adicione, edite e organize os jogos disponíveis para locação.</p>
                    <a href="${pageContext.request.contextPath}/jogos" class="btn btn-danger card-link">Acessar Jogos</a>
                </div>
            </div>

            <div class="col-sm-6 col-md-4 col-lg-3">
                <div class="card">
                    <span class="glyphicon glyphicon-pencil"></span>
                    <h4 class="card-title">Gerenciar Posts</h4>
                    <p class="card-text">Crie e visualize as publicações e notícias do sistema.</p>
                    <a href="${pageContext.request.contextPath}/post" class="btn btn-danger card-link">Acessar Posts</a>
                </div>
            </div>

        </div>
    </div>

</body>
</html>