package com.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Delete {
    
    String condition;
    
     public Delete setCondition(String condition){
        this.condition = condition;
        return this;
    }
    public boolean delete(String tableName){

        if(!condition.contains("="))
            return false;
        
        String []conditions = condition.split("=");
        if(conditions.length != 2)
            return false;
        
        String sql = "delete from "+ tableName +" where "+conditions[0]+"= ?";
        try {
            PreparedStatement pst = Database._this.getConnection().prepareStatement(sql);
            pst.setString(1, conditions[1]);
            pst.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Insert.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
}
