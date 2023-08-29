package utils;

import java.io.File;
import java.nio.file.Files;

//import org.omg.Messaging.SyncScopeHelper;

public class Folder {

	public static void createFolder() {
		File file = new File("c:\\Directory1");
		if(!file.exists()) {
			if (!file.mkdir()) {
				System.out.println("Direct is created");
			} else {
				System.out.println("Failed to create directory!");
			}
		}

		File files = new File("c:\\Directory2\\Sub2\\Sub-Sub2");
		if(!files.exists()) {
			if (files.mkdirs()) {
				System.out.println("Mutiple directories are created");
			} else {
				System.out.println("Failed to create multiple directories");
			}
		}
	}

}
