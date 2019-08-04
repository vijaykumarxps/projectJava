package com.landg.myaccount.ui;

import com.landg.myaccount.SQL.DBConnection;
import com.landg.myaccount.SQL.SQLQueries;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

import javax.jws.soap.SOAPBinding;
import javax.swing.*;

import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

import static com.landg.myaccount.ui.EnvConstants.*;


public class Dashboard extends JFrame implements ActionListener {
    protected static final long SLEEP_TIME = 3 * 1000;

    private static JDialog dialog;
    String username,password,jdbc;
    JButton dbCheckButton;
    private JComboBox envCombo;
    private JLabel envLabel, fileLabel, dbLabel,excelLabel,excelResultLabel,tableLabel,tableResultLabel,excelMultLabel,excelMultResultLabel;
    JPanel envPanel,filePanel,excelTextPanel,dbPanel,loadPanel,tablePanel,excelMultPanel;
    JMenuBar mb;
    JMenu file;
    JMenuItem open;
    JTextArea ta;
    JLabel iconLabel;
    private static final int DEFAULT_SIZE = 18;
    Dashboard() {
        super("Welcome to MYAccount");
        //setLayout(new FlowLayout());
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(grid);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,1000);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        envPanel = new JPanel();
        //Environment
        String env_arr[] = {"","FTE","PREPROD","PRODUCTION"};
        Font font = new Font("Segoe Script", Font.BOLD, 20);
        envLabel = new JLabel("Select the Environment : ");
        envLabel.setFont(new Font("Serif", Font.PLAIN, DEFAULT_SIZE));
        envCombo = new JComboBox(env_arr);
        //panel.add(envLabel);
        envCombo.addActionListener(this);
        envPanel.add(envLabel);
        envPanel.add(envCombo);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        this.add(envPanel, gbc);

        //File
        filePanel = new JPanel();
        fileLabel = new JLabel("Select the file");
        open=new JMenuItem("Open File");
        open.addActionListener(this);
        file=new JMenu("Select the file");
        file.add(open);
        mb=new JMenuBar();
        mb.setBounds(0,0,800,20);
        mb.add(file);
        filePanel.add(mb);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;

        this.add(filePanel,gbc);
        //db
        dbPanel = new JPanel();

        dbCheckButton = new JButton("Check table");
        dbCheckButton.addActionListener(this);
        dbPanel.add(dbCheckButton);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 3;
        gbc.gridy = 0;

        this.add(dbPanel,gbc);
        //View Result
        excelTextPanel = new JPanel();
        excelLabel = new JLabel("Number of user(s) in excel : ",SwingConstants.LEFT);
        excelLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        excelResultLabel = new JLabel("0");
        gbc.gridx = 0;
        gbc.gridy = 1;
        excelTextPanel.add(excelLabel);
        excelTextPanel.add(excelResultLabel);
        excelTextPanel.setBackground(Color.ORANGE);
        this.add(excelTextPanel,gbc);

        /*excelMultPanel = new JPanel();
        excelMultLabel = new JLabel("No of delicate user(s) in excel : ",SwingConstants.LEFT);
        excelMultLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        excelMultResultLabel = new JLabel("0");
        gbc.gridx = 0;
        gbc.gridy = 2;
        excelMultPanel.add(excelMultLabel);
        excelMultPanel.add(excelMultResultLabel);
        excelMultPanel.setBackground(Color.RED);
        this.add(excelMultPanel,gbc);*/

        tablePanel = new JPanel();
        tableLabel = new JLabel("Number of user(s) in table : ");
        tableResultLabel = new JLabel("0");
        gbc.gridx = 0;
        gbc.gridy = 3;
        tablePanel.add(tableLabel);
        tablePanel.add(tableResultLabel);
        tablePanel.setBackground(Color.GREEN);
        this.add(tablePanel,gbc);

        this.pack();
        this.setVisible(true);



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getSource()==dbCheckButton);
        if(e.getSource()==open){
            JFileChooser fc=new JFileChooser();
            int i=fc.showOpenDialog(this);
            if(i==JFileChooser.APPROVE_OPTION){
                File f=fc.getSelectedFile();
                final String filepath=f.getPath();

                SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>(){
                    @Override
                    protected Void doInBackground() throws Exception {
                        Thread.sleep(SLEEP_TIME);
                        readExcel(filepath);
                        return null;
                    }
                };
                processing(e,mySwingWorker);

            }
        }
        else if(e.getSource()==dbCheckButton) {
            SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>(){
                @Override
                protected Void doInBackground() throws Exception {
                    Thread.sleep(SLEEP_TIME);
                    // mimic some long-running process here...
                    //readExcel(filepath);
                    checkUserTable(username,password,jdbc);
                    return null;
                }
            };
            processing(e,mySwingWorker);
        }
        else if(e.getSource()==envCombo) {
            if(envCombo.getSelectedIndex() == 0) {
                username = "";
                password = "";
                jdbc = "";
            }
            else {
                username = String.valueOf(USERNAME.getValue(envCombo.getSelectedItem().toString()));
                password = String.valueOf(PASSWORD.getValue(envCombo.getSelectedItem().toString()));
                jdbc = String.valueOf(JDBC.getValue(envCombo.getSelectedItem().toString()));

            }
            System.out.println("Username::"+username);

        }

    }

    private void readExcel(String pathToExcel) {
        //dialog.setVisible(true);
        try {
            Workbook workbook = WorkbookFactory.create(new FileInputStream(pathToExcel));
            // If you have only one sheet you can get it by index of the sheet
            Sheet sheet = workbook.getSheetAt(0);
            readExcelSheet(sheet);

        } catch (EncryptedDocumentException | IOException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void readExcelSheet(Sheet sheet) throws ParseException{
        System.out.println("Starting to read sheet- " + sheet.getSheetName());
        Iterator<Row> rowItr = sheet.iterator();
        List<User> userList = new ArrayList<>();

        // Iterate each row in the sheet
        while(rowItr.hasNext()) {
            User user = new User();
            Row row = rowItr.next();
            // First row is header so skip it
            if(row.getRowNum() == 0) {
                continue;
            }
            Iterator<Cell> cellItr = row.cellIterator();
            // Iterate each cell in a row
            while(cellItr.hasNext()) {

                Cell cell = cellItr.next();
                int index = cell.getColumnIndex();
                switch(index) {
                    case 0:
                        user.setPartyID((String)getValueFromCell(cell));
                        break;
                    case 1:
                       // user.setLastName((String)getValueFromCell(cell));
                        break;
                    case 2:
                        //user.setEmail((String)getValueFromCell(cell));
                        break;
                    case 3:
                        //user.setDOB((Date)getValueFromCell(cell));
                        break;
                }
            }
            userList.add(user);

        }
        excelResultLabel.setText(Integer.toString(userList.size()));
        for(User user : userList) {
            System.out.println(user.getPartyID() );
        }
    }

    // Method to get cell value based on cell type
    private Object getValueFromCell(Cell cell) {
        switch(cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                }
                return cell.getNumericCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
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

    private void checkUserTable(String username, String password, String jdbc) {
        if(username != "" && password != "" && jdbc != ""){
            DBConnection dbConnection = new DBConnection(jdbc,username,password);
            SQLQueries query = new SQLQueries();
            Connection conn = dbConnection.getConnection();
            PreparedStatement pstmt;
            ResultSet rs;
            try {
                pstmt = conn.prepareStatement(query.user_count("DEMO","ssdfs"));
                pstmt.setString(1,"000010");
                rs = pstmt.executeQuery();


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {

        }
    }



}
