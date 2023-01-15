package pageactions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Input;

public final class Page {
    private String pageName;
    public Page(final String pageName) {
        this.pageName = pageName;
    }

    /**
     * This execute the changing og page
     * @param details details
     * @param inputData give the input
     * @param output give the output
     */
    public void execute(final PageDetails details, final Input inputData,
                        final ArrayNode output) {
        if (this.pageName.equals("Homepage autentificat")) {
            details.setPage("Homepage autentificat");
        } else {
            details.getAction().setPage(this.pageName);
            details.setPage(PageActions.getInstance().changePage(inputData, output,
                    details, true));
        }
    }
}
