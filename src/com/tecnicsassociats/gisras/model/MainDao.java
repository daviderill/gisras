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
package com.tecnicsassociats.gisras.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;

import com.tecnicsassociats.gisras.Constants;
import com.tecnicsassociats.gisras.util.Utils;

public class MainDao {
	
    public static Connection connectionConfig;   // SQLite
	public static Connection connectionPostgis;   // Postgis
    public static String schema;
	
	public static String folderConfig;	
    public static File fileHelp;	
	
	private static Properties iniProperties = new Properties();	
	private static String appPath;	
	private static String configPath;
	private static FileOutputStream fos;
	
	
    // Sets initial configuration files
    public static boolean configIni() {

    	if (!enabledPropertiesFile()){
    		return false;
    	}
    	
        // Get INP folder
        folderConfig = iniProperties.getProperty("FOLDER_CONFIG");
        folderConfig = appPath + folderConfig + File.separator;

    	// Set Config DB connection
        if (!setConnectionConfig(Constants.CONFIG_DB)){
        	return false;
        }
        
        // Get PDF help file
        if (fileHelp == null) {
            String filePath = iniProperties.getProperty("FILE_HELP", "help.pdf");
            filePath = folderConfig + File.separator + filePath;
            fileHelp = new File(filePath);
        }
        
        return true;

    }
    
	
    public static Properties getPropertiesFile() {
        return iniProperties;
    }


    public static void savePropertiesFile() {

        File iniFile = new File(configPath);
        try {
        	if (fos == null){
        		fos = new FileOutputStream(iniFile);
        	}
            iniProperties.store(fos, "");
        } catch (FileNotFoundException e) {
            Utils.showError("inp_error_notfound", iniFile.getPath(), "inp_descr");
        } catch (IOException e) {
            Utils.showError("inp_error_io", iniFile.getPath(), "inp_descr");
        }

    }
    
    
    // Get Properties Files
    public static boolean enabledPropertiesFile() {

    	appPath = Utils.getAppPath();
        configPath = appPath + Constants.CONFIG_FOLDER + File.separator + Constants.CONFIG_FILE;
        Utils.getLogger().info("Config file: " + configPath);
        File fileIni = new File(configPath);
        try {
            iniProperties.load(new FileInputStream(fileIni));
        } catch (FileNotFoundException e) {
            Utils.showError("inp_error_notfound", configPath, "inp_descr");
            return false;
        } catch (IOException e) {
            Utils.showError("inp_error_io", configPath, "inp_descr");
            return false;
        }
        return !iniProperties.isEmpty();

    }    
	
    
    // Connect to Config sqlite Database
    public static boolean setConnectionConfig(String fileName) {

        try {
            Class.forName("org.sqlite.JDBC");
            String filePath = folderConfig + fileName;
            File file = new File(filePath);
            if (file.exists()) {
            	connectionConfig = DriverManager.getConnection("jdbc:sqlite:" + filePath);
                return true;
            } else {
                Utils.showError("inp_error_notfound", filePath, "inp_descr");
                return false;
            }
        } catch (SQLException e) {
            Utils.showError("inp_error_connection", e.getMessage(), "inp_descr");
            return false;
        } catch (ClassNotFoundException e) {
            Utils.showError("inp_error_connection", "ClassNotFoundException", "inp_descr");
            return false;
        }

    }
    
	
    public static boolean setConnectionPostgis(String host, String port, String db, String user, String password) {
    	
        String connectionString = "jdbc:postgresql://" + host + ":" + port + "/" + db + "?user=" + user + "&password=" + password;
        try {
            connectionPostgis = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            try {
                connectionPostgis = DriverManager.getConnection(connectionString);
            } catch (SQLException e1) {
                Utils.showError(e1.getMessage(), "", "inp_descr");
                return false;
            }   		
        }
        return true;
        
    }	
    
    
	public static boolean executeUpdateSql(String sql, boolean commit) {
		try {
			Statement ps = connectionPostgis.createStatement();
	        ps.executeUpdate(sql);
	        if (commit){
	        	connectionPostgis.commit();
	        }
			return true;
		} catch (SQLException e) {
			Utils.showError(e, sql);
			return false;
		}
	}	
	
	
	public static boolean executeUpdateSql(String sql) {
		return executeUpdateSql(sql, false);
	}		
    
	
	public static boolean executeSql(String sql) {
		try {
			Statement ps = connectionPostgis.createStatement();
			Utils.getLogger().info(sql);
	        boolean result = ps.execute(sql);
			return result;
		} catch (SQLException e) {
			Utils.showError(e, sql);
			return false;
		}
	}		
	
	
    // Check if the table exists
	public static boolean checkTable(String tableName) {
        String sql = "SELECT * FROM pg_tables WHERE lower(tablename) = '" + tableName + "'";
        try {
            Statement stat = connectionPostgis.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            return (rs.next());
        } catch (SQLException e) {
        	Utils.showError(e.getMessage(), "", "inp_descr");
            return false;
        }
    }
	
	
    // Check if the table exists
	public static boolean checkTable(String schemaName, String tableName) {
        String sql = "SELECT * FROM pg_tables " +
        		"WHERE lower(schemaname) = '" + schemaName + "' AND lower(tablename) = '" + tableName + "'";
        try {
            Statement stat = connectionPostgis.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            return (rs.next());
        } catch (SQLException e) {
        	Utils.showError(e.getMessage(), "", "inp_descr");
            return false;
        }
    }	
    
    
    // Check if the view exists
    public static boolean checkView(String viewName) {
        String sql = "SELECT * FROM pg_views WHERE lower(viewname) = '" + viewName + "'";
        try {
            Statement stat = connectionPostgis.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            return (rs.next());
        } catch (SQLException e) {
        	Utils.showError(e.getMessage(), "", "inp_descr");
            return false;
        }
    }    
    
    
    // Check if the selected srid exists in spatial_ref_sys
	public static boolean checkSrid(Integer srid) {
        String sql = "SELECT srid FROM spatial_ref_sys WHERE srid = " + srid;
        try {
            Statement stat = connectionPostgis.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            return (rs.next());
        } catch (SQLException e) {
        	Utils.showError(e.getMessage(), "", "inp_descr");
            return false;
        }
    }    
    
    
	public static Vector<String> getSchemas(){

        String sql = "SELECT schema_name FROM information_schema.schemata " +
        		"WHERE schema_name <> 'information_schema' AND schema_name !~ E'^pg_' " +
        		"AND schema_name <> 'drivers' AND schema_name <> 'public' AND schema_name <> 'topology' " +
        		"ORDER BY schema_name";
        Vector<String> vector = new Vector<String>();
        try {
    		//connectionPostgis.setAutoCommit(false);        	
        	connectionPostgis.setAutoCommit(true);
            Statement stat = connectionPostgis.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
            	vector.add(rs.getString(1));
            }
            rs.close();
    		return vector;	            
        } catch (SQLException e) {
            Utils.showError(e.getMessage(), "", "inp_descr");
            return vector;
        }
		
	}
	
	
	private static ResultSet getResultset(Connection connection, String sql){
		
        ResultSet rs = null;        
        try {
        	connection.setAutoCommit(true);
        	Statement stat = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stat.executeQuery(sql);
        } catch (SQLException e) {
            Utils.showError(e.getMessage(), "", "inp_descr");
        }
        return rs;   
        
	}

	
	public static ResultSet getTableResultset(Connection connection, String table) {
		String sql;
		if (schema == null){
			sql = "SELECT * FROM " + table;
		} else{
			sql = "SELECT * FROM " + schema + "." + table;
		}
        return getResultset(connection, sql);
	}
	
	public static ResultSet getTableResultset(String table) {
		return getTableResultset(connectionPostgis, table);
	}
	
	
	
	public static Vector<String> getTable(String table, String schemaParam, boolean addBlank) {
        
        Vector<String> vector = new Vector<String>();
        
        if (addBlank){
        	vector.add("");
        }
		if (schemaParam == null){
			schemaParam = schema;
		}
		if (!checkTable(schemaParam, table)) {
			return vector;
		}
		String sql = "SELECT * FROM " + schemaParam + "." + table;
		try {
			Statement stat = connectionPostgis.createStatement();
	        ResultSet rs = stat.executeQuery(sql);
	        while (rs.next()) {
	        	vector.add(rs.getString(1));
	        }
	        stat.close();
		} catch (SQLException e) {
            Utils.showError(e.getMessage(), "", "inp_descr");
		}            
		return vector;
		
	}	
	
	
	public static Vector<String> getTable(String table, String schemaParam) {
		return getTable(table, schemaParam, true);
	}
	
	
	public static void setResultSelect(String schema, String table, String result) {
		String sql = "DELETE FROM " + schema + "." + table;
		executeUpdateSql(sql);
		sql = "INSERT INTO " + schema + "." + table + " VALUES ('" + result + "')";
		executeUpdateSql(sql);
	}
	
	
	public static void setSchema(String schema) {
		MainDao.schema = schema;
	}
	

	public static void deleteSchema(String schemaName) {
		String sql = "DROP schema IF EXISTS " + schemaName + " CASCADE;";
		executeUpdateSql(sql, true);		
		sql = "DELETE FROM public.geometry_columns WHERE f_table_schema = '" + schemaName + "'";
		executeUpdateSql(sql, true);			
	}


	private static String addGeometryColumnsTables(String schemaName, String srid) {
		
		String content = "";
		content+= "SELECT addgeometryColumn('" + schemaName + "', 'arc', 'the_geom', '" + srid + "', 'MULTILINESTRING', 2);\n";
		content+= "SELECT addgeometryColumn('" + schemaName + "', 'node', 'the_geom', '" + srid + "', 'POINT', 2);\n";
		content+= "SELECT addgeometryColumn('" + schemaName + "', 'subcatchment', 'the_geom', '" + srid + "', 'MULTIPOLYGON', 2);\n";
		content+= "SELECT addgeometryColumn('" + schemaName + "', 'catchment', 'the_geom', '" + srid + "', 'MULTIPOLYGON', 2);\n";		
		content+= "SELECT addgeometryColumn('" + schemaName + "', 'raingage', 'the_geom', '" + srid + "', 'POINT', 2);\n";
		content+= "SELECT addgeometryColumn('" + schemaName + "', 'vertice', 'the_geom', '" + srid + "', 'POINT', 2);\n";
		content+= "SELECT addgeometryColumn('" + schemaName + "', 'connec', 'the_geom', '" + srid + "', 'POINT', 2);\n";
		content+= "SELECT addgeometryColumn('" + schemaName + "', 'gully', 'the_geom', '" + srid + "', 'POINT', 2);\n";
		return content;
		
	}
	
	private static String addGeometryColumnsViews(String schemaName, String srid) {
		
		String content = "";
		content+= "INSERT INTO public.geometry_columns VALUES (' ', '" + schemaName + "', 'v_man_arc', 'the_geom', '2', '" + srid + "', 'MULTILINESTRING');\n";
		content+= "INSERT INTO public.geometry_columns VALUES (' ', '" + schemaName + "', 'v_man_node', 'the_geom', '2', '" + srid + "', 'POINT');\n";
		content+= "INSERT INTO public.geometry_columns VALUES (' ', '" + schemaName + "', 'v_rpt_arcflow_sum', 'the_geom', '2', '" + srid + "', 'POINT');\n";
		content+= "INSERT INTO public.geometry_columns VALUES (' ', '" + schemaName + "', 'v_rpt_nodeflood_sum', 'the_geom', '2', '" + srid + "', 'POINT');\n";
		return content;
		
	}	

	
	public static boolean createSdfFile(String fileName) {
		String sql = "SELECT gisras.gr_export_geo('" + fileName + "');";
		return executeSql(sql);	
	}
	
	
	public static boolean clearInfo(){
		String sql = "SELECT gisras.gr_clear();";
		return executeSql(sql);	
	}


	public static boolean caseNew(String schemaName) {
		String sql = "SELECT gisras.gr_save_case_as('" + schemaName + "');";
		return executeSql(sql);	
	}


	public static boolean caseLoad(String schemaName) {
		String sql = "SELECT gisras.gr_open_case('" + schemaName + "');";
		return executeSql(sql);	
	}


	public static boolean caseDelete(String schemaName) {
		String sql = "SELECT gisras.gr_delete_case('" + schemaName + "');";
		return executeSql(sql);	
	}


	public static String getDataDirectory() {
		
		String sql = "SELECT setting FROM pg_settings WHERE name = 'data_directory'";
		String folder = "";
		try {
			Statement stat = connectionPostgis.createStatement();
	        ResultSet rs = stat.executeQuery(sql);
	        if (rs.next()) {
	        	folder = rs.getString(1);
	        }
	        stat.close();
		} catch (SQLException e) {
            Utils.showError(e.getMessage(), "", "inp_descr");
		}    		
		return folder;
		
	}

	
}