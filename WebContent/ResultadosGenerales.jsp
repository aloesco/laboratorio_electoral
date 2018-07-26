<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Resultados</title>

<link rel="stylesheet" type="text/css" href="main.css">


<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">

</head>
<body>

<style type="text/css">
  body { background: #DAE8FC;}
  .os-percentage{color: white}
</style>

<h1 style="margin-left: 20%; color: red">LEY ELEGIDA: ${ley}</h1>

<c:if test="${file == null}">
	<h2 style="margin-left: 5%;">AÑO ELEGIDO: ${ano}</h2>
</c:if>
<h2 style="margin-left: 5%;">CIRCUNSCRIPCIÓN ELEGIDA: ${circu}</h2>


<div class="container">
 <div class="row">
 
  <div class="col-sm-8">
   <table class="table-striped" style="width: 100%;">
   <tr>
    <th style="text-align:center;">PARTIDO</th>
    <th style="text-align:center;">VOTOS</th>
    <th style="text-align:center;">ESCAÑOS</th>
   </tr>
   <c:forEach items="${res}" var="resi">
		<tr>
			<td>${resi.nombre}</td>
			<td>${resi.votos}</td>
			<td>${resi.escannos}</td>
		</tr>
	</c:forEach>
   </table>
   
  </div>
  
  <div class="col-sm-4">
  
<form action="ComunidadesServlet">
 	<input type="hidden" name="ano" value="${ano}"/>
  	<input type="hidden" name="ley" value="${ley}"/>
  	<input type="hidden" name="circu" value="${circu}"/>
  	
  	<c:if test="${file == null}">
  	
 		<button type="submit" class="btn btn-danger">Comunidades</button>
 	</c:if>
 		
</form>
   <a href="LaboratorioElectoral.jsp" class="btn btn-danger">Volver</a>
  
  </div>
  
 </div>
</div>

<div class="container">
 <div class="row">
 
  <div class="col-sm-1">
   <ul class="os-percentages horizontal-list">
                          <li>
                              <p class="ios os scnd-font-color">${nombre1}</p>
                              <p class="os-percentage">${numero1p}<sup>%</sup></p>
                          </li>
                          <li>
                              <p class="mac os scnd-font-color">${nombre2}</p>
                              <p class="os-percentage">${numero2p}<sup>%</sup></p>
                          </li>
                          <li>
                              <p class="linux os scnd-font-color">${nombre3}</p>
                              <p class="os-percentage">${numero3p}<sup>%</sup></p>
                          </li>
                          <li>
                              <p class="win os scnd-font-color">${nombre4}</p>
                              <p class="os-percentage">${numero4p}<sup>%</sup></p>
                          </li>
                          <li>
                              <p class="wil os scnd-font-color">${nombre5}</p>
                              <p class="os-percentage">${numero5p}<sup>%</sup></p>
                          </li>
                      </ul>
  </div>
  
  
   <div class="col-sm-5 donut-chart-block block " style="margin-top:80px;"> <!-- DONUT CHART BLOCK (LEFT-CONTAINER) -->
                      <h2 class="titular">VOTOS</h2>
                      <div class="donut-chart">
                          <!-- DONUT-CHART by @kseso https://codepen.io/Kseso/pen/phiyL -->
                          <div id="porcion1" class="recorte" style="-webkit-transform: rotate(0deg); transform: rotate(0deg);"><div class="quesito ios" style="background-color: #6ec2f2; -webkit-transform: rotate(${numero1}deg); transform: rotate(${numero1}deg);" data-rel="21"></div></div>
                          <div id="porcion2" class="recorte" style="-webkit-transform: rotate(${numero1}deg); transform: rotate(${numero1}deg);"><div class="quesito mac" style="background-color: #E64C65; -webkit-transform: rotate(${numero2}deg); transform: rotate(${numero2}deg);" data-rel="39"></div></div>
                          <div id="porcion3" class="recorte" style="-webkit-transform: rotate(${numero2+numero1}deg); transform: rotate(${numero2+numero1}deg);"><div class="quesito linux" style="background-color: purple; -webkit-transform: rotate(${numero3}deg); transform: rotate(${numero3}deg);" data-rel="31"></div></div>
       
                          <div id="porcion4" class="recorte" style="-webkit-transform: rotate(${numero3+numero2+numero1}deg); transform: rotate(${numero3+numero2+numero1}deg);"><div class="quesito win" style="background-color: orange; -webkit-transform: rotate(${numero4}deg); transform: rotate(${numero4}deg);" data-rel="9"></div></div>
                          <div id="porcionFin" class="recorte" style="-webkit-transform: rotate(${numero3+numero2+numero1+numero4}deg); transform: rotate(${numero3+numero2+numero1+numero4}deg);"><div class="quesito wil" style="background-color: green; -webkit-transform: rotate(${numero5}deg); transform: rotate(${numero5}deg);" data-rel="9"></div></div>
                          
                          <!-- END DONUT-CHART by @kseso https://codepen.io/Kseso/pen/phiyL -->    
                          <p class="center-date" style="color:white;">AÑO<br><span class="center-date" style="color:white;">${ano}</span></p> 
                      </div>
                      
  </div>
  <div class="col-sm-1">
   <ul class="os-percentages horizontal-list">
                          <li>
                              <p class="ios os scnd-font-color">${nombre1}</p>
                              <p class="os-percentage">${numero1ep}<sup>%</sup></p>
                          </li>
                          <li>
                              <p class="mac os scnd-font-color">${nombre2}</p>
                              <p class="os-percentage">${numero2ep}<sup>%</sup></p>
                          </li>
                          <li>
                              <p class="linux os scnd-font-color">${nombre3}</p>
                              <p class="os-percentage">${numero3ep}<sup>%</sup></p>
                          </li>
                          <li>
                              <p class="win os scnd-font-color">${nombre4}</p>
                              <p class="os-percentage">${numero4ep}<sup>%</sup></p>
                          </li>
                          <li>
                              <p class="wil os scnd-font-color">${nombre5}</p>
                              <p class="os-percentage">${numero5ep}<sup>%</sup></p>
                          </li>
                      </ul>
  </div>
 
  <div class="col-sm-5 donut-chart-block block " style="margin-top:80px;"> <!-- DONUT CHART BLOCK (LEFT-CONTAINER) -->
                      <h2 class="titular">ESCAÑOS</h2>
                      <div class="donut-chart">
                          <!-- DONUT-CHART by @kseso https://codepen.io/Kseso/pen/phiyL -->
                          <div id="porcion1" class="recorte" style="-webkit-transform: rotate(0deg); transform: rotate(0deg);"><div class="quesito ios" style="background-color: #6ec2f2; -webkit-transform: rotate(${numero1e}deg); transform: rotate(${numero1e}deg);" data-rel="21"></div></div>
                          <div id="porcion2" class="recorte" style="-webkit-transform: rotate(${numero1e}deg); transform: rotate(${numero1e}deg);"><div class="quesito mac" style="background-color: #E64C65; -webkit-transform: rotate(${numero2e}deg); transform: rotate(${numero2e}deg);" data-rel="39"></div></div>
                          <div id="porcion3" class="recorte" style="-webkit-transform: rotate(${numero2e+numero1e}deg); transform: rotate(${numero2e+numero1e}deg);"><div class="quesito linux" style="background-color: purple; -webkit-transform: rotate(${numero3e}deg); transform: rotate(${numero3e}deg);" data-rel="31"></div></div>
       
                          <div id="porcion4" class="recorte" style="-webkit-transform: rotate(${numero3e+numero2e+numero1e}deg); transform: rotate(${numero3e+numero2e+numero1e}deg);"><div class="quesito win" style="background-color: orange; -webkit-transform: rotate(${numero4e}deg); transform: rotate(${numero4e}deg);" data-rel="9"></div></div>
                          <div id="porcionFin" class="recorte" style="-webkit-transform: rotate(${numero3e+numero2e+numero1e+numero4e}deg); transform: rotate(${numero3e+numero2e+numero1e+numero4e}deg);"><div class="quesito wil" style="background-color: green; -webkit-transform: rotate(${numero5e}deg); transform: rotate(${numero5e}deg);" data-rel="9"></div></div>
                          <!-- END DONUT-CHART by @kseso https://codepen.io/Kseso/pen/phiyL -->    
                          <p class="center-date" style="color:white;">AÑO<br><span class="center-date" style="color:white;">${ano}</span></p> 
                      </div>
                      
  </div>
 </div>
</div>


</body>
</html>