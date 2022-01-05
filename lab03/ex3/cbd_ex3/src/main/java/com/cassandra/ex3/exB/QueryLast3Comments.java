package com.cassandra.ex3.exB;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;

public class QueryLast3Comments {
    public static void main(String[] args) {
        
    try {
        // connect to cassandra server
        CqlSession session = CqlSession.builder()
        .withKeyspace("cbd_ex2")
        .build();

        System.out.println("Last 3 comments of a video");


        ResultSet rs = session.execute(SimpleStatement
        .builder("select * from videos_comentarios where id_video = 10 limit 3;")
        .build());

        System.out.printf("\n%-10s %-33s %-12s %-39s\n", "id_video", "comentario_data", "autor", "comentario");

            for (Row row: rs) {
                System.out.printf("%-10s %-33s %-12s %-39s\n", 
                row.getInt("id_video"), 
                row.getObject("comentario_data"),
                row.getString("autor"),
                row.getString("comentario")); 
            }

        session.close();

    } catch (Exception e) {
        System.err.println(e);
    }
}
}
