<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>
<html>

<!-- bootstrap just to have good looking page -->
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	type="text/css" rel="stylesheet" />

<head>
<title>Family Tree App</title>
</head>
<body>


<div class="container">

	<div class="jumbotron" style="background-color: #428bca"
		id="addNewPerson">
		<h1 style="color: white;">Family Tree App</h1>
		<div class="form-row align-items-center">
			<div class="col-auto panel" style="padding-left: 30px">
				<h3>Insert New Person</h3>
				<f:form class="form-inline" action="insertPerson"
					modelAttribute="person2">

					<f:label path="name">Person Name:</f:label>
					<f:input path="name" type="text" />
					<f:errors path="name" class="alert alert-danger"></f:errors>

					<f:errors class="alert alert-danger">
						<span class="alert alert-danger">${errors1}</span>
					</f:errors>

					<f:label path="birthDate">Date of birth:</f:label>
					<f:input type="date" value="1800-01-01" path="birthDate" />
					<f:errors path="birthDate" class="alert alert-danger"></f:errors>

					<f:label path="gender">Gender:</f:label>
					<f:radiobutton checked="checked" path="gender" value="male" />M
					<f:radiobutton path="gender" value="female" />F
					<button class="btn btn-primary" type="submit">Add Person</button>


				</f:form>

			</div>

			<div class="col-auto panel" style="padding-left: 30px">
				<h3>Set Relative</h3>
				<f:form class="form-inline" action="setRelative"
					modelAttribute="people">

					<label>Person:</label>
					<f:select cssStyle="width:150px" path="person1.name"
						items="${nameList}" multiple="false">
					</f:select>
					<f:errors path="person1.name" class="alert alert-danger"></f:errors>

					<f:label path="person2.name">Relative:</f:label>
					<f:select cssStyle="width:150px" path="person2.name"
						items="${nameList}" multiple="false">
					</f:select>
					<f:errors path="person2.name" class="alert alert-danger"></f:errors>

					<br>	
					<label>Person's Relation to Relative:</label>
					<f:select cssStyle="width:150px" path="person2.relations"
						items="${relationList}" multiple="false">
					</f:select>
					<button class="btn btn-primary" type="submit">Set
						relative</button>
					<f:errors class="alert alert-danger">
						<span class="alert alert-danger">${errors}</span>
					</f:errors>

				</f:form>

			</div>



			<div class="col-auto panel" style="padding-left: 30px">
				<h3>Get Person Tree</h3>
				<f:form class="form-inline" action="getPersonTree"
					modelAttribute="person1">

					<f:label path="name">Person Name:</f:label>
					<f:select cssStyle="width:150px" path="name" items="${nameList}"
						multiple="false">
					</f:select>
					<%-- 						<f:errors path="name" class="alert alert-danger"></f:errors> --%>

					<button class="btn btn-primary" type="submit">Get Person
						Tree</button>

				</f:form>
			</div>
			

			<div class="col-auto panel" style="padding-left: 30px">
				<h3>Delete Person</h3>
				<f:form class="form-inline" action="deletePerson"
					modelAttribute="person3">
					<f:label path="name">Person Name:</f:label>
					<f:select cssStyle="width:150px" path="name" items="${nameList}"
						multiple="false">
					</f:select>
					<f:errors path="name" class="alert alert-danger"></f:errors>

					<button class="btn btn-primary" type="submit">Delete
						Person</button>

				</f:form>

			</div>
			
			<div class="col-auto panel" style="padding-left: 30px">
				<h3>Clear Database</h3>
				<form action="clearDatabase" method="post">
					<input class="btn btn-primary" type="submit" value="ClearDatabase" />
				</form>
			</div>
			
			
		</div>
	</div>
</div>

</body>
</html>
