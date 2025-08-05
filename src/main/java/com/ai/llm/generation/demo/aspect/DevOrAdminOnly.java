package com.ai.llm.generation.demo.aspect;
import java.lang.annotation.*;
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DevOrAdminOnly {
}