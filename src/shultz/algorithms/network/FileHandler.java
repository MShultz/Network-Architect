package shultz.algorithms.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileHandler {
	private BufferedReader file = null;
	private ArrayList<String> entireFile = new ArrayList<String>();
	private ArrayList<ArrayList<String>> allMazes = new ArrayList<ArrayList<String>>();

	public FileHandler(String fileName) {
		initializeReader(fileName);
		parseFile();
		sortInformation();
	}

	private void initializeReader(String fileName) {
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(fileName);
			file = new BufferedReader(new InputStreamReader(inputStream));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void parseFile() {
		try {
			while (file.ready()) {
				entireFile.add(file.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sortInformation(){
		removeComments();
		int count = 0;
		while(count < entireFile.size()){
			String str = entireFile.get(count);
			ArrayList<String> next = new ArrayList<String>();
			allMazes.add(next);
			while(!str.equals("") && count < entireFile.size()){
				next.add(str);
				++count;
				if(count < entireFile.size())
				str = entireFile.get(count);
			}
			if(str.equals(""))
				++count;
				
		}
		removeEmpty();
	}
	
	private void removeComments(){
		ArrayList<String> linesToDelete = new ArrayList<String>();
		for(String s: entireFile){
			if(s.contains("//")){
				linesToDelete.add(s);
			}
		}
		for(String line: linesToDelete){
			entireFile.remove(line);
		}
	}
	
	private void removeEmpty(){
		for(ArrayList<String> list: allMazes){
			if(list.isEmpty())
				allMazes.remove(list);
		}
	}

	public void closeReader() {
		try {
			file.close();
		} catch (IOException e) {
			System.out.println("Error: Unable to propperly close input file.");
		}
	}

	public static boolean fileExists(String file) {
		try {
			FileInputStream s = new FileInputStream(file);
			s.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public ArrayList<ArrayList<String>> getAllMazes() {
		return allMazes;
	}


	
}
