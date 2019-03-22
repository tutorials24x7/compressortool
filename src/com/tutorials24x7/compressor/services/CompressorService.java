package com.tutorials24x7.compressor.services;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.tutorials24x7.compressor.utils.FileUtil;
import com.tutorials24x7.compressor.views.RootFrame;
import com.yahoo.platform.yui.compressor.JarClassLoader;
import com.yahoo.platform.yui.compressor.YUICompressor;

public class CompressorService {

	private RootFrame rootFrame;
	
	public RootFrame getRootFrame() {

		return rootFrame;
	}

	public void setRootFrame( RootFrame rootFrame ) {

		this.rootFrame = rootFrame;
	}

	public void processFiles( String sourceFiles, String targetFilePath, int option ) {

		String targetDir = null != targetFilePath ? FileUtil.getFileDirPath( targetFilePath ) : "";

		String[] files = sourceFiles.split( "\\n" );

		File mergedFile 	= null;
		File compressedFile	= new File( targetFilePath );

		// Clear errors
		rootFrame.clearError();

		if( targetDir.length() == 0 ) {
			
			rootFrame.showError( "Target name cannot be blank." );
			
			return;
		}

		if( files.length < 1 || files[ 0 ].length() == 0 ) {

			rootFrame.showError( "Provide at least one file for conversion." );
			
			return;
		}

		// Make target directory
		FileUtil.makeDirs( targetDir );

		// Create merged file
		switch( option ) {

			case 0: {

				mergedFile = new File( targetDir + "merged.js" );
	
				break;
			}
			case 1: {

				mergedFile = new File( targetDir + "merged.css" );
	
				break;
			}
		}

		// Clean existing files
		if( null != mergedFile && null != compressedFile ) {

			try {

				mergedFile.delete();
				mergedFile.createNewFile();
			}
			catch( IOException e1 ) {

				rootFrame.showError( "An error occured while creating the intermediary merge file. Please check the target file path." );

				//e1.printStackTrace();
				
				return;
			}

			try {

				compressedFile.delete();
				compressedFile.createNewFile();
			}
			catch( IOException e ) {

				rootFrame.showError( "An error occured while creating the target file. Please check the target file path." );

				//e.printStackTrace();
				
				return;
			}
		}

		// Merge the source files
		for( int i = 0; i < files.length; i++ ) {

			File tempFile = new File( files[ i ] );

			if( tempFile.exists() ) {

				FileUtil.concatFile( mergedFile, tempFile );
			}
		}

		// Run the compressor command
		if( null != mergedFile ) {

			String args[] = null;

			switch( option ) {

				case 0: {

					args = new String[]{ "--nomunge", "--type", "js", targetDir + "merged.js", "-o", targetFilePath };
					
					break;
				}
				case 1: {

					args = new String[]{ "--nomunge", "--type", "css", targetDir + "merged.css", "-o", targetFilePath };

					break;
				}
			}

			ClassLoader loader = new JarClassLoader();

	        Thread.currentThread().setContextClassLoader( loader );

			try {

				Class c = loader.loadClass( YUICompressor.class.getName() );

				Method main = c.getMethod( "main", new Class[]{ String[].class } );

				main.invoke( null, new Object[]{ args } );
				
				mergedFile.delete();
			}
			catch( ClassNotFoundException e ) {

				rootFrame.showError( "Failed to process your request." );

				//e.printStackTrace();
			} 
			catch( SecurityException e ) {

				rootFrame.showError( "Failed to process your request." );

				//e.printStackTrace();
			}
			catch( NoSuchMethodException e ) {

				rootFrame.showError( "Failed to process your request." );

				//e.printStackTrace();
			}
			catch( IllegalArgumentException e ) {

				rootFrame.showError( "Failed to process your request." );

				//e.printStackTrace();
			}
			catch( IllegalAccessException e ) {

				rootFrame.showError( "Failed to process your request." );

				//e.printStackTrace();
			} 
			catch( InvocationTargetException e ) {

				rootFrame.showError( "Failed to process your request." );

				//e.printStackTrace();
			}
		}
	}

}
