package com.webcheckers.ui;

import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.UIProtocol.LEGIT_NAME_ATTR;

/**
 * The UI Controller to GET the Sign In page.
 * @author Shubhang Mehrotra, Dmitry Selin
 */
public class GetSignInRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetSignInRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        LOG.config("GetSignInRoute is initialized.");
    }

    /**
     * Render the Sign-in page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Sign In page
     */
    @Override
    public Object handle(Request request, Response response) {
        final Session session = request.session();

        LOG.finer("GetSignInRoute is invoked.");
        Map<String, Object> vm = new HashMap<>();

        // the key to tell someone if their last name for signin was taken by someone else (or there was none)
        try {
            boolean legitName = session.attribute(LEGIT_NAME_ATTR);
            vm.put(LEGIT_NAME_ATTR, legitName);
        }
        catch (NullPointerException ignored) {
            vm.put(LEGIT_NAME_ATTR, true);
        }

        vm.put("title", "Sign In");
        vm.put("message", Message.info("Sign in with your username."));

        // render the View
        return templateEngine.render(new ModelAndView(vm , "signIn.ftl"));
    }
}
