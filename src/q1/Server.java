package q1;

abstract class Server
{
	int id, index = 0, capacity;
	Video[] videos;

	Server(int id) {
		this.id = id;
	}

	Server(int id, int capacity) {
		this.id = id;
		this.capacity = capacity;
	}

	Server(Video[] videos) {
		this.videos = videos;
	}

	Server(int id, Video[] videos) {
		this.id = id;
		this.videos = videos;
	}

	private boolean addVideo(Video video) {
		if (this.index >= this.videos.length || !this.hasEnoughSpace(video.size)) {
			return false;
		}

		this.videos[index] = video;
		this.index++;
		return true;
	}

	private boolean hasEnoughSpace(int size) {
		int sum = 0;

		for (int i = 0; i <= index; i++) {
			sum += this.videos[i].size;
		}

		return sum + size > this.capacity;
	}
}
