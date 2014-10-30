package com.shaunscovil.api.common;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsonBean extends Object {

    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
