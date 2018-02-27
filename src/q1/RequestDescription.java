package q1;

public class RequestDescription
{
	Request request;
	int count, timePerSecond = 0;

	RequestDescription(Request request, int count)
	{
		this.request = request;
		this.count = count;
	}
}
