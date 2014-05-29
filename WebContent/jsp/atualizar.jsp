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
<h3>Atualizar dados</h3>
	
	<c:if test="${funcionario eq null }">
		<c:redirect url="/index.jsp" />
	</c:if>
	
		<form action="Controler" method="post">
			<input type="hidden" name="id" value="${funcionario.idFuncionario }" />
		
		Informe o nome: <input type="text" name="nome" value="${funcionario.nome }"/>
		<br /><br />
		Informe o email: <input type="text" name="email" value="${funcionario.email }"/>
		<br /><br />
		Informe o sexo:
		<input type="radio" name="sexo" value="${funcionario.sexo }">Masculino
 		<input type="radio" name="sexo" value="${funcionario.sexo }">Feminino
 		<br /><br />
 		Estado: 
		<c:if test="${fn:length(lista) > 0 }"> 
				<select name="estado">
					<option value="">--Selecione</option>
					<c:forEach items="${lista}" var="est">
						<option 
						<c:if test="${est eq funcionario.endereco.estado}">selected="selected"</c:if> 
						value="${est}">${est}</option>
					</c:forEach>
				</select>
			</c:if>
 		<br /><br />
 		Informe a Cidade: <input type="text" name="cidade" value="${funcionario.endereco.cidade }"/>
		<br /><br />
		Informe o logradouro: <input type="text" name="logradouro" value="${funcionario.endereco.logradouro }"/>
		<br /><br />
 		Informe o Salário: <input type="text" name="salario" value="${funcionario.salario }"/>
 		<br /><br />
		Informe a Admissão: <input type="text" name="admissao" value="<fmt:formatDate value="${funcionario.admissao }" pattern="dd/MM/yyyy" />"/>
 		<br /><br />
 		Cargo:
			<c:if test="${fn:length(listaCargo) > 0 }"> 
				<select name="cargo">
					<option value="">--Selecione</option>
					<c:forEach items="${listaCargo }" var="car">
						<option 
						<c:if test="${car.idCargo eq funcionario.cargo.idCargo }">selected="selected"</c:if> 
						value="${car.idCargo }">${car.nomeCargo }</option>
					</c:forEach>
				</select>
			</c:if>

		<br /><br /> 
		<input type="hidden" name="q" value="atualizar" />
		<input type="submit" value="Atualizar dados" />
	${msg }
	</form>
<a href="index.jsp">Voltar</a>
</body>
</html>