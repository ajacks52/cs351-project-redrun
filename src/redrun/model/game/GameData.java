package redrun.model.game;

import java.util.LinkedList;

import redrun.model.gameobject.GameObject;
import redrun.model.gameobject.MapObject;

public class GameData
{
  /** The list of most active map objects. */
  private LinkedList<MapObject> mapObjects = new LinkedList<MapObject>();
  
  /** The list of most active game objects. */
  private LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();
  
  // Add methods...
  
  /**
   * Adds a map object to the list of map objects.
   * 
   * @param mapObject a map object
   */
  public void addMapObject(MapObject mapObject)
  {
  	mapObjects.add(mapObject);
  }
  
  /**
   * Adds a game object to the list of map objects.
   * 
   * @param gameObject a game object
   */
  public void addGameObject(GameObject gameObject)
  {
  	gameObjects.add(gameObject);
  }
  
  // Getter methods...
  
  /**
   * Gets the list of active map objects.
   * 
   * @return the list of map objects
   */
  public LinkedList<MapObject> getMapObjects()
  {
  	return mapObjects;
  }
  
  /**
   * Gets the list of active game objects
   * 
   * @return the list of game objects
   */
  public LinkedList<GameObject> getGameObjects()
  {
  	return gameObjects;
  }
}
