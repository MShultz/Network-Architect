package shultz.algorithms.network;



public class Main {

	public static void main(String[] args) {
		UserInterface ui = new UserInterface();
		String file = ui.getFile();
		FileHandler handle = new FileHandler(file);
		NetworkHandler network = new NetworkHandler(handle.getAllMazes());
		network.handleMazes();

	}

}
