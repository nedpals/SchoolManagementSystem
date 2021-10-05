/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author nedpals
 */
public class Date2 {
    private Date rawDate;
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
    
    Date2() {
        this.rawDate = new Date();
    }
    
    Date2(Date rawDate) {
        this.rawDate = rawDate;
    }
    
    public Date get() {
        return this.rawDate;
    }
    
    public static Date2 fromJSON(String rawStrDate) throws Exception {
        Date gotDate = Date2.formatter.parse(rawStrDate);
        return new Date2(gotDate);
    }
    
    public String toJSON() {
        return Date2.formatter.format(rawDate);
    }
}
