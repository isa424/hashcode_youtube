package q1;

import java.util.ArrayList;

public class Endpoint {
	int id;
	ArrayList<RequestDescription> requestDescriptions = new ArrayList<RequestDescription>();
	Latency[] latencies;

	Endpoint(int id) {
		this.id = id;
	}

	public void addRequestDescription(RequestDescription requestDescriptions) {
		this.requestDescriptions.add(requestDescriptions);
	}

	public void setLatencies(Latency[] latencies) {
		this.latencies = latencies;
	}
}
