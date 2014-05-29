<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h3>Lista de Cliente</h3>
	
	<form action="Controler" method="post">
	<input type="hidden" name="q" value="consultar" />
	<input type="submit" value="Consultar" />
	</form>
	<hr />
	
	<c:if test="${fn:length(listafuncionario) > 0 }">
		<input type="hidden" name="q" value="consultar" />
		<table width="100%">
			<tr>
				<th>Nome</th>
				<th>Email</th>
				<th>Sexo</th>
				<th>Salário</th>
				<th>Admissão</th>
				<th></th>
			</tr>
	
			<c:forEach items="${listafuncionario }" var="f">
				<tr>
					<td>${f.nome }</td>
					<td>${f.email }</td>
					<td>${f.sexo }</td>
					<td>${f.salario }</td>
					<td>${f.admissao }</td>
					<td>
					<a href="Controler?q=editar&id=${f.idFuncionario }"> EDITAR</a>  ||
					<a href="Controler?q=excluir&id=${f.idFuncionario }">EXCLUIR</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<a href="index.jsp">Voltar</a>
</body>
</html>