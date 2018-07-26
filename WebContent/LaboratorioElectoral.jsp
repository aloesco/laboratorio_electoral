<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Laboratorio electoral</title>

<link rel="icon" href="laboratorio.jpg">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="main.css">
</head>

<body>
<div class="row">
	<div class="col-md-6">
		<img src="laboratorio.jpg" alt="Logo" class="flotanteizquierda">
		<h1>LABORATORIO ELECTORAL</h1>
		<p>Bienvenido al laboratorio electoral.<br>Elige el año y ley electoral, y la circunscripción que quieras aplicar.</p><br>
		<br>
	</div>
	<div class="col-md-6">
		<form action="LoginServlet">
		   <input name="email" placeholder="email"/>
		   <input type="password" name="password" placeholder="password"/>
		   <button type="submit" class="btn btn-danger">Editar datos</button>
		 </form>
		 <p>Si quieres analizar tus propios datos primero debes <a href="RegistrarseServlet">registrarte</a></p>
	</div>
</div>

<c:if test="${file != null}">
<p style="margin-left:15%"><strong>Fichero cargado  </strong><a href="BorraFicheroServlet">Quitar fichero</a></p>

<br>
</c:if>


<form action="RedireccionarServlet">
 <label>Provincia:
  <select name="ley" id="ley">
    <optgroup label = "Ley">
    <option value="D'Hont">Ley D'Hont</option>
    <option value="Saint Lagüe">Saint Lagüe</option>
  </select>
 </label>
 
 <c:if test="${file == null}">
 <label>Año:
  <select name="ano" id="ano">
    <optgroup label = "Año">
    <option value="2016">2016</option>
    <option value="2015">2015</option>
    <option value="1982">1982</option>
    
  </select>
 </label>
 </c:if>
 
 <label>Circunscripción:
  <select name="circu" id="circu">
    <optgroup label = "Circunscripción">
    <option value="provincia">Provincia</option>
    <option value="comunidad">Comunidad autónoma</option>
    <option value="pais">Única</option>
  </select>
 </label>
 
  <c:if test="${file == null}">
 
 <label>Estado:
  <select name="est" id="est">
    <optgroup label = "Estado">
    <option value="general">General</option>
    
    <option value="mapacom">Mapa comunidades</option>
  </select>
 </label>
 
 </c:if>
 
<br>
<button type="submit" class="btn btn-danger" style="margin-left:45%">Calcular</button>

</form> 

</body>
</html>

      