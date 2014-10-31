package com.shaunscovil.api.common;

import com.shaunscovil.api.Main;

public class ResourceUrl {

    protected String url;

    public ResourceUrl(String resourceUid, String resourcePath) {
        this.url = String.format("%s/%s/%s",
                Main.BASE_URI,
                resourcePath,
                resourceUid
        );
    }

    public String getUrl() {
        return url;
    }

}
