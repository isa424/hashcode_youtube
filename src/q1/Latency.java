package q1;

public class Latency
{
	int ping;
	Server server;

	Latency(Server server, int ping)
	{
		this.server = server;
		this.ping = ping;
	}
}
