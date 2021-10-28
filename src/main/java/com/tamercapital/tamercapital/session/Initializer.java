package com.tamercapital.tamercapital.session;

import com.tamercapital.tamercapital.Configuration.HttpSessionConfig;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class Initializer extends AbstractHttpSessionApplicationInitializer {

    public Initializer() {
        super(HttpSessionConfig.class);
    }

}
