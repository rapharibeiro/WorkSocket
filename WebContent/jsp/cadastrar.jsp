<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro</title>
</head>
<body>
	<h3>Cadastro Funcionário:</h3>
	<form action="Controler" method="post">
	
		Informe o nome: <input type="text" name="nome"/>
		<br /><br />
		Informe o email: <input type="text" name="email"/>
		<br /><br />
		Informe o sexo:
		<input type="radio" name="sexo" value="M">Masculino
 		<input type="radio" name="sexo" value="F">Feminino
 		<br /><br />
 		Estado: 
		<select name="estado">
			<option value="RJ">RJ</option>
			<option value="SP">SP</option>
			<option value="ES">ES</option>
			<option value="MG">MG</option>
		</select> 
 		<br /><br />
 		Informe a Cidade: <input type="text" name="cidade"/>
		<br /><br />
		Informe o logradouro: <input type="text" name="logradouro"/>
		<br /><br />
 		Informe o Salário: <input type="text" name="salario"/>
 		<br /><br />
		Informe a Admissão: <input type="text" name="admissao"/>
 		<br /><br />
 		Cargo: 
			<select name="cargo">
				<c:forEach items="${listaCargo }" var="cargo">
					<option value="${cargo.idCargo }" />
						${cargo.nomeCargo }
				</c:forEach>
			</select>
		<br /><br /> 
		<input type="hidden" name="q" value="inserir" />
		<input type="submit" value="Cadastrar Funcionario" />
	</form>
	${msg }
<a href="index.jsp">Voltar</a>
</body>
</html>