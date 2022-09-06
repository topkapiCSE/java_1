package com.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Update {
    private ArrayList<String> columns;
    private ArrayList<Object> values;
    private String condition;
    
    public Update(){
        columns = new ArrayList<String>();
        values  = new ArrayList<Object>();
    }
    
    public Update setColumn(String column){
        columns.add(column);
        return this;
    }
    public Update setValue(Object value){
        values.add(value);
        return this;
    }
    
    public Update setCondition(String condition){
        this.condition = condition;
        return this;
    }
    
    public boolean update(String tableName){
        if(columns.size() != values.size()){
            columns.clear();
            values.clear();
            return false;
        }
        
        StringBuilder col = new StringBuilder("");
         for (String column : columns) {
            col.append(column).append("=?,");
        }
 
        String columnNames = col.toString();
        
        if (columnNames.length() > 0){
            columnNames = columnNames.substring( 0, columnNames.length() - 1);
        }
 
        String sql = "UPDATE " + tableName +  " SET " + columnNames + " WHERE " + condition;
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
            
            pst.executeUpdate();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(Insert.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
}
