# LWJGLFramework
##Overview
Simple framework for rendering objects using LWJGL 3. This framework supports:

• Creating models by selecting any pre-defined model from the ModelType enum or by adding a custom model \n
• Adding world objects such as primtives, cameras, lights, audio sources and more \n
• Controlling any object within the world \n
• Rendering 2D GUIs including basic shapes, images and text \n

## Usage
To use the framework, extend from the Framework class and use getProperites to change
FrameworkProperties. 
```java
public class App extends Framework {
     private FrameworkProperties properties = FrameworkProperties.getProperties();

}
```

Adding Models
``` java
//example of pre-defined model
addModel("modelname", ModelType.SQUARE, "texture");

//model with no texture
addModel("modelname", ModelType.SQUARE);

//custom model
addModel(".OBJ filename", ModelType.CUSTOM, "texture");
```




