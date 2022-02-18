# dataset source: https://www.kaggle.com/jealousleopard/goodreadsbooks
# source: https://neo4j.com/developer/python/

from neo4j import GraphDatabase

class Ex4:
    def __init__(self, uri, user, password):
        self.driver = GraphDatabase.driver(uri, auth=(user, password))
        
    def close(self):
        self.driver.close()
        
    def drop_constraints(self):
        with self.driver.session() as session:
            session.run("DROP CONSTRAINT ON (book:Book) ASSERT book.bookID IS UNIQUE")
    
    def add_constraints(self):
        with self.driver.session() as session:
            session.run("CREATE CONSTRAINT ON (book:Book) ASSERT book.bookID IS UNIQUE")
    
    def insert_data(self):
        with self.driver.session() as session:
            session.run("""
                LOAD CSV WITH HEADERS
                FROM "file:///books.csv" AS Row
                MERGE (book:Book {bookID: Row.bookID})
                SET book.title=Row.title, book.average_rating=Row.average_rating, book.isbn=Row.isbn, book.isbn13=Row.isbn13,
                book.ratings_count=Row.ratings_count, book.num_pages=Row.num_pages, 
                book.text_reviews_count=Row.text_reviews_count
                MERGE (publisher:Publisher {publisher_name: Row.publisher_name})
                MERGE (author:Author {authors_name:Row.authors_name})
                MERGE (bookLanguage:BookLanguage {language_code:Row.language_code})                        
            """)
    
    def insert_rels(self):
        with self.driver.session() as session:
            session.run("""
                LOAD CSV WITH HEADERS
                FROM "file:///books.csv" AS Row
                MATCH (publisher:Publisher {publisher_name:Row.publisher_name}), (book:Book {bookID: Row.bookID})
                CREATE (publisher)-[:PUBLISHED {publication_date: Row.publication_date}]->(book)
            """)
            session.run("""
                LOAD CSV WITH HEADERS
                FROM "file:///books.csv" AS Row
                MATCH (author:Author {authors_name: Row.authors_name}), (book:Book {bookID:Row.bookID})
                CREATE (author)-[:WROTE]->(book)
            """)
            session.run("""
                LOAD CSV WITH HEADERS
                FROM "file:///books.csv" AS Row
                MATCH (book:Book {bookID: Row.bookID}), (bookLanguage:BookLanguage {language_code:Row.language_code})
                CREATE (book)-[:WROTE_IN]->(bookLanguage)
            """)
            
    def query(self, query):
        f = open("CBD_L44c_output.txt", "a")
        res = self.driver.session().run(query)
        f.write("\nQuery: {}\n".format(query))
        
        results=[r for r in res.data()]
        
        for r in results:
            f.write(f"\t{r}\n")
        
        
if __name__ == "__main__":
    try:
        bd = Ex4("bolt://localhost:7687", "neo4j", "password")
        bd.drop_constraints()
        bd.add_constraints()
        bd.insert_data()
        
        # query 1
        q1 = ("match (b:Book) return b.title as title,b.num_pages as num_pages;")
        res1 = bd.query(q1)
        
        # query 2
        q2 = ("match (b:Book) return b.title as book_title order by b.average_rating desc limit 1;")
        res2 = bd.query(q2)
        
        # query 3
        q3 = ("match (a:Author)-[:WROTE]-(b:Book) where b.isbn contains '007' return a.authors_name, b.isbn;")
        res3 = bd.query(q3)
        
        # query 4
        q4 = ("match (p:Publisher)-[:PUBLISHED]-(b:Book) - [:WROTE_IN] - (bl:BookLanguage) where p.publisher_name = 'Wiley' and bl.language_code = 'eng' return b.title as title, bl.language_code as language_code, b.num_pages as num_pages;\n")
        res4 = bd.query(q4)
        
        # query 5
        q5 = ("match (a:Author)-[:WROTE]-(b:Book)-[r:PUBLISHED]-(p:Publisher) where a.authors_name='J.K. Rowling/Mary GrandPré' and p.publisher_name='Scholastic Inc.'and r.publication_date='9/1/2004' return a.authors_name as author_name,b as book,p.publisher_name as publisher_name,r.publication_date as date;\n")
        res5 = bd.query(q5)
        
        # query 6
        q6 = ("match (b:Book) where toInteger(b.ratings_count) > 0 return b as book, b.ratings_count as ratings_count, b.average_rating as average_rating order by b.ratings_count asc, b.average_rating desc limit 10;\n")
        res6 = bd.query(q6)
        
        # query 7
        q7 = ("match(a:Author)-[:WROTE]-(b:Book) with a,count(b) as num_books where num_books > 3 return a.authors_name as author_name, num_books;\n")
        res7 = bd.query(q7)
        
        # query 8
        q8 = ("match (p:Publisher)-[r:PUBLISHED]-(b:Book) with r.publication_date as publication_date, count(b) as num_books return publication_date, num_books order by num_books desc limit 1;\n")
        res8 = bd.query(q8)
        
        # query 9
        q9 = ("match (p:Publisher)-[:PUBLISHED]-(b:Book) return p.publisher_name as publisher_name,count(b) as num_books order by num_books desc;\n")
        res9 = bd.query(q9)
        
        # query 10
        q10 = ("match (a:Author)-[:WROTE]-(b:Book) where not(a.authors_name contains '/') return a.authors_name as author, b.title as title;")
        res10 = bd.query(q10)
        
        #print("successfully connected to database!")
        bd.close()
    except Exception as e:
        print("Something went wrong... ", e)