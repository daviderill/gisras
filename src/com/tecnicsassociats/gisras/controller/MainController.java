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
import java.io.File;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.tecnicsassociats.gisras.gui.Form;
import com.tecnicsassociats.gisras.model.MainDao;
import com.tecnicsassociats.gisras.util.Encryption;
import com.tecnicsassociats.gisras.util.Utils;


public class MainController{

	private Form view;
    private Properties prop;
    private File fileSdf;
    private File fileAsc;    
    private String projectName;
    private boolean readyFileSdf = false;
    private boolean readyFileAsc = false;
    
    private String userHomeFolder;
    private ResourceBundle bundleText;

    
    public MainController(Form view) {
    	
    	this.view = view;	
        this.prop = MainDao.getPropertiesFile();
	    view.setControl(this);        
    	
    	this.userHomeFolder = System.getProperty("user.home");
    	this.bundleText = Utils.getBundleText();
    	setDefaultValues();
    	    	
	}

    
    private void setDefaultValues(){
    	
    	fileSdf = new File(prop.getProperty("FILE_SDF", userHomeFolder));
		if (fileSdf.exists()) {
			view.setFileSdf(fileSdf.getAbsolutePath());
			readyFileSdf = true;
		}
    	fileAsc = new File(prop.getProperty("FILE_ASC", userHomeFolder));
		if (fileAsc.exists()) {
			view.setFileAsc(fileAsc.getAbsolutePath());
			readyFileAsc = true;
		}		
		
		projectName = prop.getProperty("PROJECT_NAME");
		view.setNewSchemaName(projectName);
		
		// Get parameters connection 
		view.setHost(prop.getProperty("POSTGIS_HOST", "localhost"));
		view.setPort(prop.getProperty("POSTGIS_PORT", "5432"));
		view.setDatabase(prop.getProperty("POSTGIS_DATABASE", ""));
		view.setUser(prop.getProperty("POSTGIS_USER", ""));
		view.setPassword(Encryption.decrypt(prop.getProperty("POSTGIS_PASSWORD", "")));
	
    }
   

	public void action(String actionCommand) {
		
		Method method;
		try {
			if (Utils.getLogger() != null){
				Utils.getLogger().info(actionCommand);
			}
			method = this.getClass().getMethod(actionCommand);
			method.invoke(this);	
			view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));			
		} catch (Exception e) {
			view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			if (Utils.getLogger() != null){			
				Utils.logError(e, actionCommand);
			} else{
				Utils.showError(e, actionCommand);
			}
		}
		
	}	
	
	
	public void schemaChanged(){
		MainDao.setSchema(view.getSchema());
	}
	
	
    public void chooseFileSdf() {

        JFileChooser chooser = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("SDF extension file", "sdf");
        chooser.setFileFilter(filter);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setDialogTitle(bundleText.getString("file_sdf"));
        File file = new File(prop.getProperty("FILE_SDF", userHomeFolder));	
        chooser.setCurrentDirectory(file.getParentFile());
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            fileSdf = chooser.getSelectedFile();
            String path = fileSdf.getAbsolutePath();
            if (path.lastIndexOf(".") == -1) {
                path += ".sdf";
                fileSdf = new File(path);
            }
            view.setFileSdf(fileSdf.getAbsolutePath());            
            prop.put("FILE_SDF", fileSdf.getAbsolutePath());
            MainDao.savePropertiesFile();
            readyFileSdf = true;
        }

    }
    
    
    public void chooseFileAsc() {

        JFileChooser chooser = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("ASC extension file", "asc");
        chooser.setFileFilter(filter);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setDialogTitle(bundleText.getString("file_asc"));
        File file = new File(prop.getProperty("FILE_ASC", userHomeFolder));	
        chooser.setCurrentDirectory(file.getParentFile());
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
        	fileAsc = chooser.getSelectedFile();
            String path = fileAsc.getAbsolutePath();
            if (path.lastIndexOf(".") == -1) {
                path += ".asc";
                fileAsc = new File(path);
            }
            view.setFileAsc(fileAsc.getAbsolutePath());            
            prop.put("FILE_ASC", fileAsc.getAbsolutePath());
            MainDao.savePropertiesFile();
            readyFileAsc = true;
        }

    }    
    

    // Clear gisras schema info    
    public void clearData(){
    	    	
        int res = JOptionPane.showConfirmDialog(this.view, "Are you sure you want to clear data?", "gisRAS", JOptionPane.YES_NO_OPTION);
        if (res == 0){
    		view.setCursor(new Cursor(Cursor.WAIT_CURSOR));	        	
        	MainDao.clearData();
        	view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }    	
    	
    }

    
    public void saveCase(){
    	
    	String schemaName = view.getNewSchemaName();        	
    	MainDao.caseNew(schemaName);
    	view.setSchemas(MainDao.getSchemas());        	
        	
    }

    
    public void loadCase(){
    	
    	String schemaName = view.getSchema();
        if (schemaName.equals("")){
            Utils.showError("Any schema selected", "", "gisRAS");
            return;
        }        	
    	MainDao.caseLoad(schemaName);  	
        	
    }
    
    
    public void deleteCase(){
    	
    	String schemaName = view.getSchema();
        if (schemaName.equals("")){
            Utils.showError("Any schema selected", "", "gisRAS");
            return;
        }          	
        int res = JOptionPane.showConfirmDialog(this.view, "Are you sure you want to delete selected schema?", "gisRAS", JOptionPane.YES_NO_OPTION);
        if (res == 0){
    		view.setCursor(new Cursor(Cursor.WAIT_CURSOR));	        	
        	MainDao.caseDelete(schemaName);
        	view.setSchemas(MainDao.getSchemas());
        	view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }   
        	
    }    
    
    
    public void loadRaster(){
    	
		// Comprobamos que se ha especificado fichero .asc
		if (!readyFileAsc) {
			Utils.showError("file_inp_not_selected", "", "gisRAS");
			return;
		}
		String fileName = fileAsc.getAbsolutePath();
    	MainDao.loadRaster(fileName);  	
        	
    }
    
    
    // Create HEC-RAS file    
	public void exportSdf() {
   	
		// Comprobamos que se ha especificado fichero .sdf
		if (!readyFileSdf) {
			Utils.showError("file_inp_not_selected", "", "gisRAS");
			return;
		}
		String fileName = fileSdf.getName();
		MainDao.createSdfFile(fileName);
		
		// Copiamos fichero de la carpeta de Postgis a la carpeta especificada por el usuario
		String auxIn, auxOut;
		String folderIn = prop.getProperty("POSTGIS_DATA");
		auxIn = folderIn + File.separator + fileName;
		auxOut = fileSdf.getAbsolutePath();
		boolean ok = Utils.copyFile(auxIn, auxOut);
		if (!ok){
			Utils.showError("File generation error. See .log file for further details", "", "gisRAS");
		}
		else{
			Utils.showMessage("File generation completed:", fileSdf.getAbsolutePath(), "gisRAS");
		}
		
	}
    
	
}