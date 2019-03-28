<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
	<title>Delete Success</title>
</head>
<body>
<h1>
	Deleted Person ${person.name}  
</h1>

<h3> <a href="<spring:url value="/index"></spring:url>">Return to main page</a>  </h3>
</body>
</html>
