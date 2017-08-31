package com.minecraftlegend.inventoryapi.Router.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h1>InventoryAPI</h1>
 * <h2>Class heading</h2>
 * <p>
 * Class description.
 * </p>
 *
 * @author Drayke
 * @version 1.0
 * @since 31.08.2017.
 */
@Target( value = ElementType.METHOD )
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Query {

}

/***********************************************************************************************
 *
 *                  All rights reserved, MadDev (c) copyright 2017
 *
 ***********************************************************************************************/