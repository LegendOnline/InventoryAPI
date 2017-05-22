# InventoryAPI 1.1
![logo](https://www.minecraftlegend.com/wp-content/uploads/2016/12/legend-awakening_logo.png)  

This inventory library is used to easily create/modify Minecraft inventories.<br>
Add events to specific items, generate gui inventories automatically or create big complex menu structures!<br>
The design is inspired by the Java Swing API and is as easy to use.<br>
Have a look at our [Wiki](https://github.com/LegendOnline/InventoryAPI/wiki) to gain a better understand of how this
API will change your thinking about Minecraft inventories!

## Last CI build status: [![Build Status](https://travis-ci.org/LegendOnline/InventoryAPI.svg?branch=master)](https://travis-ci.org/LegendOnline/InventoryAPI)

## How to install
To use this library, integrate it into your Java build path and compile it with your plugin.<br>
This library is **not** a standalone plugin!<br>
There are two ways to implement this library into your project:
### Maven
```xml
 <repository>
   <id>LegendOnline</id>
   <url>https://raw.githubusercontent.com/LegendOnline/InventoryAPI/mvn-repo/</url>
 </repository>

 <dependency>
    <groupId>com.minecraftlegend</groupId>
    <artifactId>inventoryapi</artifactId>
    <version>1.1-SNAPSHOT</version>
    <scope>compile</scope>
 </dependency>
```
### Direct implementation
Either you can download the source code from [here](https://github.com/LegendOnline/InventoryAPI.git) and copy it into a separate package of your project,<br>
or download a compiled version from [here](https://github.com/LegendOnline/InventoryAPI/blob/mvn-repo/com/minecraftlegend/inventoryapi/1.0-SNAPSHOT/inventoryapi-1.0-20170414.234038-1.jar) (freshly compiled from our ci Server) and integrate the library as a dependency.

## How to use
Basically, if you know Java Swing, you know this InventoryAPI<br>
But please take a look at our [Wiki](https://github.com/LegendOnline/InventoryAPI/wiki)
Here are some snapshots of inventories created with our API:

## Changelog
### Version 1.1:
+ added TextInput support trough Anvil gui's
+ added events to handle text input
+ added GUInput element to support item inputs
+ removed lots of bugs
+ implemented more exceptions
+ added some JavaDocs
+ added ItemBuilder class for easy item creation
+ added various util classes regarding nms, reflection and exceptions 
## TODO
+ ~~implement Anvil GUIs (as text input)~~
+ better error handling
+ JavaDocs
+ ADD MORE FUNCTIONALITY!!!

## Contribute
**Note:** this project is currently under active development by **our team**!<br>
We are pushing updates here, if we successfully updated and tested our own private repository <br>
If we alter the code in our main repo we will share the updates here.<br>
If you want to contribute to this awesome project please use GitHub's [Pull request](https://github.com/LegendOnline/InventoryAPI/compare)
feature.<br>
Approved code will be merged back into our main repo, so you can see your code on our Network! Nice!<br>
If you want to contribute but you don't know what to do, we would be pleased if you work on tasks either from the TODO list
or from the [Issues](https://github.com/LegendOnline/InventoryAPI/issues) Page.<br>
If you are not that much into, digging into other peoples code feel free to open an issue and tell us your awesome feature ideas.
