package yt.mak.maklib.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoRegister {
    String name() default "";
    Type type() default Type.ITEM;

    enum Type {
        BLOCK,
        ITEM,
        ENTITY
    }
}