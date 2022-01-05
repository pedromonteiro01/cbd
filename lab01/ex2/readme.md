1 - Run the server in background: redis-server&
2 - Move names_counting.txt to src/ folder
3 - Upload the file to Redis: cat names_counting.txt | redis-cli --pipe