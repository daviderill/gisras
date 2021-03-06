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

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.tecnicsassociats.gisras.controller.MenuController;


public class MainFrame extends JFrame {

	private static final long serialVersionUID = -6630818426483107558L;
	private MenuController menuController;
	
	private JPanel contentPane;
	private JMenu mnWelcome;
	private JMenuItem mntmVersion;
	private JMenuItem mntmAgreements;
	private JMenuItem mntmLicense;
	private JMenuItem mntmHelp;
	private JMenuItem mntmWelcome;


	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	
	public MainFrame() {
		initConfig();
	}

	
	public void setControl(MenuController menuController) {
		this.menuController = menuController;
	}	
	
	
	private void initConfig(){

		ImageIcon image = new ImageIcon("images/imago.png");
		setIconImage(image.getImage());
		setTitle("gisRAS control panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnWelcome = new JMenu("Welcome");
		menuBar.add(mnWelcome);
		
		mntmWelcome = new JMenuItem("Welcome");
		mntmWelcome.setActionCommand("showWelcome");
		mnWelcome.add(mntmWelcome);
		
		mntmHelp = new JMenuItem("Help");
		mntmHelp.setActionCommand("openHelp");
		mnWelcome.add(mntmHelp);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		
		mntmVersion = new JMenuItem("Version");
		mntmVersion.setActionCommand("showAuthor");
		mnAbout.add(mntmVersion);
		
		mntmLicense = new JMenuItem("License");
		mntmLicense.setActionCommand("showLicense");
		mnAbout.add(mntmLicense);
		
		mntmAgreements = new JMenuItem("Agreements");
		mntmAgreements.setActionCommand("showAgreements");
		mnAbout.add(mntmAgreements);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);		

		setupListeners();
		
	}
	
	
	private void setupListeners(){

		mntmWelcome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuController.action(e.getActionCommand());				
			}
		});
		
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuController.action(e.getActionCommand());				
			}
		});

		mntmVersion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuController.action(e.getActionCommand());
			}
		});

		mntmLicense.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuController.action(e.getActionCommand());				
			}
		});		
		
		mntmAgreements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuController.action(e.getActionCommand());				
			}
		});		
		
	}


}