package com;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;

import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.ScrollPaneConstants;

public class Program {

	private JFrame frame;
	private JTabbedPane tabbedPane;
	private JLabel lblUploadData;
	private JLabel lblDataSourcecsv;
	private JLabel filePathlbl;
	private String directoryPath;
	private String filePath;
	private JLabel lblSetUpAttributes;
	private JLabel label;
	private JLabel lblNewLabel;
	private JTextField numberOfDistinghushValueTxt;
	private JScrollPane scrollPane;
	private JList<Attribute> discreteList;
	private JLabel lblDiscrete;
	private JScrollPane scrollPane_1;
	private JList<Attribute> continuousList;
	private JLabel lblContinuous;
	private JLabel targetlbl;
	private JScrollPane scrollPane_2;
	private JList<Attribute> targetList;
	private JList<Attribute> ignoreList;
	private ArrayList<Attribute> originalDataAttributes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Program window = new Program();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Program() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1133, 729);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(572, 19, 526, 625);
		frame.getContentPane().add(tabbedPane);

		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".csv only","csv");
				fileChooser.setFileFilter(filter);
				int result = fileChooser.showOpenDialog(frame.getContentPane());
				if (result == JFileChooser.APPROVE_OPTION) {
					try {
						File file = fileChooser.getSelectedFile();
						directoryPath = file.getParent();
						filePath = file.getAbsolutePath();
						filePathlbl.setText(filePath);
						String fileExtension = filePath.substring(filePath.length()-3, filePath.length());
						fileExtension = fileExtension.toLowerCase().trim();
						if(fileExtension.equals("csv"))
						{
							readDataToJTable(filePath);
						}
						else {
							JOptionPane.showMessageDialog(frame.getContentPane(),"The file you choose is not .csv type!","ERROR",JOptionPane.ERROR_MESSAGE);
						}
					} catch (Exception err) {
						JOptionPane.showMessageDialog(frame.getContentPane(),
								err.getMessage(), "ERROR",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnBrowse.setBounds(432, 77, 89, 23);
		frame.getContentPane().add(btnBrowse);

		lblUploadData = new JLabel("Upload Data");
		lblUploadData.setForeground(Color.BLACK);
		lblUploadData.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblUploadData.setBounds(24, 18, 97, 23);
		frame.getContentPane().add(lblUploadData);

		lblDataSourcecsv = new JLabel("Data Source (.csv) :");
		lblDataSourcecsv.setForeground(Color.BLACK);
		lblDataSourcecsv.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblDataSourcecsv.setBounds(24, 52, 124, 23);
		frame.getContentPane().add(lblDataSourcecsv);

		filePathlbl = new JLabel("Choose a csv file first ...");
		filePathlbl.setBounds(24, 77, 379, 23);
		frame.getContentPane().add(filePathlbl);

		lblSetUpAttributes = new JLabel("Set Up Attribute Type");
		lblSetUpAttributes.setForeground(Color.BLACK);
		lblSetUpAttributes.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblSetUpAttributes.setBounds(24, 144, 145, 23);
		frame.getContentPane().add(lblSetUpAttributes);

		label = new JLabel(
				".......................................................................................................................................................");
		label.setBounds(10, 111, 511, 14);
		frame.getContentPane().add(label);

		lblNewLabel = new JLabel("Classify attributes with");
		lblNewLabel.setBounds(24, 189, 145, 14);
		frame.getContentPane().add(lblNewLabel);

		numberOfDistinghushValueTxt = new JTextField();
		numberOfDistinghushValueTxt.setBounds(170, 186, 46, 20);
		frame.getContentPane().add(numberOfDistinghushValueTxt);
		numberOfDistinghushValueTxt.setColumns(10);

		JLabel lblDistinguishValueAs = new JLabel(
				"distinguish value as numeric attribute");
		lblDistinguishValueAs.setBounds(226, 189, 237, 14);
		frame.getContentPane().add(lblDistinguishValueAs);

		JButton btnSet = new JButton("Set");
		btnSet.setBounds(455, 185, 89, 23);
		frame.getContentPane().add(btnSet);

		lblDiscrete = new JLabel("Discrete");
		lblDiscrete.setForeground(Color.BLACK);
		lblDiscrete.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblDiscrete.setBounds(65, 283, 61, 23);
		frame.getContentPane().add(lblDiscrete);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(24, 303, 135, 248);
		frame.getContentPane().add(scrollPane);

		discreteList = new JList<Attribute>();
		scrollPane.setViewportView(discreteList);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(387, 303, 135, 248);
		frame.getContentPane().add(scrollPane_1);

		continuousList = new JList<Attribute>();
		scrollPane_1.setViewportView(continuousList);

		lblContinuous = new JLabel("Continuous");
		lblContinuous.setForeground(Color.BLACK);
		lblContinuous.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblContinuous.setBounds(420, 283, 94, 23);
		frame.getContentPane().add(lblContinuous);

		targetlbl = new JLabel("Target Attribute");
		targetlbl.setForeground(Color.BLACK);
		targetlbl.setFont(new Font("Times New Roman", Font.BOLD, 14));
		targetlbl.setBounds(227, 216, 124, 23);
		frame.getContentPane().add(targetlbl);

		scrollPane_2 = new JScrollPane();
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_2.setBounds(209, 242, 135, 64);
		frame.getContentPane().add(scrollPane_2);

		targetList = new JList<Attribute>();
		scrollPane_2.setViewportView(targetList);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_3.setBounds(209, 541, 135, 64);
		frame.getContentPane().add(scrollPane_3);
		
		ignoreList = new JList<Attribute>();
		scrollPane_3.setViewportView(ignoreList);
		
		JLabel lblIgnoreAttribute = new JLabel("Ignore Attribute");
		lblIgnoreAttribute.setForeground(Color.BLACK);
		lblIgnoreAttribute.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblIgnoreAttribute.setBounds(227, 517, 124, 23);
		frame.getContentPane().add(lblIgnoreAttribute);
	}
	public void readDataToJTable(String filePath) {
		final String csvFile = new String(filePath);
		Thread thread = new Thread(new Runnable() {
			String line = "";
			ArrayList<String[]> dataList = new ArrayList<String[]>();
			ArrayList<Map<String, Integer>> dataStatistics = new ArrayList<Map<String,Integer>>();
			int counter=0;

			public void run() {
				try {
					FileReader fileReader = new FileReader(csvFile);
					BufferedReader bufferedReader = new BufferedReader(
							fileReader);
					String[] columnName = bufferedReader.readLine().split(",");
					originalDataAttributes = new ArrayList<Attribute>();
					for(int i=0;i<columnName.length;i++)
					{
						originalDataAttributes.add(new Attribute(columnName[i],i));
						dataStatistics.add(new HashMap<String,Integer>());
					}
					while ((line = bufferedReader.readLine()) != null) {
						String[] rowData = line.split(",");
						dataList.add(rowData);
						for(int i=0;i<rowData.length;i++)
						{
							String key = rowData[i];
							if(dataStatistics.get(i).containsKey(key))
							{
								int value = dataStatistics.get(i).get(key);
								value++;
								dataStatistics.get(i).put(key, value);
							}
							else {
								dataStatistics.get(i).put(key, 1);
							}
						}
						counter++;
						if(counter==200) break;
					}
					
					fileReader.close();
					bufferedReader.close();
					
					DefaultListModel<Attribute> continuousListModel = new DefaultListModel<Attribute>();
					DefaultListModel<Attribute> discreteListModel = new DefaultListModel<Attribute>();;
					
					for(int i=0;i<dataStatistics.size();i++){
						if(dataStatistics.get(i).size()>=5)
						{
							originalDataAttributes.get(i).setAttributeType(0);
							continuousListModel.addElement(originalDataAttributes.get(i));
						}
						else {
							originalDataAttributes.get(i).setAttributeType(1);
							discreteListModel.addElement(originalDataAttributes.get(i));
						}
					}
					continuousList.setModel(continuousListModel);
					discreteList.setModel(discreteListModel);
					
					String[][] dataArray =  dataList.toArray(new String[0][]);
					DefaultTableModel model = new DefaultTableModel(dataArray,
							columnName);
					JTable table = new JTable(model);
					table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					JScrollPane jsPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					tabbedPane.addTab("Preview top 200", new JScrollPane(jsPane));
					
				

				} catch (Exception e) {
					final String errorMsg = new String(e.getMessage());
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							JOptionPane.showMessageDialog(
									frame.getContentPane(), errorMsg, "ERROR",
									JOptionPane.ERROR_MESSAGE);
						}
					});
				}

			}
		});
		thread.start();

	}
}
