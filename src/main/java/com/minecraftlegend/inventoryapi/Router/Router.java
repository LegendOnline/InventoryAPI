package com.minecraftlegend.inventoryapi.Router;

import com.minecraftlegend.inventoryapi.McGui;
import com.minecraftlegend.inventoryapi.Router.annotation.Query;
import com.minecraftlegend.inventoryapi.Router.annotation.Route;
import com.minecraftlegend.inventoryapi.Router.exception.InvalidGUIException;
import com.minecraftlegend.inventoryapi.Router.exception.InvalidRouteException;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static com.minecraftlegend.inventoryapi.Router.exception.InvalidRouteException.Cause.INVALID;
import static com.minecraftlegend.inventoryapi.Router.exception.InvalidRouteException.Cause.MISSING;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

/**
 * <h1>Router Singleton</h1>
 * <p>
 * The Router instance contains a collection of all
 * registered gui class with their routes. This class
 * is responsible for instantiating guis for a given URI path.
 * Also query parameter are possible.
 * </p>
 *
 * @author Drayke
 * @version 1.0
 * @since 31.08.2017.
 */
public class Router {

    private final static Router instance = new Router();

    /**
     * Gets the singleton instance.
     *
     * @return the instance
     */
    public static Router getInstance() {
        return instance;
    }

    private Router() {
    }

    private HashMap< URI, Class< ? extends McGui > > pages;


    /**
     * Registers gui class.
     * Requirements:
     * <ul>
     * <li>The class needs a @Route(value="/path") Annotation</li>
     * <li>The class needs a default constructor</li>
     * </ul>
     *
     * @param guiClass the gui class
     */
    public void registerGUI( Class< ? extends McGui > guiClass ) {

        //Check for NoArgsConstructor
        try {
            guiClass.getDeclaredConstructor();
        } catch ( NoSuchMethodException e ) {
            throw new InvalidGUIException( guiClass );
        }

        //Get URI path and register gui
        URI uri = getRouterPath( guiClass );
        if ( uri != null ) {
            if ( pages.containsKey( uri ) )
                throw new InvalidRouteException( INVALID, "Path \"" + uri.toString() + "\" already exists!" );

            pages.put( uri, guiClass );
        }
    }

    /**
     * Gets the defined route given by the @Route Annotation
     * from a class.
     *
     * @param guiClass the McGui class with Route Annotaion
     * @return the defined URI
     */
    public URI getRouterPath( Class< ? extends McGui > guiClass ) {
        //Check for Annotation
        if ( !guiClass.isAnnotationPresent( Route.class ) )
            throw new InvalidRouteException( MISSING, "Missing path for " + guiClass.getName() + "! Use the @Route annotation." );

        //force every URL to lower case letters
        String urlPath = guiClass.getDeclaredAnnotation( Route.class ).value().toLowerCase();

        URI uri = null;
        try {
            uri = URI.create( urlPath );
        } catch ( IllegalArgumentException e ) {
            throw new InvalidRouteException( INVALID, "Path \"" + urlPath + "\" can't be resolved into an URI!" );
        }
        return uri;
    }

    /**
     * Opens a gui given by a gui class.
     *
     * @param player the player
     * @param clazz  the gui class
     * @return the mc gui instance or null
     */
    public McGui open( Player player, Class< ? extends McGui > clazz ) {
        for ( Entry< URI, Class< ? extends McGui > > entry : pages.entrySet() ) {
            if ( entry.getValue().equals( clazz ) ) {
                return open( player, entry.getKey().toString() );
            }
        }
        return null;
    }

    /**
     * Opens a gui given by a path.
     *
     * @param player the player
     * @param path   the path
     * @return the mc gui instance or null
     */
    public McGui open( Player player, String path ) {
        //Save paths to lower case
        path = path.toLowerCase();
        URI uri = URI.create( path );
        //Get query parameters
        Map< String, QueryParameter > queryMap = splitQuery( uri );
        //Find the gui by the path without query
        Class< ? extends McGui > clazz = pages.get( uri.getPath() );
        //Try to open the gui
        McGui ini = tryInitialization( clazz );
        if ( ini == null )
            throw new InvalidGUIException();
        ini.draw( player );

        if ( !queryMap.isEmpty() )
            query( ini, queryMap );

        return ini;
    }

    /**
     * Calls a method of a gui instance marked with the Query annotation.
     * Methods needs to have QueryParameter parameters with the name used
     * in a query.
     * <br>Example: "/some/path?val=1&2&foo=TestValue"
     * <br>     @Query
     * <br>     public void demo(QueryParameter val, QueryParameter foo)
     * <br>     {
     * <br>         // val = ["1","2"]
     * <br>         // foo = ["TestValue"]
     * <br>     }
     *
     * @param gui      the gui
     * @param queryMap the query map
     * @see QueryParameter
     * @see Query
     */
    public void query( McGui gui, Map< String, QueryParameter > queryMap ) {
        try {
            Method queryMethod = getQueryMethod( gui.getClass(), queryMap.keySet() );
            if ( queryMap != null )
                callQueryMethod( queryMethod, gui, queryMap );
        } catch ( Exception e ) {
            throw new InvalidRouteException( INVALID, "Can't resolve query" );
        }
    }

    /**
     * Method for calling a method from a gui instance with given parameters.
     *
     * @param method      the query method
     * @param guiInstance the gui instance
     * @param values      the parameter values
     * @throws Exception different exceptions
     */
    private void callQueryMethod( Method method, McGui guiInstance, Map< String, QueryParameter > values ) throws Exception {

        QueryParameter[] parameters = new QueryParameter[ method.getParameterCount() ];

        for ( int i = 0; i < parameters.length; i++ ) {
            parameters[ i ] = values.get( method.getParameters()[ i ].getName() );
        }

        method.invoke( guiInstance, parameters );
    }

    /**
     * Searches for a method marked with the Query annotation.
     * The method needs to have parameters from type QueryParameter
     * with the same name as the later query given by the URI!
     *
     * @param clazz
     * @param keys
     * @return a query method
     */
    private Method getQueryMethod( Class< ? extends McGui > clazz, Set< String > keys ) {

        int parameters = keys.size();
        List< Method > methods = Arrays.asList( clazz.getDeclaredMethods() );
        List< Method > queryMethods = methods.stream()
                .filter( method -> method.isAnnotationPresent( Query.class ) )
                .filter( method -> method.getParameterCount() == parameters )
                .collect( toList() );

        for ( Method method : queryMethods ) {
            boolean valid = true;
            for ( int i = 0; i < parameters; i++ ) {
                String tempName = method.getParameters()[ i ].getName().toLowerCase();
                if ( !keys.contains( tempName ) )
                    valid = false;
            }
            if ( valid ) return method;
        }
        return null;
    }

    /**
     * Tries to initialize a gui.
     * A default constructor is required for that.
     *
     * @param clazz the gui class
     * @return a McGui instance or null
     */
    private McGui tryInitialization( Class< ? extends McGui > clazz ) {
        McGui ini = null;
        Constructor< ? extends McGui > constuctor;
        //Try default constructor
        try {
            constuctor = clazz.getDeclaredConstructor();
            ini = constuctor.newInstance();
            return ini;
        } catch ( Exception e ) {

        }
        return ini;
    }

    /**
     * Splits the query from an URI into a map.
     * <br>URI: "/map/edit&page=1&2&map=world" will result into
     * <ul>
     * <li>page = "1","2"</li>
     * <li>map = "world"</li>
     * </ul>
     *
     * @param uri the uri
     * @return the map
     */
    public Map< String, QueryParameter > splitQuery( URI uri ) {
        if ( uri.getQuery() == null || uri.getQuery().isEmpty() )
            return Collections.emptyMap();

        return Arrays.stream( uri.getQuery().split( "&" ) )
                .map( this::splitQueryParameter )
                .collect( Collectors.groupingBy( SimpleImmutableEntry::getKey, LinkedHashMap::new,
                        Collectors.mapping( Entry::getValue, toCollection( QueryParameter::new ) ) ) );
    }


    private SimpleImmutableEntry< String, String > splitQueryParameter( String it ) {
        final int idx = it.indexOf( "=" );
        final String key = idx > 0 ? it.substring( 0, idx ) : it;
        final String value = idx > 0 && it.length() > idx + 1 ? it.substring( idx + 1 ) : null;
        return new SimpleImmutableEntry<>( key.toLowerCase(), value );
    }

}
