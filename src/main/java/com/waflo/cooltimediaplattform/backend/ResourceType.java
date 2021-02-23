package com.waflo.cooltimediaplattform.backend;

public enum ResourceType {
    VIDEO, IMAGE, RAW;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
