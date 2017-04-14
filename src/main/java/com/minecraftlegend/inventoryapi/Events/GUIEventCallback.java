package com.minecraftlegend.inventoryapi.Events;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public interface GUIEventCallback <T extends EventWrapper>{

    void call(T event);


}
