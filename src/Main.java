import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
	public static ArrayList<Token>tokens=new ArrayList<Token>();
	public static ArrayList<Output>output=new ArrayList<Output>();
	public static boolean visited[]=new boolean[10000000];
	public static String targetString="",tmp="";
	public static void initTokens() throws FileNotFoundException {
		for(int i=0 ; i<10000000 ; i++) {
			visited[i]=false;
		}
		String fileAddress="init.txt";
		Scanner in = new Scanner(new File(fileAddress));
		while(in.hasNext()){
			//System.out.println(in.nextLine());
			String x=in.nextLine();
			String arr[]=x.split(" ");
			//System.out.println(arr[0]+" -----> "+arr[1]);
			tokens.add(new Token(arr[0],arr[1]));
		}
	}
	
	public static void readInputFile() throws FileNotFoundException {
		String fileAddress="input.txt";
		Scanner in = new Scanner(new File(fileAddress));
		while(in.hasNext()){
			String Line=in.nextLine();
			targetString=targetString+Line+" ";
			tmp+=Line;
			output.add(new Output(tmp.length(),tmp.length()+1,"ENDOFLINE",new Token("EOL","ENDOFLINE")));
		}
		System.out.println(targetString);
	}
	
	public static void sort() {
		for(int i=0 ; i<output.size(); i++) {
			for(int j=i+1 ; j<output.size(); j++) {
				if(output.get(j).startIndx<output.get(i).startIndx) {
					int tmp=output.get(i).startIndx;
					output.get(i).startIndx=output.get(j).startIndx;
					output.get(j).startIndx=tmp;

					tmp=output.get(i).endIndx;
					output.get(i).endIndx=output.get(j).endIndx;
					output.get(j).endIndx=tmp;

					String t=output.get(i).value;
					output.get(i).value=output.get(j).value;
					output.get(j).value=t;

					t=output.get(i).token.name;
					output.get(i).token.name=output.get(j).token.name;
					output.get(j).token.name=t;

					t=output.get(i).token.regex;
					output.get(i).token.regex=output.get(j).token.regex;
					output.get(j).token.regex=t;

				}
			}
		}
	}
	
	public static void modify() {
		for(int i=0 ; i<output.size(); i++) {
			int startIdx1 = output.get(i).startIndx;
			int endIdx1 = output.get(i).endIndx;
			for(int j=0 ; j<output.size() ; j++) {
				if(i==j)
					continue;
				int startIdx2 = output.get(j).startIndx;
				int endIdx2 = output.get(j).endIndx;
				//System.out.println(i+" "+j);
				if(output.get(i).value.equals(output.get(j).value) && startIdx1 == startIdx2) {
					if(output.get(i).token.name.equals("ID")) {
						//System.out.println(output.get(i).value+" "+output.get(j).value);
						output.remove(i);
						i=-1;
						break;
					}
					else {
						output.remove(j);
						j=-1;
					}
				}
				else if(startIdx1<=startIdx2 && endIdx1>=endIdx2) {
					output.remove(j);
					j=-1;
				}
				else if(startIdx2<=startIdx1 && endIdx2>=endIdx1) {
					output.remove(i);
					i=-1;
					break;
				}
			}
		}
	}
	
	public static void writeOutputToFile() throws IOException{
		BufferedWriter outfile = new BufferedWriter(new FileWriter("output.txt"));
		for(Output out : output) {
			String text = "< "+out.token.name+" > : -"+out.value+"-";
			outfile.write(text+'\n');
		}
		outfile.close();
	}
	
	public static void main(String[] args) throws IOException {
		initTokens();
		readInputFile();
		//System.out.println(tokens.size());
		for(Token token : tokens) {
			Pattern pattern = Pattern.compile(token.regex);
			Matcher matcher = pattern.matcher(targetString);
			//System.out.println(token.regex);
			while (matcher.find())
			{
				//System.out.println("YES");

				String matcherString = matcher.group();
				int startIdx=matcher.start();
				int endIdx=matcher.end();
				/*System.out.println(token.regex+" "+token.name);;
				System.out.println("Found a match: " + matcherString );
				System.out.println("Start position: " + startIdx);
				System.out.println("End position: " + endIdx);*/

				output.add(new Output(startIdx,endIdx,matcherString,new Token(token.name,token.regex)));
			}
		}
		modify();
		sort();
		writeOutputToFile();
		
		//		for(Output out : output) {
		//			//System.out.println(out.startIndx+" "+out.endIndx+" "+out.token.name+" "+out.value);
		//			System.out.println("< "+out.token.name+" > : -"+out.value+"-");
		//		}

		/*
		  code el lab
		  String regex = "\\d{1,}\\.\\d{1,}";
		Pattern pattern = Pattern.compile(regex);
		String targetString = "You can email1.12515 65464.463 235235.23523 me at g_andy@example.com or andy@example.net to get more info";
		Matcher matcher = pattern.matcher(targetString);
		while (matcher.find())
		{
			System.out.println("Found a match: " + matcher.group());
			System.out.println("Start position: " + matcher.start());
			System.out.println("End position: " + matcher.end());
		}*/
	}

}
