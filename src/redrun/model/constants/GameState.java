package redrun.model.constants;

public enum GameState
{
  /** Waiting for other players to connect. */
  WAIT,

  /** Game play is available. */
  PLAY,

  /** Player is dead. */
  DEAD,

  /** Player has won. */
  WINNER,

  /** Player has lost. */
  LOSER;
}
