package jade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import imgui.ImGui;
import jade.components.Component;
import jade.renderer.Renderer;
import jade.serialization.ComponentDeserializer;
import jade.serialization.GameObjectDeserializer;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public abstract class Scene
{
  public    boolean          isRunning        = false;
  protected Renderer         renderer         = new Renderer();
  protected Camera           camera;
  protected List<GameObject> gameObjects      = new ArrayList<>();
  protected GameObject       activeGameObject = null;
  protected boolean          levelLoaded      = false;
  
  protected Gson gson = new GsonBuilder()
    .setPrettyPrinting()
    .registerTypeAdapter(Component.class, new ComponentDeserializer())
    .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
    .create();
  
  public Scene() {}
  
  public void init() {}
  
  public void start()
  {
    for (GameObject go : gameObjects)
    {
      go.start();
      renderer.add(go);
    }
    isRunning = true;
  }
  
  public void addGameObjectToScene(GameObject go)
  {
    gameObjects.add(go);
    if (isRunning)
    {
      go.start();
      renderer.add(go);
    }
  }
  
  public void sceneImgui()
  {
    if (activeGameObject != null)
    {
      ImGui.begin("Inspector");
      activeGameObject.imgui();
      ImGui.end();
    }
    
    imgui();
  }
  
  public void saveExit()
  {
    try
    {
      FileWriter writer = new FileWriter("level.txt");
      writer.write(gson.toJson(gameObjects));
      writer.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public void load()
  {
    String inFile = "";
    try
    {
      inFile = new String(Files.readAllBytes(Paths.get("level.txt")));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    
    if (!inFile.equals(""))
    {
      GameObject[] objs = gson.fromJson(inFile, GameObject[].class);
      for (int i = 0; i < objs.length; i++)
      {
        addGameObjectToScene(objs[i]);
      }
      this.levelLoaded = true;
    }
  }
  
  public void imgui() {}
  
  public Camera getCamera()
  {
    return camera;
  }
  
  public abstract void update(float dt);
}
