package com.abdool;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {

    static int idTracker = 0;
    public static void main(String[] args) {
        Javalin app = Javalin.create();

        /*
            ROUTES TO IMPLEMENT:

            POST /scores => creates a new score...DONE

            GET /scores => Returns all scores...DONE
            GET /scores?initials={AAA} => Returns all scores by that player...DONE
            GET /scores/{id} => Returns the score with that id...DONE
              => Returns 404 if score with ID not found...

            PUT /scores/{id} => Replaces the score with that ID...DONE
              => Returns 404 if score with ID not found

            DELETE /scores/{id} => Deletes the score with that ID...DONE
              => Returns 404 if score with ID not found
        */

        //store all score objects in a HashMap
        Map<Integer, Score> scoreStore = new HashMap<>();

        app.get( "/scores", (ctx) -> {
            //create an empty list to store score object
            List<Score> scoreList = new ArrayList<>();

            for(Score score: scoreStore.values()) {
                scoreList.add(score);
            }

            //convert to json
            ctx.json(scoreList);
        });

        app.get("/scores/{id}", (ctx) -> {
            int scoreId = Integer.parseInt(ctx.formParam("id"));

            //get score from map
            Score targetScore = scoreStore.get(scoreId);

            if(targetScore == null) {
                ctx.status(HttpStatus.NOT_FOUND);
                ctx.result("No score of id of " + scoreId + " found.");
            } else {
                ctx.json(targetScore);
            }
        });

        app.get("/scores?initials={AAA}", (ctx) -> {
            String scoreInitials = ctx.queryParam("AAA");

            System.out.println(scoreInitials);

            List<Score> initialList = new ArrayList<>();
            for (Score score : scoreStore.values()) {
                if(score.getInitials().equals(scoreInitials)) {
                    initialList.add(score);
                }
            }

            //check to see if initials not found

            if(initialList == null) {
                ctx.status(HttpStatus.NOT_FOUND);
                ctx.result("No score with initials of " + scoreInitials + " found.");
            } else {
                ctx.json(initialList);
            }

        });


        app.post( "/scores", (ctx) -> {
            // first we need to access the content in the request body
            Score newScore = ctx.bodyAsClass(Score.class); //Unmarshalling: mapping json string to a Java Object

            // set id for newScore obj
            newScore.setId(++idTracker);

            scoreStore.put(newScore.getId(), newScore);
            ctx.status(HttpStatus.CREATED);
            ctx.json(newScore); //Marshalling: mapping Java Obj to JSON string
        });

        app.put("/scores/{id}", ctx -> {
            int scoreId = Integer.parseInt(ctx.pathParam("id"));

            Score updatedScore = ctx.bodyAsClass(Score.class);

            scoreStore.put(scoreId, updatedScore);
            updatedScore.setId(scoreId);
            ctx.json(updatedScore);
        });

        app.delete("/scores/{id}", ctx -> {
            int scoreId = Integer.parseInt(ctx.pathParam("id"));

            Score targetScore = scoreStore.get(scoreId);

            //Check to see if id exist before removing.
            if(targetScore == null){
                ctx.status(HttpStatus.NOT_FOUND);
                ctx.result("No score with id of " + scoreId + " found.");
            } else{
                scoreStore.remove(scoreId);
            }
        });


        app.start(8080); //Start the webserver on port 8080.
    }
}
