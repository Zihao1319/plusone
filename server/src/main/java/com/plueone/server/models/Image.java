package com.plueone.server.models;

import java.io.Serializable;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Image {

    private String userId;
    private Integer imageId;
    private String url;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static JsonArray toJsonArr(List<Image> images){

        JsonArrayBuilder jArr = Json.createArrayBuilder();
      
        for (Image i : images){

            JsonObject json = Json.createObjectBuilder()
                            .add("imageId", i.getImageId())
                            .add("url", i.getUrl())
                            .build();
            jArr.add(json);
        }
        return jArr.build();
    }

}
