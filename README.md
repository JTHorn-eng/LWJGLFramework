# LWJGLFramework
Simple framework for rendering objects using LWJGL 3. This framework supports

• Creating models by selecting any pre-defined model from the ModelType enum or by adding
a custom model
• Add light sources
• Attach a basic controller to any entity in the world

## Usage
To use the framework, extend from the Framework class and use getProperites to change
FrameworkProperties. 
'''java
public class App extends Framework {
     private FrameworkProperties properties = FrameworkProperties.getProperties();

}
'''

Adding Models
''' java
//example of pre-defined model
addModel("modelname", ModelType.SQUARE, "texture");

//model with no texture
addModel("modelname", ModelType.SQUARE);

//custom model
addModel(".OBJ filename", ModelType.CUSTOM, "texture");
'''




