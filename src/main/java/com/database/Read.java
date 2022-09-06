package com.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Read {
    
    private ArrayList<String> columns;
    private ArrayList<Object> values;
    private String condition;
    private ArrayList<String[]> result;
    
    public Read() {
        columns = new ArrayList<String>();
        values  = new ArrayList<Object>();
        result  = new ArrayList<String[]>();
        condition = "";
    }
    
    public Read setColumn(String column){
        columns.add(column);
        return this;
    }
    public Read setCondition(String condition){
        this.condition = condition;
        return this;
    }
    
    public ArrayList<String[]> read(String tableName){

        String sql = "SELECT * FROM " + tableName;
        String []conditions = null;
        
        if(columns.size() != 0){
            StringBuilder col = new StringBuilder("");
             for (String column : columns) {
                col.append(column).append(",");
            }
            String columnNames = col.toString();
            if (columnNames.length() > 0){
                columnNames = columnNames.substring( 0, columnNames.length() - 1);
            }
            sql = "SELECT "+ columnNames +" FROM " + tableName;
        }
        
        if(condition.contains("=")){
            conditions = condition.split("=");
            if(conditions.length != 2)
                return null;
            sql = sql + "WHERE " + conditions[0] + "=?";
        }
        
        try {
            ResultSet rs;
            
            if(conditions != null){
                PreparedStatement pst = Database._this.getConnection().prepareStatement(sql);
                pst.setString(1, conditions[1]);
                rs = pst.executeQuery(sql);
            }
            else{
                Statement st = Database._this.getConnection().createStatement();
                rs = st.executeQuery(sql);
            }
            
            prepareData(rs);
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(Insert.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private void prepareData(ResultSet rs){
        try {
            //kolon isimlerini getirme
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount(); //number of column
            String columnNames[] = new String[columnCount];
            
            for (int i = 1; i <= columnCount; i++){
                columnNames[i-1] = metaData.getColumnLabel(i);
            }
            result.add(columnNames);

            while (rs.next()){
                String row[] = new String[columnCount];
                for (int i = 0; i < columnCount; i++){
                    row[i] = rs.getString(columnNames[i]);
                }
                result.add(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Read.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
