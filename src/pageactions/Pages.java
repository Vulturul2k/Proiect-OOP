package pageactions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Input;

import java.util.ArrayList;

public final class Pages {
    private ArrayList<Page> pages;
    public Pages() {
        this.pages = new ArrayList<>();
    }

    /**
     * add a new page
     * @param pageName the name of the page
     */
    public void add(final String pageName) {
        Page page = new Page(pageName);
        pages.add(page);
    }

    /**
     * clear the page`s list
     */
    public void clear() {
        pages.clear();
    }

    /**
     * undo the page
     * @param details give details
     * @param inputData give input
     * @param output give output
     */
    public void undo(final PageDetails details, final Input inputData,
                      final ArrayNode output) {
        if (!this.pages.isEmpty()) {
            Page page = pages.get(pages.size() - 1);
            page.execute(details, inputData, output);
        }
    }
}
