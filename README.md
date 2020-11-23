# LWJGLFramework

## About
Simple framework for rendering objects using LWJGL 3. This framework supports:

* Creating models by selecting any pre-defined model from the ModelType enum or by adding a custom model 
* Adding world objects such as primtives, cameras, lights, audio sources and more 
* Controlling any object within the world 
* Rendering 2D GUIs including basic shapes, images and text 

Note: This is still a work in progress and is intended for generating customised default or bespoke models

## Usage
To use the framework, extend from the Framework class and use getProperites to change
FrameworkProperties. 
```java
public class MyApplication extends Framework {
     private FrameworkProperties properties = FrameworkProperties.getProperties();
     
     public MyApplication() {
          init();
          loop(); //automatically handles closing
     }
}
```
### Adding Models
``` java
//example of pre-defined model
addModel("modelname", ModelType.SQUARE, "texture");

//model with no texture
addModel("modelname", ModelType.SQUARE);

//custom model
addModel(".OBJ filename", ModelType.CUSTOM, "texture");
```
### GUIs

```java
//add text
addText("Hello World!");

```



