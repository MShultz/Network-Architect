package shultz.algorithms.network;

import java.util.ArrayList;
import java.util.HashMap;

import algo.data.structures.WeightedGraph;
import algo.data.structures.WeightedNode;

public class NetworkHandler {
	ArrayList<ArrayList<String>> allMazes;

	public NetworkHandler(ArrayList<ArrayList<String>> allMazes) {
		this.setAllMazes(allMazes);
	}

	public void handleMazes() {
		int totalLength;
		for (ArrayList<String> maze : allMazes) {
			totalLength = 0;
			MinimumSpanningTree MST = new MinimumSpanningTree(maze);
			int count = 1;
			for (ArrayList<String> spanningTree : MST.getAllMSTs()) {
				WeightedGraph<String> currentGraph = createWeightedGraph(spanningTree, maze);
				totalLength += currentGraph.getTotalLength();
				System.out.println("MST " + count);
				printSockets(spanningTree);
				System.out.println("Cable Needed: " + currentGraph.getTotalLength() + " units");
				++count;
			}
			System.out.println("Total Length of Cable Needed: " + totalLength + " units");
		}
	}

	private WeightedGraph<String> createWeightedGraph(ArrayList<String> spanningTree, ArrayList<String> currentMaze) {
		ArrayList<WeightedNode<String>> allNodes = createAllNodes(spanningTree);
		currentMaze.remove(0);
		for (WeightedNode<String> node : allNodes) {
			boolean found = false;
			String nodesToAdd = "";
			for (int i = 0; i < currentMaze.size() && !found; ++i) {
				if (currentMaze.get(i).startsWith(node.getValue())) {
					nodesToAdd = currentMaze.get(i);
					found = true;
				}
			}
			addChildren(node, nodesToAdd, allNodes);
		}
		
		return new WeightedGraph<String>(allNodes);

	}

	private void addChildren(WeightedNode<String> currentNode, String children, ArrayList<WeightedNode<String>> allNodes) {
		String[] childrenInfo = children.split(",");
		HashMap<WeightedNode<String>, Integer> neighborNodes = new HashMap<WeightedNode<String>, Integer>();
		
		for (int i = 1; i < childrenInfo.length; ++i) {
			String[] currentInfo = childrenInfo[i].split(":");
			neighborNodes.put(getNode(currentInfo[0], allNodes), Integer.parseInt(currentInfo[1]));
		}
		
		currentNode.addChildren(neighborNodes);
	}

	private ArrayList<WeightedNode<String>> createAllNodes(ArrayList<String> spanningTree) {
		ArrayList<WeightedNode<String>> allNodes = new ArrayList<WeightedNode<String>>();
		for (String value : spanningTree) {
			allNodes.add(new WeightedNode<String>(value));
		}
		return allNodes;
	}

	private void printSockets(ArrayList<String> spanningTree) {
		String sockets = "";
		for (String socket : spanningTree) {
			sockets += (socket + ",");
		}
		sockets = sockets.substring(0, sockets.length() - 1);
		System.out.println("Socket Set: " + sockets);
	}

	public ArrayList<ArrayList<String>> getAllMazes() {
		return allMazes;
	}

	public void setAllMazes(ArrayList<ArrayList<String>> allMazes) {
		this.allMazes = allMazes;
	}
	
	private WeightedNode<String> getNode(String value, ArrayList<WeightedNode<String>> allNodes) {
		WeightedNode<String> nodeFound = null;
		int count = 0;
		while (nodeFound == null) {
			if (allNodes.get(count).getValue().equals(value))
				nodeFound = allNodes.get(count);
			++count;
		}
		return nodeFound;
	}
}
