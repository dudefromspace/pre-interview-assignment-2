package org.preinterview.assignment;


import java.util.*;
import java.util.stream.Collectors;

public class App
{
    public static void main( String[] args )
    {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");

        nodeA.addDestination(nodeB, 2);
        nodeA.addDestination(nodeC, 5);
        nodeA.addDestination(nodeD, 1);

        nodeB.addDestination(nodeD, 2);
        nodeB.addDestination(nodeC, 3);
        nodeB.addDestination(nodeA, 2);

        nodeC.addDestination(nodeF, 5);
        nodeC.addDestination(nodeE, 1);
        nodeC.addDestination(nodeD, 3);
        nodeC.addDestination(nodeB, 3);

        nodeD.addDestination(nodeC, 3);
        nodeD.addDestination(nodeE, 1);
        nodeD.addDestination(nodeA, 1);
        nodeD.addDestination(nodeB, 1);

        nodeE.addDestination(nodeF, 2);
        nodeE.addDestination(nodeC, 1);
        nodeE.addDestination(nodeD, 1);

        nodeF.addDestination(nodeE, 2);
        nodeF.addDestination(nodeC, 5);

        Graph graph = new Graph();

        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);

        String source = args[0];
        String destination = args[1];
        Node sourceNode = null;
        for(Node node : graph.getNodes()){
            if(node.getName().equals(source)){
                sourceNode = node;
            }
        }


        Graph g = calculateShortestPathFromSource(graph, sourceNode);
        List<Node> nodeList = g.getNodes().stream().filter(node->node.getName().equals(destination)).collect(Collectors.toList());
        nodeList.get(0).getShortestPath().forEach(node->System.out.println(node.getName()));
        System.out.println(destination);
    }

    public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.setDistance(0);

        Set<Node> visited = new HashSet<>();
        Set<Node> notVisited = new HashSet<>();

        notVisited.add(source);

        while (notVisited.size() != 0) {
            Node currentNode = Util.getLowestDistanceNode(notVisited);
            notVisited.remove(currentNode);
            for (Map.Entry< Node, Integer> adjacencyPair:
                    currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!visited.contains(adjacentNode)) {
                    Util.calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    notVisited.add(adjacentNode);
                }
            }
            visited.add(currentNode);
        }
        return graph;
    }

}
