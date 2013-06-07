/*
 * This file is part of gisRAS
 * Copyright (C) 2013 GITS
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Author:
 *   David Erill <daviderill79@gmail.com>
 */
package com.tecnicsassociats.gisras.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

import com.tecnicsassociats.gisras.controller.MainController;
import com.tecnicsassociats.gisras.util.Utils;
import com.tecnicsassociats.gisras.controller.DatabaseController;


public class Form extends JPanel implements ActionListener {

	private static final long serialVersionUID = -2576460232916596200L;
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("form"); //$NON-NLS-1$

	private MainController controller;
	private DatabaseController databaseController;	
	
	private JFrame f;
	private JButton btnFileSdf;
	private JComboBox<String> cboSchema;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JComboBox<String> cboDriver;
	private JLabel lblPort;
	private JLabel lblIp;
	private JTextField txtIP;
	private JTextField txtPort;
	private JLabel lblDatabase;
	private JTextField txtDatabase;
	private JLabel lblUser;
	private JTextField txtUser;
	private JLabel lblPassword;
	private JPasswordField txtPassword;
	private JButton btnTest;
	private JCheckBox chkRemember;
	
	private JPanel panel_2;
	private JTabbedPane tabbedPane;
	private JLabel lblNombre;
	private JPanel panel_3;
	private JLabel lblDataManager;
	private JButton btnClearData;
	private JButton btnLoadRaster;
	private JButton btnExportSdf;
	private JLabel label;
	private JScrollPane scrollPane;
	private JTextArea txtFileSdf;
	private JPanel panel_4;
	private JLabel lblSchemaManager;
	private JButton btnSaveCase;
	private JButton btnLoadCase;
	private JButton btnDeleteCase;
	private JTextField txtSchemaName;
	private JLabel lblAscFile;
	private JTextArea txtFileAsc;
	private JScrollPane scrollPane_1;
	private JButton btnFileAsc;

	
	public Form() {
		try {
			initConfig();
			enableButtons(false);
		} catch (MissingResourceException e) {
			Utils.showError(e.getMessage(), "", "Error");
			System.exit(ERROR);
		}
	}

	public void setControl(DatabaseController databaseController) {
		this.databaseController = databaseController;
	}
	
	public void setControl(MainController nodeController) {
		this.controller = nodeController;
	}

	public JFrame getFrame() {
		return new JFrame();
	}

	public void setFrame(JFrame frame) {
		this.f = frame;
	}

	public JDialog getDialog() {
		return new JDialog();
	}

	
	// Panel Database Options
	public void enableButtons(boolean isEnabled) {
	}

	public Integer getDriver() {
		return cboDriver.getSelectedIndex();
	}

	public String getHost() {
		return txtIP.getText().trim();
	}

	public void setHost(String text) {
		txtIP.setText(text);
	}

	public String getPort() {
		return txtPort.getText().trim();
	}

	public void setPort(String text) {
		txtPort.setText(text);
	}

	public String getDatabase() {
		return txtDatabase.getText().trim();
	}

	public void setDatabase(String text) {
		txtDatabase.setText(text);
	}

	public String getUser() {
		return txtUser.getText().trim();
	}

	public void setUser(String text) {
		txtUser.setText(text);
	}

	@SuppressWarnings("deprecation")
	public String getPassword() {
		return txtPassword.getText();
	}

	public void setPassword(String path) {
		txtPassword.setText(path);
	}

	public boolean getRemember() {
		return chkRemember.isSelected();
	}
	

	// Panel Data Manager
	public void setNewSchemaName(String projectName) {
		txtSchemaName.setText(projectName.trim());
	}
	
	public String getNewSchemaName() {
		return txtSchemaName.getText().trim();
	}
	
	public void setSchemas(Vector<String> v) {
		ComboBoxModel<String> cbm = new DefaultComboBoxModel<String>(v);
		cboSchema.setModel(cbm);
	}
	
	public String getSchema() {
		String elem = "";
		if (cboSchema.getSelectedIndex() != -1) {
			elem = cboSchema.getSelectedItem().toString();
		}
		return elem;
	}

	public void setFileSdf(String path) {
		txtFileSdf.setText(path);
	}
	
	public void setFileAsc(String path) {
		txtFileAsc.setText(path);
	}	

	public void close() {
		f.setVisible(false);
		f.dispose();
	}

	
	private void initConfig() throws MissingResourceException {

		setLayout(new MigLayout("", "[8.00][:531px:531px][40.00]","[10px][410.00][12]"));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(tabbedPane, "cell 1 1,grow");

		// Panel Database Options
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab(BUNDLE.getString("Form.panel_1.title"), null, panel_1, null); //$NON-NLS-1$
		panel_1.setLayout(new MigLayout("", "[10][][380]",
				"[5][208.00][10][80][]"));

		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.add(panel, "cell 1 1 2 1,grow");
		panel.setLayout(new MigLayout("", "[5][60][15][140][11.00][125.00]",
				"[4][24][24][24][24][24][24][]"));

		lblNewLabel = new JLabel(BUNDLE.getString("Form.lblNewLabel.text_2")); //$NON-NLS-1$
		panel.add(lblNewLabel, "cell 1 1");

		cboDriver = new JComboBox<String>();
		cboDriver.setPreferredSize(new Dimension(24, 20));
		cboDriver.setMinimumSize(new Dimension(24, 20));
		cboDriver.setModel(new DefaultComboBoxModel(new String[] {"PG-9.2+PostGIS-2.0"}));
		panel.add(cboDriver, "cell 3 1,growx");

		lblIp = new JLabel(BUNDLE.getString("Form.lblIp.text")); //$NON-NLS-1$
		panel.add(lblIp, "cell 1 2");

		txtIP = new JTextField();
		txtIP.setText(BUNDLE.getString("Form.textField.text")); //$NON-NLS-1$
		panel.add(txtIP, "cell 3 2,growx");
		txtIP.setColumns(10);

		lblPort = new JLabel(BUNDLE.getString("Form.lblPort.text")); //$NON-NLS-1$
		panel.add(lblPort, "cell 1 3,alignx left");

		txtPort = new JTextField();
		txtPort.setText(BUNDLE.getString("Form.txtPort.text")); //$NON-NLS-1$
		txtPort.setColumns(10);
		panel.add(txtPort, "cell 3 3,growx");

		lblDatabase = new JLabel(BUNDLE.getString("Form.lblDatabase.text")); //$NON-NLS-1$
		panel.add(lblDatabase, "cell 1 4");

		txtDatabase = new JTextField();
		txtDatabase.setText("");
		txtDatabase.setColumns(10);
		panel.add(txtDatabase, "cell 3 4,growx");

		lblUser = new JLabel(BUNDLE.getString("Form.lblUser.text")); //$NON-NLS-1$
		panel.add(lblUser, "cell 1 5");

		txtUser = new JTextField();
		txtUser.setText("postgres");
		txtUser.setColumns(10);
		panel.add(txtUser, "cell 3 5,growx");

		lblPassword = new JLabel(BUNDLE.getString("Form.lblPassword.text")); //$NON-NLS-1$
		panel.add(lblPassword, "cell 1 6");

		txtPassword = new JPasswordField();
		txtPassword.setText("");
		panel.add(txtPassword, "cell 3 6,growx");

		chkRemember = new JCheckBox(BUNDLE.getString("Form.chckbxNewCheckBox.text")); //$NON-NLS-1$
		chkRemember.setSelected(true);
		panel.add(chkRemember, "cell 3 7,aligny baseline");

		btnTest = new JButton(BUNDLE.getString("Form.btnTest.text")); //$NON-NLS-1$
		btnTest.setMinimumSize(new Dimension(107, 23));
		btnTest.setActionCommand(BUNDLE.getString("Form.btnTest.actionCommand")); //$NON-NLS-1$
		panel.add(btnTest, "cell 5 7,alignx right");


		// Panel gisRAS
		panel_2 = new JPanel();
		tabbedPane.addTab(BUNDLE.getString("Form.panel_3.title"), null, panel_2, null); //$NON-NLS-1$
		panel_2.setLayout(new MigLayout("", "[40px][90.00px][152.00][:114.00:100px][]", "[8px][15][30][30px][50][][10][15][100][30px][]"));
		
		lblDataManager = new JLabel(BUNDLE.getString("Form.lblDataManager.text")); //$NON-NLS-1$
		lblDataManager.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_2.add(lblDataManager, "cell 0 1 4 1,alignx center");
		
		panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.add(panel_3, "flowx,cell 0 2 5 4,grow");
		panel_3.setLayout(new MigLayout("", "[40px][:100px:100px,grow][:100px:100px][:100px:100px][]", "[25px][4px][45px][45]"));
		
		btnClearData = new JButton(BUNDLE.getString("Form.btnClearData.text")); //$NON-NLS-1$
		btnClearData.setMaximumSize(new Dimension(95, 23));
		btnClearData.setMinimumSize(new Dimension(95, 23));
		btnClearData.setActionCommand(BUNDLE.getString("Form.btnClearData.actionCommand")); //$NON-NLS-1$
		panel_3.add(btnClearData, "flowx,cell 1 0,alignx center,aligny center");
		
		btnLoadRaster = new JButton(BUNDLE.getString("Form.btnLoadRaster.text"));
		btnLoadRaster.setMaximumSize(new Dimension(95, 23));
		btnLoadRaster.setMinimumSize(new Dimension(95, 23));
		btnLoadRaster.setActionCommand(BUNDLE.getString("Form.btnLoadRaster.actionCommand")); //$NON-NLS-1$
		panel_3.add(btnLoadRaster, "cell 2 0,alignx center,aligny center");
		
		btnExportSdf = new JButton(BUNDLE.getString("Form.btnExportSdf.text")); //$NON-NLS-1$
		btnExportSdf.setMinimumSize(new Dimension(100, 23));
		btnExportSdf.setMaximumSize(new Dimension(100, 23));
		btnExportSdf.setActionCommand(BUNDLE.getString("Form.btnExportSdf.actionCommand")); //$NON-NLS-1$
		panel_3.add(btnExportSdf, "cell 3 0,alignx center,aligny center");
		
		label = new JLabel();
		label.setText("SDF file:");
		panel_3.add(label, "cell 0 2,alignx right");
				
		scrollPane = new JScrollPane();
		panel_3.add(scrollPane, "cell 1 2 3 1,grow");
		
		txtFileSdf = new JTextArea();
		txtFileSdf.setLineWrap(true);
		txtFileSdf.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrollPane.setViewportView(txtFileSdf);

		btnFileSdf = new JButton();
		panel_3.add(btnFileSdf, "cell 4 2");
		btnFileSdf.setActionCommand(BUNDLE.getString("Form.btnFileInp.actionCommand")); //$NON-NLS-1$
		btnFileSdf.setText("...");
		btnFileSdf.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		lblAscFile = new JLabel();
		lblAscFile.setText(BUNDLE.getString("Form.lblAscFile.text")); //$NON-NLS-1$
		panel_3.add(lblAscFile, "cell 0 3");
		
		scrollPane_1 = new JScrollPane();
		panel_3.add(scrollPane_1, "cell 1 3 3 1,grow");
		
		txtFileAsc = new JTextArea();
		scrollPane_1.setViewportView(txtFileAsc);
		txtFileAsc.setLineWrap(true);
		txtFileAsc.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		btnFileAsc = new JButton();
		btnFileAsc.setText("...");
		btnFileAsc.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnFileAsc.setActionCommand(BUNDLE.getString("Form.btnFileAsc.actionCommand")); //$NON-NLS-1$
		panel_3.add(btnFileAsc, "cell 4 3");
		
		lblSchemaManager = new JLabel(BUNDLE.getString("Form.lblSchemaManager.text")); //$NON-NLS-1$
		lblSchemaManager.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_2.add(lblSchemaManager, "cell 0 7 5 1,alignx center");
		
		panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.add(panel_4, "cell 0 8 5 1,grow");
		panel_4.setLayout(new MigLayout("", "[40px][:100px:100px][:100px:100px][:100px:100px][]", "[25px][4][][]"));
		
		btnSaveCase = new JButton(BUNDLE.getString("Form.btnSaveCase.text")); //$NON-NLS-1$
		btnSaveCase.setMaximumSize(new Dimension(95, 23));
		btnSaveCase.setMinimumSize(new Dimension(95, 23));
		btnSaveCase.setActionCommand(BUNDLE.getString("Form.btnSaveCase.actionCommand")); //$NON-NLS-1$
		panel_4.add(btnSaveCase, "cell 1 0,alignx center,aligny center");
		
		btnLoadCase = new JButton(BUNDLE.getString("Form.btnLoadCase.text")); //$NON-NLS-1$
		btnLoadCase.setMaximumSize(new Dimension(95, 23));
		btnLoadCase.setMinimumSize(new Dimension(95, 23));
		btnLoadCase.setActionCommand(BUNDLE.getString("Form.btnLoadCase.actionCommand")); //$NON-NLS-1$
		panel_4.add(btnLoadCase, "cell 2 0,alignx center,aligny center");
		
		btnDeleteCase = new JButton(BUNDLE.getString("Form.btnDeleteCase.text")); //$NON-NLS-1$
		btnDeleteCase.setMaximumSize(new Dimension(100, 23));
		btnDeleteCase.setMinimumSize(new Dimension(100, 23));
		btnDeleteCase.setActionCommand(BUNDLE.getString("Form.btnDeleteCase.actionCommand")); //$NON-NLS-1$
		panel_4.add(btnDeleteCase, "cell 3 0,alignx center,aligny center");
		
		lblNombre = new JLabel(BUNDLE.getString("Form.lblNombre.text"));
		panel_4.add(lblNombre, "cell 1 2");
		
		txtSchemaName = new JTextField();
		txtSchemaName.setText("schema_name");
		txtSchemaName.setColumns(10);
		panel_4.add(txtSchemaName, "cell 2 2 2 1,growx");
				
		JLabel lblSelectSchema = new JLabel(BUNDLE.getString("Form.lblSelectSchema.text"));
		panel_4.add(lblSelectSchema, "cell 1 3");
		
		cboSchema = new JComboBox<String>();
		panel_4.add(cboSchema, "cell 2 3 2 1,growx");
		cboSchema.setPreferredSize(new Dimension(24, 20));
		cboSchema.setActionCommand(BUNDLE.getString("Form.cboSchema.actionCommand")); //$NON-NLS-1$
		cboSchema.setMinimumSize(new Dimension(150, 20));

		// Select Database Options by default
		tabbedPane.setSelectedIndex(0);
		panel_2.setVisible(false);

		setupListeners();

	}

	
	// Setup component's listener
	private void setupListeners() {

		// Panel Database Connection
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				databaseController.action(e.getActionCommand());
			}
		});
		
		// Panel Data Manager
		btnClearData.addActionListener(this);
		btnLoadRaster.addActionListener(this);
		btnExportSdf.addActionListener(this);
		btnFileSdf.addActionListener(this);
		btnFileAsc.addActionListener(this);		

		btnSaveCase.addActionListener(this);
		btnLoadCase.addActionListener(this);
		btnDeleteCase.addActionListener(this);
		cboSchema.addActionListener(this);

	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		controller.action(e.getActionCommand());
	}

	public String getRasterFile() {
		return null;
	}

	
}