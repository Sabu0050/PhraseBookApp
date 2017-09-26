package com.sabutos.dictionary.model;

/**
 * Created by s on 12/22/16.
 */

public class GetFevourit {
    private int id;
    private int fevid;

    public GetFevourit(int id, int fevid) {
        this.id = id;
        this.fevid = fevid;
    }

    public int getId() {
        return id;
    }

    public int getFevid() {
        return fevid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFevid(int fevid) {
        this.fevid = fevid;
    }
}
