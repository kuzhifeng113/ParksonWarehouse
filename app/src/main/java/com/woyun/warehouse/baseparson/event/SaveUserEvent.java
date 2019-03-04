package com.woyun.warehouse.baseparson.event;

/**
 * event bus
 */
public class SaveUserEvent {
    private boolean isSave;

    public SaveUserEvent(boolean isSave) {
        this.isSave = isSave;
    }

    public boolean isSave() {
        return isSave;
    }

    public void setSave(boolean save) {
        isSave = save;
    }
}
