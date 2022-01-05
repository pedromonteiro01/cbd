package com.cassandra.ex3.exB;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;

public class Last5EventsByUtilizador {
    public static void main(String[] args) {
        
        try {
            // connect to cassandra server
            CqlSession session = CqlSession.builder()
            .withKeyspace("cbd_ex2")
            .build();
            
            System.out.println("Last 5 events by utilizador");


            ResultSet rs = session.execute(SimpleStatement
            .builder("select tipo from eventos where id_video = 1 and utilizador = 'renatod' order by data desc limit 5;")
            .build());
            
            System.out.println("\nTipo");
            for (Row row: rs) {
                System.out.println(row.getString("tipo"));
            }
    
            session.close();
    
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
}
