package q1;

import java.io.File;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Arrays;

public class Main
{
	/**
	 * @param args Arguments list
	 */
    public static void main(String[] args)
    {
    	try {
    		File file = new File("src/input.txt");
		    Scanner scanner = new Scanner(file);

		    // Declarations
		    Video[] videos = new Video[scanner.nextInt()];
		    DataCenter dataCenter = new DataCenter(videos);
		    Endpoint[] endpoints = new Endpoint[scanner.nextInt()];
		    int requestDescriptionsCount = scanner.nextInt();
		    CacheServer[] cacheServers = new CacheServer[scanner.nextInt()];

		    // Fill cache server
		    int cacheCapacity = scanner.nextInt();
		    for (int i = 0; i < cacheServers.length; i++) {
		    	cacheServers[i] = new CacheServer(i, cacheCapacity);
		    }

		    // Fill videos
		    for (int i = 0; i < videos.length; i++) {
		    	videos[i] = new Video(i, scanner.nextInt());
		    }

		    // Fill endpoints
		    for (int i = 0; i < endpoints.length; i++) {
		        endpoints[i] = new Endpoint(i);

		        Latency dataCenterLatency = new Latency(dataCenter, scanner.nextInt());
			    int connectedCacheServerCount = scanner.nextInt();

			    Latency[] serverLatencies = new Latency[connectedCacheServerCount + 1];
			    serverLatencies[0] = dataCenterLatency;

			    for (int j = 0; j < connectedCacheServerCount; j++) {
			    	serverLatencies[j + 1] = new Latency(cacheServers[scanner.nextInt()], scanner.nextInt());
			    }

			    endpoints[i].setLatencies(serverLatencies);
		    }

		    // Request Descriptions
		    for (int i = 0; i < requestDescriptionsCount; i++) {
		    	Video video = videos[scanner.nextInt()];
		    	Request request = new Request(video);
		    	Endpoint endpoint = endpoints[scanner.nextInt()];
		    	endpoint.addRequestDescription(new RequestDescription(request, scanner.nextInt()));
		    }

		    // Sort endpoints by their latency to data center
		    Arrays.sort(endpoints, Comparator.comparing((Endpoint endpoint) -> endpoint.latencies[0].ping));

		    // Calculate video locations
		    calculateRequestDescs(endpoints);

		    for (Endpoint endpoint : endpoints) {
		    	if (endpoint.latencies[1] == null) {
		    		continue;
			    }

			    int lowestPingIndex = getLowestPing(endpoint.latencies);
		    	if (lowestPingIndex == 0) {
		    		continue;
			    }

			    for (RequestDescription requestDescription : endpoint.requestDescriptions) {
					Server cacheServer = endpoint.latencies[lowestPingIndex].server;
			    }
		    }

		    printData(videos, endpoints, requestDescriptionsCount, cacheServers);
	    } catch (Exception e) {
    		System.out.println(e.getMessage());
    		System.exit(0);
	    }
    }

	public static void calculateRequestDescs(Endpoint[] endpoints) {
		for (Endpoint endpoint : endpoints) {
			for (RequestDescription requestDescription : endpoint.requestDescriptions) {
				int lowestLatencyIndex = getLowestPing(endpoint.latencies);
				int lowestLatency = endpoint.latencies[lowestLatencyIndex].ping;
				requestDescription.timePerSecond = (endpoint.latencies[0].ping - lowestLatency) * requestDescription.count;
			}

			endpoint.requestDescriptions.sort(
					Comparator.comparing(
							(RequestDescription requestDescription) -> requestDescription.timePerSecond
					).reversed()
			);
		}
	}

	/**
	 * @param videos All videos array
	 * @param endpoints All endpoints array
	 * @param requestDescriptionsCount Number of request descriptions
	 * @param cacheServers All cache servers array
	 */
	private static void printData(Video[] videos, Endpoint[] endpoints, int requestDescriptionsCount, CacheServer[] cacheServers)
	{
		// Counts
		System.out.println(videos.length + " " + endpoints.length + " " + requestDescriptionsCount + " " +
				cacheServers.length + " " + cacheServers[0].capacity);

		// Videos
		for (Video video : videos) {
			System.out.print(video.size + " ");
		}
		System.out.print("\n");

		// Endpoints latencies
		for (Endpoint endpoint : endpoints) {
			System.out.println(endpoint.latencies[0].ping + " " + (endpoint.latencies.length - 1));

			for (int i = 1; i < endpoint.latencies.length; i++) {
					System.out.println(endpoint.latencies[i].server.id + " " + endpoint.latencies[i].ping);
			}
		}

		// Request Descriptions
		for (Endpoint endpoint : endpoints) {
			for (RequestDescription requestDescription : endpoint.requestDescriptions) {
				System.out.println(requestDescription.request.video.id + " " + endpoint.id + " " + requestDescription.count);
			}
		}
	}

	private static int getLowestPing(Latency[] latencies) {
		int index = 0;

		for (int i = 1; i < latencies.length; i++) {
			if (latencies[index].ping > latencies[i].ping) {
				index = i;
			}
		}

		return index;
	};

	private static void writeStats(CacheServer[] cacheServers) {
		int numOfServerWithVideos = 0;

		for (CacheServer cacheServer : cacheServers) {
			numOfServerWithVideos += cacheServer.index + 1;
		}
	}
}
