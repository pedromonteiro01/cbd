# source: https://neo4j.com/developer/python/

from neo4j import GraphDatabase

class Neo4jConnect:

    def __init__(self, uri, user, password):
        self.driver = GraphDatabase.driver(uri, auth=(user, password))

    def close(self):
        self.driver.close()


if __name__ == "__main__":
    try:
        bd = Neo4jConnect("bolt://localhost:7687", "neo4j", "password")
        print("successfully connected to database!")
        bd.close()
    except Exception as e:
        print("Something went wrong... ", e)