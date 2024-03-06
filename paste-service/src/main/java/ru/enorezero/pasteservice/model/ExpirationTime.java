package ru.enorezero.pasteservice.model;

public enum ExpirationTime {
    MINUTE(60),
    TEN_MINUTE(600),
    HOUR(3600),
    SIX_HOUR(21600),
    DAY(86400),
    WEEK(604800),
    MONTH(2419200),
    YEAR(31536000),
    FIVE_YEAR(157680000);

    final int seconds;

    ExpirationTime(int seconds) {
            this.seconds = seconds;
    }
    public int getSeconds() {
            return this.seconds;
    }
}
