<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div>
    <svg></svg>

    <script src="https://d3js.org/d3.v4.min.js"></script>
</div>


<script>
    var nodes = [{
        id: "patient",
        group: 0,
        label: "${patient.surname} ${patient.name.charAt(0)}.${patient.middle_name.charAt(0)}.",
        level: 0
    },
        <c:forEach var="specialization" items="${specializationList}" varStatus="i">
        {
            id: "${specialization.id}",
            group: 1,
            label: "${specialization.name}",
            level: 1
        },
        </c:forEach>

        <c:forEach var="disease" items="${diseaseList}" varStatus="i">
        {
            id: "${disease.ICD_10}",
            group: 2,
            label: "${disease.name}",
            level: 3
        },
        </c:forEach>
    ]

    var links = [
        <c:forEach var="visit" items="${visitList}" varStatus="i">
        {
            target: "patient",
            source: "${visit.doctor.specialization.id}",
            strength: 0.2,
            value: 1
        },
        </c:forEach>


        <c:forEach var="visit" items="${visitList}" varStatus="i">
        {
            target: "${visit.doctor.specialization.id}",
            source: "${visit.disease.ICD_10}",
            strength: 0.2,
            value: 1
        },
        </c:forEach>

    ]

    function getNeighbors(node) {
        return links.reduce(function (neighbors, link) {
            if (link.target.id === node.id) {
                neighbors.push(link.source.id)
            } else if (link.source.id === node.id) {
                neighbors.push(link.target.id)
            }
            return neighbors
        }, [node.id])
    }

    function isNeighborLink(node, link) {
        return link.target.id === node.id || link.source.id === node.id
    }


    function getNodeColor(node, neighbors) {
        if (Array.isArray(neighbors) && neighbors.indexOf(node.id) > -1) {
            return node.level === 1 ? 'blue' : 'green'
        }
        let color = 'gray';
        if (node.level === 1)
            color = '#9b3963';
        if (node.level === 0)
            color = '#D2C1C1';

        return color
    }


    function getLinkColor(node, link) {
        return isNeighborLink(node, link) ? 'green' : '#E5E5E5'
    }

    function getTextColor(node, neighbors) {
        return Array.isArray(neighbors) && neighbors.indexOf(node.id) > -1 ? 'green' : 'black'
    }

    var width = window.innerWidth
    var height = window.innerHeight

    var svg = d3.select('svg')
    svg.attr('width', width).attr('height', height);

    var circles = svg.selectAll(null)
        .data([250, 125])
        .enter()
        .append("circle")
        .attr("cx", width / 2)
        .attr("cy", height / 2)
        .attr("r", d => d)
        .style("fill", "none")
        .style("stroke", "#ccc");

    // simulation setup with all forces
    var linkForce = d3
        .forceLink()
        .id(function (link) {
            return link.id
        });

    var simulation = d3
        .forceSimulation()
        .force('link', linkForce)
        .force('charge', d3.forceManyBody().strength(-120))
        .force('radial', d3.forceRadial(function (d) {
            return d.level * 250
        }, width / 2, height / 2))
        .force('center', d3.forceCenter(width / 2, height / 2))

    var dragDrop = d3.drag().on('start', function (node) {
        node.fx = node.x
        node.fy = node.y
    }).on('drag', function (node) {
        simulation.alphaTarget(0.7).restart()
        node.fx = d3.event.x
        node.fy = d3.event.y
    }).on('end', function (node) {
        if (!d3.event.active) {
            simulation.alphaTarget(0)
        }
        node.fx = null
        node.fy = null
    })

    function selectNode(selectedNode) {
        var neighbors = getNeighbors(selectedNode)

        // we modify the styles to highlight selected nodes
        nodeElements.attr('fill', function (node) {
            return getNodeColor(node, neighbors)
        })
        textElements.attr('fill', function (node) {
            return getTextColor(node, neighbors)
        })
        linkElements.attr('stroke', function (link) {
            return getLinkColor(selectedNode, link)
        })
    }

    var linkElements = svg.append("g")
        .attr("class", "links")
        .selectAll("line")
        .data(links)
        .enter().append("line")
        .attr("stroke-width", function (link) {
            return link.value;
        })
        .attr("stroke", "rgba(50, 50, 50, 0.2)")

    var nodeElements = svg.append("g")
        .attr("class", "nodes")
        .selectAll("circle")
        .data(nodes)
        .enter().append("circle")
        .attr("r", 10)
        .attr("fill", getNodeColor)
        .call(dragDrop)
        .on('click', selectNode)

    var textElements = svg.append("g")
        .attr("class", "texts")
        .selectAll("text")
        .data(nodes)
        .enter().append("text")
        .text(function (node) {
            return node.label
        })
        .attr("font-size", 15)
        .attr("dx", 15)
        .attr("dy", 4)

    simulation.nodes(nodes).on('tick', () => {
        nodeElements
            .attr('cx', function (node) {
                return node.x
            })
            .attr('cy', function (node) {
                return node.y
            })
        textElements
            .attr('x', function (node) {
                return node.x
            })
            .attr('y', function (node) {
                return node.y
            })
        linkElements
            .attr('x1', function (link) {
                return link.source.x
            })
            .attr('y1', function (link) {
                return link.source.y
            })
            .attr('x2', function (link) {
                return link.target.x
            })
            .attr('y2', function (link) {
                return link.target.y
            })
    })

    simulation.force("link").links(links)
</script>
