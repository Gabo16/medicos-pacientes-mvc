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

	<h1>Pacientes</h1>

	<c:if test="${ pacientes.getContent().size() > 0 }">
		<c:forEach var="paciente" items="${ pacientes.getContent() }">
			<p>${ paciente } Medico: [${ paciente.medico.id }] ${ paciente.medico.nombre } ${ paciente.medico.apPaterno
				} <a href="/pacientes/${ paciente.id }/eliminar">eliminar</a></p>
		</c:forEach>
	</c:if>

	<c:if test="${ pacientes.getContent().size() == 0 }">
		<p>No hay pacientes registrados en la base de datos</p>
	</c:if>

</body>

</html>