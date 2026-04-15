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

import maze.ui.MazeViewer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
/**
 *
 * @author Sunny
 * @version 1.0
 * @since 1.0
 */
public class SimpleMazeGame
{
	/**
	 * Creates a small maze.
	 */
	public static Maze createMaze()
	{

		Maze maze = new Maze();

		Room room0 = new Room(0);
		Room room1 = new Room(1);

		Door door = new Door(room0, room1);

		room0.setSide(Direction.North, new Wall());
		room0.setSide(Direction.South, door);
		room0.setSide(Direction.East, new Wall());
		room0.setSide(Direction.West, new Wall());

		room1.setSide(Direction.North, door);
		room1.setSide(Direction.South, new Wall());
		room1.setSide(Direction.East, new Wall());
		room1.setSide(Direction.West, new Wall());

		maze.addRoom(room0);
		maze.addRoom(room1);

		maze.setCurrentRoom(0);

		return maze;


	}

	public static Maze loadMaze(final String path)
	{
		Maze maze = new Maze();

		//Storage for the maze files.
		HashMap<Integer, Room> rooms = new HashMap<Integer, Room>();
		HashMap<String, Door> doors = new HashMap<String, Door>();
		HashMap<Integer, String[]> room_data = new HashMap<Integer, String[]>();

		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(path));

			String line;

			while ((line = reader.readLine()) != null)
			{
				line = line.trim();

				if (line.isEmpty())
				{
					continue;
				}

				String[] parts = line.split("\\s+");

				if (parts[0].equalsIgnoreCase("room"))
				{
					int room_number = Integer.parseInt(parts[1]);

					Room room = new Room(room_number);
					rooms.put(room_number, room);
					maze.addRoom(room);

					room_data.put(room_number, new String[]{parts[2], parts[3], parts[4], parts[5]});
				}

				else if (parts[0].equalsIgnoreCase("door"))
				{
					String door_id = parts[1];

					int room1_number = Integer.parseInt(parts[2]);
					int room2_number = Integer.parseInt(parts[3]);

					Room room1 = rooms.get(room1_number);
					Room room2 = rooms.get(room2_number);

					if (room1 == null)
					{
						room1 = new Room(room1_number);
						rooms.put(room1_number, room1);
						maze.addRoom(room1);
					}

					if (room2 == null)
					{
						room2 = new Room(room2_number);
						rooms.put(room2_number, room2);
						maze.addRoom(room2);
					}

					Door door = new Door(room1, room2);
					doors.put(door_id, door);
				}
			}

			reader.close();

			for (int room_number : room_data.keySet())
			{
				Room room = rooms.get(room_number);
				String[] sides = room_data.get(room_number);

				room.setSide(Direction.North, getSide(sides[0], rooms, doors));
				room.setSide(Direction.South, getSide(sides[1], rooms, doors));
				room.setSide(Direction.East, getSide(sides[2], rooms, doors));
				room.setSide(Direction.West, getSide(sides[3], rooms, doors));
			}

			maze.setCurrentRoom(0);
		}
		catch (IOException e)
		{
			System.out.println("Please load a maze from the file!");
		}

		return maze;
	}

	public static MapSite getSide(String text, HashMap<Integer, Room> rooms, HashMap<String, Door> doors)
	{
		if (text.equalsIgnoreCase("wall"))
		{
			return new Wall();
		}

		if (text.startsWith("d"))
		{
			return doors.get(text);
		}

		return rooms.get(Integer.parseInt(text));
	}

	public static void main(String[] args)
	{
		Maze maze = loadMaze("large.maze");
		MazeViewer viewer = new MazeViewer(maze);
		viewer.run();
	}
}
