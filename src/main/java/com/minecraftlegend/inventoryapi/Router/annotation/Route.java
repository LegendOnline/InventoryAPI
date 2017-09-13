package com.minecraftlegend.inventoryapi.Router.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h1>Route Annoation</h1>
 * <p>
 * This Annotation sets the path for a McGui to be reachable
 * by the Router:
 *
 * Example:
 * <br>     @Route( value= "/demo" )
 * <br>     public class DemoGui extends McGui
 * <br>     {
 * <br>         public DemoGui(){
 * <br>             super(Plugin.getInstance(),"Demo GUI",someLayout);
 * <br>             Router.registerGui(this);
 * <br>     }
 * </p>
 *
 * @author Drayke
 * @version 1.0
 * @since 31.08.2017.
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface Route {

    String value();
}
