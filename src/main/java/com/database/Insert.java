package com.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Insert {
    
    private ArrayList<String> columns;
    private ArrayList<Object> values;
    
    public Insert(){
        columns = new ArrayList<String>();
        values  = new ArrayList<Object>();
    }
    
    public Insert setColumn(String column){
        columns.add(column);
        return this;
    }
    public Insert setValue(Object value){
        values.add(value);
        return this;
    }
    
    public boolean insert(String tableName){
        if(columns.size() != values.size()){
            columns.clear();
            values.clear();
            return false;
        }
        
        StringBuilder col = new StringBuilder("");
        StringBuilder val = new StringBuilder("");
         for (String column : columns) {
            col.append(column).append(",");
            val.append("?,");
        }
 
        String columnNames = col.toString();
        String valueNames  = val.toString();
        
        if (columnNames.length() > 0){
            columnNames = columnNames.substring( 0, columnNames.length() - 1);
            valueNames  = valueNames.substring(  0, valueNames.length() - 1);
        }
 
        String sql = "INSERT INTO " + tableName +  "(" + columnNames + ")" + "VALUES(" + valueNames +")";
        
        try {
            PreparedStatement pst = Database._this.getConnection().prepareStatement(sql);
            for(int i = 0 ; i < values.size() ; i++){
                Object o = values.get(i);
                if(o instanceof String)
                    pst.setString (i+1, (String)o);
                else if(o instanceof Integer)
                    pst.setInt(i+1, (int)o);
                else if(o instanceof Double)
                    pst.setDouble(i+1, Double.parseDouble(o.toString()));
            }
            
            pst.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Insert.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
