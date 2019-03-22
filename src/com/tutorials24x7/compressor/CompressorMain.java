package com.tutorials24x7.compressor;

import java.awt.EventQueue;

import com.tutorials24x7.compressor.services.CompressorService;
import com.tutorials24x7.compressor.views.RootFrame;

public class CompressorMain {

	private String title;

	private RootFrame frame;

	private CompressorService compressorService;

	public static void main( String[] args ) {

		EventQueue.invokeLater( new Runnable() {

			public void run() {

				try {

					CompressorMain window = new CompressorMain();

					window.frame.setVisible( true );
				}
				catch( Exception e ) {

					e.printStackTrace();
				}
			}
		});
	}

	public CompressorMain() {
		
		title = "Compressor Tool - Tutorials24x7";

		compressorService = new CompressorService();

		frame = new RootFrame( title, compressorService );
		
		compressorService.setRootFrame( frame );
	}

}
