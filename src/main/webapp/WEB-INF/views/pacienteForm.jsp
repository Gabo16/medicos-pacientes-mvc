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
	<h1>Paciente Form</h1>

	<span>Formulario aqu�...</span>

	<c:if test="${ paciente.id > 0 }">
		<p> ${ paciente } </p>
		<button>Actualizar</button>
	</c:if>

	<c:if test="${ paciente.id == null }">
		<button>Guardar</button>
	</c:if>

	<c:if test="${errors.size() > 0}">
		<c:forEach var="error" items="${errors}">
			<p>
				<c:out value="${error}" />
			</p>
		</c:forEach>
	</c:if>

</body>

</html>