package jade;

import jade.components.SpriteRenderer;
import jade.components.SpriteSheet;
import jade.util.AssetPool;
import org.joml.Vector2f;


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
    obj1.addComponent(
      new SpriteRenderer(sprites.getSprite(0))
    );
    addGameObjectToScene(obj1);
    activeGameObject = obj1;
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
