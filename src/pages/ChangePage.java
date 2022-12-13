package pages;

import page.Actions.PageDetails;

public interface ChangePage {
    /**
     * this method will help to change page
     * @param details is the database where is the page
     * @return if the page could be changed
     */
    boolean changePage(PageDetails details);
}
