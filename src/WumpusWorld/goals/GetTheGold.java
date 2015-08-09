package WumpusWorld.goals;

import java.util.Random;
import java.util.Stack;

import WumpusWorld.Coordinate;
import WumpusWorld.Room;
import WumpusWorld.agents.Hunter;
import WumpusWorld.util.Direction;
import WumpusWorld.util.Path;
import jamder.behavioural.Plan;
import jamder.structural.Goal;

public class GetTheGold extends Goal 
{
	private Hunter agent;
	private Random random;
	private Stack<Room> path = new Stack<Room>();
	
	public GetTheGold(Hunter agent, Random random) {
		setName("GetTheGold");
		this.agent = agent;
		this.random = random;
	}
	
	public Plan toDo() {
		Plan plan = new Plan(agent);
		agent.addPlan("GetTheGold", plan);
		
		if( agent.getKnowledgeBase().getCurrentRoom().isGlitter() ) {
			plan.addAction( agent.getAction("Grab") );
		} else {
			Room currentRoom = agent.getKnowledgeBase().getCurrentRoom();
			Coordinate target = Path.getNextRoomBFS(agent);
			
			if( Path.thereIsSafePlaceToGo(agent) && target != null ) {
				if( path.isEmpty() ) {
					path = Path.getPathDFS(agent, target);
				}
				
				System.out.println(path);//FIXME
				
				if( !path.isEmpty() ) {
					Room nextRoom = path.pop();
					Path.moveToNextRoom(agent, nextRoom, plan);
				} else {
					Room nextRoom = pathfinder(currentRoom, target);
					if(nextRoom != null) {
						Path.moveToNextRoom(agent, nextRoom, plan);
					} else {
						System.out.println("Sorry, the agent don't know how to find a path!");
						Direction direction = agent.getKnowledgeBase().getRandomNextDirection( currentRoom );
						Path.moveTo( agent, direction, plan );
					}
				}
			} else {
				System.out.println("Sorry, but there is no safe place to go!");
				Direction direction = agent.getKnowledgeBase().getRandomNextDirection( currentRoom );
				Path.moveTo( agent, direction, plan );
			}
		}
		
		/*
		if( agent.getKnowledgeBase().getCurrentRoom().isGlitter() ) {
			plan.addAction( agent.getAction("Grab") );
		} else {
			Room currentRoom = agent.getKnowledgeBase().getCurrentRoom();
			Coordinate coordinate = Path.getNextRoomBFS(agent);
			
			if( Path.thereIsSafePlaceToGo(agent) && coordinate != null ) {	
				int x = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getX();
				int y = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getY();
					
				if( coordinate.getX() < x && coordinate.getY() < y ) {
					if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) || agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.WEST) ) {
						if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) && agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.WEST) ) {
							int coin = random.nextInt(2);
							if( coin == 1 ) {
								Path.moveTo(agent, Direction.NORTH, plan);
							}
							else {
								Path.moveTo(agent, Direction.WEST, plan);
							}
						}
						else if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) ) {
							Path.moveTo(agent, Direction.NORTH, plan);
						}
						else {
							Path.moveTo(agent, Direction.WEST, plan);
						}
					}
					else {
						Path.moveToNextRoom(agent, agent.getKnowledgeBase().getPreviousRoom(), plan);
					}
				}
				else if( coordinate.getX() < x && coordinate.getY() > y ) {
					if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.SOUTH) || agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.WEST) ) {
						if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.SOUTH) && agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.WEST) ) {
							int coin = random.nextInt(2);
							if( coin == 1 ) {
								Path.moveTo(agent, Direction.SOUTH, plan);
							}
							else {
								Path.moveTo(agent, Direction.WEST, plan);
							}
						}
						else if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.SOUTH) ) {
							Path.moveTo(agent, Direction.SOUTH, plan);
						}
						else {
							Path.moveTo(agent, Direction.WEST, plan);
						}
					}
					else {
						Path.moveToNextRoom(agent, agent.getKnowledgeBase().getPreviousRoom(), plan);
					}
				}
				else if( coordinate.getX() > x && coordinate.getY() < y ) {
					if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.EAST) || agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) ) {
						if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.EAST) && agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) ) {
							int coin = random.nextInt(2);
							if( coin == 1 ) {
								Path.moveTo(agent, Direction.EAST, plan);
							}
							else {
								Path.moveTo(agent, Direction.NORTH, plan);
							}
						}
						else if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.EAST) ) {
							Path.moveTo(agent, Direction.EAST, plan);
						}
						else {
							Path.moveTo(agent, Direction.NORTH, plan);
						}
					}
					else {
						Path.moveToNextRoom(agent, agent.getKnowledgeBase().getPreviousRoom(), plan);
					}
				}
				else if( coordinate.getX() > x && coordinate.getY() > y ) {
					if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.SOUTH) || agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.EAST) ) {
						if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.SOUTH) && agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.EAST) ) {
							int coin = random.nextInt(2);
							if( coin == 1 ) {
								Path.moveTo(agent, Direction.SOUTH, plan);
							}
							else {
								Path.moveTo(agent, Direction.EAST, plan);
							}
						}
						else if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.SOUTH) ) {
							Path.moveTo(agent, Direction.SOUTH, plan);
						}
						else {
							Path.moveTo(agent, Direction.EAST, plan);
						}
					}
					else {
						Path.moveToNextRoom(agent, agent.getKnowledgeBase().getPreviousRoom(), plan);
					}
				}
				else if( coordinate.getX() == x && coordinate.getY() < y ) {
					if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) ) {
						Path.moveTo(agent, Direction.NORTH, plan);
					} else {
						if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.WEST) || agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.EAST) ) {
							if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.WEST) && agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.EAST) ) {
								int coin = random.nextInt(2);
								if( coin == 1 ) {
									Path.moveTo(agent, Direction.WEST, plan);
								}
								else {
									Path.moveTo(agent, Direction.EAST, plan);
								}
							}
							else if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.WEST) ) {
								Path.moveTo(agent, Direction.WEST, plan);
							}
							else {
								Path.moveTo(agent, Direction.EAST, plan);
							}
						}
						else {
							Path.moveToNextRoom(agent, agent.getKnowledgeBase().getPreviousRoom(), plan);
						}
					}
				}
				else if( coordinate.getX() == x && coordinate.getY() > y ) {
					if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.SOUTH) ) {
						Path.moveTo(agent, Direction.SOUTH, plan);
					}
					else {
						if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.WEST) || agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.EAST) ) {
							if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.WEST) && agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.EAST) ) {
								int coin = random.nextInt(2);
								if( coin == 1 ) {
									Path.moveTo(agent, Direction.WEST, plan);
								}
								else {
									Path.moveTo(agent, Direction.EAST, plan);
								}
							}
							else if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.WEST) ) {
								Path.moveTo(agent, Direction.WEST, plan);
							}
							else {
								Path.moveTo(agent, Direction.EAST, plan);
							}
						}
						else {
							Path.moveToNextRoom(agent, agent.getKnowledgeBase().getPreviousRoom(), plan);
						}
					}
				}
				else if( coordinate.getX() < x && coordinate.getY() == y ) {
					if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.WEST) ) {
						Path.moveTo(agent, Direction.WEST, plan);
					}
					else {
						if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) || agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.SOUTH) ) {
							if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) && agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.SOUTH) ) {
								int coin = random.nextInt(2);
								if( coin == 1 ) {
									Path.moveTo(agent, Direction.NORTH, plan);
								}
								else {
									Path.moveTo(agent, Direction.SOUTH, plan);
								}
							}
							else if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) ) {
								Path.moveTo(agent, Direction.NORTH, plan);
							}
							else {
								Path.moveTo(agent, Direction.SOUTH, plan);
							}
						}
						else {
							Path.moveToNextRoom(agent, agent.getKnowledgeBase().getPreviousRoom(), plan);
						}
					}
				}
				else if( coordinate.getX() > x && coordinate.getY() == y ) {
					if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.EAST) ) {
						Path.moveTo(agent, Direction.EAST, plan);
					}
					else {
						if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) || agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.SOUTH) ) {
							if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) && agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.SOUTH) ) {
								int coin = random.nextInt(2);
								if( coin == 1 ) {
									Path.moveTo(agent, Direction.NORTH, plan);
								}
								else {
									Path.moveTo(agent, Direction.SOUTH, plan);
								}
							}
							else if( agent.getKnowledgeBase().isSafeMove(currentRoom, Direction.NORTH) ) {
								Path.moveTo(agent, Direction.NORTH, plan);
							}
							else {
								Path.moveTo(agent, Direction.SOUTH, plan);
							}
						}
						else {
							Path.moveToNextRoom(agent, agent.getKnowledgeBase().getPreviousRoom(), plan);
						}
					}
				}
			}
			else {
				System.out.println("Sorry, but there is no safe place to go!");
				Direction direction = agent.getKnowledgeBase().getRandomNextDirection( currentRoom );
				Path.moveTo( agent, direction, plan );
			}
		}*/
		
		return plan;
	}
	
	private Room pathfinder(Room room,Coordinate target) {	
		int x = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getX();
		int y = agent.getKnowledgeBase().getCurrentRoom().getCoordinate().getY();
			
		if( target.getX() < x && target.getY() < y ) {
			if( agent.getKnowledgeBase().isSafeMove(room, Direction.NORTH) || agent.getKnowledgeBase().isSafeMove(room, Direction.WEST) ) {
				if( agent.getKnowledgeBase().isSafeMove(room, Direction.NORTH) && agent.getKnowledgeBase().isSafeMove(room, Direction.WEST) ) {
					int coin = random.nextInt(2);
					if( coin == 1 ) {
						return agent.getMaze().getRoom(x, y-1);
					} else {
						return agent.getMaze().getRoom(x-1, y);
					}
				} else if( agent.getKnowledgeBase().isSafeMove(room, Direction.NORTH) ) {
					return agent.getMaze().getRoom(x, y-1);
				} else {
					return agent.getMaze().getRoom(x-1, y);
				}
			}
		} else if( target.getX() < x && target.getY() > y ) {
			if( agent.getKnowledgeBase().isSafeMove(room, Direction.SOUTH) || agent.getKnowledgeBase().isSafeMove(room, Direction.WEST) ) {
				if( agent.getKnowledgeBase().isSafeMove(room, Direction.SOUTH) && agent.getKnowledgeBase().isSafeMove(room, Direction.WEST) ) {
					int coin = random.nextInt(2);
					if( coin == 1 ) {
						return agent.getMaze().getRoom(x, y+1);
					} else {
						return agent.getMaze().getRoom(x-1, y);
					}
				} else if( agent.getKnowledgeBase().isSafeMove(room, Direction.SOUTH) ) {
					return agent.getMaze().getRoom(x, y+1);
				} else {
					return agent.getMaze().getRoom(x-1, y);
				}
			}
		} else if( target.getX() > x && target.getY() < y ) {
			if( agent.getKnowledgeBase().isSafeMove(room, Direction.EAST) || agent.getKnowledgeBase().isSafeMove(room, Direction.NORTH) ) {
				if( agent.getKnowledgeBase().isSafeMove(room, Direction.EAST) && agent.getKnowledgeBase().isSafeMove(room, Direction.NORTH) ) {
					int coin = random.nextInt(2);
					if( coin == 1 ) {
						return agent.getMaze().getRoom(x+1, y);
					}
					else {
						return agent.getMaze().getRoom(x, y-1);
					}
				} else if( agent.getKnowledgeBase().isSafeMove(room, Direction.EAST) ) {
					return agent.getMaze().getRoom(x+1, y);
				} else {
					return agent.getMaze().getRoom(x, y-1);
				}
			}
		} else if( target.getX() > x && target.getY() > y ) {
			if( agent.getKnowledgeBase().isSafeMove(room, Direction.SOUTH) || agent.getKnowledgeBase().isSafeMove(room, Direction.EAST) ) {
				if( agent.getKnowledgeBase().isSafeMove(room, Direction.SOUTH) && agent.getKnowledgeBase().isSafeMove(room, Direction.EAST) ) {
					int coin = random.nextInt(2);
					if( coin == 1 ) {
						return agent.getMaze().getRoom(x, y+1);
					} else {
						return agent.getMaze().getRoom(x+1, y);
					}
				} else if( agent.getKnowledgeBase().isSafeMove(room, Direction.SOUTH) ) {
					return agent.getMaze().getRoom(x, y+1);
				} else {
					return agent.getMaze().getRoom(x-1, y);
				}
			}
		} else if( target.getX() == x && target.getY() < y ) {
			if( agent.getKnowledgeBase().isSafeMove(room, Direction.NORTH) ) {
				return agent.getMaze().getRoom(x, y-1);
			} else {
				if( agent.getKnowledgeBase().isSafeMove(room, Direction.WEST) || agent.getKnowledgeBase().isSafeMove(room, Direction.EAST) ) {
					if( agent.getKnowledgeBase().isSafeMove(room, Direction.WEST) && agent.getKnowledgeBase().isSafeMove(room, Direction.EAST) ) {
						int coin = random.nextInt(2);
						if( coin == 1 ) {
							return agent.getMaze().getRoom(x-1, y);
						} else {
							return agent.getMaze().getRoom(x+1, y);
						}
					} else if( agent.getKnowledgeBase().isSafeMove(room, Direction.WEST) ) {
						return agent.getMaze().getRoom(x-1, y);
					} else {
						return agent.getMaze().getRoom(x+1, y);
					}
				}
			}
		} else if( target.getX() == x && target.getY() > y ) {
			if( agent.getKnowledgeBase().isSafeMove(room, Direction.SOUTH) ) {
				return agent.getMaze().getRoom(x, y+1);
			} else {
				if( agent.getKnowledgeBase().isSafeMove(room, Direction.WEST) || agent.getKnowledgeBase().isSafeMove(room, Direction.EAST) ) {
					if( agent.getKnowledgeBase().isSafeMove(room, Direction.WEST) && agent.getKnowledgeBase().isSafeMove(room, Direction.EAST) ) {
						int coin = random.nextInt(2);
						if( coin == 1 ) {
							return agent.getMaze().getRoom(x-1, y);
						}
						else {
							return agent.getMaze().getRoom(x+1, y);
						}
					} else if( agent.getKnowledgeBase().isSafeMove(room, Direction.WEST) ) {
						return agent.getMaze().getRoom(x-1, y);
					} else {
						return agent.getMaze().getRoom(x+1, y);
					}
				}
			}
		} else if( target.getX() < x && target.getY() == y ) {
			if( agent.getKnowledgeBase().isSafeMove(room, Direction.WEST) ) {
				return agent.getMaze().getRoom(x-1, y);
			} else {
				if( agent.getKnowledgeBase().isSafeMove(room, Direction.NORTH) || agent.getKnowledgeBase().isSafeMove(room, Direction.SOUTH) ) {
					if( agent.getKnowledgeBase().isSafeMove(room, Direction.NORTH) && agent.getKnowledgeBase().isSafeMove(room, Direction.SOUTH) ) {
						int coin = random.nextInt(2);
						if( coin == 1 ) {
							return agent.getMaze().getRoom(x, y-1);
						} else {
							return agent.getMaze().getRoom(x, y+1);
						}
					} else if( agent.getKnowledgeBase().isSafeMove(room, Direction.NORTH) ) {
						return agent.getMaze().getRoom(x, y-1);
					} else {
						return agent.getMaze().getRoom(x, y+1);
					}
				}
			}
		} else if( target.getX() > x && target.getY() == y ) {
			if( agent.getKnowledgeBase().isSafeMove(room, Direction.EAST) ) {
				return agent.getMaze().getRoom(x+1, y);
			} else {
				if( agent.getKnowledgeBase().isSafeMove(room, Direction.NORTH) || agent.getKnowledgeBase().isSafeMove(room, Direction.SOUTH) ) {
					if( agent.getKnowledgeBase().isSafeMove(room, Direction.NORTH) && agent.getKnowledgeBase().isSafeMove(room, Direction.SOUTH) ) {
						int coin = random.nextInt(2);
						if( coin == 1 ) {
							return agent.getMaze().getRoom(x, y-1);
						} else {
							return agent.getMaze().getRoom(x, y+1);
						}
					} else if( agent.getKnowledgeBase().isSafeMove(room, Direction.NORTH) ) {
						return agent.getMaze().getRoom(x, y-1);
					} else {
						return agent.getMaze().getRoom(x, y+1);
					}
				}
			}
		}
		
		return null;
	}
	
	public Plan clairvoyance() {
		Plan plan = new Plan(agent);
		agent.addPlan("GetTheGold", plan);
		
		if(agent.getKnowledgeBase().getCurrentRoom().isGlitter()  && agent.getAmountGold() < 1) {
			plan.addAction( agent.getAction("Grab") );
		} else {
			//TODO
		}
		
		return plan;
	}
}
