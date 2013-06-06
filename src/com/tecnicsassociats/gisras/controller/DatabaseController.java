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
package com.tecnicsassociats.gisras.controller;

import java.awt.Cursor;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.tecnicsassociats.gisras.gui.Form;
import com.tecnicsassociats.gisras.model.MainDao;
import com.tecnicsassociats.gisras.util.Encryption;
import com.tecnicsassociats.gisras.util.Utils;


public class DatabaseController {

	private Form view;
    private Properties prop;
	private boolean isConnected = false;
	private ResourceBundle bundleText;
	
	
	public DatabaseController(Form cmWindow) {
		
		this.view = cmWindow;	
        this.prop = MainDao.getPropertiesFile();
	    view.setControl(this);        
    	this.bundleText = Utils.getBundleText();
    	
	}
	
	
	public void action(String actionCommand) {
		
		Method method;
		try {
			if (Utils.getLogger() != null){
				Utils.getLogger().info(actionCommand);
			}
			method = this.getClass().getMethod(actionCommand);
			method.invoke(this);	
		} catch (Exception e) {
			if (Utils.getLogger() != null){			
				Utils.logError(e, actionCommand);
			} else{
				Utils.showError(e, actionCommand);
			}
		}
		
	}	
	
	
	public void deleteSchema(){
		
		String schemaName = view.getSchema();
        int res = JOptionPane.showConfirmDialog(this.view, Utils.getBundleString(bundleText, "delete_schema_name") + "\n" + schemaName, 
        		"INPCom", JOptionPane.YES_NO_OPTION);
        if (res == 0){
    		view.setCursor(new Cursor(Cursor.WAIT_CURSOR));	        	
        	MainDao.deleteSchema(schemaName);
        	view.setSchemas(MainDao.getSchemas());
    		view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    		Utils.showMessage("Schema deleted", "", "INPcom");
        }
        
	}	
	
	
	public void testConnection(){
		
		String host, port, db, user, password;
		
		// Get parameteres connection from view
		host = view.getHost();		
		port = view.getPort();
		db = view.getDatabase();
		user = view.getUser();
		password = view.getPassword();
    	isConnected = MainDao.setConnectionPostgis(host, port, db, user, password);
		
		if (isConnected){
			view.setSchemas(MainDao.getSchemas());
			prop.put("POSTGIS_HOST", host);
			prop.put("POSTGIS_PORT", port);
			prop.put("POSTGIS_DATABASE", db);
			prop.put("POSTGIS_USER", user);
			if (view.getRemember()){
				prop.put("POSTGIS_PASSWORD", Encryption.encrypt(password));
			} else{
				prop.put("POSTGIS_PASSWORD", "");
			}
	    	String folder = MainDao.getDataDirectory();
	    	Utils.getLogger().info("Postgis data directory: " + folder);
	        prop.put("POSTGIS_DATA", folder);
			MainDao.savePropertiesFile();
			Utils.showMessage("Connection successful!", "", "gisRAS");
			view.enableButtons(true);
		} 
		else{
			view.enableButtons(false);
			view.setSchemas(null);			
		}
		
	}	
	
}