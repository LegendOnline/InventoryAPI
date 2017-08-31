package com.minecraftlegend.inventoryapi.Router.exception;

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
public class InvalidRouteException extends RuntimeException {


    public InvalidRouteException( Cause cause ) {
        super( cause.getMessage() );
    }

    public InvalidRouteException( Cause cause, String route ) {
        super( cause.getMessage() + " (" + route + ")" );
    }


    public enum Cause {
        MISSING( "Route is missing!" ),
        INVALID( "Route is invalid!" );

        private String message;

        Cause( String message ) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}

/***********************************************************************************************
 *
 *                  All rights reserved, MadDev (c) copyright 2017
 *
 ***********************************************************************************************/