package com.minecraftlegend.inventoryapi.Router.exception;

/**
 * <h1>InvalidRouteException</h1>
 * <p>
 * A runtime exception thrown when the Router
 * can't get a valid URI route to a McGui.
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

    public InvalidRouteException( Cause cause, String message ) {
        super( cause.getMessage() + message );
    }

    public InvalidRouteException( Exception e, Cause cause, String message)
    {
        super(cause.getMessage()+" "+message,e.getCause());
    }


    public enum Cause {
        MISSING( "Route is missing!" ),
        INVALID( "Route is invalid!" ),
        NOTFOUND("Route not found or registered!"),
        QUERY("No suitable query found!");

        private String message;

        Cause( String message ) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
