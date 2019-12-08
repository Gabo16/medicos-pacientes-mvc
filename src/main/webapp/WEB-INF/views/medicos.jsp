<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="ISO-8859-1">
	<title>Insert title here</title>
</head>

<body>

	<h1>Médicos</h1>

	<c:if test="${ medicos.size() > 0 }">
		<c:forEach var="medico" items="${ medicos }">
			<p>${ medico } <a href="/medicos/${ medico.id }/eliminar">eliminar</a> </p>
		</c:forEach>
	</c:if>

	<c:if test="${ medicos.size() == 0 }">
		<p>No hay m�dicos registrados en la base de datos</p>
	</c:if>


</body>

</html>