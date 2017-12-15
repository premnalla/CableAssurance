<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="css/styles.css" />
</head>
<body class="welcome">

<div class="center_para">
<form name='loginForm' action='caservlet/AuthServlet' method="post">
<table>
	<tr>
		<td>Username:</td>
		<td><input type="text" size="30" name="username" /></td>
	</tr>
	<tr>

		<td>Password:</td>
		<td><input type="password" size="30" name="password" /></td>
	</tr>
</table>
<p><input type="submit" value="Login" /></p>
</form>
</div>

</body>
</html>
