package com.minecraftlegend.inventoryapi.Router.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h1>Query Annotation</h1>
 * <p>
 * This annotation marks a method to be "queryable". The method
 * needs parameter of type QueryParameter with the same name as
 * the query request. Also the order of the parameters is important!
 *
 * <br>Example: "/some/path?val=1&2&foo=TestValue"
 * <br>     @Query( args = {"val","foo"} )
 * <br>     private void demo(QueryParameter val, QueryParameter foo)
 * <br>     {
 * <br>         // val = ["1","2"]
 * <br>         // foo = ["TestValue"]
 * <br>     }
 * </p>
 *
 * @author Drayke
 * @version 1.0
 * @since 31.08.2017.
 */
@Target( value = ElementType.METHOD )
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Query {

    String[] args();

}
