package org.toilelibre.libe.ratrap.server.build;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.toilelibre.libe.ratrap.shared.DataMap;
import org.toilelibre.libe.ratrap.shared.Line;
import org.toilelibre.libe.ratrap.shared.Station;
import org.toilelibre.libe.ratrap.shared.TransportType;

public class BuildConnectionsImpl implements Builder {

	private DataMap dataMap;

	@Override
	public void build(final String input) throws IOException {
		final Map<String, Line> lines = this.buildLineNames(input);

		this.dataMap.setLines(lines);

		this.fillLinesStations(input);

		this.guessLinesPaths();
	}

	private Map<String, Line> buildLineNames(final String input)
			throws IOException {
		final Map<String, Line> result = new HashMap<String, Line>();
		final BufferedReader reader = new BufferedReader(
				new StringReader(input));
		String line = "";

		while ((line = reader.readLine()) != null) {
			line = line.replace("&#39;", "'");
			final String[] fields = line.split("#");
			final String lineField = fields[1];
			final java.util.regex.Pattern p1 = java.util.regex.Pattern
					.compile("^([A-Z0-9]+) \\((.*)\\)$");
			final java.util.regex.Matcher m1 = p1.matcher(lineField);
			if (m1.matches()) {
				final String id = m1.group(1);
				Line l = result.get(id);
				final String currentName = m1.group(2);
				if (l == null) {
					l = new Line(id, currentName, null);
				}
				l.setId(m1.group(1));
				if ((currentName != null) && (l.getName() != null)
						&& !currentName.equals(l.getName())) {
					l.setName(l.getName() + "/" + m1.group(2));
				} else {
					l.setName(m1.group(2));
				}
				l.setType(TransportType.getByValue(fields[2]));
				result.put(id, l);
			}
		}
		return result;
	}

	private void fillLinesStations(final String input)
			throws NumberFormatException, IOException {
		final BufferedReader reader = new BufferedReader(
				new StringReader(input));
		String line = "";

		while ((line = reader.readLine()) != null) {
			final String[] fields = line.split("#");
			final String stationId = fields[0];
			final String lineId = fields[1].split(" ")[0];
			final Line tLine = this.dataMap.getLines().get(lineId);
			final Station station = this.dataMap.getStations().get(
					new Integer(stationId));
			if ((station != null) && (tLine != null)) {
				station.getConnections().add(tLine);
				tLine.getStations().add(station);
			}
		}
	}

	private Station findNextLineStation(final Line line, final Station s) {
		double x = 90.0;
		double y = 90.0;
		Station result = null;
		if (s == null) {
			return null;
		}
		for (final Station current : line.getStations()) {
			final double newX = current.getPosition().getLongitude()
					- s.getPosition().getLongitude();
			final double newY = current.getPosition().getLatitude()
					- s.getPosition().getLatitude();
			if ((current != s) && this.notInReversePath(line, s, current)
					&& (((x * x) + (y * y)) > ((newX * newX) + (newY * newY)))) {
				x = newX;
				y = newY;
				result = current;
			}
		}
		return result;
	}

	private void guessLinesPaths() {
		for (final String lineName : this.dataMap.getLines().keySet()) {
			final Line line = this.dataMap.getLines().get(lineName);
			final String lineNewName = DestinationTransform.transform(line
					.getName());
			final String[] destinationsS = lineNewName.split("/");
			final List<Station> browse = new LinkedList<Station>();

			for (final String destination : destinationsS) {
				final Station s = this.dataMap.getStationsByName().get(
						destination.trim());
				if ((s != null) && !browse.contains(s)) {
					browse.add(s);
				}
			}
			boolean closedPath = false;
			while (!closedPath) {
				for (int i = 0; i < browse.size(); i++) {
					final Station s = browse.get(i);
					final Station nextStop = this.findNextLineStation(line, s);
					if (nextStop != null) {
						List<Station> nextStations = line.getPath().get(
								s.getId());
						List<Station> previousStations = line.getReversePath()
								.get(nextStop.getId());
						if (nextStations == null) {
							nextStations = new LinkedList<Station>();
							line.pathPut(s, nextStations);
						}
						if (previousStations == null) {
							previousStations = new LinkedList<Station>();
							line.reversePathPut(nextStop, previousStations);
						}
						if (!previousStations.contains(s)) {
							previousStations.add(s);
						}
						if (!nextStations.contains(nextStop)) {
							nextStations.add(nextStop);
							browse.set(i, nextStop);
						} else {
							browse.set(i, null);
						}
					} else {
						browse.set(i, null);
					}
				}
				closedPath = true;
				for (int i = 0; i < browse.size(); i++) {
					if (browse.get(i) != null) {
						closedPath = false;
					}
				}
			}
		}
	}

	private boolean notInReversePath(final Line line, final Station current,
			final Station test) {
		if (line.getReversePath().get(current.getId()) == null) {
			return true;
		}
		if (line.getReversePath().get(current.getId()).contains(test)) {
			return false;
		}
		boolean eachBranchTest = true;
		for (final Station s : line.getReversePath().get(current.getId())) {
			eachBranchTest &= this.notInReversePath(line, s, test);
		}
		return eachBranchTest;
	}

	@Override
	public void setDataMap(final DataMap dm) {
		this.dataMap = dm;
	}
}
