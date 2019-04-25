var mapr = (function(){
	var config = {
		index: 0,
		duration: 750,
		depth: 180,
		width: 800,
		height: 400,
		margin: {
			top: 50,
			right: 50,
			bottom: 50,
			left: 100
		}
	};

	return {
		tree: function(data) {
			var _data = data;
			var _svg;
			var _tree = Object.assign({}, _data, {x0: config.height/2, y0: 0});
			var _gauges = [];

			var d3tree = d3.layout.tree().size([config.height, config.width]);
			var d3diagonal = d3.svg.diagonal().projection(function(d) { return [d.y, d.x]; });

			var showTooltip = false;

			var d3tooltip = d3.select("body").append("div")
            	.attr("class", "tooltip")
            	.style("opacity", 0);

			function setupSvg(parent) {
				return d3.select(parent).append("svg")
							.attr("width", config.width + config.margin.right + config.margin.left)
							.attr("height", config.height + config.margin.top + config.margin.bottom)
							.append("g")
								.attr("transform", "translate(" + config.margin.left + "," + config.margin.top + ")");
			};

			function simpleNode() {
				var element = document.createElementNS(d3.ns.prefix.svg, "g");

				var circle = document.createElementNS(d3.ns.prefix.svg, "circle");
				//circle.setAttribute("r", 1e-6);
				circle.setAttribute("style", "fill: #fff");

				element.appendChild(circle);
				
				return element;
			}

			/*
			beim Einblenden müssen die Position und die Größe angepasst werden
			*/
			function renderTree(rootNode) {
				  // Compute the new tree layout.
				  var nodes = d3tree.nodes(_tree).reverse(),
					  links = d3tree.links(nodes);

				  // Normalize for fixed-depth.
				  nodes.forEach(function(d) {
				  	d.y = d.depth * config.depth;
				  });

				  // Update the nodes…
				  var node = _svg.selectAll("g.node").data(nodes, function(d) {
					return d.id || (d.id = ++config.index); 
				  });

					// Enter any new nodes at the parent's previous position.
					// TODO: use node functions
				  var nodeEnter = node.enter().append(function(d) {
				  	var element, _class;

				  	
				    switch(d.type) {
				    	case "volume":
				    		var _gauge = gauge.liquid(((typeof d.value !== 'undefined') ? d.value : 100), {"waveColor":"#FF8080"});
				    		_gauges[d.id] = _gauge;
				    		element = _gauge.element;
				    		_class = "node gauge";
				    		break;
				    	case "object-pool":
				    		var _gauge = gauge.liquid(((typeof d.value !== 'undefined') ? d.value : 100));
				    		_gauges[d.id] = _gauge;
				    		element = _gauge.element;
				    		_class = "node gauge";
				    		break;
				    	default:
				    		element = simpleNode();
				    		_class = "node";
				    		break;
				    };
				    
				    var d3element = d3.select(element);
				    d3element.selectAll(".gauge>circle").attr("r", 1e-6);
				    d3element.selectAll(".wave").style("opacity", 0);
				     
				   
				    
				    element.onclick = function() {
				      if (d.children) {
				        d._children = d.children;
				        d.children = null;
				      } else {
				        d.children = d._children;
				        d._children = null;
				      }
				      renderTree(d);
				    };

				    if (d.type) {
					    element.onmouseover = function() {
					    	console.log("enter");

					    	var _that = this;
					    	var url;
                            //var currentx = d3.transform(d3.select(this).attr("transform")).translate[0];
                            var currenty = d3.transform(d3.select(this).attr("transform")).translate[1];
					    	if(d.type == "volume") {
					    		url = "/rest/context/hot/" + d.name;
							}else {
                                url = "/rest/context/cold/" + d.parent.name;
							}

                            d3.json(url, function(data) {
                                var html = "";
                                for (var key in data) {
                                    if (data.hasOwnProperty(key)) {
                                        html += "<b>" + key + ": " + data[key] + "</b></br>"
                                    }
                                }

                                d3tooltip.transition().duration(500).style("opacity", 1);
                                d3tooltip.html(html)
								.style("left", (1100) + "px")
								.style("top", (currenty	+ 125) + "px")
								.style("height", ((d.type == "volume") ? 80 : 130) + "px");
                                showTooltip = true;
							});
					    };

					    element.onmouseout = function() {
							d3tooltip.transition().duration(500).style("opacity", 0);
					    };
					}

					element.setAttribute("class", _class);

					var ry = rootNode.y0;// - (d.type ? 40 : 0);
					var rx = rootNode.x0 - (d.type ? 40 : 0);
				    element.setAttribute("transform", "translate(" + ry + "," + rx + ")");
				  	/*
				    var elg = document.createElementNS(d3.ns.prefix.svg, "g");
				    elg.setAttribute("class", d.type ? "node "+d.type : "node");
				    elg.setAttribute("transform", "translate(" + rootNode.y0 + "," + rootNode.x0 + ")");
				    elg.onclick = function() {
				      if (d.children) {
				        d._children = d.children;
				        d.children = null;
				      } else {
				        d.children = d._children;
				        d._children = null;
				      }
				      renderTree(d);
				    };

				    var elc = document.createElementNS(d3.ns.prefix.svg, "circle");
				    elc.setAttribute("r", 1e-6);
				    elc.setAttribute("style", "fill: " + d._children ? "lightsteelblue" : "#fff");
				    */
					var txt = document.createElementNS(d3.ns.prefix.svg, "text");
					txt.setAttribute("x", (d.type) ? +50 : +15);
					txt.setAttribute("dy", (d.type) ? "-0.5em" : "-2em");
					txt.setAttribute("text-anchor", "end");
					txt.textContent = d.name;
					element.appendChild(txt);

				    return element;
				  });  

				  //node.selectAll("circle").attr("r", 1e-6);

				    // Transition nodes to their new position.
				  var nodeUpdate = node.transition()
					  .duration(config.duration)
					  .attr("transform", function(d) {
					  		var dx = d.x - (d.type ? 40 : 0);
					  		return "translate(" + d.y + "," + dx + ")";
					  	});
					  nodeUpdate.select(".wave").each("end", function(n) {
					  		d3.select(this).style("opacity", 1);
					  	});

				  nodeUpdate.select("circle")
					  .attr("r", function(d) {
				      var radius = 40;
				      return d.type ? radius : radius/2;
				    })
					  .style("fill", function(d) { return d._children ? "lightsteelblue" : "#fff"; });

				  nodeUpdate.select("text")
					  .style("fill-opacity", 1);



					// Transition exiting nodes to the parent's new position.
					node.exit().select(".wave").remove();
					node.exit().select("text").remove();

					  var nodeExit = node.exit().transition()
						  .duration(config.duration)
						  .attr("transform", function(d) { 
						  	var rx = rootNode.x - (d.type ? 40 : 0);
						  	var ry = rootNode.y - (d.type ? 40 : 0);
						  	return "translate(" + ry + "," + rx + ")"; 
						  })
						  .remove();

					  nodeExit.select("circle").attr("r", 1e-6);
					  nodeExit.select("text").style("fill-opacity", 1e-6);



					  // Update the links…
					  var link = _svg.selectAll("path.link")
						  .data(links, function(d) { return d.target.id; });

					  // Enter any new links at the parent's previous position.
					  link.enter().insert("path", "g")
						  .attr("class", "link")
						  .attr("d", function(d) {
							var o = {x: rootNode.x0, y: rootNode.y0};
							return d3diagonal({source: o, target: o});
						  });

					  // Transition links to their new position.
					  link.transition()
						  .duration(config.duration)
						  .attr("d", d3diagonal);

					  // Transition exiting nodes to the parent's new position.
					  link.exit().transition()
						  .duration(config.duration)
						  .attr("d", function(d) {
							var o = {x: rootNode.x, y: rootNode.y};
							return d3diagonal({source: o, target: o});
						  })
						  .remove();

					  // Stash the old positions for transition.
					  nodes.forEach(function(d) {
						d.x0 = d.x;
						d.y0 = d.y;
  });
			};

			return {
				render: function(parent) {
					d3.select(parent)
						.style("width", (config.width + config.margin.right + config.margin.left)+"px")
						.style("height", (config.height + config.margin.top + config.margin.bottom)+"px");
					_svg = setupSvg(parent);
					renderTree(_tree);
				},

				update: function(newData) {
					//console.log(newData);
					Object.entries(newData).forEach(([key, value]) => {
					//	console.log(key);
					//	console.log(_gauges[key]);
						_gauges[key].update(value);
					});
				}
			};
		}
	};
})();