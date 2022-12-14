package main.Pages;

import input.Input;
import page.Actions.PageDetails;

public interface MainPages {
    /**
     * this method is created to take the user to the main pages of the platform
     * @param inputData database
     * @param details about the current user, page ok action
     * @return if you could go to this page
     */
    boolean nextPage(Input inputData, PageDetails details);
}
