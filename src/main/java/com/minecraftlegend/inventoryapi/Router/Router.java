package com.minecraftlegend.inventoryapi.Router;

import com.minecraftlegend.inventoryapi.GUILayout;
import com.minecraftlegend.inventoryapi.McGui;
import com.minecraftlegend.inventoryapi.Router.annotation.Query;
import com.minecraftlegend.inventoryapi.Router.annotation.Route;
import com.minecraftlegend.inventoryapi.Router.exception.InvalidGUIException;
import com.minecraftlegend.inventoryapi.Router.exception.InvalidRouteException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

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
public class Router {

    private final static Router instance = new Router();

    public static Router getInstance() {
        return instance;
    }

    private Router() {
    }

    private HashMap< URI, Class< ? extends McGui > > pages;


    public void registerGUI( Class< ? extends McGui > guiClass ) {
        //Check for Annotation
        if ( !guiClass.isAnnotationPresent( Route.class ) )
            throw new InvalidRouteException( MISSING, "Missing path for " + guiClass.getName() + "! Use the @Route annotation." );

        //force every URL to lower case letters
        String urlPath = guiClass.getDeclaredAnnotation( Route.class ).value().toLowerCase();
        if ( pages.containsKey( urlPath ) )
            throw new InvalidRouteException( INVALID, "Path \"" + urlPath + "\" already exists!" );
        URI uri = URI.create( urlPath );
        pages.put( uri, guiClass );
    }

    public void open( Player player, String path ) {
        path = path.toLowerCase();
        URI uri = URI.create( path );
        Map< String, QueryParameter > queryMap = splitQuery( uri );
        Class< ? extends McGui > clazz = pages.get( path );
        McGui ini = tryInitialization( clazz );
        if ( ini == null )
            throw new InvalidGUIException();
        ini.draw( player );

        if ( !queryMap.isEmpty() )
            query( ini,queryMap );

    }

    public void query( McGui gui, Map< String, QueryParameter > queryMap ) {
        try {
            Method queryMethod = getQueryMethod( gui.getClass(), queryMap.keySet() );
            if ( queryMap != null )
                callQueryMethod( queryMethod, gui, queryMap );
        } catch ( Exception e ) {
            throw new InvalidRouteException( INVALID, "Can't resolve query" );
        }
    }

    private void callQueryMethod( Method method, McGui guiInstance, Map< String, QueryParameter > values ) throws Exception {

        QueryParameter[] parameters = new QueryParameter[ method.getParameterCount() ];

        for ( int i = 0; i < parameters.length; i++ ) {
            parameters[ i ] = values.get( method.getParameters()[ i ].getName() );
        }

        method.invoke( guiInstance, parameters );
    }

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

    private McGui tryInitialization( Class< ? extends McGui > clazz ) {
        McGui ini;
        Constructor< ? extends McGui > constuctor;
        try {
            constuctor = clazz.getDeclaredConstructor();
            ini = constuctor.newInstance();
            return ini;
        } catch ( Exception e ) {

        }
        try {
            constuctor = clazz.getDeclaredConstructor( JavaPlugin.class, GUILayout.class );
            ini = constuctor.newInstance();
            return ini;
        } catch ( Exception e ) {

        }
        try {
            constuctor = clazz.getDeclaredConstructor( JavaPlugin.class, String.class, GUILayout.class );
            ini = constuctor.newInstance();
            return ini;
        } catch ( Exception e ) {

        }
        return null;
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

/***********************************************************************************************
 *
 *                  All rights reserved, MadDev (c) copyright 2017
 *
 ***********************************************************************************************/