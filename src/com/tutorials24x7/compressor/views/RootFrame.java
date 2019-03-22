package com.tutorials24x7.compressor.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.tutorials24x7.compressor.config.CoreGlobal;
import com.tutorials24x7.compressor.services.CompressorService;

import net.miginfocom.swing.MigLayout;

public class RootFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel fileSelectorPanel;
	private JPanel actionPanel;

	private JTextArea sourceFilesArea;

	private JTextField destinationFileText;

	private JComboBox<String> comboBox;

	private JButton compressButton;

	private JLabel errorLabel;

	private CompressorService compressorService;

	public RootFrame( String title, CompressorService compressorService ) {

		this.compressorService = compressorService;

		// Window Title
		setTitle( title );
		
		// Initialize Screen
		initScreen();

		// Initialize components
		initialize();		
	}

	public void initScreen() {

		// Default close operation
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		// Calculate Screen Bounds
		GraphicsConfiguration gc	= getGraphicsConfiguration();
        Rectangle screenBounds		= gc.getBounds();
        Toolkit tk					= Toolkit.getDefaultToolkit();
        Insets desktopBounds		= tk.getScreenInsets(gc);

        // Update Globals
        CoreGlobal.screenWidth	= screenBounds.width - ( desktopBounds.left + desktopBounds.right );
        CoreGlobal.screenHeight	= screenBounds.height - ( desktopBounds.top + desktopBounds.bottom );

        if( CoreGlobal.screenWidth <= 1 || CoreGlobal.screenHeight <= 1 ) {

        	CoreGlobal.screenWidth	= 800;
            CoreGlobal.screenHeight	= 600;
        }

        // Set the Screen Bounds
        setBounds( 0, 0, CoreGlobal.screenWidth, CoreGlobal.screenHeight );

        // Create the root scroll pane
        JScrollPane scrollPane = new JScrollPane();

        getContentPane().add( scrollPane, BorderLayout.CENTER );

        // Add the content pane to scroll pane
        contentPane = new JPanel();

        scrollPane.setViewportView( contentPane );
        contentPane.setLayout( new MigLayout( "", "[grow]", "[grow][grow]" ) );

        // Add window listener
        addWindowListener( new WindowAdapter() {

        	@Override
            public void windowClosing( WindowEvent e ) {

            }
        });
	}

	public void initialize()  {
		
		// Clear main panel
		contentPane.removeAll();

		// Add the File Selector Panel
		fileSelectorPanel = new JPanel();

		fileSelectorPanel.setBackground( Color.LIGHT_GRAY );
		contentPane.add( fileSelectorPanel, "cell 0 0,grow" );
		fileSelectorPanel.setLayout( new MigLayout( "", "[768px,grow]", "[22px][296px,grow]" ) );

		JLabel lblNewLabel = new JLabel("Add the source files:");

		lblNewLabel.setFont( new Font( "Tahoma", Font.BOLD, 18 ) );
		fileSelectorPanel.add( lblNewLabel, "cell 0 0,alignx left,aligny center" );
		
		sourceFilesArea = new JTextArea();

		fileSelectorPanel.add( sourceFilesArea, "cell 0 1,grow" );
		
		actionPanel = new JPanel();

		contentPane.add( actionPanel, "cell 0 1,grow" );
		actionPanel.setLayout( new MigLayout( "", "[][300,grow][grow]", "[][][][][]" ) );
		
		JLabel fileDestLabel = new JLabel( "Destination File" );

		actionPanel.add( fileDestLabel, "cell 0 0,alignx trailing" );
		
		destinationFileText = new JTextField();

		actionPanel.add( destinationFileText, "cell 1 0,growx" );
		destinationFileText.setColumns( 10 );
		
		JLabel fileTypeLabel = new JLabel( "File Type" );

		actionPanel.add( fileTypeLabel, "cell 0 1,alignx trailing" );

		comboBox = new JComboBox<String>();

		comboBox.addItem( "Javascript" );
		comboBox.addItem( "CSS" );
		actionPanel.add( comboBox, "cell 1 1,growx" );

		compressButton = new JButton( "Compress" );

		compressButton.addMouseListener( new MouseAdapter() {

			@Override
			public void mouseClicked( MouseEvent me ) {

				processFiles();
			}
		});

		actionPanel.add( compressButton, "cell 1 3" );
		
		errorLabel = new JLabel();

		actionPanel.add( errorLabel, "cell 1 4" );
	}

	public void processFiles() {

		String sourceFiles		= sourceFilesArea.getText();
		String targetFileName 	= destinationFileText.getText();

		int option = comboBox.getSelectedIndex();

		compressorService.processFiles( sourceFiles, targetFileName, option );
	}
	
	public void showError( String errorMsg ) {
		
		errorLabel.setText( errorMsg );
	}

	public void clearError() {

		errorLabel.setText( null );
	}

}
