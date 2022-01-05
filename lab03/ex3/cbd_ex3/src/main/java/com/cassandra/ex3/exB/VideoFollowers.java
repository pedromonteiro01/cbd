package com.cassandra.ex3.exB;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;

public class VideoFollowers {
    public static void main(String[] args) {
        
        try {
            // connect to cassandra server
            CqlSession session = CqlSession.builder()
            .withKeyspace("cbd_ex2")
            .build();

            System.out.println("Followers of one video");

    
            ResultSet rs = session.execute(SimpleStatement
            .builder("select * from followers where id_video = 13;")
            .build());
            
            System.out.printf("\n%-5s %20s", "id_video", "utilizadores\n");
            for (Row row: rs) {
                System.out.printf("%-15s %-20s\n",
                row.getInt("id_video"),
                row.getSet("utilizador", String.class));
            }
    
            session.close();
    
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
}
