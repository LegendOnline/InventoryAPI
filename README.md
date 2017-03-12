#InventoryAPI  
![logo](https://www.minecraftlegend.com/wp-content/uploads/2016/12/legend-awakening_logo.png)  

The official Legend Online Inventory API<br>

##How to install
To use this library, add it to your Java build path and compile it with your plugin. This library is **not** a standalone plugin.<br>
There are two ways to implement this library into your IDE:
###Maven
```xml
<repositories>
  <repository>
    <id>dmulloy2-repo</id>
    <url>http://repo.dmulloy2.net/content/groups/public/</url>
  </repository>
  ...
</repositories>

<dependencies>
  <dependency>
    <groupId>com.comphenix.protocol</groupId>
    <artifactId>ProtocolLib</artifactId>
    <version>3.6.5</version>
  </dependency>
</dependencies>
```
###Direct implementation
Either you can download the source code from [here](https://github.com/LegendOnline/InventoryAPI.git) and copy it into a separate package of your project,<br>
or download a compiled version from [here]() (freshly compiled from our ci Server) and integrate the library as a dependency.

##How to use
Basically, if you know Java Swing, you know this InventoryAPI

##TODO
+ implement Anvil GUIs (as text input)
+ better error handling
+ JavaDocs

##Contribute
Note: this project is currently **not** under active development by our team!<br>
If we alter the code in our main repo we will share the updates here.<br>
If you want to contribute to this awesome project please use GitHub's [Pull request](https://github.com/LegendOnline/InventoryAPI/compare)
feature.<br>
Approved code will be merged back into our main repo, so you can see your code on our Network! Nice!<br>
If you are not that much into, digging into other peoples code feel free to open an issue and tell us your awesome feature ideas.
