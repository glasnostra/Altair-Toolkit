package org.altairtoolkit.annotation.scheduler;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Deny Prasetyo,S.T
 * Java(Scala) Developer and Trainer
 * Software Engineer | Java Virtual Machine Junkies!
 * jasoet87@gmail.com
 * <p>
 * http://github.com/jasoet
 * http://bitbucket.com/jasoet
 */


@Target(TYPE)
@Retention(RUNTIME)
public @interface QuartzEnable {
    String value() default "DEFAULT";

    boolean concurrent() default true;
    
    boolean autoStart() default true;

    CronSchedule[] cron() default {};

    IntervalSchedule[] interval() default {};
}
