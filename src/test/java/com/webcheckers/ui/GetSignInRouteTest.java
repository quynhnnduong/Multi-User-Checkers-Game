package com.webcheckers.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.TemplateEngine;

import static org.mockito.Mockito.mock;

/**
 * Unit Test suite for {@link GetSignInRoute} component
 * @author Quynh Duong
 */
@Tag("UI-tier")
public class GetSignInRouteTest {
    private GetSignInRoute CuT;
    private TemplateEngine engine;
    private Request request;

    private final String LEGIT_NAME_KEY = "legitName";

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        engine = mock(TemplateEngine.class);

        CuT = new GetSignInRoute(engine);

    }

}
