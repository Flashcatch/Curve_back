package controllers;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * This controller contains an action to handle HTTP requests to the application's home page.
 * @author Timur Isachenko
 */
public class HomeController extends Controller {

    /**
     * For build process from TC side.
     * @return
     */
    public Result index() {
        return ok();
    }

}
