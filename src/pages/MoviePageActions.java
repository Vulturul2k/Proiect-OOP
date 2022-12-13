package pages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Action;
import input.Input;
import page.Actions.PageDetails;

public interface MoviePageActions {
    /**
     * This method implement an action from MoviePage
     * @param inputData the datbase
     * @param action the input of the user
     * @param output it is where will be the output
     * @param details about page and user
     */
    void action(Input inputData, Action action,
                       ArrayNode output, PageDetails details);
}
