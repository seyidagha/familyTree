<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
  <title>Person Tree</title>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/vis/4.21.0/vis.min.js"></script>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/vis/4.21.0/vis.min.css" rel="stylesheet" type="text/css" />

  <style type="text/css">
    body, html {
      font-family: sans-serif;
    }
  </style>
</head>
<body>
<h3> <a href="<spring:url value="/index"></spring:url>">Return to main page</a>  </h3>
<div id="visualization"></div>

<script type="text/javascript">

  

  // create an array with nodes
//   var nodes = new vis.DataSet([
//     {id: 1, label: 'Node 1'},
//     {id: 2, label: 'Node 2'},
//     {id: 3, label: 'Node 3'},
//     {id: 4, label: 'Node 4'},
//     {id: 5, label: 'Node 5'}
//   ]);
  
  var data = JSON.parse('${jsonList}');
  console.log(data);
  var nodes = new vis.DataSet(data[0]);
  var edges = new vis.DataSet(data[1]);
  
//   edges.forEach(function(edge){
// 	  edge.push("roundness", 0.4);
//   });


  // create an array with edges
//   var edges = new vis.DataSet([
// 	  {from: 3, to: 4, arrows: 'to', label:'fuck'},  
//     {from: 1, to: 3, arrows: 'to', label:'fuck', font:{align: 'middle'}},
//     {from: 1, to: 2, arrows: 'to', label:'fuck'},
//     {from: 2, to: 4, arrows: 'to', label:'fuck'},
//     {from: 2, to: 5, arrows: 'to', label:'fuck'}
//   ]);

  // create a network
  var container = document.getElementById('visualization');
  var data = {
    nodes: nodes,
    edges: edges
  };
  var options = {
		  edges: {
			  font:{
				  align :'middle',
				  size : 12
			  },
              smooth: {
                  type: 'curvedCW'
                  
                  //roundness: 0.7
              }
          },
          
//           layout: {
//         	  //improvedLayout:true,
//         	  hierarchical: {
//                   direction: "UD",
//                   sortMethod: "directed",
//                   levelSeparation: 250,
//                   nodeSpacing: 500,
//                   parentCentralization: true,
//                   edgeMinimization: true
//               }
//           },
          
          physics:
        	  {
                  stabilization: false
                }
      };
  var network = new vis.Network(container, data, options);

</script>
</body>
</html>
