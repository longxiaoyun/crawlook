package io.github.longxiaoyun.enums;

/**
 * WebDriverEnum
 * @author longxiaoyun
 */
public enum WebDriverEnum {
    CHROME("chrome"),FIREFOX("firefox"),EDGE("edge"),CHROMIUM("chromium");

    private String driver;

    public String getDriver() {
        return driver;
    }

    WebDriverEnum(String driver) {
        this.driver = driver;
    }
}
