package com.landg.ecom.ui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import com.landg.ecom.util.GenerateScript;

public class Dashboard {

	String applications[] = {"","REP","MyAccounts"};
	String selectedApp = "";
	JLabel expireLabel;
	JTextField dateText;
	JButton GenerateButton;
	private JProgressBar progressBar;
	JTextArea textArea; 
	private static final Insets WEST_INSETS = new Insets(2, 0, 1, 5);
	private static final Insets EAST_INSETS = new Insets(2, 5, 1, 0);
	public Dashboard() {
		super();
		gui();
		
	}
	
	public void gui() {
		
		JFrame frame = new JFrame("Script Generator");
		frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel panel = new JPanel(); 
        placeComponents(panel,layout, gbc);
       
        
        
        REPPlaceComponents(panel,layout, gbc);
        TextAreaPlaceCompents(panel,layout, gbc);
        
        frame.add(panel);
        //frame.add(REPPanel);
   
        
        frame.setVisible(true);
		
	}
	private void TextAreaPlaceCompents(JPanel panel,GridBagLayout layout,GridBagConstraints gbc) {
		 textArea = new JTextArea(800, 500);
		 JScrollPane scrollableTextArea = new JScrollPane(textArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);  
		 Dimension listSize = new Dimension(400, 150);
		 scrollableTextArea.setSize(listSize);
		 scrollableTextArea.setMaximumSize(listSize);
		 scrollableTextArea.setPreferredSize(listSize);
	     gbc = createGbc(0, 6);
	     gbc.ipady = 100;
	     gbc.weightx=0.0;
	     
	     panel.add(scrollableTextArea,gbc);   
	}
	
	private void placeComponents(JPanel panel,GridBagLayout layout,GridBagConstraints gbc) {
		
		panel.setLayout(layout);
		
		gbc = createGbc(0, 0);
		JLabel userLabel = new JLabel("Applications:");
		panel.add(userLabel,gbc);
        
        JComboBox cb=new JComboBox(applications); 
        gbc = createGbc(1, 0);
        panel.add(cb,gbc);
        
        cb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedApp = cb.getItemAt(cb.getSelectedIndex()).toString();  
           	 	System.out.println(selectedApp);
           	 	
	           	 if(selectedApp.equals("REP")) {
	           		 System.out.println(1);
	             	GenerateButton.setEnabled(true);
	             	expireLabel.setVisible(false);
	             	dateText.setVisible(false);
	             }
	             else if(selectedApp == "MyAccounts") {
	            	 System.out.println(2);
	             	expireLabel.setVisible(true);
	             	dateText.setVisible(true);
	             }
	             else {
	            	System.out.println(3);
	             	expireLabel.setVisible(false);
	             	dateText.setVisible(false);
	             	GenerateButton.setEnabled(false);
	             }
	           	 
	           	int len = textArea.getText().trim().length();
	           	if(len == 0) {
	           		textArea.setText("Selected Application :"+selectedApp);
	           	}
	           	else {
	           		textArea.append("\nSelected Application :"+selectedApp);
	           	}
			}
		});
        
   }
	
	private void REPPlaceComponents(JPanel panel, GridBagLayout layout,GridBagConstraints gbc) {
		panel.setLayout(layout);
		
		JLabel userLabel = new JLabel("Location File:");
		gbc = createGbc(0, 2);
        panel.add(userLabel,gbc);
        
        JTextField userText = new JTextField(20);
        gbc = createGbc(1, 2);
        panel.add(userText,gbc);
        
        JButton button = new JButton("Click Me!");
        gbc = createGbc(3, 2);
        panel.add(button,gbc);
        
        
        JLabel destLabel = new JLabel("Destination Location:");
        gbc = createGbc(0, 3);
        panel.add(destLabel,gbc);
        
        JTextField destText = new JTextField(20);
        gbc = createGbc(1, 3);
        panel.add(destText,gbc);
        
        JButton button1 = new JButton("Click Me!");
        gbc = createGbc(3, 3);
        panel.add(button1,gbc);
        
        
        expireLabel = new JLabel("Expire Date:");
        gbc = createGbc(0, 4);
        panel.add(expireLabel,gbc);
        
        dateText = new JTextField(20);
        gbc = createGbc(1, 4);
        panel.add(dateText,gbc);
        
        
        GenerateButton = new JButton("Generate Script");
        gbc = createGbc(0, 5);
        panel.add(GenerateButton,gbc);
        
        GenerateButton.setEnabled(true);
     	expireLabel.setVisible(false);
     	dateText.setVisible(false);
     	GenerateButton.setEnabled(false);
     	
        
        button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String file = getLocation(false);
				userText.setText(file);
				int len = textArea.getText().trim().length();
	           	if(len == 0) {
	           		textArea.setText("File Selected :"+file);
	           	}
	           	else {
	           		textArea.append("\nFile Selected :"+file);
	           	}
			}
		});
        

        button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String file = getLocation(true);
				destText.setText(file);
				int len = textArea.getText().trim().length();
	           	if(len == 0) {
	           		textArea.setText("Destination location :"+file);
	           	}
	           	else {
	           		textArea.append("\nDestination location :"+file);
	           	}
			}
		});
        
        
        GenerateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String location = userText.getText();
				String destination = destText.getText();
				String app = selectedApp;
				SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>(){
	                    @Override
	                    protected Void doInBackground() throws Exception {
	                    	GenerateScript generate = new GenerateScript();
	        				int count = generate.readFile(location, destination, app);
	        				textArea.append("\nNo of queries: "+count);
							return null;
	                    }
	                };
	                processing(e,mySwingWorker);
	                int len = textArea.getText().trim().length();
		           	if(len == 0) {
		           		textArea.setText("Scripts generated!");
		           	}
		           	else {
		           		textArea.append("\nScripts generated!");
		           	}
			}
		});
	}
	
	private GridBagConstraints createGbc(int x, int y) {
	      GridBagConstraints gbc = new GridBagConstraints();
	      gbc.gridx = x;
	      gbc.gridy = y;
	      gbc.gridwidth = 1;
	      gbc.gridheight = 1;

	      gbc.anchor = (x == 0) ? GridBagConstraints.WEST : GridBagConstraints.EAST;
	      gbc.fill = (x == 0) ? GridBagConstraints.BOTH
	            : GridBagConstraints.HORIZONTAL;

	      //gbc.insets = (x == 0) ? WEST_INSETS : EAST_INSETS;
	      gbc.weightx = (x == 0) ? 0.1 : 1.0;
	      gbc.weighty = 0.5;
	      return gbc;
	 }
	
	public String getLocation(boolean flag) {
		String location = "";
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Choose a directory to save your file: ");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Only select txt file", "txt");
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	    //jfc.setAcceptAllFileFilterUsed(false);
		int returnValue = jfc.showSaveDialog(null);
		System.out.println(returnValue);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			if(flag && jfc.getSelectedFile().isDirectory()) {
				File f = jfc.getSelectedFile().getAbsoluteFile();
				location = f.toString();
				
			}
			else if(!flag && jfc.getSelectedFile().isFile()) {
				File f = jfc.getSelectedFile().getAbsoluteFile();
				location = f.toString();
			}
			
		}
		//System.out.println("You selected the directory: " + location);
		return location;
	}
	
	private void processing(ActionEvent e, SwingWorker<Void, Void> mySwingWorker) {
        Window win = SwingUtilities.getWindowAncestor((AbstractButton)e.getSource());
        final JDialog dialog = new JDialog(win, "Dialog", Dialog.ModalityType.APPLICATION_MODAL);
        mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("state")) {
                    if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
                        dialog.dispose();
                    }
                }
            }
        });
        mySwingWorker.execute();
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(progressBar, BorderLayout.CENTER);
        panel.add(new JLabel("Please wait......."), BorderLayout.PAGE_START);
        dialog.add(panel);
        dialog.setUndecorated(true);
        dialog.pack();
        dialog.setLocationRelativeTo(win);
        dialog.setVisible(true);

    }
}
