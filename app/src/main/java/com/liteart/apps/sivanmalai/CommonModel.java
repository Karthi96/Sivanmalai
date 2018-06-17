package com.liteart.apps.sivanmalai;

/**
 * Created by Magudesh on 18-09-2017.
 */

public class CommonModel {


    public CommonModel(String long_text, String date_updated) {

        this.date_updated = date_updated;
        this.long_text = long_text;
    }


    public String getDate_updated() {
        return date_updated;
    }

    public String getLong_text() {
        return long_text;
    }

    String date_updated;
    String long_text;

}
