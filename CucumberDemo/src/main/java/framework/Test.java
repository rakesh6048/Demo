package framework;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Test {
	
	public static void test1()throws IOException{
		
	  FileWriter fr=new FileWriter("c:\\Framework\\data.txt");
	  BufferedWriter br=new BufferedWriter(fr);
	  
	  br.write("This is sample");
	  br.newLine();
	  br.write("Testing tools");
	  br.newLine();
	  
	  br.close();
	}

}
