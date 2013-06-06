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
package com.tecnicsassociats.gisras;

import java.util.Locale;

import javax.swing.UIManager;

import com.tecnicsassociats.gisras.controller.DatabaseController;
import com.tecnicsassociats.gisras.controller.MainController;
import com.tecnicsassociats.gisras.controller.MenuController;
import com.tecnicsassociats.gisras.gui.Form;
import com.tecnicsassociats.gisras.gui.MainFrame;
import com.tecnicsassociats.gisras.model.MainDao;
import com.tecnicsassociats.gisras.util.Utils;


public class MainClass {

    public static void main(String[] args) {
    	
    	// English language
    	Locale.setDefault(Locale.ENGLISH);

    	// Look&Feel
    	String className = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
    	try {
			UIManager.setLookAndFeel(className);
		} catch (Exception e) {
			e.printStackTrace();
		}  
    	
    	if (!MainDao.configIni()){
    		return;
    	}

        // Create form
    	Form cmWindow = new Form();
        MainFrame mainFrame = new MainFrame();    	
    	
    	// Create controllers
		new DatabaseController(cmWindow);        
		new MainController(cmWindow);
        new MenuController(mainFrame);
        
        // Open Main Frame
        Utils.openForm(cmWindow, mainFrame, 535, 405);
        cmWindow.setFrame(mainFrame);    

    }
    
}