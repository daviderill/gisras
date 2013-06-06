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
	private JTextArea txtFileSdf;
	private JButton btnFileSdf;
	private JButton btnAccept;
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
	
	private JPanel panel_4;
	private JTabbedPane tabbedPane;
	private JScrollPane scrollPane_2;
	private JCheckBox chkClearInfo;
	private JCheckBox chkCreateFile;
	private JCheckBox chkCaseNew;
	private JLabel lblNombre;
	private JTextField txtSchemaName;
	private JCheckBox chkCaseLoad;
	private JCheckBox chkCaseDelete;

	
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
		btnAccept.setEnabled(isEnabled);
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
	

	// Main Panel
	public boolean isClearInfoSelected() {
		return chkClearInfo.isSelected();
	}
	
	public boolean isCreateFileSelected() {
		return chkCreateFile.isSelected();
	}

	public boolean isCaseNewSelected() {
		return chkCaseNew.isSelected();
	}

	public boolean isCaseLoadSelected() {
		return chkCaseLoad.isSelected();
	}
	
	public boolean isCaseDeleteSelected() {
		return chkCaseDelete.isSelected();
	}
	
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
		tabbedPane.addTab(
				BUNDLE.getString("Form.panel_1.title"), null, panel_1, null); //$NON-NLS-1$
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

		chkRemember = new JCheckBox(
				BUNDLE.getString("Form.chckbxNewCheckBox.text")); //$NON-NLS-1$
		chkRemember.setSelected(true);
		panel.add(chkRemember, "cell 3 7,aligny baseline");

		btnTest = new JButton(BUNDLE.getString("Form.btnTest.text")); //$NON-NLS-1$
		btnTest.setMinimumSize(new Dimension(107, 23));
		btnTest.setActionCommand(BUNDLE.getString("Form.btnTest.actionCommand")); //$NON-NLS-1$
		panel.add(btnTest, "cell 5 7,alignx right");


		// Panel gisRAS
		panel_4 = new JPanel();
		tabbedPane.addTab(
				BUNDLE.getString("Form.panel_3.title"), null, panel_4, null); //$NON-NLS-1$
		panel_4.setLayout(new MigLayout("", "[8px][:70.00:70px][90.00px][152.00][:114.00:100px][]", "[8px][30px][30][44][8px][30px][30][30][]"));

		chkClearInfo = new JCheckBox();
		chkClearInfo.setText(BUNDLE.getString("Form.chkDelete.text")); //$NON-NLS-1$
		panel_4.add(chkClearInfo, "cell 1 1 3 1");

		chkCreateFile = new JCheckBox();
		chkCreateFile.setText(BUNDLE
				.getString("Form.chckbxGenerarElFichero.text")); //$NON-NLS-1$
		panel_4.add(chkCreateFile, "cell 1 2 4 1");

		JLabel label = new JLabel();
		label.setText(BUNDLE.getString("Form.label.text")); //$NON-NLS-1$
		panel_4.add(label, "cell 1 3,alignx right");
		
				btnFileSdf = new JButton();
				btnFileSdf.setActionCommand(BUNDLE
						.getString("Form.btnFileInp.actionCommand")); //$NON-NLS-1$
				btnFileSdf.setText("...");
				btnFileSdf.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel_4.add(btnFileSdf, "cell 5 3,alignx center");

		chkCaseNew = new JCheckBox();
		chkCaseNew.setText(BUNDLE.getString("Form.chckbxGuardarElCaso.text")); //$NON-NLS-1$
		panel_4.add(chkCaseNew, "cell 1 5 2 1");

		lblNombre = new JLabel(BUNDLE.getString("Form.lblNombre.text")); //$NON-NLS-1$
		panel_4.add(lblNombre, "cell 3 5,alignx right");

		txtSchemaName = new JTextField();
		txtSchemaName.setText(BUNDLE.getString("Form.textField.text_1")); //$NON-NLS-1$
		panel_4.add(txtSchemaName, "cell 4 5 2 1,growx");
		txtSchemaName.setColumns(10);

		chkCaseLoad = new JCheckBox();
		chkCaseLoad.setText(BUNDLE.getString("Form.chckbxCargarUnCaso.text")); //$NON-NLS-1$
		panel_4.add(chkCaseLoad, "cell 1 6");

		JLabel lblSelectSchema = new JLabel(
				BUNDLE.getString("Form.lblSelectSchema.text")); //$NON-NLS-1$
		panel_4.add(lblSelectSchema, "cell 3 6 1 2,alignx right");

		cboSchema = new JComboBox<String>();
		cboSchema.setPreferredSize(new Dimension(24, 20));
		cboSchema.setActionCommand(BUNDLE
				.getString("Form.cboSchema.actionCommand")); //$NON-NLS-1$
		cboSchema.setMinimumSize(new Dimension(150, 20));
		panel_4.add(cboSchema, "cell 4 6 2 2,growx");

		scrollPane_2 = new JScrollPane();
		panel_4.add(scrollPane_2, "cell 2 3 3 1,grow");

		txtFileSdf = new JTextArea();
		scrollPane_2.setViewportView(txtFileSdf);
		txtFileSdf.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtFileSdf.setLineWrap(true);

		chkCaseDelete = new JCheckBox();
		chkCaseDelete.setText(BUNDLE.getString("Form.chkCaseDelete.text")); //$NON-NLS-1$
		panel_4.add(chkCaseDelete, "cell 1 7");
				
		btnAccept = new JButton();
		btnAccept.setEnabled(false);
		btnAccept.setText(BUNDLE.getString("Form.btnAccept.text")); //$NON-NLS-1$
		btnAccept.setName("btn_accept_postgis");
		btnAccept.setActionCommand(BUNDLE.getString("Form.btnAccept.actionCommand")); //$NON-NLS-1$
		panel_4.add(btnAccept, "flowx,cell 4 8,alignx left");

		// Select Database Options by default
		tabbedPane.setSelectedIndex(0);
		panel_4.setVisible(false);

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
		
		// Panel Postgis
		cboSchema.addActionListener(this);
		btnFileSdf.addActionListener(this);
		btnAccept.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controller.action(e.getActionCommand());
	}

	
}