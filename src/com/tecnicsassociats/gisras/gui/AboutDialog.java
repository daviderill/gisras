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
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

public class AboutDialog extends JDialog {

	private static final long serialVersionUID = 2829254148112384387L;
	private JLabel lblInfo;
	public URI uri = null;	


	public static void main(String[] args) {
		try {
			AboutDialog dialog = new AboutDialog("About", "gisRAS version: 1.0.001");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AboutDialog(String title, String version) {

		ImageIcon image = new ImageIcon("images/imago.png");
		setIconImage(image.getImage());		
		setTitle(title);
		setSize(320, 180);
		getContentPane().setLayout(new MigLayout("", "[133.00][173.00px,grow]", "[60.00px][:15px:20px][15px][15px]"));

		final ImageIcon backgroundImage = new ImageIcon("images/logo_gits.png");
		
        JPanel panelLogo = new JPanel(new BorderLayout()) {
			private static final long serialVersionUID = 3096090575648819722L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage.getImage(), 65, 10, 170, 50, this);
            }

            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width = Math.max(backgroundImage.getIconWidth(), size.width);
                size.height = Math.max(backgroundImage.getIconHeight(), size.height);
                return size;
            }
        };
		getContentPane().add(panelLogo, "cell 0 0 2 1,alignx center,growy");

		class OpenUrlAction implements ActionListener {
		    @Override public void actionPerformed(ActionEvent e) {
		        if (Desktop.isDesktopSupported()) {
		            try {
		              Desktop.getDesktop().browse(uri);
		            } catch (IOException e1) { }
		        }
		    }
		}		

		try {
			uri = new URI("http://www.gits.ws/");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}		
		JButton button = new JButton();
		button.setFont(new Font("Tahoma", Font.BOLD, 12));
		button.setText("<HTML><FONT color=\"#000099\"><U>www.gits.ws</U></FONT></HTML>");
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setBackground(Color.WHITE);
		button.setToolTipText(uri.toString());
		button.addActionListener(new OpenUrlAction());		
		
		//String label = "<HTML><FONT size=6 color=\"#336600\">T�cnics</FONT><FONT size=6 color=\"#000000\">Associats</FONT></HTML>";
		getContentPane().add(button, "cell 0 1 2 1,alignx center");
		
		lblInfo = new JLabel("Developers: David Erill, Vicente Medina");
		lblInfo.setFont(new Font("Tahoma", Font.BOLD, 11));
		getContentPane().add(lblInfo, "cell 0 2 2 1,alignx center");	
		
		JLabel lblNewLabel = new JLabel(version);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		getContentPane().add(lblNewLabel, "cell 0 3 2 1,alignx center");
	
	}

}