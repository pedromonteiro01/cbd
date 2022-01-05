package com.cassandra.ex3.exA;

import java.util.Scanner;
import java.util.UUID;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;
import com.datastax.oss.driver.api.core.uuid.Uuids;

public class InsertUtilizador {
    public static void main(String args[]) {
        // https://docs.datastax.com/en/developer/java-driver/4.3/manual/core/

        try {
            // connect to cassandra server
            CqlSession session = CqlSession.builder()
            .withKeyspace("cbd_ex2")
            .build();

            Scanner sc = new Scanner(System.in);

            UUID uuid = Uuids.timeBased();

            System.out.print("Username: ");
            String username = sc.nextLine();

            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("Email: ");
            String email = sc.nextLine();

            sc.close();

            System.out.println("Inserting utilizador");

            // insert utilizador
            session.execute(
                SimpleStatement.builder("insert into utilizadores (id, username, nome, email, data) values (?, ?, ?, ?, toTimestamp(now()))")
                .addPositionalValues(uuid, username, email, nome)
                .build()

            );

            session.close();

    } catch (Exception e){
        System.err.println(e);
    }
    }
    
}
