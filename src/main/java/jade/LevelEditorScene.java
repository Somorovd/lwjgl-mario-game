package jade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jade.components.SpriteRenderer;
import jade.components.SpriteSheet;
import jade.util.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;


public class LevelEditorScene extends Scene
{
  private GameObject obj1;
  
  public LevelEditorScene() {}
  
  private void loadResources()
  {
    AssetPool.getShader("assets/shaders/default.glsl");
    AssetPool.addSpriteSheet(
      "assets/images/spritesheet.png",
      new SpriteSheet(AssetPool.getTexture("assets/images/spritesheet.png"), 16, 16, 26, 0)
    );
  }
  
  @Override
  public void init()
  {
    loadResources();
    
    camera = new Camera(new Vector2f());
    
    SpriteSheet sprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");
    
    obj1 = new GameObject(
      "Object 1",
      new Transform(
        new Vector2f(0, 0),
        new Vector2f(256, 256)
      ),
      -1
    );
    SpriteRenderer obj1Sprite = new SpriteRenderer();
    obj1Sprite.setColor(new Vector4f(1, 0, 0, 1));
    obj1.addComponent(new SpriteRenderer());
    
    addGameObjectToScene(obj1);
    activeGameObject = obj1;
    
    Gson gson = new GsonBuilder()
      .setPrettyPrinting()
      .create();
    
    System.out.println(gson.toJson(obj1Sprite));
  }
  
  @Override
  public void update(float dt)
  {
    obj1.transform.position.x += 10 * dt;
    
    for (GameObject go : gameObjects)
    {
      go.update(dt);
    }
    
    renderer.render();
  }
}
