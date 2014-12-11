package com.shaunscovil.api.resource;

import com.shaunscovil.api.http.Server;

public class ResourceUrl {

    protected String url;

    public ResourceUrl(String resourceUid, String resourcePath) {
        this.url = String.format("%s/%s/%s",
                Server.BASE_URI,
                resourcePath,
                resourceUid
        );
    }

    public String getUrl() {
        return url;
    }

}
