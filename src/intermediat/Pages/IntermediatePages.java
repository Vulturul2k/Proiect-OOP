package intermediat.Pages;

import page.Actions.PageDetails;

public interface IntermediatePages {
    /**
     * this method will help to change to intermediate pages
     * @param details is the database where is the page
     * @return if the page could be changed
     */
    boolean changePage(PageDetails details);
}
