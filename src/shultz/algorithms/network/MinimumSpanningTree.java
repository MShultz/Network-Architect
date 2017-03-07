package shultz.algorithms.network;

import java.util.ArrayList;

import algo.data.structures.Graph;
import algo.data.structures.GraphNode;

public class MinimumSpanningTree {
	ArrayList<ArrayList<String>> allMSTs = new ArrayList<ArrayList<String>>();
	ArrayList<String> currentMaze;

	public MinimumSpanningTree(ArrayList<String> currentMaze) {
		this.setCurrentMaze(currentMaze);
		generateGraphs();
	}

	private void generateGraphs() {
		String[] allNodeString = currentMaze.get(0).split(",");
		ArrayList<GraphNode<String>> allNodes = getAllNodes(allNodeString);
		populateNodes(allNodes);
		calculateMSTs(allNodes);
	}

	private void calculateMSTs(ArrayList<GraphNode<String>> allNodes) {
		allMSTs.add(new ArrayList<String>());
		allMSTs.get(0).add(allNodes.get(0).getValue());
		Graph<String> testingGraph = new Graph<String>();
		for (int i = 1; i < allNodes.size(); ++i) {
			resetNodes(allNodes);
			int currentMST = 0;
			boolean tailPlaced = false;
			GraphNode<String> tail = allNodes.get(i);
			while (!tailPlaced) {
				if (currentMST < allMSTs.size()) {
					GraphNode<String> head = getNode(allMSTs.get(currentMST).get(0), allNodes);
					if (testingGraph.pathExists(head, tail)) {
						tailPlaced = allMSTs.get(currentMST).add(tail.getValue());
					}else{
						++currentMST;
					}
				} else {
					allMSTs.add(new ArrayList<String>());
					tailPlaced = allMSTs.get(currentMST).add(tail.getValue());
				}
			}
		}
	}

	private ArrayList<GraphNode<String>> getAllNodes(String[] allNodeString) {
		ArrayList<GraphNode<String>> allNodes = new ArrayList<GraphNode<String>>();
		for (int i = 0; i < allNodeString.length; ++i) {
			allNodes.add(new GraphNode<String>(allNodeString[i]));
		}
		return allNodes;
	}

	private void populateNodes(ArrayList<GraphNode<String>> allNodes) {
		for (int i = 1; i < currentMaze.size(); ++i) {
			String[] nodeInformation = currentMaze.get(i).split(",");
			GraphNode<String> nodeToEdit = getNode(nodeInformation[0], allNodes);
			for (int j = 1; j < nodeInformation.length; ++j) {
				nodeToEdit.add(getNode(nodeInformation[j].replaceAll(":\\d*", ""), allNodes));
			}
		}
	}

	public void setCurrentMaze(ArrayList<String> currentMaze) {
		this.currentMaze = currentMaze;
	}

	private GraphNode<String> getNode(String value, ArrayList<GraphNode<String>> allNodes) {
		GraphNode<String> nodeFound = null;
		int count = 0;
		while (nodeFound == null) {
			if (allNodes.get(count).getValue().equals(value))
				nodeFound = allNodes.get(count);
			++count;
		}
		return nodeFound;
	}

	public ArrayList<ArrayList<String>> getAllMSTs() {
		return allMSTs;
	}
	
	private void resetNodes(ArrayList<GraphNode<String>> allNodes){
		for(GraphNode<String> node: allNodes){
			node.setPath(null);
			node.setDistance(Integer.MAX_VALUE);
		}
	}
}
