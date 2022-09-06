package com.uygulama1;

import com.database.Delete;
import com.database.Insert;
import com.database.Read;
import com.database.Update;
import com.ui.MainFrame;
import java.util.ArrayList;


public class Main {
    public static void main(String args[]) {
        com.database.Database.connect();
        boolean d = false;
        //d = new Insert().setColumn("name").setColumn("surname").setColumn("gender").setValue("Emre").setValue("can").setValue("E").insert("tbl_users2");
        // d = new Insert().setColumn("username").setColumn("password").setValue("v1").setValue(123.123d).insert("tbl_users");
        //d = new Update().setColumn("username").setColumn("password").setValue("up1").setValue(321.321d).setCondition("id=4").update("tbl_users");
        //d = new Delete().setCondition("id=3").delete("tbl_users");
        //ArrayList<String[]> result = new Read().setColumn("username").read("tbl_users");
            
        /*
        for (int i = 0; i < result.size(); i++) {
            for (int j = 0; j < result.get(i).length; j++) {
                System.out.print(result.get(i)[j] + " ");
            }
            System.out.println("");
        }
        */
        new MainFrame().setVisible(true);
        
        
    }
}
