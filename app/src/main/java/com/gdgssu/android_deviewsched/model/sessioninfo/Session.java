package com.gdgssu.android_deviewsched.model.sessioninfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Session implements Serializable {

    public int session_id;
    public boolean is_session;
    public long starts_at;
    public long ends_at;
    public String title;
    public String description;
    public String presentation_url;
    public String video_url;
    public ArrayList<Speaker> speakers;
    public boolean isSelected = false;

}