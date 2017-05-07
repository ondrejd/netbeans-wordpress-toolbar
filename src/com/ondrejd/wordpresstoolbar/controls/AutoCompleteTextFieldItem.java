
package com.ondrejd.wordpresstoolbar.controls;

/**
 * Simple class representing data item for {@see AutoCompleteTextField}.
 *
 * @author ondrejd
 */
public class AutoCompleteTextFieldItem {
    private String name;
    private String url;

    /**
     * Constructs data item with name and URL.
     * @param name Name of resource represented by the item.
     * @param url URL of resource represented by the item.
     */
    public AutoCompleteTextFieldItem(String name, String url) {
        this.name = name;
        this.url = url;
    }
}