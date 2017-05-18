package com.minecraftlegend.inventoryapi.Elements;


import com.minecraftlegend.inventoryapi.GUIComponent;
import com.minecraftlegend.inventoryapi.GUIContainer;
import com.minecraftlegend.inventoryapi.GUIElement;
import com.minecraftlegend.inventoryapi.ItemBuilder;
import com.minecraftlegend.inventoryapi.utils.Vector2i;
import org.bukkit.Material;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2017 by Jan Hof
 * All rights reserved.
 **/
public class GUIndicator extends GUILabel implements GUIElement {
    private GUILabel label;
    private IndicatorState state = IndicatorState.DENIED; //0 = denied, 1 = delayed, 2 = accepted
    private String actionDenied = "", actiondelayed = "", actionAccepted = "";

    public GUIndicator(GUILabel label, String actionDenied, String actiondelayed, String actionAccepted){
        super(actionDenied,  Material.STAINED_GLASS_PANE, (byte) 14 );
        this.label = label;
        this.actiondelayed = actiondelayed;
        this.actionAccepted = actionAccepted;
        setSize(new Vector2i( 1, 2 ));

    }


    public void accept(){
        state = IndicatorState.ACCEPTED;
        setIcon( new ItemBuilder().type( Material.STAINED_GLASS_PANE ).durability( (short) 13 ).name( actionAccepted ).build() );
        draw();
    }

    public void delayed(){
        state = IndicatorState.DELAYED;
        setIcon( new ItemBuilder().type( Material.STAINED_GLASS_PANE ).durability( (short) 1 ).name( actiondelayed ).build() );
        draw();
    }

    public void denied(){
        state = IndicatorState.DENIED;
        setIcon( new ItemBuilder().type( Material.STAINED_GLASS_PANE ).durability( (short) 14 ).name( actionDenied ).build() );
        draw();
    }

    @Override
    public void setParent( GUIComponent parent ) {
        super.setParent( parent );
        if(label != null)
            ((GUIContainer)parent).add( label );
    }


    @Override
    public void setPosition( Vector2i dimension ) {
        super.setPosition( dimension );

        if(label != null) {
            Vector2i labelPos = dimension.clone();
            labelPos.setY( dimension.getY() - 1  );
            this.label.setPosition( labelPos);
        }
    }

    @Override
    public void draw() {
        super.draw();
        if(label != null)
            label.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        if(label != null)
            label.dispose();
    }

    public GUILabel getLabel() {
        return label;
    }


    public String getActionDenied() {
        return actionDenied;
    }

    public void setActionDenied( String actionDenied ) {
        this.actionDenied = actionDenied;
        setTitle( actionDenied );
    }

    public String getActiondelayed() {
        return actiondelayed;
    }

    public void setActiondelayed( String actiondelayed ) {
        this.actiondelayed = actiondelayed;
        setTitle( actiondelayed );
    }

    public String getActionAccepted() {
        return actionAccepted;
    }

    public void setActionAccepted( String actionAccepted ) {
        this.actionAccepted = actionAccepted;
        setTitle( actionAccepted );
    }

    public IndicatorState getState() {
        return state;
    }


    public enum IndicatorState{
        ACCEPTED, DELAYED, DENIED
    }

}
