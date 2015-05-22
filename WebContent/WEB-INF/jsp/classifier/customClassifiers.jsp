<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="pt-BR" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Portal LOA</title>
	
	<link rel="shortcut icon" href="<c:url value="/img/favicon.ico" />" type="image/x-icon"/>
	<link rel="stylesheet" href="<c:url value="/css/foundation.css" />" />
	<link rel="stylesheet" href="<c:url value="/css/default.css" />" />
	
	
</head>

<body>
	
	<!-- Nav Bar -->
<!--     <div id="header" style="background-color:#00500F"> -->
<!-- 		<div id="barra-brasil"> -->
<!-- 			<div class="barra"> -->
<!-- 				<ul> -->
<!-- 					<li><a title="Acesso Ã  informação" class="ai" -->
<!-- 						href="http://www.acessoainformacao.gov.br">www.sic.gov.br</a></li> -->
<!-- 					<li><a title="Portal de Estado do Brasil" class="brasilgov" -->
<!-- 						href="http://www.brasil.gov.br">www.brasil.gov.br</a></li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
	</br></br>
	<!-- End Nav -->
 	
	<!-- Main Page Content and Sidebar -->
	<div class="row">

		<!-- Sidebar -->
		<aside class="large-2 columns">
			<h5>Classificações Orçamentárias</h5>
			<br />
			<ul class="side-nav">
				<c:forEach items="${enumList}" var="classifierEnum">
					<li class="menu-lateral-classificadores">
						<a href="<c:url value="/classificador/${classifierEnum.id}/${defaultYear}" />" >${classifierEnum.name}</a>
					</li>
					<li class="divider"></li>
				</c:forEach>
			</ul>
		</aside>
		<!-- End Sidebar -->

		<!-- Main Blog Content -->
		<div class="large-10 columns" role="content">
			
			<div id="modalTextFilter" class="reveal-modal small" data-reveal aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
				<h2 id="modalTitle">Filtragem textual</h2>
				<h6 class="subheader"> Deixei em branco caso não deseje filtragem.</h6>
				
				<form id="form" action="<c:url value="/classificadores/busca"/>" method="post">
					<div id="textFilterInputsContainer" class="row"> </div>
					
					<div id="hiddenClassifiers"></div>
					<input type="hidden" name="year" value="${defaultYear}">
					
					<div id="submitContainer" class="row">
						<div class="small-11 columns">
							<input type="submit" class="button" value="Pesquisar">
						</div>
					</div>
				</form>
				
				<a id="dismissFilterButton" class="close-reveal-modal" aria-label="Close">&#215;</a>
			</div>
			
			<article>
			<h2><strong>Consulta Personalizada</strong></h2>
			<hr/>
			
			<h6 class="subheader"><em><strong>Arraste os itens para a coluna à direita e monte sua consulta com os critérios de classificação desejados</strong></em></h6>
			</br></br>
			
			<div class="large-3 large-offset-1 columns">
				<div class="row">
					<div class="large-8 large-centered columns panel callout radius">
						<h6 align="center">Classificadores Disponíveis</h6>
					</div>	
				</div>
				<div class="row">
					<div class="large-8 large-centered columns">
						<ul id="availableClassifiers" class="sortableList classifierList">
							<c:forEach items="${enumList}" var="classifier">
								<li data-value="${classifier.id}">
									<a class="button tiny expand radius" >${classifier.name}</a>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>		
			</div>
			
			<div class="large-3 columns">
				<div class="row">
					<div class="large-8 large-centered columns panel callout radius">
						<h6 align="center">Classificadores Selecionados</h6>
					</div>	
				</div>
				<div class="row">
					<div class="large-8 large-centered columns">			
						<ul id="selectedClassifiers" class="sortableList classifierList">
						</ul>
					</div>
				</div>	
			</div>
			
			<div class="large-1 columns end">
				<a id="goFilterButton" class="button">Pesquisar</a>
			</div>
			
			</article>
		<!-- End Main Content -->		
		</div>
	</div>
	<!-- End Main Content and Sidebar -->
 
 
	<!-- Footer -->
	<footer class="row">
	  <div class="large-12 columns">
	    <hr />
	    <div class="row">
	      <div class="large-6 columns">
	        <p>© Copyright.</p>
	      </div>
	    </div>
	  </div>
	</footer>
	
	<script type="text/javascript" src="<c:url value="/js/jquery.js"/>" ></script>
	<script type="text/javascript" src="<c:url value="/js/jquery-ui-1.10.4.custom.js"/>" ></script>
	<script type="text/javascript" src="<c:url value="/js/modernizr.js"/>" ></script>
	<script type="text/javascript" src="<c:url value="/js/foundation.min.js"/>" ></script>
	<script type="text/javascript" src="<c:url value="/js/foundation/foundation.reveal.js"/>" ></script>
	<script type="text/javascript">
		var classifiersEnumDict = {}
		<c:forEach items="${enumList}" var="classifier">
		classifiersEnumDict["${classifier.id}"] = "${classifier.name}";
		</c:forEach>
		var classifiersArray = [];
		var classifiersJsonDict = {};
		var ajaxRequestsCount = 0;
		
		function putFilterOptions() {
			var allFilters = [];
			
			for(var i=0; i<classifiersArray.length; i++) {
				var filterSelect = $('<select>').attr({
									name: "idListCodeFilter["+i+"]"
									});

				filterSelect.append('<option value=""/>');

				var classifierJson = classifiersJsonDict[classifiersArray[i]]["list"];
				for(var j=0; j<classifierJson.length; j++) {
					var code = classifierJson[j]["code"];
					var label = classifierJson[j]["label"];
					
					filterSelect.append(
						'<option value="'+code+'">'+code+' - '+label+'</option>'
					);
				}
				
				allFilters.push(
					$('<div>').addClass('row').append(
						$('<div>').addClass('small-3 columns').append(
							$('<label>').addClass('right inline')
							.attr({
								for: "textFilter_" + classifiersArray[i]
							}).text(classifiersEnumDict[classifiersArray[i]] + ': ')
						)
					).append(
						$('<div>').addClass('small-9 columns').append(
							$('<div>').addClass('row collapse').append(
								$('<div>').addClass('small-8 columns').append(
									$('<input>').attr({
										type: "text",
										name: "idListTextFilter" + "["+i+"]",
										id: "textFilter_" +classifiersArray[i]
									})
								)
							).append(
								$('<div>').addClass('small-4 columns').append(
									filterSelect
								)
							)
						)
					)
				);
			}

			$('#loading').remove();

			allFilters.forEach(function(filter) {
				filter.appendTo('#textFilterInputsContainer')
			});
		}
		
		$(function() {
			$("#availableClassifiers, #selectedClassifiers").sortable({
				connectWith: ".classifierList"
			}).disableSelection();
		});

		$("#dismissFilterButton").click(function(){
			$('#modalTextFilter').foundation('reveal', 'close');
			
			setTimeout(function() {
				$('#textFilterInputsContainer').empty();    
			}, 300);
		});	
		
		$("#goFilterButton").click(function(){
			classifiersArray = [];
			ajaxRequestsCount = 0;
			classifiersJsonDict = {};
			
			$("ul#selectedClassifiers li").each(function(){
				classifiersArray.push($(this).data("value"));
			});
			
			if(classifiersArray.length == 0){
				alert("Escolha pelo menos um classificador");
				return;
			}

			$('#modalTextFilter').foundation('reveal', 'open');
			$('#textFilterInputsContainer').html('<img id="loading" src="<c:url value="/img/carregando.gif"/>" />');
			setTimeout(function(){
				classifiersArray.forEach(function(classifier) {
					var url = "/loa-portal/json/classificador/"+classifier+"/${defaultYear}";
					
				    $.ajax({
				        type: "GET",
				        dataType: "json",
				        url: url,
				        success: function(json) {
				        	classifiersJsonDict[classifier] = json;
				        	
				        	ajaxRequestsCount++;

				        	if (ajaxRequestsCount >= classifiersArray.length) {
				        		putFilterOptions();
						    }
				        }
				    });
				});
			}, 1000);
		});

		$("#form").submit(function(){
			//Fix Firefox Back Button Error
			for(var i=0; i<${enumSize}; i++){
				$('input[name="idList[' +i+ ']"]').remove();
			}
				
			for(var i=0; i<classifiersArray.length; i++){
				$('<input>').attr({
					type: "hidden",
					name: "idList" + "["+i+"]",
					value: classifiersArray[i]
				}).appendTo('#form');					
			}	
		});
	</script>
</body>
</html>