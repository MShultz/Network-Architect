package shultz.algorithms.network;

import java.util.ArrayList;


public class Main {

	public static void main(String[] args) {
		UserInterface ui = new UserInterface();
		String file = ui.getFile();
		FileHandler handle = new FileHandler(file);
		for(ArrayList<String> maze: handle.getAllMazes()){
				
			}

	}

}
