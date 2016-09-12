package com.github.lanjusto.moneytransferservice.api.url;

import org.jetbrains.annotations.NotNull;

/**
 * This class should make API URLs using more convenient.
 * <p>
 * It works only in the simplest cases, but it is enough for us.
 */
public class APIUrl {
    public static final String DELIMITER = "/";

    private final String root;
    private final String version;
    private final String resource;
    private final String parameterPlaceHolder;

    APIUrl(String root, String version, String resource, String parameterPlaceHolder) {
        this.root = root;
        this.version = version;
        this.resource = resource;
        this.parameterPlaceHolder = parameterPlaceHolder;
    }

    @NotNull
    public String getRelative() {
        if (parameterPlaceHolder == null) {
            return DELIMITER + String.join(DELIMITER, version, resource);
        } else {
            return DELIMITER + String.join(DELIMITER, version, resource, envelop(parameterPlaceHolder));
        }
    }

    @NotNull
    public String getAbsolute() {
        if (parameterPlaceHolder == null) {
            return String.join(DELIMITER, root, version, resource);
        } else {
            return String.join(DELIMITER, root, version, resource, envelop(parameterPlaceHolder));
        }
    }

    @NotNull
    public String getAbsoluteWithParameter(@NotNull String parameterValue) {
        if (parameterPlaceHolder == null) {
            return String.join(DELIMITER, root, version, resource);
        } else {
            return String.join(DELIMITER, root, version, resource, parameterValue);
        }
    }

    @NotNull
    private String envelop(@NotNull String s) {
        return "{" + s + "}";
    }
}
