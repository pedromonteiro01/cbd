package com.cassandra.ex3.exB;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;

public class VideoByTimeAndUtilizador {
    public static void main(String[] args) {
        
        try {
            // connect to cassandra server
            CqlSession session = CqlSession.builder()
            .withKeyspace("cbd_ex2")
            .build();
            
            System.out.println("Video by autor and by date");


            ResultSet rs = session.execute(SimpleStatement
            .builder("select * from videos where autor = 'pedrom' and data = '2021-12-10 20:46:04.459000+0000';")
            .build());
            
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
    
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
}
