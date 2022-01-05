package com.cassandra.ex3.exA;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;

public class SearchVideos {
	public static void main(String args[]) {
        // https://docs.datastax.com/en/developer/java-driver/4.3/manual/core/

        try {
            // connect to cassandra server
            CqlSession session = CqlSession.builder()
            .withKeyspace("cbd_ex2")
            .build();


            // search videos
            ResultSet rs = session.execute("SELECT * FROM videos");
            System.out.printf("\n%-8s %-33s %-12s %-39s %-28s %-5s\n", "id", "nome", "autor", "descricao", "data", "tags");
            for (Row row : rs) {
                // process the row
                System.out.printf("%-8s %-33s %-12s %-39s %-28s %-5s\n", 
                row.getInt("id"), 
                row.getString("nome"),
                row.getString("autor"),
                row.getString("descricao"),
                row.getObject("data"),
                row.getList("tags", String.class));
            }

            session.close();

        } catch (Exception e){
            System.err.println(e);
        }
    }
}