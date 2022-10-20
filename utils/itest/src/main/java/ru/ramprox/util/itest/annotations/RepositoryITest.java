package ru.ramprox.util.itest.annotations;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@DataJpaTestContainers
@Import({})
@Transactional(propagation = Propagation.NEVER)
@ActiveProfiles
public @interface RepositoryITest {
    @AliasFor(
            annotation = ActiveProfiles.class
    )
    String[] profiles() default {};

    @AliasFor(
            annotation = Import.class,
            attribute = "value"
    )
    Class<?>[] importConfigs() default {};
}
