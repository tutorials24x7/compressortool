package com.tutorials24x7.compressor.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

	public static String getFileDirPath( String filePath ) {

		String dirPath = "";

		String[] targetDirs = filePath.split( "/" );

		for( int i = 0; i < targetDirs.length - 1; i++ ) {

			dirPath += targetDirs[ i ] + "/";
		}
		
		return dirPath;
	}

	public static void makeDirs( String path ) {

		File file = new File( path );

		file.mkdirs();
	}

	public static void concatFile( File targetFile, File sourceFile ) {

		try {

			BufferedWriter target	= new BufferedWriter(new FileWriter( targetFile, true ) );
			BufferedReader source	= new BufferedReader( new FileReader( sourceFile ) );

			String str;
			
			while ( ( str = source.readLine() ) != null ) {

				target.append( str ); 
				target.newLine();
			}

			target.newLine();

			source.close();	
			target.close();
		}
		catch( IOException e ) {

			e.printStackTrace();
		}			
	}

}
