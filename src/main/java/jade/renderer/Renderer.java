package jade.renderer;

import jade.GameObject;
import jade.components.SpriteRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Renderer
{
  private final int MAX_BATCH_SIZE = 1000;
  
  private List<RenderBatch> batches;
  
  private static Shader currentShader;
  
  public Renderer()
  {
    batches = new ArrayList<>();
  }
  
  public void add(GameObject go)
  {
    SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
    if (spr != null)
    {
      add(spr);
    }
  }
  
  public void add(SpriteRenderer spr)
  {
    boolean added = false;
    for (RenderBatch batch : batches)
    {
      if (batch.hasRoom() && batch.getzIndex() == spr.gameObject.getzIndex())
      {
        Texture tex = spr.getTexture();
        
        if (
          tex == null ||
            (batch.hasTexture(tex) || batch.hasTextureRoom())
        )
        {
          batch.addSprite(spr);
          added = true;
          break;
        }
      }
    }
    
    if (!added)
    {
      RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE, spr.gameObject.getzIndex());
      newBatch.start();
      batches.add(newBatch);
      newBatch.addSprite(spr);
    }
    
    Collections.sort(batches);
  }
  
  public void render()
  {
    currentShader.use();
    for (RenderBatch batch : batches)
    {
      batch.render();
    }
  }
  
  public static void bindShader(Shader shader)
  {
    currentShader = shader;
  }
  
  public static Shader getCurrentShader()
  {
    return currentShader;
  }
}
