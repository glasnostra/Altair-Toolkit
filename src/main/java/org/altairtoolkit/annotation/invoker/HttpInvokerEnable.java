package org.altairtoolkit.annotation.invoker;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Deny Prasetyo,S.T
 * Java(Scala) Developer and Trainer
 * Software Engineer
 * jasoet87@gmail.com
 * <p>
 * http://github.com/jasoet
 * http://bitbucket.com/jasoet
 * <p>
 * [at] jasoet
 */

@Target(TYPE)
@Retention(RUNTIME)
public @interface HttpInvokerEnable {
    String id();

    Class serviceTrait();
}
