package com.cassandra.ex3.exA;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;

public class SearchUtilizador {
	public static void main(String args[]) {
        // https://docs.datastax.com/en/developer/java-driver/4.3/manual/core/

        try {
            // connect to cassandra server
            CqlSession session = CqlSession.builder()
            .withKeyspace("cbd_ex2")
            .build();


            // search utilizador
            ResultSet rs = session.execute("SELECT * FROM utilizadores");
            System.out.printf("\n%-16s %-25s %-10s\n", "username", "email", "nome");
            for (Row row : rs) {
                // process the row
                System.out.printf("%-16s %-25s %-10s\n", row.getString("username"), row.getString("email"), row.getString("nome"));
            }

            session.close();

        } catch (Exception e){
            System.err.println(e);
        }
    }
}