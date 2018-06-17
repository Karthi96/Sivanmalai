package com.liteart.apps.sivanmalai;

/**
 * Created by Magudesh on 18-09-2017.
 */

public class CommonModelPetti {


    public CommonModelPetti(String long_text, String date_updated,String desc) {

        this.date_updated = date_updated;
        this.long_text = long_text;
        this.descriptions=desc;
    }


    public String getDate_updated() {
        return date_updated;
    }

    public String getLong_text() {
        return long_text;
    }

    public String getDescription(){
        return descriptions;
    }

    String date_updated;
    String long_text;
    String descriptions;

}
