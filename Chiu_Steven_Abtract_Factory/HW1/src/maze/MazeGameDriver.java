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
public class MazeGameDriver {

	public static Maze loadMaze(final String path, MazeFactory factory) {

		Maze maze = factory.makeMaze();

		HashMap<Integer, Room> rooms = new HashMap<>();
		HashMap<String, Door> doors = new HashMap<>();
		ArrayList<String[]> lines = new ArrayList<>();

		try {

			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line;

			while ((line = reader.readLine()) != null) {
				line = line.trim();

				if (!line.isEmpty()) {
					lines.add(line.split("\\s+"));
				}
			}

			reader.close();

			for (String[] parts : lines) {

				if (parts[0].equalsIgnoreCase("room")) {

					int room_number = Integer.parseInt(parts[1]);

					if (!rooms.containsKey(room_number)) {

						Room room = factory.makeRoom(room_number);
						rooms.put(room_number, room);
						maze.addRoom(room);
					}
				}
			}

			for (String[] parts : lines) {

				if (parts[0].equalsIgnoreCase("door")) {

					String door_id = parts[1];

					int room1_number = Integer.parseInt(parts[2]);
					int room2_number = Integer.parseInt(parts[3]);

					Room room1 = rooms.get(room1_number);
					Room room2 = rooms.get(room2_number);

					Door door = factory.makeDoor(room1, room2);

					if (parts.length > 4) {

						if (parts[4].equalsIgnoreCase("open")) {

							door.setOpen(true);

						} else {

							door.setOpen(false);
						}
					}

					doors.put(door_id, door);
				}
			}

			for (String[] parts : lines) {
				if (parts[0].equalsIgnoreCase("room")) {
					int room_number = Integer.parseInt(parts[1]);
					Room room = rooms.get(room_number);

					room.setSide(Direction.North, getSide(parts[2], rooms, doors, factory));
					room.setSide(Direction.South, getSide(parts[3], rooms, doors, factory));
					room.setSide(Direction.East, getSide(parts[4], rooms, doors, factory));
					room.setSide(Direction.West, getSide(parts[5], rooms, doors, factory));
				}
			}

			maze.setCurrentRoom(0);
		} catch (IOException e) {
			System.out.println("Please load a maze from the file!");
		}

		return maze;
	}

	public static MapSite getSide(String text, HashMap<Integer, Room> rooms, HashMap<String, Door> doors, MazeFactory factory) {
		if (text.equalsIgnoreCase("wall")) {
			return factory.makeWall();
		}

		if (doors.containsKey(text)) {
			return doors.get(text);
		}

		return rooms.get(Integer.parseInt(text));
	}

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Choose maze type: red or blue: ");
		String choice = scanner.nextLine();

		MazeFactory factory;

		if (choice.equalsIgnoreCase("blue")) {
			factory = new BlueMazeFactory();
		} else {
			factory = new RedMazeFactory();
		}

		Maze maze = loadMaze("large.maze", factory);

		if (maze.getNumberOfRooms() == 0) {
			maze = loadMaze("small.maze", factory);
		}

		MazeViewer viewer = new MazeViewer(maze);
		viewer.run();

		scanner.close();
	}
}

