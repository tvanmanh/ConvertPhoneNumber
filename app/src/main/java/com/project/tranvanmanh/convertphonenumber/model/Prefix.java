package com.project.tranvanmanh.convertphonenumber.model;

public class Prefix {
    private String mOldPrefix;
    private String mNewPrefix;

    public Prefix(String mOldPrefix, String mNewPrefix) {
        this.mOldPrefix = mOldPrefix;
        this.mNewPrefix = mNewPrefix;
    }

    public String getmOldPrefix() {
        return mOldPrefix;
    }

    public void setmOldPrefix(String mOldPrefix) {
        this.mOldPrefix = mOldPrefix;
    }

    public String getmNewPrefix() {
        return mNewPrefix;
    }

    public void setmNewPrefix(String mNewPrefix) {
        this.mNewPrefix = mNewPrefix;
    }
}
