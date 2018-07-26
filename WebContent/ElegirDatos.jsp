<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<style type="text/css">
  body { background: #DAE8FC;}
  .os-percentage{color: white}
</style>
<h1 style="color: red">Página de elección de datos electorales</h1>

<p>Aquí proporcionamos la plantilla con los resultados de votos de 1982, 2015 y 2016 en la cual podemos cambiar los datos.</p>

<p><a href="CONGRESO1982.csv">Descargar datos 1982</a></p>
<p><a href="CONGRESO2015.csv">Descargar datos 2015</a></p>
<p><a href="CONGRESO2016.csv">Descargar datos 2016</a></p>


<p>Una vez modificados subir aquí dicho archivo para ver el resultado electoral resultante.</p>


<form action="ProcesaDatosServlet" method="post" enctype="multipart/form-data">
 <input type="file" name="file" />
 <input type="hidden" name="ano" value="${ano}"/>
  <input type="hidden" name="ley" value="${ley}"/>
  <input type="hidden" name="circu" value="${circu}"/>
 <button type="submit">Subir fichero</button>
</form>

</body>
</html>