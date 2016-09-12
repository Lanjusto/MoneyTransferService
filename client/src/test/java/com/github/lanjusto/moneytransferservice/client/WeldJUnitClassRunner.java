package com.github.lanjusto.moneytransferservice.client;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import javax.inject.Inject;
import java.lang.reflect.Field;

@SuppressWarnings({"Duplicates", "WeakerAccess"})
public class WeldJUnitClassRunner extends BlockJUnit4ClassRunner {
    private final Class<?> clazz;
    private final WeldContainer weldContainer;

    public WeldJUnitClassRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
        this.clazz = clazz;
        this.weldContainer = new Weld().initialize();
    }

    @Override
    @NotNull
    protected Object createTest() throws InitializationError {
        final Object testInstance;
        try {
            testInstance = clazz.newInstance();

        } catch (InstantiationException | IllegalAccessException e) {
            throw new InitializationError(e);
        }

        try {
            injectDependencies(testInstance);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
        return testInstance;
    }

    private void injectDependencies(@NotNull Object object) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (isFieldAnnotatedWithInjectAnnotation(field)) {
                field.setAccessible(true);
                final Class<?> fieldType = field.getType();
                field.set(object, weldContainer.instance().select(fieldType).get());
            }
        }
    }

    private boolean isFieldAnnotatedWithInjectAnnotation(@NotNull Field field) {
        return (field.getAnnotation(Inject.class) != null);
    }
}
