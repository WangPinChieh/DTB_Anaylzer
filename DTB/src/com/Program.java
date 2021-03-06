package com;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.Utilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.xml.crypto.Data;

import org.omg.CORBA.portable.ValueBase;

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
	private ArrayList<Attribute> allDataAttributes;
	private Attribute target = null;
	private String oneOfTargetValue;
	private boolean isOneOfTargetValueAssigned;
	private int targetACount=0; // total number of data belong to target A
	private int targetBCount=0; // total number of data belong to target B
	private JLabel statusLabel;
	private JButton btnCollect; 
	private JLabel progressBarLabel;
	private JButton leftMoveBtn;
	private JButton rightMoveBtn;
	private JButton upMoveBtn;
	private JButton downMoveBtn;

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
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		isOneOfTargetValueAssigned = false;
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						".csv only", "csv");
				fileChooser.setFileFilter(filter);
				int result = fileChooser.showOpenDialog(frame.getContentPane());
				if (result == JFileChooser.APPROVE_OPTION) {
					try {
						File file = fileChooser.getSelectedFile();
						directoryPath = file.getParent();
						filePath = file.getAbsolutePath();
						filePathlbl.setText(filePath);
						String fileExtension = filePath.substring(
								filePath.length() - 3, filePath.length());
						fileExtension = fileExtension.toLowerCase().trim();
						if (fileExtension.equals("csv")) {
							readTop200DataToJTable(filePath);
						} else {
							JOptionPane.showMessageDialog(
									frame.getContentPane(),
									"The file you choose is not .csv type!",
									"ERROR", JOptionPane.ERROR_MESSAGE);
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

		lblDiscrete = new JLabel("Discrete");
		lblDiscrete.setForeground(Color.BLACK);
		lblDiscrete.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblDiscrete.setBounds(65, 249, 61, 23);
		frame.getContentPane().add(lblDiscrete);

		scrollPane = new JScrollPane();
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(24, 269, 135, 248);
		frame.getContentPane().add(scrollPane);

		discreteList = new JList<Attribute>();
		discreteList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				continuousList.clearSelection();
				targetList.clearSelection();
				ignoreList.clearSelection();

			}
		});
		scrollPane.setViewportView(discreteList);

		scrollPane_1 = new JScrollPane();
		scrollPane_1
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(387, 269, 135, 248);
		frame.getContentPane().add(scrollPane_1);

		continuousList = new JList<Attribute>();
		continuousList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				targetList.clearSelection();
				discreteList.clearSelection();
				ignoreList.clearSelection();

			}
		});
		scrollPane_1.setViewportView(continuousList);

		lblContinuous = new JLabel("Continuous");
		lblContinuous.setForeground(Color.BLACK);
		lblContinuous.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblContinuous.setBounds(420, 249, 94, 23);
		frame.getContentPane().add(lblContinuous);

		targetlbl = new JLabel("Target Attribute");
		targetlbl.setForeground(Color.BLACK);
		targetlbl.setFont(new Font("Times New Roman", Font.BOLD, 14));
		targetlbl.setBounds(227, 182, 124, 23);
		frame.getContentPane().add(targetlbl);

		scrollPane_2 = new JScrollPane();
		scrollPane_2
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_2
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_2.setBounds(209, 208, 135, 64);
		frame.getContentPane().add(scrollPane_2);

		targetList = new JList<Attribute>();
		targetList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				continuousList.clearSelection();
				discreteList.clearSelection();
				ignoreList.clearSelection();
			}
		});
		scrollPane_2.setViewportView(targetList);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_3
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_3.setBounds(209, 507, 135, 64);
		frame.getContentPane().add(scrollPane_3);

		ignoreList = new JList<Attribute>();
		ignoreList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				continuousList.clearSelection();
				discreteList.clearSelection();
				targetList.clearSelection();

			}
		});
		scrollPane_3.setViewportView(ignoreList);

		JLabel lblIgnoreAttribute = new JLabel("Ignore Attribute");
		lblIgnoreAttribute.setForeground(Color.BLACK);
		lblIgnoreAttribute.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblIgnoreAttribute.setBounds(227, 483, 124, 23);
		frame.getContentPane().add(lblIgnoreAttribute);

		leftMoveBtn = new JButton("\u2190");
		leftMoveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				moveAllSelectedItems("left");
			}
		});
		leftMoveBtn.setBounds(206, 364, 50, 50);
		frame.getContentPane().add(leftMoveBtn);

		upMoveBtn = new JButton("\u2191");
		upMoveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				moveAllSelectedItems("up");
			}
		});
		upMoveBtn.setBounds(255, 309, 50, 50);
		frame.getContentPane().add(upMoveBtn);

		rightMoveBtn = new JButton("\u2192");
		rightMoveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				moveAllSelectedItems("right");
			}
		});
		rightMoveBtn.setBounds(305, 364, 50, 50);
		frame.getContentPane().add(rightMoveBtn);

		downMoveBtn = new JButton("\u2193");
		downMoveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				moveAllSelectedItems("down");
			}
		});
		downMoveBtn.setBounds(255, 420, 50, 50);
		frame.getContentPane().add(downMoveBtn);

		btnCollect = new JButton("Analyze");
		
		btnCollect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				collectStatistics();
			}
		});
		btnCollect.setBounds(432, 594, 89, 50);
		frame.getContentPane().add(btnCollect);
		
		progressBarLabel = new JLabel();
		progressBarLabel.setBounds(10, 607, 220, 19);
		frame.getContentPane().add(progressBarLabel);
		
		statusLabel = new JLabel("");
		statusLabel.setBounds(242, 594, 89, 50);
		frame.getContentPane().add(statusLabel);
		
		btnCollect.setEnabled(false);
		upMoveBtn.setEnabled(false);
		leftMoveBtn.setEnabled(false);
		rightMoveBtn.setEnabled(false);
		downMoveBtn.setEnabled(false);
	}

	public void readTop200DataToJTable(String filePath) {
		btnCollect.setEnabled(true);
		upMoveBtn.setEnabled(true);
		leftMoveBtn.setEnabled(true);
		rightMoveBtn.setEnabled(true);
		downMoveBtn.setEnabled(true);
		final String csvFile = new String(filePath);
		Thread thread = new Thread(new Runnable() {
			String line = "";
			ArrayList<String[]> dataList = new ArrayList<String[]>();
			ArrayList<Map<String, Integer>> dataStatistics = new ArrayList<Map<String, Integer>>();
			int counter = 0;

			public void run() {
				try {
					FileReader fileReader = new FileReader(csvFile);
					BufferedReader bufferedReader = new BufferedReader(
							fileReader);
					String[] columnName = bufferedReader.readLine().split(",");
					allDataAttributes = new ArrayList<Attribute>();
					for (int i = 0; i < columnName.length; i++) {
						allDataAttributes.add(new Attribute(columnName[i], i));
						dataStatistics.add(new HashMap<String, Integer>());
					}
					while ((line = bufferedReader.readLine()) != null) {
						String[] rowData = line.split(",");
						dataList.add(rowData);
						for (int i = 0; i < rowData.length; i++) {
							String key = rowData[i];
							if (dataStatistics.get(i).containsKey(key)) {
								int value = dataStatistics.get(i).get(key);
								value++;
								dataStatistics.get(i).put(key, value);
							} else {
								dataStatistics.get(i).put(key, 1);
							}
						}
						counter++;
						if (counter == 200)
							break;
					}

					fileReader.close();
					bufferedReader.close();

					DefaultListModel<Attribute> continuousListModel = new DefaultListModel<Attribute>();
					DefaultListModel<Attribute> discreteListModel = new DefaultListModel<Attribute>();
					DefaultListModel<Attribute> targetListModel = new DefaultListModel<Attribute>();
					DefaultListModel<Attribute> ignoreListModel = new DefaultListModel<Attribute>();
					for (int i = 0; i < dataStatistics.size(); i++) {
						if(i==dataStatistics.size()-1){
							allDataAttributes.get(i).setAttributeType(
									Attribute.TARGET);
							targetListModel.addElement(allDataAttributes.get(i));
						}
						else if(dataStatistics.get(i).size() >= counter){
							allDataAttributes.get(i).setAttributeType(
									Attribute.IGNORE);
							ignoreListModel.addElement(allDataAttributes.get(i));
						}
						else if (dataStatistics.get(i).size() > 20) {
							allDataAttributes.get(i).setAttributeType(
									Attribute.CONTINUOUS);
							continuousListModel.addElement(allDataAttributes
									.get(i));
						} 
						else {
							allDataAttributes.get(i).setAttributeType(
									Attribute.DISCRETE);
							discreteListModel.addElement(allDataAttributes
									.get(i));
						}
					}
					continuousList.setModel(continuousListModel);
					discreteList.setModel(discreteListModel);
					targetList.setModel(targetListModel);
					ignoreList.setModel(ignoreListModel);

					String[][] dataArray = dataList.toArray(new String[0][]);
					DefaultTableModel model = new DefaultTableModel(dataArray,
							columnName);
					JTable table = new JTable(model);
					table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					JScrollPane jsPane = new JScrollPane(table,
							JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
							JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					tabbedPane.addTab("Preview top 200",
							new JScrollPane(jsPane));
					tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);

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

	public void moveAllSelectedItems(String direction) {
		List<Attribute> selectedList = new ArrayList<Attribute>();
		selectedList.addAll(targetList.getSelectedValuesList());
		selectedList.addAll(ignoreList.getSelectedValuesList());
		selectedList.addAll(continuousList.getSelectedValuesList());
		selectedList.addAll(discreteList.getSelectedValuesList());

		DefaultListModel<Attribute> targetListModel = (DefaultListModel<Attribute>) targetList
				.getModel();
		DefaultListModel<Attribute> ignoreListModel = (DefaultListModel<Attribute>) ignoreList
				.getModel();
		DefaultListModel<Attribute> discreteListModel = (DefaultListModel<Attribute>) discreteList
				.getModel();
		DefaultListModel<Attribute> continuousListModel = (DefaultListModel<Attribute>) continuousList
				.getModel();
		for (Attribute attr : selectedList) {

			if (targetListModel.contains(attr))
				targetListModel.removeElement(attr);
			else if (ignoreListModel.contains(attr))
				ignoreListModel.removeElement(attr);
			else if (continuousListModel.contains(attr))
				continuousListModel.removeElement(attr);
			else if (discreteListModel.contains(attr))
				discreteListModel.removeElement(attr);

			switch (direction) {
			case "left":
				discreteListModel.addElement(attr);
				allDataAttributes.get(attr.getAttributeIndex())
						.setAttributeType(Attribute.DISCRETE);
				break;
			case "right":
				continuousListModel.addElement(attr);
				allDataAttributes.get(attr.getAttributeIndex())
						.setAttributeType(Attribute.CONTINUOUS);
				break;
			case "up":
				targetListModel.addElement(attr);
				allDataAttributes.get(attr.getAttributeIndex())
						.setAttributeType(Attribute.TARGET);
				break;
			case "down":
				ignoreListModel.addElement(attr);
				allDataAttributes.get(attr.getAttributeIndex())
						.setAttributeType(Attribute.IGNORE);
				break;

			}

		}
		discreteList.setModel(discreteListModel);
		continuousList.setModel(continuousListModel);
		targetList.setModel(targetListModel);
		ignoreList.setModel(ignoreListModel);

	}

	public void collectStatistics() {
		ImageIcon icon = new ImageIcon(getClass().getResource("/progressBarLoading.gif"));
		progressBarLabel.setIcon(icon);
		btnCollect.setEnabled(false); 
		statusLabel.setText("Reading Data ...");
		for (Attribute attr : allDataAttributes) {
			if (attr.getAttributeType() == Attribute.TARGET)
				target = attr;
		}
		Thread thread = new Thread(new Runnable() {
			public void run() {
				FileReader fReader = null;
				BufferedReader bReader = null;
				int dataCounter = 0;
				try {
					fReader = new FileReader(filePath);
					bReader = new BufferedReader(fReader);
					bReader.readLine(); // read the column name row
					String[] splitLine;
					String line;
					while ((line = bReader.readLine()) != null) {
						splitLine = line.split(",");
						for (int i = 0; i < splitLine.length; i++) {
							Attribute currentAttribute = allDataAttributes.get(i);
							String currentValue = splitLine[currentAttribute.getAttributeIndex()];
							if (currentAttribute.getAttributeType() == Attribute.IGNORE)
								continue;
							
							else if (currentAttribute.getAttributeType() == Attribute.CONTINUOUS) {
								String key = splitLine[target.getAttributeIndex()];
								if (!isOneOfTargetValueAssigned) {
									oneOfTargetValue = key;
									isOneOfTargetValueAssigned = true;
								}
								Map<String, List<Double>> statisticsMap = currentAttribute.getContinuousStatistics();
								double parsedDouble=0.0;
								try{
									parsedDouble = Double.parseDouble(currentValue);
								}catch(Exception e){
									System.out.println(e.getMessage());
								}
								if (statisticsMap.containsKey(key)) {
									statisticsMap.get(key).add(parsedDouble);
								} else {
									List<Double> list =  new ArrayList<Double>();
										list.add(parsedDouble);
									
									statisticsMap.put(key,list);
								}
								
							} else if (currentAttribute.getAttributeType() == Attribute.DISCRETE) {
								String targetValue = splitLine[target.getAttributeIndex()];
								currentValue = splitLine[currentAttribute.getAttributeIndex()];
								Map<String, Integer[]> statisticsMap = currentAttribute.getDiscreteStatistics();

								if (!isOneOfTargetValueAssigned) {
									oneOfTargetValue = targetValue;
									isOneOfTargetValueAssigned = true;
								}

								if (statisticsMap.containsKey(currentValue)) {
									Integer[] value = statisticsMap
											.get(currentValue);
									if (targetValue.equals(oneOfTargetValue))
										value[0]++;
									else
										value[1]++;
									statisticsMap.put(currentValue, value);
								} else {
									statisticsMap.put(currentValue,
											new Integer[2]);

									if (targetValue.equals(oneOfTargetValue)) {
										Integer[] value = statisticsMap
												.get(currentValue);
										value[0] = 1;
										value[1] = 0;
										statisticsMap.put(currentValue, value);

									} else {
										Integer[] value = statisticsMap
												.get(currentValue);
										value[0] = 0;
										value[1] = 1;
										statisticsMap.put(currentValue, value);
									}

								}
								
							} else if (currentAttribute.getAttributeType() == Attribute.TARGET) {
								String targetValue = splitLine[target.getAttributeIndex()];
								Map<String, Integer[]> statisticsMap = currentAttribute.getDiscreteStatistics();

								if (!isOneOfTargetValueAssigned) {
									oneOfTargetValue = targetValue;
									isOneOfTargetValueAssigned = true;
								}

								if (statisticsMap.containsKey(targetValue)) {
									Integer[] value = statisticsMap.get(targetValue);
									value[0]++;
									statisticsMap.put(targetValue, value);
								} else {
									statisticsMap.put(targetValue,new Integer[] { 1 });

								}
							}

						}
						dataCounter++;
					}
					String[] key = target.getDiscreteStatistics().keySet().toArray(new String[0]);
					targetACount = target.getDiscreteStatistics().get(key[0])[0];
					targetBCount = target.getDiscreteStatistics().get(key[1])[0];
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							statusLabel.setText("Analyzing Data ...");
						}
					});
					loadAnalysisResultToJTable();
				} catch (FileNotFoundException e) {
							JOptionPane.showMessageDialog(null,
									e.getMessage(), "ERROR",
									JOptionPane.ERROR_MESSAGE);
				} catch (IOException e) {
							JOptionPane.showMessageDialog(null,
									e.getMessage(), "ERROR",
									JOptionPane.ERROR_MESSAGE);
				} catch(Exception e){
							JOptionPane.showMessageDialog(null,
									e.getMessage(), "ERROR",
									JOptionPane.ERROR_MESSAGE);
				}finally {
					try {
						fReader.close();
						bReader.close();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(frame.getContentPane(),
								e.getMessage(), "ERROR",
								JOptionPane.ERROR_MESSAGE);
					}

				}

			}
		});
		thread.start();
	}

	public void loadAnalysisResultToJTable(){
		String[] columnsName = new String[]{"Attribute Name","Information Value","Importance", "Gain Ratio"};
		ArrayList<String[]> dataList = new ArrayList<String[]>();
		for(Attribute attr: allDataAttributes)
		{
			if(attr.getAttributeType()==Attribute.IGNORE || attr.getAttributeType()==Attribute.TARGET) continue;
			else
			{
				String[] data = new String[4];
				data[0] = attr.getAttributeName();
				data[1] = String.valueOf(String.format("%.3f", attr.getAttributeIV()));
				data[2] = String.valueOf(String.format("%.3f",attr.getAttributeImportance()));
				data[3] = String.valueOf(String.format("%.3f",attr.getAttributeGainRatio(targetACount, targetBCount)));
				dataList.add(data);
			}
		}
		String[][] dataArray = dataList.toArray(new String[0][]);
		DefaultTableModel model = new DefaultTableModel(dataArray,columnsName);
		JTable table = new JTable(model);
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		sorter.setSortable(1, true);
		table.setRowSorter(sorter);
		JScrollPane pane = new JScrollPane(table);
		tabbedPane.add("Analysis Results",pane);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				btnCollect.setEnabled(true);
				statusLabel.setText("Done!");
				progressBarLabel.setIcon(null);
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
			}
		});
		
		
	}
}
