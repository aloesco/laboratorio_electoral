<!DOCTYPE html>
<html>
<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<%@page contentType="text/html"%>
	<%@page pageEncoding="UTF-8"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
	<title>Mapa Comunidades</title>
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
	
	
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
	<script type="text/javascript" >
	
	(function($) {
		var has_VML, has_canvas, create_canvas_for, add_shape_to, clear_canvas, shape_from_area,
			canvas_style, hex_to_decimal, css3color, is_image_loaded, options_from_area;

		has_canvas = !!document.createElement('canvas').getContext;

		// VML: more complex
		has_VML = (function() {
			var a = document.createElement('div');
			a.innerHTML = '<v:shape id="vml_flag1" adj="1" />';
			var b = a.firstChild;
			b.style.behavior = "url(#default#VML)";
			return b ? typeof b.adj == "object": true;
		})();

		if(!(has_canvas || has_VML)) {
			$.fn.maphilight = function() { return this; };
			return;
		}
		
		if(has_canvas) {
			hex_to_decimal = function(hex) {
				return Math.max(0, Math.min(parseInt(hex, 16), 255));
			};
			css3color = function(color, opacity) {
				return 'rgba('+hex_to_decimal(color.substr(0,2))+','+hex_to_decimal(color.substr(2,2))+','+hex_to_decimal(color.substr(4,2))+','+opacity+')';
			};
			create_canvas_for = function(img) {
				var c = $('<canvas style="width:'+img.width+'px;height:'+img.height+'px;"></canvas>').get(0);
				c.getContext("2d").clearRect(0, 0, c.width, c.height);
				return c;
			};
			var draw_shape = function(context, shape, coords, x_shift, y_shift) {
				x_shift = x_shift || 0;
				y_shift = y_shift || 0;
				
				context.beginPath();
				if(shape == 'rect') {
					// x, y, width, height
					context.rect(coords[0] + x_shift, coords[1] + y_shift, coords[2] - coords[0], coords[3] - coords[1]);
				} else if(shape == 'poly') {
					context.moveTo(coords[0] + x_shift, coords[1] + y_shift);
					for(i=2; i < coords.length; i+=2) {
						context.lineTo(coords[i] + x_shift, coords[i+1] + y_shift);
					}
				} else if(shape == 'circ') {
					// x, y, radius, startAngle, endAngle, anticlockwise
					context.arc(coords[0] + x_shift, coords[1] + y_shift, coords[2], 0, Math.PI * 2, false);
				}
				context.closePath();
			}
			add_shape_to = function(canvas, shape, coords, options, name) {
				var i, context = canvas.getContext('2d');
				
				// Because I don't want to worry about setting things back to a base state
				
				// Shadow has to happen first, since it's on the bottom, and it does some clip /
				// fill operations which would interfere with what comes next.
				if(options.shadow) {
					context.save();
					if(options.shadowPosition == "inside") {
						// Cause the following stroke to only apply to the inside of the path
						draw_shape(context, shape, coords);
						context.clip();
					}
					
					// Redraw the shape shifted off the canvas massively so we can cast a shadow
					// onto the canvas without having to worry about the stroke or fill (which
					// cannot have 0 opacity or width, since they're what cast the shadow).
					var x_shift = canvas.width * 100;
					var y_shift = canvas.height * 100;
					draw_shape(context, shape, coords, x_shift, y_shift);
					
					context.shadowOffsetX = options.shadowX - x_shift;
					context.shadowOffsetY = options.shadowY - y_shift;
					context.shadowBlur = options.shadowRadius;
					context.shadowColor = css3color(options.shadowColor, options.shadowOpacity);
					
					// Now, work out where to cast the shadow from! It looks better if it's cast
					// from a fill when it's an outside shadow or a stroke when it's an interior
					// shadow. Allow the user to override this if they need to.
					var shadowFrom = options.shadowFrom;
					if (!shadowFrom) {
						if (options.shadowPosition == 'outside') {
							shadowFrom = 'fill';
						} else {
							shadowFrom = 'stroke';
						}
					}
					if (shadowFrom == 'stroke') {
						context.strokeStyle = "rgba(0,0,0,1)";
						context.stroke();
					} else if (shadowFrom == 'fill') {
						context.fillStyle = "rgba(0,0,0,1)";
						context.fill();
					}
					context.restore();
					
					// and now we clean up
					if(options.shadowPosition == "outside") {
						context.save();
						// Clear out the center
						draw_shape(context, shape, coords);
						context.globalCompositeOperation = "destination-out";
						context.fillStyle = "rgba(0,0,0,1);";
						context.fill();
						context.restore();
					}
				}
				
				context.save();
				
				draw_shape(context, shape, coords);
				
				// fill has to come after shadow, otherwise the shadow will be drawn over the fill,
				// which mostly looks weird when the shadow has a high opacity
				if(options.fill) {
					context.fillStyle = css3color(options.fillColor, options.fillOpacity);
					context.fill();
				}
				// Likewise, stroke has to come at the very end, or it'll wind up under bits of the
				// shadow or the shadow-background if it's present.
				if(options.stroke) {
					context.strokeStyle = css3color(options.strokeColor, options.strokeOpacity);
					context.lineWidth = options.strokeWidth;
					context.stroke();
				}
				
				context.restore();
				
				if(options.fade) {
					$(canvas).css('opacity', 0).animate({opacity: 1}, 100);
				}
			};
			clear_canvas = function(canvas) {
				canvas.getContext('2d').clearRect(0, 0, canvas.width,canvas.height);
			};
		} else {   // ie executes this code
			create_canvas_for = function(img) {
				return $('<var style="zoom:1;overflow:hidden;display:block;width:'+img.width+'px;height:'+img.height+'px;"></var>').get(0);
			};
			add_shape_to = function(canvas, shape, coords, options, name) {
				var fill, stroke, opacity, e;
				for (var i in coords) { coords[i] = parseInt(coords[i], 10); }
				fill = '<v:fill color="#'+options.fillColor+'" opacity="'+(options.fill ? options.fillOpacity : 0)+'" />';
				stroke = (options.stroke ? 'strokeweight="'+options.strokeWidth+'" stroked="t" strokecolor="#'+options.strokeColor+'"' : 'stroked="f"');
				opacity = '<v:stroke opacity="'+options.strokeOpacity+'"/>';
				if(shape == 'rect') {
					e = $('<v:rect name="'+name+'" filled="t" '+stroke+' style="zoom:1;margin:0;padding:0;display:block;position:absolute;left:'+coords[0]+'px;top:'+coords[1]+'px;width:'+(coords[2] - coords[0])+'px;height:'+(coords[3] - coords[1])+'px;"></v:rect>');
				} else if(shape == 'poly') {
					e = $('<v:shape name="'+name+'" filled="t" '+stroke+' coordorigin="0,0" coordsize="'+canvas.width+','+canvas.height+'" path="m '+coords[0]+','+coords[1]+' l '+coords.join(',')+' x e" style="zoom:1;margin:0;padding:0;display:block;position:absolute;top:0px;left:0px;width:'+canvas.width+'px;height:'+canvas.height+'px;"></v:shape>');
				} else if(shape == 'circ') {
					e = $('<v:oval name="'+name+'" filled="t" '+stroke+' style="zoom:1;margin:0;padding:0;display:block;position:absolute;left:'+(coords[0] - coords[2])+'px;top:'+(coords[1] - coords[2])+'px;width:'+(coords[2]*2)+'px;height:'+(coords[2]*2)+'px;"></v:oval>');
				}
				e.get(0).innerHTML = fill+opacity;
				$(canvas).append(e);
			};
			clear_canvas = function(canvas) {
				// jquery1.8 + ie7 
				var $html = $("<div>" + canvas.innerHTML + "</div>");
				$html.children('[name=highlighted]').remove();
				canvas.innerHTML = $html.html();
			};
		}
		
		shape_from_area = function(area) {
			var i, coords = area.getAttribute('coords').split(',');
			for (i=0; i < coords.length; i++) { coords[i] = parseFloat(coords[i]); }
			return [area.getAttribute('shape').toLowerCase().substr(0,4), coords];
		};

		options_from_area = function(area, options) {
			var $area = $(area);
			return $.extend({}, options, $.metadata ? $area.metadata() : false, $area.data('maphilight'));
		};
		
		is_image_loaded = function(img) {
			if(!img.complete) { return false; } // IE
			if(typeof img.naturalWidth != "undefined" && img.naturalWidth === 0) { return false; } // Others
			return true;
		};

		canvas_style = {
			position: 'absolute',
			left: 0,
			top: 0,
			padding: 0,
			border: 0
		};
		
		var ie_hax_done = false;
		$.fn.maphilight = function(opts) {
			opts = $.extend({}, $.fn.maphilight.defaults, opts);
			
			if(!has_canvas && !ie_hax_done) {
				$(window).ready(function() {
					document.namespaces.add("v", "urn:schemas-microsoft-com:vml");
					var style = document.createStyleSheet();
					var shapes = ['shape','rect', 'oval', 'circ', 'fill', 'stroke', 'imagedata', 'group','textbox'];
					$.each(shapes,
						function() {
							style.addRule('v\\:' + this, "behavior: url(#default#VML); antialias:true");
						}
					);
				});
				ie_hax_done = true;
			}
			
			return this.each(function() {
				var img, wrap, options, map, canvas, canvas_always, mouseover, highlighted_shape, usemap;
				img = $(this);

				if(!is_image_loaded(this)) {
					// If the image isn't fully loaded, this won't work right.  Try again later.
					return window.setTimeout(function() {
						img.maphilight(opts);
					}, 200);
				}

				options = $.extend({}, opts, $.metadata ? img.metadata() : false, img.data('maphilight'));

				// jQuery bug with Opera, results in full-url#usemap being returned from jQuery's attr.
				// So use raw getAttribute instead.
				usemap = img.get(0).getAttribute('usemap');

				if (!usemap) {
					return
				}

				map = $('map[name="'+usemap.substr(1)+'"]');

				if(!(img.is('img,input[type="image"]') && usemap && map.size() > 0)) {
					return;
				}

				if(img.hasClass('maphilighted')) {
					// We're redrawing an old map, probably to pick up changes to the options.
					// Just clear out all the old stuff.
					var wrapper = img.parent();
					img.insertBefore(wrapper);
					wrapper.remove();
					$(map).unbind('.maphilight').find('area[coords]').unbind('.maphilight');
				}

				wrap = $('<div></div>').css({
					display:'block',
					background:'url("'+this.src+'")',
					position:'relative',
					padding:0,
					width:this.width,
					height:this.height
					});
				if(options.wrapClass) {
					if(options.wrapClass === true) {
						wrap.addClass($(this).attr('class'));
					} else {
						wrap.addClass(options.wrapClass);
					}
				}
				img.before(wrap).css('opacity', 0).css(canvas_style).remove();
				if(has_VML) { img.css('filter', 'Alpha(opacity=0)'); }
				wrap.append(img);
				
				canvas = create_canvas_for(this);
				$(canvas).css(canvas_style);
				canvas.height = this.height;
				canvas.width = this.width;
				
				mouseover = function(e) {
					var shape, area_options;
					area_options = options_from_area(this, options);
					if(
						!area_options.neverOn
						&&
						!area_options.alwaysOn
					) {
						shape = shape_from_area(this);
						add_shape_to(canvas, shape[0], shape[1], area_options, "highlighted");
						if(area_options.groupBy) {
							var areas;
							// two ways groupBy might work; attribute and selector
							if(/^[a-zA-Z][\-a-zA-Z]+$/.test(area_options.groupBy)) {
								areas = map.find('area['+area_options.groupBy+'="'+$(this).attr(area_options.groupBy)+'"]');
							} else {
								areas = map.find(area_options.groupBy);
							}
							var first = this;
							areas.each(function() {
								if(this != first) {
									var subarea_options = options_from_area(this, options);
									if(!subarea_options.neverOn && !subarea_options.alwaysOn) {
										var shape = shape_from_area(this);
										add_shape_to(canvas, shape[0], shape[1], subarea_options, "highlighted");
									}
								}
							});
						}
						// workaround for IE7, IE8 not rendering the final rectangle in a group
						if(!has_canvas) {
							$(canvas).append('<v:rect></v:rect>');
						}
					}
				}

				$(map).bind('alwaysOn.maphilight', function() {
					// Check for areas with alwaysOn set. These are added to a *second* canvas,
					// which will get around flickering during fading.
					if(canvas_always) {
						clear_canvas(canvas_always);
					}
					if(!has_canvas) {
						$(canvas).empty();
					}
					$(map).find('area[coords]').each(function() {
						var shape, area_options;
						area_options = options_from_area(this, options);
						if(area_options.alwaysOn) {
							if(!canvas_always && has_canvas) {
								canvas_always = create_canvas_for(img[0]);
								$(canvas_always).css(canvas_style);
								canvas_always.width = img[0].width;
								canvas_always.height = img[0].height;
								img.before(canvas_always);
							}
							area_options.fade = area_options.alwaysOnFade; // alwaysOn shouldn't fade in initially
							shape = shape_from_area(this);
							if (has_canvas) {
								add_shape_to(canvas_always, shape[0], shape[1], area_options, "");
							} else {
								add_shape_to(canvas, shape[0], shape[1], area_options, "");
							}
						}
					});
				});
				
				$(map).trigger('alwaysOn.maphilight').find('area[coords]')
					.bind('mouseover.maphilight', mouseover)
					.bind('mouseout.maphilight', function(e) { clear_canvas(canvas); });
				
				img.before(canvas); // if we put this after, the mouseover events wouldn't fire.
				
				img.addClass('maphilighted');
			});
		};
		$.fn.maphilight.defaults = {
			fill: true,
			fillColor: '000000',
			fillOpacity: 0.2,
			stroke: true,
			strokeColor: 'ff0000',
			strokeOpacity: 1,
			strokeWidth: 1,
			fade: true,
			alwaysOn: false,
			neverOn: false,
			groupBy: false,
			wrapClass: true,
			// plenty of shadow:
			shadow: false,
			shadowX: 0,
			shadowY: 0,
			shadowRadius: 6,
			shadowColor: 'FFFFFF',
			shadowOpacity: 0.8,
			shadowPosition: 'outside',
			shadowFrom: false
		};
	})(jQuery);

	
	</script>
	<script type="text/javascript" >
	
	//Función para la clase map
	$(function() {
		$('.map').maphilight(
	
		{
			fill: true,
			fillColor: '8FA4C9',
			strokeColor: '3B5998',
			
			/* Propiedades por defecto en maphilight
			* Si queremos cambiar alguna, descomentamos y le ponemos el valor que nos guste

			fillOpacity: 0.2,
			stroke: true,
			strokeOpacity: 1,
			strokeWidth: 1,
			fade: true,
			alwaysOn: false,
			neverOn: false,
			groupBy: false,
			wrapClass: true,
			shadow: false,
			shadowX: 0,
			shadowY: 0,
			shadowRadius: 6,
			shadowColor: '000000',
			shadowOpacity: 0.8,
			shadowPosition: 'outside',
			shadowFrom: false
			*/
			
			
        });
});
	
//Función para la clase map_islas
	$(function() {
		$('.map_islas').maphilight(
	
		{
			fill: true,
			strokeColor: '3B5998',

			/* Propiedades por defecto en maphilight
			* Si queremos cambiar alguna, descomentamos y le ponemos el valor que nos guste

			fillOpacity: 0.2,
			stroke: true,
			strokeOpacity: 1,
			strokeWidth: 1,
			fade: true,
			alwaysOn: false,
			neverOn: false,
			groupBy: false,
			wrapClass: true,
			shadow: false,
			shadowX: 0,
			shadowY: 0,
			shadowRadius: 6,
			shadowColor: '000000',
			shadowOpacity: 0.8,
			shadowPosition: 'outside',
			shadowFrom: false
			*/				

        });
});

	
	</script>
		
</head>
<h1 style="color: red">Vista por comunidades</h1>
<h2>Comunidad elegida: ${comunidad}</h2>



</head>

<body>
<style type="text/css">
  body { background: #DAE8FC;}
  .os-percentage{color: white}
</style>

<div class="row">

<div class ="col-md-5" style="margin-left:50px;">

<h3>Ley elegida: ${ley}</h3>
<c:if test="${file == null}">
	<h3>Año elegido: ${ano}</h3>
</c:if>
<h3>Circunscripción elegida: ${circu}</h3>



<img class="map" src="mapaes.jpg" width="300" height="264" usemap="#spain" border-style="dotted" border-width="100px">


<map id="spain" name="spain">
<area shape="poly" href="ComunidadesServlet?comunidad=Galicia&ley=${ley}&circu=${circu}&ano=${ano}" title="Galicia" coords="26,5,27,4,28,4,29,3,30,4,31,4,32,3,33,3,34,2,35,2,36,3,37,3,40,6,41,6,42,7,44,7,44,9,43,10,43,13,44,14,44,15,46,17,46,18,48,20,49,20,48,20,46,22,46,24,48,26,49,26,48,27,48,28,47,29,47,30,45,32,45,34,44,35,44,38,45,39,47,39,50,42,50,45,45,50,45,53,42,53,39,56,37,56,36,57,34,55,32,55,31,54,25,54,24,55,22,55,22,54,23,53,23,49,21,47,20,47,19,46,15,46,14,47,11,47,10,48,9,48,8,49,8,48,9,47,9,46,11,44,11,43,12,42,12,38,11,37,9,37,11,35,11,32,9,30,7,30,6,31,7,30,7,29,8,28,8,26,7,25,4,25,4,22,3,21,1,21,1,20,4,17,4,16,5,16,7,14,9,14,10,13,13,13,14,14,16,14,17,13,18,13,19,12,20,13,21,13,22,14,24,14,25,13,25,7,24,6,23,6,24,6,25,5" >
<area shape="poly" href="ComunidadesServlet?comunidad=Principado de Asturias&ley=${ley}&circu=${circu}&ano=${ano}" title="Asturias" coords="77,9,78,10,81,10,82,11,84,11,85,12,92,12,93,13,96,13,97,14,98,14,99,15,96,15,95,16,95,18,89,18,88,19,88,20,87,20,86,21,83,21,81,23,79,23,78,22,75,22,74,23,73,23,71,21,69,21,68,20,66,20,64,22,62,22,61,23,60,23,59,24,58,24,57,25,53,25,52,26,52,24,51,23,53,21,53,19,47,13,47,11,49,9,53,9,54,8,64,8,65,9,66,9,67,10,70,10,71,9,72,9,74,7,76,9" >
<area shape="poly" href="ComunidadesServlet?comunidad=Cantabria&ley=${ley}&circu=${circu}&ano=${ano}" title="Cantabria" coords="118,14,119,13,123,13,124,14,126,14,127,15,129,15,126,15,124,17,124,18,123,19,123,20,121,20,120,21,117,21,115,23,114,23,112,25,112,27,115,30,115,31,112,31,109,28,109,27,106,24,105,24,104,23,100,23,99,22,97,22,97,21,98,20,98,19,101,19,102,18,102,15,103,15,104,14" >
<area shape="poly" href="ComunidadesServlet?comunidad=País Vasco&ley=${ley}&circu=${circu}&ano=${ano}" title="Euskadi" coords="151,17,152,18,157,18,159,16,159,17,156,20,156,21,148,29,148,32,147,33,147,34,146,35,146,36,145,37,145,39,144,38,143,38,142,37,143,37,145,35,145,34,144,33,144,32,142,30,137,30,136,31,136,33,137,34,134,31,133,31,132,30,133,30,135,28,135,26,134,25,134,24,133,23,133,20,131,18,132,18,135,15,137,15,138,14,140,14,141,15,142,15,143,16,145,16,146,17" >
<area shape="poly" href="ComunidadesServlet?comunidad=Comunidad Foral de Navarra&ley=${ley}&circu=${circu}&ano=${ano}" title="Navarra" coords="167,20,173,26,176,26,177,27,180,27,179,28,179,29,178,30,178,31,176,33,175,33,170,38,170,39,169,40,169,42,168,43,168,45,167,46,167,50,168,51,168,53,169,54,167,56,165,56,164,55,162,55,162,53,163,52,163,49,156,42,155,42,154,41,153,41,152,40,149,40,148,39,149,38,149,37,150,36,150,35,151,34,151,32,152,31,152,30,160,22,160,21,163,18,166,18,167,19" >
<area shape="poly" href="ComunidadesServlet?comunidad=La Rioja&ley=${ley}&circu=${circu}&ano=${ano}" title="La Rioja" coords="145,42,146,43,149,43,150,44,152,44,154,46,155,46,159,50,159,51,158,52,158,55,155,55,155,54,154,53,154,52,153,51,152,51,151,50,150,50,149,49,146,49,143,52,143,53,142,54,141,53,142,52,142,51,140,49,139,49,136,52,135,52,135,50,134,49,134,39,136,37,137,38,138,38,141,41,142,41,143,42" >
<area shape="poly" href="ComunidadesServlet?comunidad=Aragón&ley=${ley}&circu=${circu}&ano=${ano}" title="Aragón" coords="192,32,193,33,194,33,197,36,200,36,201,35,204,35,205,34,212,34,214,36,214,37,212,39,212,40,214,42,214,44,213,45,213,48,212,49,212,52,211,53,211,55,210,56,210,57,209,58,209,59,208,60,208,61,207,62,207,63,206,64,206,66,207,67,207,68,206,69,206,74,207,75,207,76,204,79,204,90,202,92,201,92,200,91,195,91,192,94,192,95,191,96,191,97,193,99,193,103,192,104,192,105,191,106,190,106,189,107,188,107,187,108,187,109,186,110,186,112,181,117,174,110,170,110,169,109,168,109,163,104,163,102,164,102,167,99,167,88,159,80,158,80,157,79,155,79,155,78,154,77,156,75,157,75,159,73,159,70,160,69,160,68,162,66,162,59,170,59,172,57,172,51,171,50,171,49,170,48,172,46,172,42,174,40,174,39,177,36,178,36,182,32,182,30,183,30,185,32" >
<area shape="poly" href="ComunidadesServlet?comunidad=Cataluña&ley=${ley}&circu=${circu}&ano=${ano}" title="Catalunya" coords="237,39,241,43,242,43,243,44,245,44,246,43,252,43,253,44,256,44,258,42,259,42,260,41,262,41,263,40,265,40,267,42,265,44,265,47,268,50,268,53,266,55,266,56,265,57,264,57,259,62,256,62,254,64,253,64,251,66,250,66,248,68,248,69,247,70,247,71,246,72,246,73,245,74,244,74,243,75,241,75,240,76,237,76,236,77,234,77,233,78,231,78,230,79,228,79,227,80,226,80,225,81,224,81,222,83,221,83,215,89,215,92,216,93,218,93,215,96,214,96,213,97,212,97,212,96,211,95,209,95,208,94,208,93,207,92,207,91,208,90,208,86,207,85,207,82,208,81,208,80,210,78,210,73,209,72,210,71,210,70,211,69,211,67,210,66,210,64,211,63,211,62,213,60,213,59,214,58,214,56,215,55,215,52,216,51,216,48,217,47,217,39,218,38,218,36,217,35,217,34,216,33,216,32,215,31,215,30,216,31,217,31,218,32,219,32,220,33,221,33,223,35,228,35,228,41,229,42,231,42,232,43,233,43" >
<area shape="poly" href="ComunidadesServlet?comunidad=Comunitat Valenciana&ley=${ley}&circu=${circu}&ano=${ano}" title="C.Valenciana" coords="186,121,186,117,187,117,188,116,188,115,189,114,189,113,190,112,190,111,191,110,192,110,196,106,196,95,197,94,198,95,204,95,208,99,209,99,211,101,210,102,210,103,205,108,205,109,203,111,203,112,201,114,201,115,200,116,200,118,199,119,199,120,197,122,197,123,196,124,196,125,195,126,195,127,194,128,194,137,195,138,195,141,196,142,196,144,197,145,197,146,198,147,198,148,199,149,199,150,204,155,205,155,204,156,203,156,202,157,201,157,198,160,197,160,194,163,193,163,192,164,192,165,191,166,191,167,190,168,190,169,189,170,189,171,186,174,186,177,185,178,185,180,184,180,184,179,182,177,182,176,181,175,181,172,182,171,182,169,179,166,179,165,180,164,180,160,181,159,181,158,183,156,183,152,182,151,182,149,180,147,177,147,176,146,176,142,177,141,177,139,176,138,176,136,175,135,171,135,170,134,170,133,169,132,170,131,170,130,172,128,173,128,174,127,174,125,175,124,175,122,176,121,176,120,177,119,177,118,179,120,180,120,182,122,185,122" >
<area shape="poly" href="ComunidadesServlet?comunidad=Región de Murcia&ley=${ley}&circu=${circu}&ano=${ano}" title="Murcia" coords="173,158,174,157,175,158,176,158,176,164,175,165,175,166,176,167,176,168,178,170,178,178,179,179,179,180,183,184,182,185,182,188,184,190,178,190,177,191,172,191,168,195,166,195,166,194,165,193,162,193,159,190,159,182,158,181,156,181,155,180,154,180,153,179,152,179,152,178,151,177,151,176,156,171,157,171,158,170,160,170,161,169,162,169,163,170,168,170,169,169,169,167,170,166,170,163,171,162,171,160,172,159,172,158" >
<area shape="poly" href="ComunidadesServlet?comunidad=Castilla - La Mancha&ley=${ley}&circu=${circu}&ano=${ano}" title="Castilla La Mancha" coords="145,85,154,85,156,83,157,83,163,89,163,98,162,98,160,100,160,101,158,103,158,104,166,112,166,113,167,114,167,115,170,118,171,118,172,119,173,119,172,120,172,121,171,122,171,124,166,129,166,135,169,138,170,138,171,139,173,139,173,141,172,142,172,143,171,144,171,145,172,146,172,147,176,151,179,151,179,155,178,155,177,154,176,154,175,153,173,153,172,154,171,154,169,156,169,157,168,158,168,159,167,160,167,163,166,164,166,166,165,166,163,164,162,164,160,166,158,166,157,167,155,167,147,175,146,175,146,174,147,173,147,172,148,171,148,168,144,164,144,163,143,162,133,162,132,163,125,163,124,164,117,164,116,165,110,165,109,166,105,166,104,165,103,165,98,160,97,160,96,159,95,159,94,158,91,158,91,156,90,155,89,155,92,152,92,151,93,150,93,148,92,147,93,146,93,143,94,144,95,144,96,143,96,134,95,133,94,133,93,134,92,133,91,133,90,132,89,132,87,130,87,129,86,128,86,123,85,122,85,121,84,120,83,120,82,119,82,117,81,116,81,115,84,115,85,114,90,114,91,113,92,113,94,111,95,111,96,112,101,112,103,110,103,112,104,113,105,113,107,111,109,111,110,112,112,112,115,115,117,115,115,117,114,117,113,118,113,120,115,122,116,122,117,121,119,121,120,120,121,120,122,119,123,119,124,118,127,118,128,119,131,119,133,117,133,116,134,115,134,114,135,113,135,110,132,107,131,107,132,106,132,104,131,103,131,102,129,100,129,98,125,94,125,90,126,89,126,84,125,83,125,82,126,81,127,81,128,80,131,80,132,79,137,79,138,80,140,80,141,81,141,82,143,84,144,84" >
<area shape="poly" href="ComunidadesServlet?comunidad=Andalucía&ley=${ley}&circu=${circu}&ano=${ano}" title="Andalucía" coords="115,168,122,168,123,167,129,167,130,166,137,166,138,165,141,165,141,166,144,169,144,171,143,172,143,173,142,174,142,175,141,176,141,177,142,178,145,178,146,179,148,179,148,180,151,183,152,183,153,184,155,184,156,185,156,186,155,187,155,188,156,189,156,192,157,193,157,194,159,196,160,196,161,197,163,197,163,198,161,200,161,201,159,203,159,204,158,205,158,207,157,208,157,211,156,212,156,213,153,216,152,216,149,213,146,213,145,214,144,214,140,218,139,217,137,217,136,216,131,216,130,215,129,215,128,216,125,216,124,217,123,217,122,218,120,216,119,216,118,215,106,215,105,216,100,216,99,217,98,217,97,218,97,219,93,223,93,224,83,224,82,225,81,225,79,227,79,228,77,230,77,231,76,232,76,233,74,235,73,235,72,236,72,237,71,238,70,237,69,237,66,234,65,234,64,233,63,233,61,231,61,230,60,229,60,228,59,227,59,226,58,225,58,221,57,220,56,220,55,219,55,217,57,215,57,210,56,209,53,209,52,208,51,208,49,206,48,206,45,203,44,203,44,202,43,201,43,199,42,198,41,198,39,200,34,200,33,201,31,201,30,200,30,199,31,198,31,191,30,190,30,188,31,187,31,186,32,185,32,184,35,181,35,180,37,178,38,178,39,177,40,177,41,176,41,175,42,174,42,173,43,172,45,174,46,174,47,175,48,175,50,177,54,177,55,178,56,178,57,179,62,179,63,178,64,178,65,177,66,177,69,174,69,176,70,177,71,177,72,176,73,176,76,173,76,168,77,167,77,164,80,161,81,161,83,159,84,159,86,157,87,158,88,158,88,161,89,162,94,162,96,164,97,164,101,168,102,168,103,169,104,169,105,170,106,170,107,169,114,169" >
<area shape="poly" href="ComunidadesServlet?comunidad=Extremadura&ley=${ley}&circu=${circu}&ano=${ano}" title="Extremadura" coords="67,109,69,109,70,108,71,108,72,109,73,109,74,110,75,110,76,111,78,111,78,120,79,121,79,122,80,123,82,123,82,124,83,125,82,126,82,128,83,129,83,130,84,131,84,132,87,135,88,135,90,137,93,137,93,139,92,139,90,141,90,143,88,145,88,146,87,147,87,148,88,149,89,149,88,150,88,151,85,154,84,154,83,155,82,155,79,158,78,158,74,162,74,163,73,164,73,170,72,171,72,172,71,171,67,171,64,174,63,174,62,175,61,175,60,176,59,176,57,174,56,174,55,173,51,173,49,171,47,171,44,168,40,168,38,166,38,164,37,163,37,158,38,157,38,155,43,150,43,148,44,147,44,144,42,142,42,141,39,138,39,136,38,135,38,132,37,131,37,130,36,129,36,128,35,127,40,127,41,126,43,126,45,124,45,123,46,122,46,117,47,116,47,115,46,114,46,110,47,109,50,109,51,108,53,108,55,106,56,106,60,102,61,102,63,104,63,105" >
<area shape="poly" href="ComunidadesServlet?comunidad=Comunidad de Madrid&ley=${ley}&circu=${circu}&ano=${ano}" title="C. de Madrid" coords="123,86,122,87,122,96,125,99,125,101,127,103,127,104,128,105,128,109,129,110,130,110,131,111,131,112,130,113,130,115,121,115,120,116,120,113,118,111,116,111,114,109,112,109,110,107,104,107,104,106,105,106,106,105,106,104,107,103,107,102,108,101,108,100,109,99,109,97,110,96,110,95,111,95,112,94,113,94,114,93,114,92,121,85,122,85" >
<area shape="poly" href="ComunidadesServlet?comunidad=Castilla y León&ley=${ley}&circu=${circu}&ano=${ano}" title="Castilla y León" coords="102,26,103,27,104,27,105,28,105,29,110,34,111,34,112,35,115,35,116,34,117,34,118,33,118,32,119,31,119,29,117,27,117,26,118,25,119,25,120,24,125,24,126,23,127,23,128,22,129,22,129,23,130,24,130,25,129,25,127,27,127,30,132,35,132,36,130,38,130,43,131,44,131,45,130,46,130,48,131,49,131,52,132,53,132,54,135,57,137,57,138,56,139,56,140,57,144,57,147,54,147,53,149,53,150,54,151,54,151,56,153,58,155,58,156,59,158,59,158,61,159,62,159,64,157,66,157,67,155,69,155,71,154,71,152,73,152,74,151,75,151,80,152,81,151,81,150,82,147,82,141,76,138,76,138,75,137,74,136,74,135,75,133,75,132,76,128,76,127,77,126,77,125,78,124,78,122,80,121,80,111,90,111,91,110,91,109,92,108,92,107,93,107,94,106,95,106,97,104,99,104,101,97,108,96,108,95,107,94,107,93,108,92,108,90,110,88,110,87,111,82,111,81,110,81,109,79,107,76,107,74,105,73,105,71,103,70,103,68,105,67,104,67,103,62,98,59,98,55,102,54,102,52,104,51,104,50,105,49,105,49,102,50,101,50,92,51,91,51,86,50,85,51,85,52,84,52,82,53,81,53,80,54,79,55,79,56,78,57,78,60,75,61,75,64,72,64,70,65,69,65,66,64,65,64,64,62,62,61,62,60,61,58,61,58,59,57,58,57,56,55,54,54,54,53,53,50,53,49,52,49,51,51,49,52,49,53,48,53,47,54,46,54,42,53,41,53,40,48,35,49,34,49,33,51,31,51,30,52,30,53,29,57,29,58,28,60,28,62,26,64,26,65,25,66,25,67,24,68,24,69,25,70,25,70,26,72,28,73,28,74,27,75,27,76,26,78,26,79,27,81,27,83,25,84,25,85,24,89,24,92,21,92,23,95,26" >


<area shape="poly" href="ComunidadesServlet?comunidad=Canarias&ley=${ley}&circu=${circu}&ano=${ano}" title="Canarias" class="map_islas" coords="296,203,296,256,187,256,187,203" >
<area shape="poly" href="ComunidadesServlet?comunidad=Illes Balears&ley=${ley}&circu=${circu}&ano=${ano}" title="Baleares" class="map_islas" coords="298,100,298,145,255,145,253,147,253,164,221,164,221,151,224,148,224,147,232,139,232,138,242,128,242,127,247,122,247,121,254,114,254,113,262,105,262,104,265,101,265,100" >




</map>
<br>
<br>
<a href="LaboratorioElectoral.jsp" class="btn btn-danger" style="margin-left: 35%;">Volver</a>


</div>

<div class ="col-md-6">

<table class="table">
	<thead>
	<tr>
		<th>Partido</th>
		<th>Votos</th>
		<th>Escaños</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${res}" var="resi">
		<tr>
			<td>${resi.nombre}</td>
			<td>${resi.votos}</td>
			<td>${resi.escannos}</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</div>

</div>





</body>
</html> 


