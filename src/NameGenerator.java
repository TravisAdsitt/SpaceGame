

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.print.DocFlavor.READER;

/**
 * 
 * @author Tyler FiveCents Nicholls
 *
 */


public class NameGenerator {
	private Random rand = new Random();
	private String name;
	private static ArrayList<String> adjectiveArray;
	private static ArrayList<String> nounArray;


	public NameGenerator(){
		rand = new Random();
		name = "";
		adjectiveArray = new ArrayList<String>();
		nounArray = new ArrayList<String>();
		getAdjectiveList();
		getNounList();
	}

	public ArrayList<String> getAdjectiveList(){

		//looks for file to pull adjectives from
		File adjectiveFile = new File("adjectiveList.txt");

		try{
			Scanner adjectiveScan = new Scanner(adjectiveFile);
			String adjective;
			
			BufferedReader br = new BufferedReader(new FileReader(adjectiveFile));
			
			

			
			//System.out.println(adjectiveScan);
			
			while ((adjective = br.readLine()) != null){
				if(!adjective.equals("")){
					adjectiveArray.add(adjective);
				}
			}
			adjectiveScan.close();
		}
		catch (FileNotFoundException e) {
			System.err.println("Failed to find adjective file :( " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

		/*for (String s: adjectiveArray){
			System.out.println(s);
		}*/

		return adjectiveArray;
	}

	public ArrayList<String> getNounList(){
		
		//looks for file to pull nouns from
		File nounFile = new File("nounList.txt");


		try{
			Scanner nounScan = new Scanner(nounFile);



			while (nounScan.hasNextLine()){
				String noun = nounScan.nextLine();
				if(!noun.equals("")){
					nounArray.add(noun);
				}
			}
			nounScan.close();
		}
		catch (FileNotFoundException e) {
			System.err.println("Failed to find noun file :( " + e.getMessage());
		}

		/*for (String s: nounArray){
			System.out.println(s);
		}*/
		return nounArray;
	}


	//gets a random, likely goofy name
	public String getRandomName(){
		String ad = adjectiveArray.get(rand.nextInt(adjectiveArray.size())),
				noun = nounArray.get(rand.nextInt(nounArray.size()));
		
		
		
		name = ad + " " + noun;
		//System.out.println(name);
		return name;
	}

}
