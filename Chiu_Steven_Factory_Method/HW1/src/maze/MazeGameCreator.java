/*
 * SimpleMazeGame.java
 * Copyright (c) 2008, Drexel University.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Drexel University nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY DREXEL UNIVERSITY ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DREXEL UNIVERSITY BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package maze;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import maze.ui.MazeViewer;

/**
 *
 * @author Sunny
 * @version 1.0
 * @since 1.0
 */
public class MazeGameCreator
{
	public Maze createMaze()
	{
		Maze maze = makeMaze();

		Room room0 = makeRoom(0);
		Room room1 = makeRoom(1);
		Door door0 = makeDoor(room0, room1);

		room0.setSide(Direction.North, makeWall());
		room0.setSide(Direction.South, door0);
		room0.setSide(Direction.East, makeWall());
		room0.setSide(Direction.West, makeWall());

		room1.setSide(Direction.North, door0);
		room1.setSide(Direction.South, makeWall());
		room1.setSide(Direction.East, makeWall());
		room1.setSide(Direction.West, makeWall());

		maze.addRoom(room0);
		maze.addRoom(room1);
		maze.setCurrentRoom(0);

		return maze;
	}

	public Maze loadMaze(final String path)
	{
		Maze maze = makeMaze();

		HashMap<Integer, Room> rooms = new HashMap<>();
		HashMap<String, Door> doors = new HashMap<>();
		ArrayList<String[]> lines = new ArrayList<>();

		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line;

			while ((line = reader.readLine()) != null)
			{
				line = line.trim();

				if (!line.isEmpty())
				{
					lines.add(line.split("\\s+"));
				}
			}

			reader.close();

			for (String[] parts : lines)
			{
				if (parts[0].equalsIgnoreCase("room"))
				{
					int room_number = Integer.parseInt(parts[1]);

					if (!rooms.containsKey(room_number))
					{
						Room room = makeRoom(room_number);
						rooms.put(room_number, room);
						maze.addRoom(room);
					}
				}
			}

			for (String[] parts : lines)
			{
				if (parts[0].equalsIgnoreCase("door"))
				{
					String door_id = parts[1];
					int room1_number = Integer.parseInt(parts[2]);
					int room2_number = Integer.parseInt(parts[3]);

					Room room1 = rooms.get(room1_number);
					Room room2 = rooms.get(room2_number);

					Door door = makeDoor(room1, room2);

					if (parts.length > 4)
					{
						if (parts[4].equalsIgnoreCase("open"))
						{
							door.setOpen(true);
						}
						else
						{
							door.setOpen(false);
						}
					}

					doors.put(door_id, door);
				}
			}

			for (String[] parts : lines)
			{
				if (parts[0].equalsIgnoreCase("room"))
				{
					int room_number = Integer.parseInt(parts[1]);
					Room room = rooms.get(room_number);

					room.setSide(Direction.North, getSide(parts[2], rooms, doors));
					room.setSide(Direction.South, getSide(parts[3], rooms, doors));
					room.setSide(Direction.East, getSide(parts[4], rooms, doors));
					room.setSide(Direction.West, getSide(parts[5], rooms, doors));
				}
			}

			maze.setCurrentRoom(0);
		}
		catch (IOException e)
		{
			System.out.println("Please load a maze from the file!");
		}

		return maze;
	}

	public MapSite getSide(String text, HashMap<Integer, Room> rooms, HashMap<String, Door> doors)
	{
		if (text.equalsIgnoreCase("wall"))
		{
			return makeWall();
		}

		if (doors.containsKey(text))
		{
			return doors.get(text);
		}

		return rooms.get(Integer.parseInt(text));
	}

	public Maze makeMaze()
	{
		return new Maze();
	}

	public Wall makeWall()
	{
		return new Wall();
	}

	public Door makeDoor(Room r1, Room r2)
	{
		return new Door(r1, r2);
	}

	public Room makeRoom(int roomNum)
	{
		return new Room(roomNum);
	}

	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);

		System.out.println("Choose your maze type: basic, red, or blue: ");
		String choice = scanner.nextLine();

		MazeGameCreator creator;

		if (choice.equalsIgnoreCase("red"))
		{
			creator = new RedMazeGameCreator();
		}
		else if (choice.equalsIgnoreCase("blue"))
		{
			creator = new BlueMazeGameCreator();
		}
		else
		{
			creator = new MazeGameCreator();
		}

		Maze maze = creator.loadMaze("large.maze");

		if (maze.getNumberOfRooms() == 0)
		{
			maze = creator.loadMaze("small.maze");
		}

		MazeViewer viewer = new MazeViewer(maze);
		viewer.run();
	}
}
